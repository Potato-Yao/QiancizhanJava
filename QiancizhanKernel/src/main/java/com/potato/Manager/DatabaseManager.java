package com.potato.Manager;

import com.potato.Config;
import com.potato.Word.Word;
import com.potato.Word.WordClass;
import com.potato.Word.WordHelper;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.potato.ToolKit.DatabaseToolKit.*;

public class DatabaseManager extends Manager
{
    private List<Word> insertWords = new ArrayList<>();
    private List<Word> deleteWords = new ArrayList<>();
    private HashMap<Word, Word> modifyWords = new HashMap<>();
    private Connection connection;
    private Config config;

    /**
     * DatabaseManager用于对数据库单词本文件进行增、删、改的操作
     * @param file      需要管理的单词本文件
     */
    public DatabaseManager(File file)
    {
        super(file, "db");

        config = new Config();
        connection = getConnection(file, config.getDatabaseType());
    }

    /**
     * 向数据库中插入单词
     * @param word 需要插入的单词
     */
    @SneakyThrows
    @Override
    public void insert(Word word)
    {
        insertWords.add(word);
    }

    /**
     * 从数据库删除单词
     * @param word 需要删除的单词
     */
    @Override
    public void delete(Word word)
    {
        deleteWords.add(word);
    }

    /**
     * 在数据库中替换单词
     * @param from 需要替换的单词
     * @param to   替换后的单词
     */
    @Override
    public void modify(Word from, Word to)
    {
        modifyWords.put(from, to);
    }

    /**
     * 将所有修改写入数据库
     */
    @SneakyThrows
    @Override
    public void push()
    {
        String insertSQL = "insert into WordList(WORD_NAME, WORD_CLASS, MEANING, " +
            "REVIEW_COUNT, REVIEW_DATE, CORRECT_COUNT, WRONG_COUNT, IS_KILLED) " +
            "VALUES(?,?,?,?,?,?,?,?)";

        // FIXME 这种实现效率太低，必须进行优化！
        for (Word w : insertWords)
        {
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, w.getWordName());
            statement.setString(2, WordHelper.writeToString(w.getWordClass()));
            statement.setString(3, w.getMeaning());
            statement.setInt(4, w.getReviewCount());
            statement.setString(5, w.getLastReviewDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setInt(6, w.getCorrectCount());
            statement.setInt(7, w.getWrongCount());
            statement.setInt(8, w.getIntIsKilled());
            statement.executeUpdate();
        }

        for (Word w : deleteWords)
        {
            String deleteSQL = "delete from WordList where WORD_NAME = '" + w.getWordName() + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteSQL);
        }

        for (Map.Entry<Word, Word> w : modifyWords.entrySet())
        {
            Word to = w.getValue();
            String modifySQL = "update WordList set WORD_NAME ='" + to.getWordName()
                + "', WORD_CLASS ='" + WordHelper.writeToString(to.getWordClass())
                + "', MEANING ='" + to.getMeaning()
                + "', REVIEW_COUNT =" + to.getReviewCount()
                + ", REVIEW_DATE ='" + to.getLastReviewDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "', CORRECT_COUNT =" + to.getCorrectCount()
                + ", WRONG_COUNT =" + to.getWrongCount()
                + ", IS_KILLED =" + to.getIntIsKilled()
                + " where WORD_NAME ='" + w.getKey().getWordName() + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate(modifySQL);
        }

        connection.close();
    }
}
