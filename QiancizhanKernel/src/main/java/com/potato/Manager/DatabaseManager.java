package com.potato.Manager;

import com.potato.Config;
import com.potato.ToolKit.History;
import com.potato.Word.Word;
import com.potato.Word.WordHelper;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.potato.ToolKit.DatabaseToolKit.*;

/**
 * DatabaseManager用于管理数据库类型的单词本文件
 */
public class DatabaseManager extends Manager
{
    private Connection connection;

    /**
     * DatabaseManager用于对数据库单词本文件进行增、删、改的操作
     *
     * @param file 需要管理的单词本文件
     */
    public DatabaseManager(File file)
    {
        super(file, "db");

        connection = getConnection(file, Config.getDatabaseType());
    }

    /**
     * 将所有修改写入数据库
     */
    @SneakyThrows
    @Override
    public void push()
    {
        // 写入添加的单词
        String insertSQL = "insert into WordList(WORD_NAME, WORD_CLASS, MEANING, " +
                "REVIEW_COUNT, REVIEW_DATE, CORRECT_COUNT, WRONG_COUNT, IS_KILLED) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement wordInsertStatement = connection.prepareStatement(insertSQL);

        // FIXME 这种实现效率太低，必须进行优化！
        for (Word w : getInsertWords())
        {
            wordInsertStatement.setString(1, w.getWordName());
            wordInsertStatement.setString(2, WordHelper.wordClassToString(w.getWordClass()));
            wordInsertStatement.setString(3, w.getMeaning());
            wordInsertStatement.setInt(4, w.getReviewCount());
            wordInsertStatement.setString(5, w.getLastReviewDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            wordInsertStatement.setInt(6, w.getCorrectCount());
            wordInsertStatement.setInt(7, w.getWrongCount());
            wordInsertStatement.setInt(8, w.getIntIsKilled());
            wordInsertStatement.executeUpdate();
        }

        // 写入删除的单词
        for (Word w : getDeleteWords())
        {
            String deleteSQL = "delete from WordList where WORD_NAME = '" + w.getWordName() + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteSQL);
        }

        // 写入修改的单词
        for (Map.Entry<Word, Word> w : getModifyWords().entrySet())
        {
            Word to = w.getValue();
            String modifySQL = "update WordList set WORD_NAME ='" + to.getWordName()
                    + "', WORD_CLASS ='" + WordHelper.wordClassToString(to.getWordClass())
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

        // 写入修改的单词本信息
        if (getInfo() != null)
        {
            String modifySQL = "update Info set LANGUAGE ='" + getInfo().language() + "'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(modifySQL);
        }

        // 写入修改的背诵历史信息
        insertSQL = "insert into History(DATE, SUM_COUNT, CORRECT_COUNT, WRONG_COUNT, TIME_COST) values(?, ?, ?, ?, ?)";
        PreparedStatement historyInsertStatement = connection.prepareStatement(insertSQL);

        for (History h : getInsertHistory())
        {
            historyInsertStatement.setString(1, h.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            historyInsertStatement.setInt(2, h.sumCount());
            historyInsertStatement.setInt(3, h.correctCount());
            historyInsertStatement.setInt(4, h.wrongCount());
            historyInsertStatement.setInt(5, h.timeCost());
            historyInsertStatement.executeUpdate();
        }

        for (History h : getDeleteHistory())
        {
            String deleteSQL = "delete from History where DATE = '" + h.date() + "' and TIME_COST = "
                    + h.timeCost();
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteSQL);
        }

        for (Map.Entry<History, History> h : getModifyHistory().entrySet())
        {
            History to = h.getValue();
            String modifySQL = "update History set DATE = '" + to.date()
                    + "', SUM_COUNT = " + to.sumCount()
                    + ", CORRECT_COUNT = " + to.correctCount()
                    + ", WRONG_COUNT = " + to.wrongCount()
                    + ", TIME_COST = " + to.timeCost()
                    + " where DATE ='" + h.getKey().date() + "' and TIME_COST = " + h.getKey().timeCost();

            Statement statement = connection.createStatement();
            statement.executeUpdate(modifySQL);
        }

        connection.close();
    }
}
