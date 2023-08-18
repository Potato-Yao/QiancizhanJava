package com.potato.Parser;

import com.potato.ToolKit.WordFileType;

import java.io.File;

/**
 * ExcelParser是excel类型单词本的解析器
 */
public class ExcelParser extends Parser
{
    /**
     * ExcelParser用于解析储存单词本的excel文件
     * 即后缀名是xlsx的文件
     * 该方法还没有实现，不要用
     *
     * @param file      需要解析的文件
     */
    public ExcelParser(File file)
    {
        super(file, WordFileType.EXCEL.type());
    }

    /**
     * 解析，将this.file解析为一个储存Word的List，将这个List赋给this.wordList
     */
    @Override
    protected void parser()
    {

    }
}
