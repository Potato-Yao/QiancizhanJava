package com.potato.Parser;

import com.potato.ToolKit.WordFileType;

import java.io.File;

/**
 * JSONParser是JSON类型单词本的解析器
 */
public class JSONParser extends Parser
{
    /**
     * JSONParser用于解析储存单词本的JSON文件
     * 即后缀名是json的文件
     * 该方法还没有实现，不要用
     *
     * @param file 需要解析的JSON文件
     */
    public JSONParser(File file)
    {
        super(file, WordFileType.JSON.type());
    }

    /**
     * 解析，将this.file解析为一个储存Word的List，将这个List赋给this.wordList
     */
    @Override
    protected void parser()
    {

    }
}
