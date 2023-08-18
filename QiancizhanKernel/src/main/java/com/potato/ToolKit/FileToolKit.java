package com.potato.ToolKit;

import com.potato.Log.Log;

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
     *
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
     * 获取没有扩展名的文件名
     *
     * @param file 需要获取文件名的文件
     * @return 传入文件的文件名
     */
    public static String getNameWithoutExtension(File file)
    {
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    /**
     * 将文件转换成字符串
     *
     * @param file 需要转换的文件
     * @return 转换后的字符串
     */
    public static String fileToString(File file)
    {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader;  // 读取文件
        try
        {
            reader = new BufferedReader(new FileReader(file));
            char[] buf = new char[1024];
            int numRead;

            // -1是文件的截止符，以此判断以后已经读取到尽头
            while ((numRead = reader.read(buf)) != -1)
            {
                String readData = String.valueOf(buf, 0, numRead);
                builder.append(readData);
            }
            reader.close();
        }
        catch (Exception e)
        {
            Log.e(FileToolKit.class.toString(), String.format("读取文件%s失败", file.getName()), e);
            throw new RuntimeException(e);
        }

        return builder.toString();
    }
}
