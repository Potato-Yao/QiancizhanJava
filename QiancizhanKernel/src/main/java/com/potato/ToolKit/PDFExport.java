package com.potato.ToolKit;

import com.potato.Config;
import com.potato.Log.Log;
import com.potato.Parser.AutoParser;
import com.potato.Word.Word;
import com.potato.Word.WordHelper;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * PDFExport用于将单词列表导出为PDF
 */
public class PDFExport
{
    private final String docHead = """
            \\documentclass[UTF-8]{article}
                        
            \\usepackage{ctex}
            \\usepackage{longtable}
            \\usepackage{geometry}
                        
            \\geometry{scale=0.85}
                        
            \\begin{document}
            	\\textsf{英语单词单}由\\textit{千词斩}生成于\\today
            """;
    private final String tableHead = """
            	\\begin{longtable}{c|p{%scm}|c|p{%scm}}
            		序号 & 单词 & 词性 & 汉义\\\\
            """;
    private final String cell = """
            \\hline        %s& %s& %s& %s \\\\
            """;
    private final String tableTail = """
            \\end{longtable}
            """;
    private final String docTail = "\\end{document}";

    private boolean isDetailed;
    private List<Word> wordList;
    private File sourceFile;
    private BufferedWriter writer;

    /**
     * 导出PDF文件
     * 这个功能是通过单词本 -> LaTeX -> PDF实现的，因此需要安装lualatex
     *
     * @param file           需要导出的单词本文件
     * @param isDetailNeeded 是否需要导出详细信息
     */
    @SneakyThrows
    public PDFExport(File file, String outputName, boolean isDetailNeeded)
    {
        wordList = new ArrayList<>();
        AutoParser parser = new AutoParser(file);
        sourceFile = new File(Config.outputFilePath, outputName + ".tex");
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(sourceFile, false), StandardCharsets.UTF_8));

        if (!sourceFile.exists())
        {
            if (!sourceFile.createNewFile())
            {
                Log.i(getClass().toString(), String.format("创建tex源代码文件成功，文件名%s", sourceFile.getName()));
            }
        }

        this.wordList = parser.getWordList();
        this.isDetailed = isDetailNeeded;
    }

    /**
     * 智能输出，可以根据单词本单词的平均长度自动判断使用单行还是双行
     * TODO 还没实现
     */
    public void smartExport()
    {
        if (!isDetailed)
        {
            roughExport(1);
        }

        Log.i(getClass().toString(), "PDF文件导出成功");
    }

    /**
     * 不要求详细信息的输出
     * TODO 还没写完
     *
     * @param columnNumber 输出表格的列数
     */
    @SneakyThrows
    private void roughExport(int columnNumber)
    {
        int index = 1;
//        int columnSize = 0;
        writer.append(docHead);
        writer.append(String.format(tableHead, "6", "6"));

        for (Word w : wordList)
        {
            writer.append(String.format(cell, index, w.getWordName(),
                    WordHelper.writeToString(w.getWordClass()), w.getMeaning()));
            index++;
        }

        writer.append(tableTail);
        writer.append(docTail);
        writer.flush();
        writer.close();
        outputProcess();
    }

    /**
     * 输出过程
     * 这个方法用于将LaTeX输出为PDF
     */
    @SneakyThrows
    private void outputProcess()
    {
        String fullPath = sourceFile.getAbsolutePath();
        String pathWithoutName = fullPath.replace(sourceFile.getName(), "");
        File file = new File(pathWithoutName);
        // 使用lualatex编译pdf
        // lualatex对汉语支持较好，因此选用它
        // 另外也可以引用xeCJK后使用xelatex编译
        Process makePDF = Runtime.getRuntime().exec("lualatex " + fullPath, null, file);
        printResults(makePDF);  // 必须通过输出来占据线程，保证编译完成

        // 在完成编译后删除临时文件，只保留pdf
        File tempFile = new File(pathWithoutName, Config.outputFileName + ".aux");
        tempFile.delete();
        tempFile = new File(pathWithoutName, Config.outputFileName + ".log");
        tempFile.delete();
        sourceFile.delete();
    }

    /**
     * 这个方法通过一个Process监视lualatex的编译信息
     * 它的意义在于，Process会占用线程，从而保证输出pdf的方法能完整进行
     *
     * @param process 需要监视的进程
     */
    @SneakyThrows
    private void printResults(Process process)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null)
        {
            System.out.println(line);
        }
    }
}
