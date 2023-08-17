package com.potato.Parser;

import com.potato.Config;
import com.potato.Log.Log;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.WordFileType;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Constructor;

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
    @SneakyThrows
    @Override
    protected void parser()
    {
        Parser parser = null;

        // 根据不同文件类型选取不同的解析器
        if (getExtensionName(getFile()).equals(WordFileType.DATABASE.type()))  // 数据库使用DatabaseParser
        {
            Constructor<Parser> alternativeParserConstructor =
                Config.alternativeParser.get(WordFileType.DATABASE);

            // 如果替换解析器是null，则使用默认解析器
            if (alternativeParserConstructor == null)
            {
                parser = new DatabaseParser(getFile());
            }
            else
            {
                // 否则使用替换的解析器
                parser = alternativeParserConstructor.newInstance(getFile());
            }
        }

        assert parser != null;
        setWordList(parser.getWordList());
        setInfo(parser.getInfo());
        setHistoryList(parser.getHistoryList());

        Log.i(getClass().toString(), String.format("解析器为%s，已完成解析", parser.getClass()));
    }
}
