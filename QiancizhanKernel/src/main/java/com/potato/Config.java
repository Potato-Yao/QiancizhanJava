package com.potato;

import com.alibaba.fastjson2.JSONObject;
import com.potato.Log.ConsoleLogger;
import com.potato.Log.Log;
import com.potato.Log.Logger;
import com.potato.Manager.DatabaseManager;
import com.potato.Manager.ExcelManager;
import com.potato.Manager.JSONManager;
import com.potato.Manager.Manager;
import com.potato.Parser.DatabaseParser;
import com.potato.Parser.ExcelParser;
import com.potato.Parser.JSONParser;
import com.potato.Parser.Parser;
import com.potato.ToolKit.DatabaseType;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.WordFileType;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Config是用于读取和写入配置文件的类
 */
@Data
public class Config
{
    @Option(keyName = "language", type = OptionType.ADVANCE, meaning = "去向语言")
    public static String language;  // 默认语言，用于配置UI默认语言和翻译默认去向语言

    @Option(keyName = "stand_wordlist_path", type = OptionType.DIR, meaning = "长期单词本路径")
    public static String standWordListPath;  // 长期单词库储存文件夹

    @Option(keyName = "normal_wordlist_path", type = OptionType.DIR, meaning = "一般单词本路径")
    public static String normalWordListPath;  // 一般单词库储存文件夹

    @Option(keyName = "output_file_name", type = OptionType.ADVANCE, meaning = "输出文件名称")
    public static String outputFileName;  // 输出文件的名称

    @Option(keyName = "output_file_path", type = OptionType.DIR, meaning = "输出文件路径")
    public static String outputFilePath; //  输出文件的路径

    @Option(keyName = "baidu_app_id", type = OptionType.ADVANCE, meaning = "百度翻译ID")
    public static String baiduAppId;  // 百度翻译API的ID

    @Option(keyName = "baidu_app_key", type = OptionType.ADVANCE, meaning = "百度翻译KEY")
    public static String baiduAppKey;  // 百度翻译API的KEY

    @Option(keyName = "ocr_app_id", type = OptionType.ADVANCE, meaning = "百度OCR的ID")
    public static String ocrAppId; //  百度ocrAPI的ID

    @Option(keyName = "ocr_api_key", type = OptionType.ADVANCE, meaning = "百度OCR的KEY")
    public static String ocrAppKey; //  百度ocrAPI的Key

    @Option(keyName = "ocr_secret_key", type = OptionType.ADVANCE, meaning = "百度OCR的SecretKey")
    public static String ocrSecretKey; //  百度ocrAPI的SecretKey

    @Option(keyName = "database_type", type = OptionType.ADVANCE, meaning = "数据库类型")
    public static String databaseType;  // 数据库类型

    @Option(keyName = "author", type = OptionType.NORMAL, meaning = "PDF作者")
    public static String author;  // PDF输出作者

    @Option(keyName = "title", type = OptionType.NORMAL, meaning = "PDF标题")
    public static String title;  // PDF输出标题

    @Option(keyName = "version1", type = OptionType.ADVANCE, meaning = "内核版本")
    public static String version1;  // 版本代码1，代表内核版本

    @Option(keyName = "version2", type = OptionType.ADVANCE, meaning = "桌面界面版本")
    public static String version2;  // 版本代码2，代表桌面界面版本

    @Option(keyName = "version3", type = OptionType.ADVANCE, meaning = "安卓界面版本")
    public static String version3;  // 版本代码3，代表安卓界面版本

    private static JSONObject jsonObject;
    private static File configFile;
    private static BufferedWriter writer;
    private static Field[] fields;  // Config的所有变量

    /* 配置每个文件类对应的解析器 */
    public static Map<WordFileType, Constructor<? extends Parser>> parserMap;

    /* 配置每个文件类对应的管理器 */
    public static Map<WordFileType, Constructor<? extends Manager>> managerMap;

    /**
     * 初始化
     * 从config.json中读取配置信息
     *
     * @param configFile 配置文件
     * @param normalDir  一般单词本目录，如果为null则使用配置文件中的设置
     * @param standDir   长期单词本目录，如果为null则使用配置文件中的设置
     * @param outputDir  输出文件目录，如果为null则使用配置文件中的设置
     * @param logger     日志输出器，如果为null则使用终端输出器
     */
    public static void initial(File configFile, File normalDir, File standDir, File outputDir, Logger logger)
    {
        Log.setLogger(Objects.requireNonNullElseGet(logger, ConsoleLogger::new));

        writeInitial(configFile, normalDir, standDir, outputDir);  // 假如不存在配置文件，那么就创建一个配置文件

        Config.configFile = configFile;
        String configString = FileToolKit.fileToString(Config.configFile);  // fastjson没有直接解析文件的方法，所以先转成字符串
        jsonObject = JSONObject.parse(configString);
        fields = Config.class.getDeclaredFields();  // 获取Config的所有变量

        runner(new ConfigAction()
        {
            @SneakyThrows
            @Override
            public void interAction(Field field, Option option)
            {
                field.setAccessible(true);  // 将变量设为可访问的
                field.set(null, jsonObject.getString(option.keyName()));  // 将变量设置为配置文件中的对应值
            }
        });

        parserMap = new HashMap<>();
        managerMap = new HashMap<>();
        // 配置默认解析器和管理器
        try
        {
            parserMap.put(WordFileType.DATABASE, DatabaseParser.class.getConstructor(File.class));
            parserMap.put(WordFileType.JSON, JSONParser.class.getConstructor(File.class));
            parserMap.put(WordFileType.EXCEL, ExcelParser.class.getConstructor(File.class));

            managerMap.put(WordFileType.DATABASE, DatabaseManager.class.getConstructor(File.class));
            managerMap.put(WordFileType.JSON, JSONManager.class.getConstructor(File.class));
            managerMap.put(WordFileType.EXCEL, ExcelManager.class.getConstructor(File.class));
        }
        catch (NoSuchMethodException e)
        {
            Log.e(Config.class.toString(), "未找到对应解析器或管理器", e);
            throw new RuntimeException(e);
        }

        Log.i(Config.class.toString(), "已完成初始化");
    }

    /**
     * 获取指定类型的配置
     *
     * @param type 配置类型
     * @return 所有指定类型的配置
     */
    public static HashMap<String, String> getOptions(OptionType type)
    {
        HashMap<String, String> options = new HashMap<>();

        runner(new ConfigAction()
        {
            @SneakyThrows
            @Override
            public void interAction(Field field, Option option)
            {
                options.put(option.meaning(), field.get(field).toString());  // 将配置的汉义和值加入哈希表
            }

            @Override
            public boolean condition(Field field, Option option)
            {
                return option.type() == type;  // 匹配类型一致的变量
            }
        });

        return options;
    }

    /**
     * 将所有与配置相关的变量写入配置文件
     */
    public static void write()
    {
        jsonObject = new JSONObject();
        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(configFile, false), StandardCharsets.UTF_8));
        }
        catch (FileNotFoundException e)
        {
            Log.e(Config.class.toString(), "未找到配置文件", e);
            throw new RuntimeException(e);
        }

        runner(new ConfigAction()
        {
            @SneakyThrows
            @Override
            public void interAction(Field field, Option option)
            {
                jsonObject.put(option.keyName(), field.get(field));  // config.json的键是keyName，值就是变量的值
            }

            @Override
            public void outerAction()
            {
                try
                {
                    writer.write(jsonObject.toJSONString());
                    writer.close();
                }
                catch (Exception e)
                {
                    Log.e(Config.class.toString(), "写入配置文件失败", e);
                }
            }
        });

        Log.v(Config.class.toString(), "已完成写入配置文件");
    }

    /**
     * TODO 应当一般化
     * 根据配置的汉义给配置变量赋值
     *
     * @param meaning 需要修改的配置变量的汉义
     * @param value   赋的值
     */
    public static void update(String meaning, String value)
    {
        runner(new ConfigAction()
        {
            @SneakyThrows
            @Override
            public void interAction(Field field, Option option)
            {
                field.set(null, value);  // 将值赋给变量
            }

            @Override
            public boolean condition(Field field, Option option)
            {
                return option.meaning().equals(meaning);  // 筛选条件是变量的汉义匹配
            }
        });

        Log.v(Config.class.toString(), "已更新配置变量值");
    }

    /**
     * runner用于遍历Config下的每一个与配置相关的变量并执行指定的动作
     *
     * @param action 需要执行的动作
     */
    private static void runner(ConfigAction action)
    {
        for (Field field : fields)
        {
            Option annotation = field.getAnnotation(Option.class);  // 获取每个变量的Option注解
            // 观察可知，有几个并不表示配置选项的变量，它们没有Option注解，因此对应的annotation是null
            // 若有注解并且type是OptionType.NORMAL，那么就是一般配置；反之若是type是OptionType.ADVANCE就是高级配置
            if (annotation != null && action.condition(field, annotation))  // 第一个条件是筛选配置变量，第二个条件是自定义的
            {
                action.interAction(field, annotation);  // 执行内层动作
            }
        }
        action.outerAction();  // 执行外层动作
    }

    /**
     * 写入初始化内容
     * TODO  此方法应当删掉，即给所有变量初值，初始化的时候直接write()就可以了
     *
     * @param configFile 配置文件
     * @param normalDir  一般单词本目录，如果为null则使用配置文件中的设置
     * @param standDir   长期单词本目录，如果为null则使用配置文件中的设置
     * @param outputDir  输出文件目录，如果为null则使用配置文件中的设置
     */
    @SneakyThrows
    private static void writeInitial(File configFile, File normalDir, File standDir, File outputDir)
    {
        if (configFile.createNewFile())
        {
            Log.v(Config.class.toString(), "配置文件创建成功");

            Config.configFile = configFile;
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(configFile, false), StandardCharsets.UTF_8));

            String text = """
                {
                    "language": "en",
                    "stand_wordlist_path": "",
                    "normal_wordlist_path": "",
                    "output_file_name": "OutputFile",
                    "output_file_path": "",
                    "baidu_app_id": "",
                    "baidu_app_key": "",
                    "ocr_app_id": "",
                    "ocr_api_key": "",
                    "ocr_secret_key": "",
                    "database_type": "SQLite",
                    "author": "千词斩",
                    "title": "英语单词单",
                    "version1": "1.0.3b",
                    "version2": "1.0.1b",
                    "version3": "InDev"
                }
                """;

            writer.write(text);
            writer.close();
            Log.v(Config.class.toString(), "配置文件写入成功");
        }
        else
        {
            Log.v(Config.class.toString(), "已检测到配置文件");
        }

        if (normalDir == null || standDir == null || outputDir == null)
        {
            Log.v(Config.class.toString(), "相关文件夹使用配置文件中的配置");
        }
        else
        {
            if (normalDir.mkdir() && standDir.mkdir() && outputDir.mkdir())
            {
                Log.v(Config.class.toString(), "相关文件夹创建成功");
            }
            else
            {
                Log.v(Config.class.toString(), "已检测到相关文件夹");
            }
        }

        Log.i(Config.class.toString(), "初始化配置文件和相关文件夹完成");
    }

    /**
     * 根据文件类型（即扩展名）获取对应的解析器构造器
     *
     * @param type 文件类型
     * @return 对应的解析器的构造器
     */
    public static Constructor<? extends Parser> getParserConstructor(String type)
    {
        return parserMap.get(WordFileType.getWordFileType(type));
    }

    /**
     * 根据文件类型（即扩展名）获取对应的管理器构造器
     *
     * @param type 文件类型
     * @return 对应的管理器的构造器
     */
    public static Constructor<? extends Manager> getManagerConstructor(String type)
    {
        return managerMap.get(WordFileType.getWordFileType(type));
    }

    /**
     * 设置替换的解析器
     *
     * @param type   解析器对应的文件类型
     * @param parser 解析器
     */
    public static void setParser(WordFileType type, Constructor<? extends Parser> parser)
    {
        parserMap.replace(type, parser);
    }

    /**
     * 设置替换的管理器
     *
     * @param type    管理器对应的文件类型
     * @param manager 管理器
     */
    public static void setManager(WordFileType type, Constructor<? extends Manager> manager)
    {
        managerMap.replace(type, manager);
    }

    /**
     * 获取databaseType变量，Config中用String储存该变量
     *
     * @return databaseType
     */
    public static DatabaseType getDatabaseType()
    {
        return DatabaseType.valueOf(databaseType);
    }

    /**
     * 获取标准单词本文件
     *
     * @return 标准单词本文件
     */
    public static File getStandingWordListFile()
    {
        return new File(standWordListPath, "Word.db");
    }
}

/**
 * runner需要执行的动作
 */
abstract class ConfigAction
{
    /**
     * 循环内层的动作，即每一个变量都要执行的动作
     * 很显然这个方法是必须要实现的，否则使用runner没有意义
     *
     * @param field  变量
     * @param option 变量对应的注解
     */
    abstract void interAction(Field field, Option option);

    /**
     * 循环外层的动作，这是函数最后执行的动作
     */
    void outerAction()
    {
    }

    /**
     * 循环内层的筛选条件，用其来筛选满足条件的变量
     *
     * @param field  变量
     * @param option 变量对应的注解
     */
    boolean condition(Field field, Option option)
    {
        return true;
    }
}

/**
 * 配置文件变量的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Option
{
    /* 配置的名称，此量应当与config.json中的key一致 */
    String keyName();

    /* 配置的类型 */
    OptionType type() default OptionType.NORMAL;

    /* 配置的含义，这个项用在设置GUI上 */
    String meaning();
}
