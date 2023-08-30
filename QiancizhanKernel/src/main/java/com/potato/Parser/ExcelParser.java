package com.potato.Parser;

import com.potato.ToolKit.WordFileType;
import com.potato.Word.Word;

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
     * 解析单词本文件
     * <p> 将this.file解析为一个储存{@link Word}的List，将这个List赋给this.wordList
     * <p> 如果在解析前有需要初始化的对象，将初始化写在构造器中
     */
    @Override
    protected void parser()
    {

    }
}
