package com.potato.Parser;

import com.potato.Config;
import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
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

/**
 * DatabaseParser是数据库类型单词本的解析器
 */
public class DatabaseParser extends Parser
{
    /**
     * DatabaseParser用于解析储存单词本的数据库文件
     * 即后缀名是db的文件
     *
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
        Info info;
        List<History> historyList = new ArrayList<>();
        String query = "select * from WordList";  // 表的名称是固定在API指南中的
        Connection connection = getConnection(getFile(), Config.getDatabaseType());

        assert connection != null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);  // 获取查询结果

        // 遍历查询结果，获取所有单词
        // 看看遍历能不能优化一下
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

        // 获取数据库信息
        query = "select * from Info";
        resultSet = statement.executeQuery(query);
        info = new Info(resultSet.getString(1));
        setInfo(info);

        // 获取历史记录
        query = "select * from History";
        resultSet = statement.executeQuery(query);

        while (resultSet.next())
        {
            // TODO 这里看看能不能优化一下
            historyList.add(new History(LocalDate.parse(resultSet.getString(1)), resultSet.getInt(2),
                    resultSet.getInt(3), resultSet.getInt(4),
                    resultSet.getInt(5)));
        }
        setHistoryList(historyList);

        connection.close();
    }
}
