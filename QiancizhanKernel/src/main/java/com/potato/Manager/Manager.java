package com.potato.Manager;

import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.Word.Word;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.potato.ToolKit.FileToolKit.getExtensionName;

/**
 * Manager用于管理单词本文件
 */
public abstract class Manager
{
    private File file;  // TODO 后期评估下这些东西是不是可以删掉

    Info info;
    List<Word> insertWords = new ArrayList<>();
    List<Word> deleteWords = new ArrayList<>();
    HashMap<Word, Word> modifyWords = new HashMap<>();
    List<History> insertHistory = new ArrayList<>();
    List<History> deleteHistory = new ArrayList<>();
    HashMap<History, History> modifyHistory = new HashMap<>();

    /**
     * Manager用于对单词本文件进行增、删、改的操作
     * Manager进行的操作不是实时的，因此在做完更改后需要使用push()将更改写入文件
     * 之所以要这样，是因为考虑到数据库等文件不应到频繁地修改，而是应当一次插入多条语句
     *
     * @param file      需要管理的单词本文件
     * @param extension 扩展名
     */
    public Manager(File file, String extension)
    {
        // 使用卫语句捕捉文件类型错误
        if (!extension.equals(getExtensionName(file)))
        {
            throw new RuntimeException("文件类型错误");
        }
        this.file = file;
    }

    /**
     * 向this.file插入单词
     *
     * @param word 需要插入的单词
     */
    public void insert(Word word)
    {
        insertWords.add(word);
    }

    /**
     * 从this.file删除单词
     *
     * @param word 需要删除的单词
     */
    public void delete(Word word)
    {
        deleteWords.add(word);
    }

    /**
     * 在this.file中替换单词
     *
     * @param from 需要替换的单词
     * @param to   替换后的单词
     */
    public void modify(Word from, Word to)
    {
        modifyWords.put(from, to);
    }

    /**
     * 向this.file插入测试记录
     *
     * @param history 测试记录
     */
    public void insert(History history)
    {
        insertHistory.add(history);
    }

    /**
     * 从this.file删除记录
     *
     * @param history 需要删除的记录
     */
    public void delete(History history)
    {
        deleteHistory.add(history);
    }

    /**
     * 在this.file中替换记录
     *
     * @param from 需要替换的记录
     * @param to   替换后的记录
     */
    public void modify(History from, History to)
    {
        modifyHistory.put(from, to);
    }

    /**
     * 在this.file中替换信息
     *
     * @param info 替换后的信息
     */
    public void modify(Info info)
    {
        this.info = info;
    }

    /**
     * 将所有修改写入this.file
     */
    public abstract void push();
}
