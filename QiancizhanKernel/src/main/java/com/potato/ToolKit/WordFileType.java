package com.potato.ToolKit;

import java.util.HashMap;
import java.util.Map;

/**
 * WordFileType是单词本文件的文件类型
 */
public enum WordFileType
{
    /* 数据库 */
    DATABASE("db"),

    /* JSON文件 */
    JSON("json"),

    /* excel文件 */
    EXCEL("xlsx");

    private static final Map<String, WordFileType> indexMap = new HashMap<>();  // 用于根据字符串检索枚举
    static
    {
        for (WordFileType t : WordFileType.values())
        {
            indexMap.put(t.type, t);
        }
    }

    private final String type;  // 文件类型，即扩展名，如对于xxx.db，扩展名就是db

    /**
     * 单词本文件的文件类型
     *
     * @param type 文件类型
     */
    WordFileType(String type)
    {
        this.type = type;
    }

    /**
     * 根据字符串获取对应文件类型的WordFileType对象
     *
     * @param type 文件类型
     * @return WordFileType对象
     */
    public static WordFileType getWordFileType(String type)
    {
        return indexMap.get(type);
    }

    /**
     * 根据WordFileType对象获取其文件类型的字符串
     *
     * @return 对象对应的字符串
     */
    public String type()
    {
        return type;
    }
}
