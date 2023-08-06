package com.potato.Parser;

import com.potato.ToolKit.FileToolKit;

import java.io.File;

import static com.potato.ToolKit.FileToolKit.*;

public class AutoParser extends Parser
{
    /**
     * AutoParser用于根据文件类型生成对应的解析储存单词本文件的Parser
     * 如对于db，生成DatabaseParser
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
        Parser parser = null;
        // 根据不同文件类型选取不同的解析器
        if (getExtensionName(getFile()).equals("db"))  // 数据库使用DatabaseParser
        {
            parser = new DatabaseParser(getFile());
        }
        assert parser != null;
        setWordList(parser.getWordList());
        setInfo(parser.getInfo());
        setHistoryList(parser.getHistoryList());
    }
}
