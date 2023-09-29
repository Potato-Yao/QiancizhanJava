package com.potato.Parser;

import com.potato.Config;
import com.potato.Log.Log;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.WordFileType;
import com.potato.Word.Word;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.potato.ToolKit.FileToolKit.*;

/**
 * AutoParser用于根据单词本文件的文件类型匹配其对应的Parser并解析单词本文件
 */
public class AutoParser extends Parser
{
    /**
     * AutoParser用于根据单词本文件的文件类型匹配其对应的{@link Parser}并解析单词本文件
     * <p> 文件类型对应的解析器在{@link Config}中{@code setParser}方法配置
     * <p> 如对于db，默认给出{@link DatabaseParser}
     *
     * @param file 需要解析的单词本文件
     */
    public AutoParser(File file)
    {
        super(file, FileToolKit.getExtensionName(file));
    }

    /**
     * 解析单词本文件
     * <p> 将this.file解析为一个储存{@link Word}的List，将这个List赋给this.wordList
     * <p> 如果在解析前有需要初始化的对象，将初始化写在构造器中
     */
    @Override
    protected void parser()
    {
        // 获取对应类型的解析器
        Constructor<? extends Parser> parserConstructor = Config
                .getParserConstructor(getExtensionName(getFile()));
        Parser parser;
        try
        {
            parser = parserConstructor.newInstance(getFile());
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            Log.e(getClass().toString(), String.format("获取解析器%s的实例失败", parserConstructor), e);
            throw new RuntimeException(e);
        }

        setWordList(parser.getWordList());
        setInfo(parser.getInfo());
        setHistoryList(parser.getHistoryList());

        Log.i(getClass().toString(), String.format("解析器为%s，已完成解析", parser.getClass()));
    }
}
