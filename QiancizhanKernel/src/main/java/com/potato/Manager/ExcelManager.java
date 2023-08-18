package com.potato.Manager;

import com.potato.ToolKit.WordFileType;

import java.io.File;

/**
 * ExcelManager用于管理excel类型的单词本文件
 */
public class ExcelManager extends Manager
{
    /**
     * ExcelManager用于对excel单词本文件进行增、删、改的操作
     *
     * @param file 需要管理的单词本文件
     */
    public ExcelManager(File file)
    {
        super(file, WordFileType.EXCEL.type());
    }

    /**
     * 将所有修改写入this.file
     */
    @Override
    public void push()
    {

    }
}
