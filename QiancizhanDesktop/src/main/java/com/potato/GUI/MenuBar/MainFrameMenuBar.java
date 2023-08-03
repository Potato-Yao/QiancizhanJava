package com.potato.GUI.MenuBar;

import com.potato.Config;
import com.potato.GUI.Chooser.FileChooser;
import com.potato.GUI.Dialog.AboutDialog;
import com.potato.GUI.Dialog.CreateFileDialog;
import com.potato.GUI.Dialog.InsertWordDialog;
import com.potato.GUI.Dialog.TranslateDialog;
import com.potato.GUI.Memory;
import com.potato.GUI.Panel.FunctionPanel;
import com.potato.Manager.AutoManager;
import com.potato.OCRUtil.OCRReader;
import com.potato.ToolKit.DatabaseToolKit;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.PDFExport;
import com.potato.Word.Word;
import com.potato.Word.WordHelper;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class MainFrameMenuBar extends JMenuBar
{
    private JMenu fileMenu = new JMenu("文件");
    private JMenu editMenu = new JMenu("编辑");
    private JMenu translateMenu = new JMenu("翻译");
    private JMenu settingMenu = new JMenu("设置");
    private JMenu helpMenu = new JMenu("帮助");

    // 文件菜单
    private JMenuItem createFileItem = new JMenuItem("新建");
    private JMenuItem openFileItem = new JMenuItem("打开");
    private JMenuItem openStandingItem = new JMenuItem("打开长期单词本");
    private JMenuItem exportPDFItem = new JMenuItem("导出为PDF");
    private JMenuItem exportExcelItem = new JMenuItem("导出为Excel");
    private JMenuItem ocrItem = new JMenuItem("从图像获取单词本");
    private JMenuItem excelItem = new JMenuItem("从Excel获取单词本");

    // 编辑菜单
    private JMenuItem insertWordItem = new JMenuItem("插入单词");
    private JMenuItem deleteWordItem = new JMenuItem("删除单词");
    private JMenuItem modifyWordItem = new JMenuItem("修改单词");
    private JMenuItem transformItem = new JMenuItem("补全单词汉义");

    // 翻译菜单
    private JMenuItem transItem = new JMenuItem("打开翻译器");

    // 设置菜单
    private JMenuItem preferencesItem = new JMenuItem("偏好设置");
    private JMenuItem  advancedSettingItem  = new JMenuItem("高级设置");

    // 帮助菜单
    private JMenuItem checkUpdateItem = new JMenuItem("检查更新");
    private JMenuItem aboutItem = new JMenuItem("关于");

    private CreateFileDialog createFileDialog;
    private InsertWordDialog insertWordDialog;
    private InsertWordDialog modifyWordDialog;
    private FileChooser fileChooser;
    private AboutDialog aboutDialog;
    private TranslateDialog translateDialog;

    /**
     * MainFrameMenuBar是主界面的菜单栏
     * @param owner 菜单栏所属的JFrame
     */
    public MainFrameMenuBar(JFrame owner)
    {
        createFileDialog = new CreateFileDialog(owner);
        insertWordDialog = new InsertWordDialog(owner, "插入单词");
        modifyWordDialog = new InsertWordDialog(owner, "修改单词");
        fileChooser = new FileChooser();
        aboutDialog = new AboutDialog(owner);
        translateDialog = new TranslateDialog(owner);

        fileMenu.add(createFileItem);
        fileMenu.add(openFileItem);
        fileMenu.add(openStandingItem);
        fileMenu.add(exportPDFItem);
        fileMenu.add(exportExcelItem);
        fileMenu.add(ocrItem);
        fileMenu.add(excelItem);

        createFileItem.addActionListener(e -> createFileItemAction());

        openFileItem.addActionListener(e ->
        {
            fileChooser.showOpenDialog(null);
            Memory.setChosenWordListFile(fileChooser.getSelectedFile());
        });

        openStandingItem.addActionListener(e ->
            Memory.setChosenWordListFile(Config.getStandingWordListFile()));

        exportPDFItem.addActionListener(e ->
        {
            if (Memory.mode == 1)
            {
                PDFExport export = new PDFExport(Memory.chosenWordListFile, false);
                export.smartExport();
                JOptionPane.showMessageDialog(null, "导出已完成",
                        "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        ocrItem.addActionListener(e -> OCRItemAction());

        exportExcelItem.setEnabled(false);
        excelItem.setEnabled(false);

        editMenu.add(insertWordItem);
        editMenu.add(deleteWordItem);
        editMenu.add(modifyWordItem);
        editMenu.add(transformItem);

        insertWordItem.addActionListener(e ->
        {
            if (Memory.mode == 0)  // 0是选择单词本目录模式，此时不做任何动作
            {
                return;
            }
            insertWordDialog.setTextAndCheckBox(null);
            insertWordDialog.setVisible(true);
        });

        deleteWordItem.addActionListener(e -> FunctionPanel.deleteAction());

        modifyWordItem.addActionListener(e ->
        {
            if (Memory.mode == 0)  // 0是选择单词本目录模式，此时不做任何动作
            {
                return;
            }
            modifyWordDialog.setTextAndCheckBox(Memory.word);
            modifyWordDialog.setVisible(true);
        });

        transformItem.addActionListener(e ->
        {
            if (Memory.mode == 0)  // 0是选择单词本目录模式，此时不做任何动作
            {
                return;
            }
            transformItemAction();
        });

        translateMenu.add(transItem);

        transItem.addActionListener(e -> translateDialog.setVisible(true));

        settingMenu.add(preferencesItem);
        settingMenu.add(advancedSettingItem);

        helpMenu.add(checkUpdateItem);
        helpMenu.add(aboutItem);

        checkUpdateItem.setEnabled(false);
        aboutItem.addActionListener(e -> aboutDialog.setVisible(true));

        add(fileMenu);
        add(editMenu);
        add(translateMenu);
        add(settingMenu);
        add(helpMenu);
    }

    private void OCRItemAction()
    {
        fileChooser.showOpenDialog(null);
        File image = fileChooser.getSelectedFile();
        OCRReader reader = new OCRReader();
        List<Word> wordList = reader.recognizeWordList(image);

        if (Memory.mode == 0)
        {
            String name = FileToolKit.getNameWithoutExtension(image);
            File database = new File(Config.normalWordListPath, name + ".db");

            if (!DatabaseToolKit.createInitialedDatabase(database, Config.getDatabaseType()))
            {
                // TODO log
            }
            else
            {
                AutoManager manager = new AutoManager(database);

                for (Word w : wordList)
                {
                    WordHelper.autoWordClass(w);
                    manager.insert(w);
                }
                manager.push();

                Memory.setChosenWordListFile(database);
                Memory.globalRefresh();
            }
        }
        else if (Memory.mode == 1)
        {
            AutoManager manager = new AutoManager(Memory.chosenWordListFile);

            for (Word w : wordList)
            {
                WordHelper.autoWordClass(w);
                manager.insert(w);
            }
            manager.push();

            Memory.globalRefreshWithWordListSync();
        }
    }

    /**
     * CreateFileItem的动作
     */
    private void createFileItemAction()
    {
        createFileDialog.clearText();  // 清除上次留下的文本
        createFileDialog.setVisible(true);
    }

    private void transformItemAction()
    {
        List<Integer> indexList = new ArrayList<>();
        List<Word> transNeededList = new ArrayList<>();
        AutoManager manager = new AutoManager(Memory.chosenWordListFile);

        for (int i = 0; i < Memory.wordList.size(); i++)
        {
            if (Memory.wordList.get(i).getMeaning().isEmpty())
            {
                indexList.add(i);
                transNeededList.add(Memory.wordList.get(i));
            }
        }

        String[] resultList = WordHelper.transWords(transNeededList);

        for (int i = 0; i < indexList.size(); i++)
        {
            Word w = Memory.wordList.get(indexList.get(i));
            w.setMeaning(resultList[i]);
            manager.modify(Memory.wordList.get(indexList.get(i)), w);
        }

        manager.push();
        Memory.globalRefreshWithWordListSync();
    }
}
