package com.potato.ToolKit;

import com.potato.Log.Log;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseToolKit提供一系列与数据库相关的工具
 */
public class DatabaseToolKit
{
    /**
     * 根据数据库类型获取相应JDBC标识
     * <p> 如，sqlite对应的标识是jdbc:sqlite:
     *
     * @param databaseType 数据库类型
     * @return 数据库对应的JDBC标识，如果没有找到对应的标识则返回null
     */
    private static String getJDBCSymbol(DatabaseType databaseType)
    {
        switch (databaseType)
        {
            case SQLite ->
            {
                return "jdbc:sqlite:";
            }
            case MySQL ->
            {
                return "jdbc:mysql:";
            }
        }
        return null;
    }

    /**
     * 该方法用于获取一个数据库文件的{@link Connection}实例
     *
     * @param file         数据库文件
     * @param databaseType 该文件的数据库类型
     * @return 该数据库文件的Connection实例
     */
    public static Connection getConnection(File file, DatabaseType databaseType)
    {
        Connection connection = null;
        String jdbc = getJDBCSymbol(databaseType);  // 获取数据库文件的JDBC标识

        // 这里给出的是使用JDBC连接的数据库，对于不使用JDBC连接的，应当独立写对应的方法
        // 目前并不打算写对应的方法，也许用到MongoDB的时候会写
        if (databaseType == DatabaseType.SQLite || databaseType == DatabaseType.MySQL ||
            databaseType == DatabaseType.Oracle)
        {
            jdbc = jdbc + file.getPath();
            try
            {
                connection = DriverManager.getConnection(jdbc);
            }
            catch (SQLException e)
            {
                Log.e(DatabaseToolKit.class.toString(), String.format("获取数据库文件%s的连接错误", file.getName()), e);
                throw new RuntimeException(e);
            }
        }

        return connection;
    }

    /**
     * 创建一个储存单词本的数据库并将其初始化
     * 即创建一个数据库后新建叫做WordList、History和Info的table
     *
     * @param file         需要创建的数据库文件
     * @param databaseType 数据库文件的数据库类型
     * @return 是否创建成功，若成功则返回true
     */
    @SneakyThrows
    public static boolean createInitialedDatabase(File file, DatabaseType databaseType)
    {
        if (!file.createNewFile())  // 文件是否成功创建
        {
            return false;
        }

        Connection connection = getConnection(file, databaseType);
        // 创建表的语句，表名WordList是API规范要求的命名，因此直接写死
        String sql = """
            create table WordList
            (
                WORD_NAME TEXT,
                WORD_CLASS TEXT,
                MEANING TEXT,
                REVIEW_COUNT INT,
                REVIEW_DATE TEXT,
                CORRECT_COUNT INT,
                WRONG_COUNT INT,
                IS_KILLED INT
            )""";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);  // 执行创建表的命令

        // 创建历史记录表
        sql = """
            create table History
            (
                DATE TEXT,
                SUM_COUNT INT,
                CORRECT_COUNT INT,
                WRONG_COUNT INT,
                TIME_COST INT
            )
            """;

        statement = connection.createStatement();
        statement.executeUpdate(sql);  // 执行创建表的命令

        // 创建单词本信息表
        sql = "create table Info(LANGUAGE TEXT)";
        statement = connection.createStatement();
        statement.executeUpdate(sql);  // 执行创建表的命令

        // 顺利创建
        return true;
    }
}
