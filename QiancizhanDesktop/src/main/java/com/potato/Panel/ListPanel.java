package com.potato.GUI.Panel;

import com.potato.Config;
import com.potato.GUI.Memory;
import com.potato.ToolKit.FileToolKit;
import com.potato.Word.Word;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;

public class ListPanel extends JPanel
{
    private JTable listTable;
    private JButton backButton = new JButton("回退");
    private static Vector<Object> columnForFile;  // 显示文件时的列名
    private static Vector<Object> columnForWordList;  // 显示单词时的列名
    private static DefaultTableModel tableModel;
    private static File[] files;  // NormalWordList下所有文件

    /**
     * ListPanel是列表所在平面
     * @param owner 平面所属的JFrame
     */
    public ListPanel(JFrame owner)
    {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        files = new File(Config.normalWordListPath).listFiles();

        columnForFile = new Vector<>();
        columnForFile.add("单词本");
        columnForWordList = new Vector<>();
        columnForWordList.add("单词");
        columnForWordList.add("汉义");

        refresh();  // 初始化
        listTable = new JTable(tableModel)
        {
            @Override
            public boolean isCellEditable(int row, int column)  // 设置表格内容不可编辑
            {
                return false;
            }
        };
        listTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  // 允许自动对所有列调整大小
        listTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能单选

        listTable.addMouseListener(new ListTableMouseAction());

        backButton.addActionListener(e -> Memory.backToFilesList());

        // TODO 大小应该通过比例调节
        JScrollPane scrollPane = new JScrollPane(listTable);
        scrollPane.setPreferredSize(new Dimension(200, 575));

        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    /**
     * ListTableMouseAction定义listTable的鼠标动作
     */
    private class ListTableMouseAction implements MouseListener
    {
        /**
         * 按下鼠标的动作
         * @param e 要处理的事件
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (Memory.mode == 0)  // 选择单词本模式下点击的作用是进入单词本
            {
                enterWordList();
            }
            else if (Memory.mode == 1)  // 单词本模式下点击的作用是选择单词
            {
                // 改变选中的单词，这个方法会刷新WordPanel
                Memory.changeWord(listTable.getSelectedRow());
            }
        }

        /**
         * 进入单词本
         */
        private void enterWordList()
        {
            int index = listTable.getSelectedRow();  // 获得选中行的索引
            Memory.setChosenWordListFile(files[index]);  // 改变选择的文件
            tableModel.setDataVector(getWords(), columnForWordList);  // 列表数据刷新为选定文件下的单词数据
            Memory.globalRefresh();
        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    /**
     * 刷新表格内容
     */
    public static void refresh()
    {
        if (Memory.mode == 0)  // 0模式下数据是单词本文件列表
        {
            tableModel.setDataVector(getFilesName(), columnForFile);
        }
        else if (Memory.mode == 1)  // 1模式下数据是单词本的单词列表
        {
            tableModel.setDataVector(getWords(), columnForWordList);
        }
    }

    /**
     * 获取NormalWordList下所有单词本
     * @return 所有单词本名称组成的Vector
     */
    private static Vector getFilesName()
    {
        Vector filesName = new Vector<>();
        files = new File(Config.normalWordListPath).listFiles();

        assert files != null;
        for (File file : files)
        {
            String extension = FileToolKit.getExtensionName(file);

            // 筛选出是单词本文件的文件
            if (extension.equals("db") || extension.equals("json") ||
                extension.equals("xls") || extension.equals("xlsx"))
            {
                Vector v = new Vector<>();
                v.add(file.getName());
                filesName.add(v);
            }
        }

        return filesName;
    }

    /**
     * 获取单词本中所有单词
     * @return 所有单词本身和汉义构成的Vector
     */
    private static Vector getWords()
    {
        Vector words = new Vector<>();

        for (Word word : Memory.wordList)
        {
            Vector row = new Vector<>();
            row.add(word.getWordName());
            row.add(word.getMeaning());
            words.add(row);
        }

        return words;
    }
}
