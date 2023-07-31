package com.potato.Parser;

import com.potato.Config;
import com.potato.ToolKit.DatabaseType;
import com.potato.Word.Word;
import com.potato.Word.WordClass;
import com.potato.Word.WordHelper;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.potato.ToolKit.DatabaseToolKit.*;

public class DatabaseParser extends Parser
{
    /**
     * DatabaseParser用于解析储存单词本的数据库文件
     * 即后缀名是db的文件
     * @param file 需要解析的数据库文件
     */
    public DatabaseParser(File file)
    {
        super(file, "db");  // db是数据库的扩展名
    }

    /**
     * 解析，将this.file解析为一个储存Word的List，将这个List赋给this.wordList
     */
    @SneakyThrows
    @Override
    protected void parser()
    {
        List<Word> wordList = new ArrayList<>();
        String query = "select * from WordList";  // 表的名称是固定在API指南中的
        Connection connection = getConnection(getFile(), Config.getDatabaseType());

        assert connection != null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);  // 获取查询结果

        // 遍历查询结果，一一创建对象
        // TODO 需要一般化
        while (resultSet.next())
        {
            WordClass[] wordClass = WordHelper.parserWordClass(resultSet.getString(2));

            Word word = new Word.WordBuilder()
                    .name(resultSet.getString(1))
                    .wordClass(wordClass)
                    .meaning(resultSet.getString(3))
                    .reviewCount(resultSet.getInt(4))
                    .lastReviewDate(LocalDate.parse(resultSet.getString(5)))
                    .correctCount(resultSet.getInt(6))
                    .isKilled(resultSet.getBoolean(8))
                    .build();
            wordList.add(word);
        }

        setWordList(wordList);

        connection.close();
    }
}
