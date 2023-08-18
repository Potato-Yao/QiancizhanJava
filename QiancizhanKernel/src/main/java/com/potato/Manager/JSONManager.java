package com.potato.Manager;

import com.potato.ToolKit.WordFileType;

import java.io.File;

/**
 * JSONManager用于管理JSON类型的单词本文件
 */
public class JSONManager extends Manager
{
    /**
     * JSONManager用于对json单词本文件进行增、删、改的操作
     *
     * @param file 需要管理的单词本文件
     */
    public JSONManager(File file)
    {
        super(file, WordFileType.JSON.type());
    }

    /**
     * 将所有修改写入this.file
     */
    @Override
    public void push()
    {

    }
}
