package com.potato.Parser;

import com.potato.Log.Log;
import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.Word.Word;
import lombok.Data;

import static com.potato.ToolKit.FileToolKit.*;

import java.io.File;
import java.util.List;

/**
 * Parser用于将单词本文件解析为单词列表
 */
@Data
public abstract class Parser
{
    private final File file;

    private List<Word> wordList;  // 储存单词的文件

    private Info info;  // 单词集信息

    private List<History> historyList;  // 单词集的历史信息

    /**
     * Parser用于将单词本文件解析为单词列表
     *
     * @param file      需要解析的单词本文件
     * @param extension 单词本文件对应的文件类型（即扩展名）
     *                  <p> 这个参数是为了区别不同的解析器，在实现解析器的时候应当给其具体的值
     *                  <p> 如对于xxx.db，其文件类型就是db
     *                  <p> {@link com.potato.ToolKit.WordFileType}给出了一些文件类型，建议使用这里给出的枚举
     * @see DatabaseParser
     * @see Word
     */
    public Parser(File file, String extension)
    {
        // 假如解析的单词本文件的扩展名和指定的文件类型不同，说明解析器给错了，因此报错
        if (!extension.equals(getExtensionName(file)))
        {
            Log.e(getClass().toString(), String.format("解析文件%s类型错误", file.getName()));
        }
        this.file = file;

        parser();
        Log.i(getClass().toString(), String.format("解析文件%s成功", file.getName()));
    }

    /**
     * 解析单词本文件
     * <p> 将this.file解析为一个储存{@link Word}的List，将这个List赋给this.wordList
     * <p> 如果在解析前有需要初始化的对象，将初始化写在构造器中
     */
    protected abstract void parser();
}

