package com.potato.Parser;

import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.Word.Word;
import lombok.Data;

import static com.potato.ToolKit.FileToolKit.*;

import java.io.File;
import java.util.List;

/**
 * Parser用于将单词本文件解析为单词列表的解析器
 */
@Data
public abstract class Parser
{
    private final File file;

    private List<Word> wordList;  // 储存单词的文件

    private Info info;  // 单词集信息

    private List<History> historyList;  // 单词集的历史信息

    /**
     * Parser用于解析储存单词本的文件
     * @param file 需要解析的文件
     * @param extension 文件应有的扩展名，对于文件xxx.db，其扩展名是db
     */
    public Parser(File file, String extension)
    {
        // 使用卫语句捕捉文件类型错误
        if (!extension.equals(getExtensionName(file)))
        {
            throw new RuntimeException("文件类型错误");
        }
        this.file = file;

        parser();
    }

    /**
     * 解析，将this.file解析为一个储存Word的List，将这个List赋给this.wordList
     */
    protected abstract void parser();
}

