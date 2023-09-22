package com.potato.GUI.Panel;

import com.potato.GUI.Dialog.InsertWordDialog;
import com.potato.GUI.Dialog.OneOfFourQuizDialog;
import com.potato.GUI.Dialog.SpellQuizDialog;
import com.potato.GUI.Dialog.TranslateDialog;
import com.potato.GUI.Memory;
import com.potato.Manager.AutoManager;

import javax.swing.*;
import java.awt.*;

public class FunctionPanel extends JPanel
{
    private JButton insertButton = new JButton("插入单词");
    private JButton deleteButton = new JButton("删除单词");
    private JButton modifyButton = new JButton("修改单词");
    private JButton oneOfFourTestButton = new JButton("开始四选一测试");
    private JButton spellTestButton = new JButton("开始默写测试");
    private JButton translateButton = new JButton("翻译器");

    private InsertWordDialog insertWordDialog;
    private InsertWordDialog modifyWordDialog;
    private TranslateDialog translateDialog;
    private OneOfFourQuizDialog oneOfFourQuizDialog;
    private SpellQuizDialog spellQuizDialog;

    /**
     * FunctionPanel是功能按钮的平面
     * @param owner 平面所属的JFrame
     */
    public FunctionPanel(JFrame owner)
    {
        setLayout(new GridLayout(6, 1));

        insertWordDialog = new InsertWordDialog(owner, "插入单词", false);
        modifyWordDialog = new InsertWordDialog(owner, "修改单词", false);
        translateDialog = new TranslateDialog(owner);
        oneOfFourQuizDialog = new OneOfFourQuizDialog(owner);
        spellQuizDialog = new SpellQuizDialog(owner);

        insertButton.addActionListener(e ->
        {
            if (Memory.mode == 0)  // 0是选择单词本目录模式，此时不做任何动作
            {
                return;
            }
            insertWordDialog.setTextAndCheckBox(null);
            insertWordDialog.setVisible(true);
        });

        deleteButton.addActionListener(e -> deleteAction());

        modifyButton.addActionListener(e ->
        {
            if (Memory.mode == 0)  // 0是选择单词本目录模式，此时不做任何动作
            {
                return;
            }
            modifyWordDialog.setTextAndCheckBox(Memory.word);
            modifyWordDialog.setVisible(true);
        });

        translateButton.addActionListener(e -> translateDialog.setVisible(true));

        oneOfFourTestButton.addActionListener(e ->
        {
            if (Memory.mode == 1)  // 1是单词本下选择单词的模式
            {
                oneOfFourQuizDialog.initial();
                oneOfFourQuizDialog.setVisible(true);
            }
        });

        spellTestButton.addActionListener(e ->
        {
            if (Memory.mode == 1)  // 1是单词本下选择单词的模式
            {
                spellQuizDialog.initial();
                spellQuizDialog.setVisible(true);
            }
        });

        add(insertButton);
        add(deleteButton);
        add(modifyButton);
        add(oneOfFourTestButton);
        add(spellTestButton);
        add(translateButton);
    }

    /**
     * deleteAction用于删除单词
     */
    public static void deleteAction()
    {
        if (Memory.mode == 0)
        {
            return;
        }

        if (Memory.word != null)
        {
            AutoManager manager = new AutoManager(Memory.chosenWordListFile);
            manager.delete(Memory.word);
            manager.push();
        }
        Memory.word = null;  // 清空选择的单词
        Memory.globalRefreshWithWordListSync();
    }
}
