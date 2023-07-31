package com.potato.GUI;

import com.potato.Config;
import com.potato.GUI.Panel.ListPanel;
import com.potato.GUI.Panel.WordPanel;
import com.potato.Parser.AutoParser;
import com.potato.Word.Word;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * Memory用于储存一些静态的变量
 */
@Data
public class Memory
{
    /*
    当前的模式
    0代表在选择单词本的界面（即初始界面）
    1代表已经选定单词本的界面
     */
    public static int mode = 0;

    /*
    选定的单词本文件
     */
    public static File chosenWordListFile;

    /*
    当前目录
     */
    public static String path;

    /*
    选定单词本文件的单词列表
     */
    public static List<Word> wordList;

    /*
    在ListPanel中选定单词的索引
     */
    public static int wordChosenIndex = 0;

    /*
    选定的单词
     */
    public static Word word;

    /**
     * 设置选定的单词本文件
     * @param file 单词本文件
     */
    public static void setChosenWordListFile(File file)
    {
        mode = 1;  // 选定后，模式变为1（已选定模式）
        chosenWordListFile = file;
        AutoParser parser = new AutoParser(file);
        wordList = parser.getWordList();
        globalRefresh();
    }

    /**
     * 回到选择单词本文件的界面
     */
    public static void backToFilesList()
    {
        mode = 0;
        chosenWordListFile = null;
        word = null;
        globalRefresh();
    }

    /**
     * 改变选中的单词
     * @param index 单词的索引
     */
    public static void changeWord(int index)
    {
        wordChosenIndex = index;
        word = wordList.get(index);
        WordPanel.refresh();
    }

    /**
     * 全局刷新界面
     */
    public static void globalRefresh()
    {
        if (chosenWordListFile != null)
        {
            path = chosenWordListFile.getPath();
        }
        else
        {
            path = Config.normalWordListPath;
        }
        ListPanel.refresh();
        MainFrame.setPathLabelText("当前目录为" + path);
        WordPanel.refresh();
    }

    /**
     * 同步WordList的全局刷新
     * 如果WordList改变了，那么使用这个全局刷新
     */
    public static void globalRefreshWithWordListSync()
    {
        AutoParser parser = new AutoParser(chosenWordListFile);
        wordList = parser.getWordList();
        globalRefresh();
    }
}
