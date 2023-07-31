package com.potato.Manager;

import com.potato.Word.Word;

import java.io.File;

import static com.potato.ToolKit.FileToolKit.getExtensionName;

public abstract class Manager
{
    private File file;

    /**
     * Manager用于对单词本文件进行增、删、改的操作
     * Manager进行的操作不是实时的，因此在做完更改后需要使用push()将更改写入文件
     * 之所以要这样，是因为考虑到数据库等文件不应到频繁地修改，而是应当一次插入多条语句
     * @param file 需要管理的单词本文件
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
     * @param word 需要插入的单词
     */
    public abstract void insert(Word word);

    /**
     * 从this.file删除单词
     * @param word 需要删除的单词
     */
    public abstract void delete(Word word);

    /**
     * 在this.file中替换单词
     * @param from 需要替换的单词
     * @param to 替换后的单词
     */
    public abstract void modify(Word from, Word to);

    /**
     * 将所有修改写入this.file
     */
    public abstract void push();
}
