package com.potato.ToolKit;

public enum WordFileType
{
    DATABASE("db"),
    JSON("json"),
    EXCEL("xlsx");

    private final String type;

    WordFileType(String type)
    {
        this.type = type;
    }

    public String type()
    {
        return type;
    }
}
