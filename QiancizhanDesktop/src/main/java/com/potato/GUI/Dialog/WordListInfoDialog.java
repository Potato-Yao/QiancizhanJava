package com.potato.GUI.Dialog;

import com.potato.Parser.AutoParser;
import com.potato.ToolKit.History;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Vector;

public class WordListInfoDialog extends JDialog
{
    private File wordListFile;
    private AutoParser parser;

    private JLabel langLabel;
    private String[] columnName = {"复习日期", "单词总数", "正确个数", "错误个数", "用时（毫秒）"};
    private Vector<Object> columnNames = new Vector<>(List.of(columnName));
    private DefaultTableModel tableModel;
    private JTable historyTable;
    private JButton confirmButton;

    public WordListInfoDialog(JFrame owner)
    {
        super(owner);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        langLabel = new JLabel();

        historyTable = new JTable(tableModel)
        {
            @Override
            public boolean isCellEditable(int row, int column)  // 设置表格内容不可编辑
            {
                return false;
            }
        };
        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  // 允许自动对所有列调整大小
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 只能单选


        confirmButton = new JButton("确认");
        confirmButton.addActionListener(e -> setVisible(false));

        JScrollPane scrollPane = new JScrollPane(historyTable);

        add(langLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
    }

    public void refresh(File file)
    {
        wordListFile = file;
        parser = new AutoParser(file);

        setTitle(String.format("单词本%s的信息", wordListFile.getName()));
        tableModel.setDataVector(getHistory(), columnNames);
        langLabel.setText("单词本语言：" + parser.getInfo().language());

        pack();
        setLocationRelativeTo(null);
    }

    private Vector getHistory()
    {
        Vector history = new Vector<>();

        for (History h : parser.getHistoryList())
        {
            Vector v = new Vector<>();
            v.add(h.date());
            v.add(h.sumCount());
            v.add(h.correctCount());
            v.add(h.wrongCount());
            v.add(h.timeCost());

            history.add(v);
        }

        return history;
    }
}
