package com.potato.Manager;

import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.Word.Word;

import java.io.File;

/**
 * AutoManager用于自动根据指定单词本文件类型选择对应的管理器管理单词本
 */
public class AutoManager extends Manager
{
    Manager manager;

    /**
     * AutoManager用于对单词本文件进行增、删、改的操作
     * AutoManager可以自动识别文件类型并给出正确的管理器
     *
     * @param file 需要管理的单词本文件
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
     *
     * @param word 需要插入的单词
     */
    @Override
    public void insert(Word word)
    {
        manager.insert(word);
    }

    /**
     * 从this.file删除单词
     *
     * @param word 需要删除的单词
     */
    @Override
    public void delete(Word word)
    {
        manager.delete(word);
    }

    /**
     * 在this.file中替换单词
     *
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

    /**
     * 向this.file插入测试记录
     *
     * @param history 测试记录
     */
    @Override
    public void insert(History history)
    {
        manager.insert(history);
    }

    /**
     * 从this.file删除记录
     *
     * @param history 需要删除的记录
     */
    @Override
    public void delete(History history)
    {
        manager.delete(history);
    }

    /**
     * 在this.file中替换记录
     *
     * @param from 需要替换的记录
     * @param to   替换后的记录
     */
    @Override
    public void modify(History from, History to)
    {
        manager.modify(from, to);
    }

    /**
     * 在this.file中替换信息
     *
     * @param info 替换后的信息
     */
    @Override
    public void modify(Info info)
    {
        manager.modify(info);
    }
}
