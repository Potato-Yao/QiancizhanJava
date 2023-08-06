package com.potato.ToolKit;

import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * DatabaseToolKit提供一系列与数据库相关的工具
 */
public class DatabaseToolKit
{
    /**
     * 根据数据库类型获取相应JDBC标识
     * @param databaseType 数据库类型
     * @return JDBC标识
     */
    private static String getDatabaseJDBCString(DatabaseType databaseType)
    {
        if (databaseType == DatabaseType.SQLite)
        {
            return "jdbc:sqlite:";
        }
        else if (databaseType == DatabaseType.MySQL)
        {
            return "jdbc:mysql:";
        }
        return null;
    }

    /**
     * 根据文件和数据库类型快速获得一个Connection
     * @param file 连接的文件
     * @param databaseType 所用数据库类型
     * @return 使用该数据库连接该文件的Connection
     */
    @SneakyThrows
    public static Connection getConnection(File file, DatabaseType databaseType)
    {
        Connection connection = null;
        String jdbc = getDatabaseJDBCString(databaseType);

        // 这里给出的是使用JDBC连接的数据库，对于不使用JDBC连接的，应当独立写对应的方法
        // 目前并不打算写对应的方法，也许用到MongoDB的时候会写
        if (databaseType == DatabaseType.SQLite || databaseType == DatabaseType.MySQL ||
                databaseType == DatabaseType.Oracle)
        {
            jdbc = jdbc + file.getPath();
            connection = DriverManager.getConnection(jdbc);
        }

        return connection;
    }

    /**
     * 创建一个初始化好的数据库
     * 即创建一个数据库后新建一个叫做WordList的table
     * @param file 需要创建的数据库文件
     * @param databaseType 数据库类型
     * @return 是否创建成功，若成功则返回true
     */
    @SneakyThrows
    public static boolean createInitialedDatabase(File file, DatabaseType databaseType)
    {
        if (file.exists())  // 检查文件是否存在
        {
            return false;
        }
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

        sql = "create table Info(LANGUAGE TEXT)";
        statement = connection.createStatement();
        statement.executeUpdate(sql);  // 执行创建表的命令

        return true;
    }
}
