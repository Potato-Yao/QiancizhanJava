package com.potato.GUI.Dialog;

import com.potato.GUI.Memory;
import com.potato.Manager.AutoManager;
import com.potato.Translate.Translate;
import com.potato.Word.Word;
import com.potato.Word.WordClass;
import com.potato.Word.WordHelper;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InsertWordDialog extends JDialog
{
    /*
    insideMode指示的是使用插入单词模式还是修改单词模式
    0代表插入单词模式
    1代表修改单词模式
     */
    private int insideMode;
    private Translate translate = new Translate();
    private JPanel parameterPanel = new JPanel();
    private JLabel wordNameLabel = new JLabel("单词");
    private JTextField wordNameField = new JTextField();
    private JLabel wordClassLabel = new JLabel("词性");
    private JPanel wordClassPanel = new JPanel();
    private JLabel meaningLabel = new JLabel("汉义");
    private JTextField meaningField = new JTextField();
    private JPanel buttonPanel = new JPanel();
    private JButton confirmButton = new JButton("确认");
    private JButton translateButton = new JButton("翻译");

    /**
     * InsertWordDialog是插入单词的对话框
     * 对话框有两种模式，插入单词将显示一个无信息的对话框，调用Manager.insert()方法
     * 而修改单词则显示需要修改单词数据的对话框，调用Manager.modify()方法
     * @param owner 对话框所属的JFrame
     * @param title 对话框的标题，值为”插入单词“或”修改单词“
     */
    public InsertWordDialog(JFrame owner, String title)
    {
        super(owner, title, true);

        meaningField.setText("");

        // 确定模式
        if (title.equals("修改单词"))
        {
            insideMode = 1;
        }
        else
        {
            insideMode = 0;
        }

        // 将所有词性都添加复选框
        for (var w : WordClass.values())
        {
            addWordClassCheckBox(w.toString());
        }
        JScrollPane wordClassScrollPane = new JScrollPane(wordClassPanel);

        parameterPanel.setLayout(new GridLayout(3, 2));
        parameterPanel.add(wordNameLabel);
        parameterPanel.add(wordNameField);
        parameterPanel.add(wordClassLabel);
        parameterPanel.add(wordClassScrollPane);
        parameterPanel.add(meaningLabel);
        parameterPanel.add(meaningField);

        confirmButton.addActionListener(e -> confirmButtonAction());
        translateButton.addActionListener(e -> translateButtonAction());

        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(confirmButton);
        buttonPanel.add(translateButton);

        add(parameterPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * setTextAndCheckBox用于设置文本框和复选框的默认值
     * @param word 提供默认值的单词，如果不需要默认值的单词则传入null
     */
    public void setTextAndCheckBox(Word word)
    {
        if (word == null)
        {
            wordNameField.setText("");
            meaningField.setText("");
        }
        else
        {
            // TODO 复选框也加入记忆功能
            wordNameField.setText(word.getWordName());
            meaningField.setText(word.getMeaning());
        }
    }

    /**
     * addWordClassCheckBox用于添加一个指代词性的复选框
     * @param text 复选框的文本
     */
    private void addWordClassCheckBox(String text)
    {
        JCheckBox checkBox = new JCheckBox(text);
        wordClassPanel.add(checkBox);
    }

    /**
     * confirmButtonAction是confirmButton的动作
     */
    private void confirmButtonAction()
    {
        String wordName = wordNameField.getText();
        String wordMeaning  = meaningField.getText();
        List<WordClass> wordClasses = new ArrayList<>();
        AutoManager manager = new AutoManager(Memory.chosenWordListFile);

        // 假如单词信息不全，则不执行动作
        // 现在支持不输入汉义，由于MeaningField的默认值是""，因此即使不输入汉义也可以正常执行
        if (wordName.isEmpty())
        {
            return;
        }

        // 收集词性信息
        for (var component : wordClassPanel.getComponents())
        {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected())
            {
                wordClasses.add(WordClass.valueOf(checkBox.getText()));
            }
        }

        Word word = new Word.WordBuilder()
            .name(wordName)
            .meaning(wordMeaning)
            .lastReviewDate(LocalDate.now())
            .build();

        if (wordClasses.isEmpty())
        {
            WordHelper.setWordClass(word);
        }
        else
        {
            word.setWordClass(wordClasses.toArray(new WordClass[0]));
        }

        // 判断是插入还是修改模式，从而确定执行的动作
        if (insideMode == 0)
        {
            manager.insert(word);
        }
        else if (insideMode == 1)
        {
            manager.modify(Memory.word, word);
            Memory.word = word;  // 刷新选定单词信息
        }

        manager.push();
        Memory.globalRefreshWithWordListSync();  // 由于WordList改变，所以全局刷新时要同步WordList

        setVisible(false);
    }

    private void translateButtonAction()
    {
        meaningField.setText(Translate.filter(translate
                .translate(wordNameField.getText(), "en", "zh")));
    }
}
