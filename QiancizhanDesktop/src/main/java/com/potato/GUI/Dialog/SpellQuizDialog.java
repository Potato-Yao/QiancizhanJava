package com.potato.GUI.Dialog;

import com.potato.GUI.Memory;
import com.potato.Manager.AutoManager;
import com.potato.ToolKit.QuizMaker;
import com.potato.Word.Word;
import com.potato.Word.WordHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpellQuizDialog extends JDialog
{
    private JLabel meaningLabel;
    private JPanel buttonPanel;
    private JTextField answerField;
    private JButton confirmButton;
    private JButton promptButton;

    private QuizPassedDialog quizPassedDialog;
    private QuizMaker maker;
    private List<Word> quizList;
    private AutoManager manager;
    private int quizIndex = 0;

    /**
     * SpellQuizDialog是默写测试的对话框
     * @param owner 对话框所属的JFrame
     */
    public SpellQuizDialog(JFrame owner)
    {
        super(owner, "默写测试", true);
        setLayout(new BorderLayout());
        setSize(new Dimension(500, 300));
        setLocationRelativeTo(null);
        meaningLabel = new JLabel();
        buttonPanel = new JPanel();
        answerField = new JTextField();
        quizPassedDialog = new QuizPassedDialog(owner);
        confirmButton = new JButton("确定");
        promptButton = new JButton("提示");

        meaningLabel.setPreferredSize(new Dimension(500, 100));

        confirmButton.addActionListener(e -> confirmButtonAction());
        promptButton.addActionListener(e -> promptButtonAction());

        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(promptButton);
        buttonPanel.add(confirmButton);

        add(meaningLabel, BorderLayout.NORTH);
        add(answerField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * initial用于初始化对话框
     * 也即将索引归零并新建测试
     */
    public void initial()
    {
        maker = new QuizMaker(Memory.wordList);
        manager = new AutoManager(Memory.chosenWordListFile);
        quizList = maker.randomQuizMaker();
        setQuiz();
    }

    /**
     * confirmButtonAction是confirmButton的动作
     * 若题目没有遍历完，则点击确定按钮应当展示下一题
     * 若题目已经遍历完，则点击确定按钮应当打开完成对话框
     */
    private void confirmButtonAction()
    {
        if (!quizList.get(quizIndex).getWordName()
                .equals(answerField.getText()))
        {
            Word word = WordHelper.updateWord(quizList.get(quizIndex), false, false);
            manager.modify(quizList.get(quizIndex), word);
            return;
        }

        Word word = WordHelper.updateWord(quizList.get(quizIndex), true, false);
        manager.modify(quizList.get(quizIndex), word);
        quizIndex++;

        if (quizIndex < quizList.size())
        {
            setQuiz();
        }
        else
        {
            quizPassedDialog.setVisible(true);
            quizIndex = 0;
            manager.push();
            setVisible(false);
            Memory.globalRefreshWithWordListSync();
        }
    }

    /**
     * 提示按钮的动作
     */
    private void promptButtonAction()
    {
        int splitLength = 2;
        List<Integer> index = new ArrayList<>();
        char[] text = quizList.get(quizIndex).getWordName().toCharArray();
        char[] hint = new char[text.length];
        Random random = new Random();

        Arrays.fill(hint, '*');

        for (int i = 0; i < text.length / splitLength; i++)
        {
            index.add(random.nextInt(0, text.length));
        }

        for (int i : index)
        {
            hint[i] = text[i];
        }

        String labelText = "<html><h1>" + quizList.get(quizIndex).getMeaning() + "</h1>" +
                "<h2>" + Arrays.toString(hint) + "</h2></html>";
        meaningLabel.setText(labelText);
    }

    /**
     * setQuiz用于展示题目
     */
    private void setQuiz()
    {
        String labelText = "<html><h1>" + quizList.get(quizIndex).getMeaning() + "</h1></html>";

        meaningLabel.setText(labelText);
        answerField.setText("");  // 清空答题的文本框
    }
}
