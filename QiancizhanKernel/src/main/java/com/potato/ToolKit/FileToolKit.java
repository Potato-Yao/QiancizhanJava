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
     * <p> 如文件xxx.db，返回的结果是db
     *
     * @param file 需要获取扩展名的文件
     * @return 文件的扩展名
     */
    public static String getExtensionName(File file)
    {
        // Windows、Linux、MacOS都以.作为文件名和扩展名的分割，因此最后一个.一定分割了文件名和扩展名
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    /**
     * 将文件名化为没有扩展名的形式
     * 如xxx.db将返回xxx
     *
     * @param file 需要获取文件名的文件
     * @return 该文件没有扩展名的文件名
     */
    public static String getNameWithoutExtension(File file)
    {
        // 最后一个.一定分割了文件名和扩展名
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    /**
     * 将文件内容读取为字符串
     *
     * @param file 需要读取内容的文件
     * @return 读取出的字符串
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
