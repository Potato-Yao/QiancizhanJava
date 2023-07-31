package com.potato.Parser;

import com.potato.Word.Word;
import lombok.Getter;
import lombok.Setter;

import static com.potato.ToolKit.FileToolKit.*;

import java.io.File;
import java.util.List;

public abstract class Parser
{
    @Getter
    private final File file;

    @Getter
    @Setter
    private List<Word> wordList;  // 储存单词的文件

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
