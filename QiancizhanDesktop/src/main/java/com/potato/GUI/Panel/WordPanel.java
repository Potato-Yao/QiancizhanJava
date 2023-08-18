package com.potato.GUI.Panel;

import com.potato.GUI.Memory;
import com.potato.Word.Word;
import com.potato.Word.WordClass;

import javax.swing.*;
import java.awt.*;

public class WordPanel extends JPanel
{
    private static JLabel wordLabel;
    private JButton leftButton;
    private JButton rightButton;
    private JPanel buttonPanel;
    private JPanel wordPanel;

    /**
     * WordPanel是显示单词的平面
     * @param owner 平面所属的JFrame
     */
    public WordPanel(JFrame owner)
    {
        setLayout(new BorderLayout());

        wordLabel = new JLabel("单词将显示于此");
        leftButton = new JButton("向左");
        rightButton = new JButton("向右");
        buttonPanel = new JPanel();
        wordPanel = new JPanel();

        wordPanel.add(wordLabel);

        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);

        leftButton.addActionListener(e -> moveButtonAction(-1));
        rightButton.addActionListener(e -> moveButtonAction(1));

        add(buttonPanel, BorderLayout.SOUTH);
        add(wordPanel, BorderLayout.CENTER);
    }

    /**
     * 刷新界面
     */
    public static void refresh()
    {
        if (Memory.word != null)
        {
            setWord(Memory.word);
        }
        else
        {
            wordLabel.setText("");
        }
    }

    /**
     * 单词间跳转按钮的动作
     * @param step 一次跳转的步数，正数是向右跳转，负数是向左跳转
     *             如，要想让按钮实现向右跳转一个单词，则使用moveButtonAction(1)
     *             向左跳转一个则是moveButtonAction(-1)
     */
    private void moveButtonAction(int step)
    {
        if (Memory.mode == 0)
        {
            return;
        }

        int index = Memory.wordChosenIndex;
        index += step;  // 将索引改变到跳转后位置，这步实际上是index += step * 1;

        // 考虑索引越界的情况，越界就回到另一端
        if (index < 0)
        {
            index = Memory.wordList.size() - 1;
        }
        else if (index >= Memory.wordList.size())
        {
            index = step - 1;  // 索引偏移
        }

        Memory.changeWord(index);
    }

    /**
     * 设置界面上显示的单词
     * @param word 需要显示的单词
     */
    private static void setWord(Word word)
    {
        StringBuilder text = new StringBuilder();
        text.append("<html><h1 align=\"center\">");  // Swing有内置HTML解析器
        text.append(word.getWordName()).append("</h1><br>");
        text.append("<h3 align=\"center\">");

        for (WordClass w : word.getWordClass())
        {
            text.append(w.toString()).append(".").append("  ");
        }
        text.delete(text.length() - 2, text.length());
        text.append("</h3><br><h3 align=\"center\">");
        text.append(word.getMeaning());
        text.append("</h3><br><h3 align=\"center\">");
        text.append("复习次数").append(word.getReviewCount());
        text.append("</h3><br><h3 align=\"center\">");
        text.append("最后复习日期").append(word.getLastReviewDate());
        text.append("</h3><br><h3 align=\"center\">");
        text.append("正确次数").append(word.getCorrectCount());
        text.append("&nbsp;&nbsp;");
        text.append("错误次数").append(word.getWrongCount());
        text.append("</h3><br><h3 align=\"center\">");
        text.append("</html>");

        wordLabel.setText(text.toString());
    }
}
