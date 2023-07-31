package com.potato.ToolKit;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * FileToolKit提供一系列与文件相关的工具
 */
public class FileToolKit
{
    /**
     * 获取文件的扩展名
     * 如文件xxx.db，返回的结果是db
     * @param file 需要获取的文件
     * @return 获取的扩展名
     */
    public static String getExtensionName(File file)
    {
        String extension = file.getName();
        // 我所知道的操作系统（Windows，Linux，MacOS）都以.作为文件名和扩展名的分割
        extension = extension.substring(extension.lastIndexOf(".") + 1);

        return extension;
    }

    /**
     * 将文件转换成字符串
     * @param file 需要转换的文件
     * @return 转换后的字符串
     */
    @SneakyThrows
    public static String fileToString(File file)
    {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));  // 读取文件
        char[] buf = new char[1024];
        int numRead = 0;

        // -1是文件的截止符，以此判断以后已经读取到尽头
        while ((numRead = reader.read(buf)) != -1)
        {
            String readData = String.valueOf(buf, 0, numRead);
            builder.append(readData);
        }

        reader.close();
        return builder.toString();
    }
}
