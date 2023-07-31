package com.potato.Manager;

import com.potato.ToolKit.FileToolKit;
import com.potato.Word.Word;

import java.io.File;

public class AutoManager extends Manager
{
    Manager manager;

    /**
     * AutoManager用于对单词本文件进行增、删、改的操作
     * AutoManager可以自动识别文件类型并给出正确的管理器
     * @param file      需要管理的单词本文件
     */
    public AutoManager(File file)
    {
        super(file, FileToolKit.getExtensionName(file));

        String extension = FileToolKit.getExtensionName(file);
        if (extension.equals("db"))
        {
            manager = new DatabaseManager(file);
        }
    }

    /**
     * 向this.file插入单词
     * @param word 需要插入的单词
     */
    @Override
    public void insert(Word word)
    {
        manager.insert(word);
    }

    /**
     * 从this.file删除单词
     * @param word 需要删除的单词
     */
    @Override
    public void delete(Word word)
    {
        manager.delete(word);
    }

    /**
     * 在this.file中替换单词
     * @param from 需要替换的单词
     * @param to   替换后的单词
     */
    @Override
    public void modify(Word from, Word to)
    {
        manager.modify(from, to);
    }

    /**
     * 将所有修改写入this.file
     */
    @Override
    public void push()
    {
        manager.push();
    }
}
