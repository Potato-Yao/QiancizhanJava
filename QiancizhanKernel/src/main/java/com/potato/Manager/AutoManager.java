package com.potato.Manager;

import com.potato.Config;
import com.potato.Log.Log;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.ToolKit.WordFileType;
import com.potato.Word.Word;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Constructor;

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
    @SneakyThrows
    public AutoManager(File file)
    {
        super(file, FileToolKit.getExtensionName(file));

        String extension = FileToolKit.getExtensionName(file);
        if (extension.equals(WordFileType.DATABASE.type()))
        {
            Constructor<Manager> alternativeManagerConstructor =
                Config.alternativeManager.get(WordFileType.DATABASE);

            // 如果替换管理器是null，则使用默认管理器
            if (alternativeManagerConstructor == null)
            {
                manager = new DatabaseManager(file);
            }
            else
            {
                // 否则使用替换的管理器
                manager = alternativeManagerConstructor.newInstance(file);
            }
        }

        assert manager != null;
        Log.i(getClass().toString(), String.format("管理器为%s，已加载管理器", manager.getClass()));
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
        Log.v(getClass().toString(), String.format("插入单词%s", word));
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
        Log.v(getClass().toString(), String.format("删除单词%s", word));
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
        Log.v(getClass().toString(), String.format("将单词%s修改为单词%s", from, to));
    }

    /**
     * 将所有修改写入this.file
     */
    @Override
    public void push()
    {
        manager.push();
        Log.v(getClass().toString(), "将修改写入文件完成");
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
        Log.v(getClass().toString(), String.format("插入历史记录%s", history));
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
        Log.v(getClass().toString(), String.format("删除历史记录%s", history));
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
        Log.v(getClass().toString(), String.format("将历史记录%s修改为历史记录%s", from, to));
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
        Log.v(getClass().toString(), String.format("修改数据库信息至%s", info));
    }
}
