package com.potato.Parser;

import com.potato.Config;
import com.potato.Log.Log;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.WordFileType;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static com.potato.ToolKit.FileToolKit.*;

/**
 * AutoParser用于自动根据传入单词本文件类型选择对应的解析器解析单词本
 */
public class AutoParser extends Parser
{
    /**
     * AutoParser用于根据文件类型生成对应的解析储存单词本文件的Parser
     * 如对于db，生成DatabaseParser
     *
     * @param file 需要解析的文件
     */
    public AutoParser(File file)
    {
        super(file, FileToolKit.getExtensionName(file));
    }

    /**
     * 解析，将this.file解析为一个储存Word的List，将这个List赋给this.wordList
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
