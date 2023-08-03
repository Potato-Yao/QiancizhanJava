package com.potato;

import com.alibaba.fastjson2.JSONObject;
import com.potato.ToolKit.DatabaseType;
import com.potato.ToolKit.FileToolKit;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 读取和写入配置文件的类
 */
@Data
public class Config
{
    @Option(keyName = "language", type = OptionType.ADVANCE, meaning = "去向语言")
    public static String language;  // 默认语言，用于配置UI默认语言和翻译默认去向语言

    @Option(keyName = "stand_wordlist_path", type = OptionType.ADVANCE, meaning = "长期单词本路径")
    public static String standWordListPath;  // 长期单词库储存文件夹

    @Option(keyName = "normal_wordlist_path", type = OptionType.ADVANCE, meaning = "一般单词本路径")
    public static String normalWordListPath;  // 一般单词库储存文件夹

    @Option(keyName = "output_file_name", type = OptionType.ADVANCE, meaning = "输出文件名称")
    public static String outputFileName;  // 输出文件的名称

    @Option(keyName = "output_file_path", type = OptionType.ADVANCE, meaning = "输出文件路径")
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
    public static String version3;  // 版本代码1，代表安卓界面版本

    private static JSONObject jsonObject;
    private static File configFile;

    /**
     * 初始化
     */
    @SneakyThrows
    public static void initial()
    {
        configFile = new File(".", "config.json");  // 获取配置文件
        String configString = FileToolKit.fileToString(configFile);
        jsonObject = JSONObject.parse(configString);

        Field[] fields = Config.class.getDeclaredFields();  // 获取Config的所有变量
        for (Field field : fields)
        {
            Option annotation = field.getAnnotation(Option.class);  // 获取每个变量的Option注解
            // 观察可知，有几个并不表示配置选项的变量，它们没有Option注解，因此对应的annotation是null
            if (annotation != null)
            {
                field.setAccessible(true);
                field.set(null, jsonObject.getString(annotation.keyName()));  // 将其设置为配置文件中的对应值
            }
        }
    }

    /**
     * 获取指定类型的配置选项
     * @param type 配置选项的类型
     * @return 配置选项，key是选项在config.json中对应的key，value是选项的含义（即在GUI中显示的文本）
     */
    public static HashMap<String, String> getOptions(OptionType type)
    {
        HashMap<String, String> options = new HashMap<>();
        Field[] fields = Config.class.getDeclaredFields();  // 获取Config的所有变量

        for (Field field : fields)
        {
            Option annotation = field.getAnnotation(Option.class);  // 获取每个变量的Option注解
            // 观察可知，有几个并不表示配置选项的变量，它们没有Option注解，因此对应的annotation是null
            // 若有注解并且type是normal，那么就是一般配置；反之若是type是advance就是高级配置
            if (annotation != null && annotation.type() == type)
            {
                options.put(annotation.keyName(), annotation.meaning());
            }
        }

        return options;
    }

    /**
     * 获取databaseType变量，Config中用String储存该变量
     * @return databaseType
     */
    public static DatabaseType getDatabaseType()
    {
        return DatabaseType.valueOf(databaseType);
    }

    /**
     * 获取标准单词本文件
     * @return 标准单词本文件
     */
    public static File getStandingWordListFile()
    {
        return new File(standWordListPath, "Word.db");
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

    /*
    * 配置的类型
    */
    OptionType type() default OptionType.NORMAL;

    /* 配置的含义，这个项用在设置GUI上 */
    String meaning();
}
