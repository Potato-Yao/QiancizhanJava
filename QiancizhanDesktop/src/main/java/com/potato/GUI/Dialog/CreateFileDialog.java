package com.potato.GUI.Dialog;

import com.potato.Config;
import com.potato.GUI.Memory;
import com.potato.ToolKit.DatabaseToolKit;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CreateFileDialog extends JDialog
{
    JTextField fileNameField;  // 用于输入单词本的名称
    JButton confirmButton;
    JLabel promptTextLabel;  // 提示信息

    /**
     * CreateFileDialog是新建单词本文件的界面
     * 输入一个文件名，它将在Config.NormalWordList指代的目录下创建一个对应的单词本文件
     * 默认的单词本文件是数据库格式，这里没有做其它格式是因为数据库是API指南的推荐格式，因此直接写死
     * 如果需要别的格式，请自己实现对应的类
     * @param owner 对话框所属的JFrame
     */
    public CreateFileDialog(JFrame owner)
    {
        super(owner, "新建单词本", true);
        setSize(300, 110);
        setLocationRelativeTo(null);

        fileNameField = new JTextField();
        promptTextLabel = new JLabel("请输入单词本名称");
        confirmButton = new JButton("确定");

        confirmButton.addActionListener(e -> confirmButtonAction());

        add(fileNameField, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
        add(promptTextLabel, BorderLayout.NORTH);
    }

    /**
     * confirmButtonAction是confirmButton的动作
     */
    private void confirmButtonAction()
    {
        String name = fileNameField.getText();  // 获取单词本名称

        // 卫语句，假如没有输入就不进行任何动作
        if (name.isEmpty())
        {
            return;
        }

        File file = new File(Config.normalWordListPath, name + ".db");  // 创建单词本文件

        // createInitialedDatabase会在两个情况给出false
        // 第一是名称重复，第二是创建操作失败
        // 第二个错误会直接报错（该方法有SneakyThrows注解），因此此处false一定是名称重复导致的
        if (!DatabaseToolKit.createInitialedDatabase(file, Config.getDatabaseType()))
        {
            JOptionPane.showMessageDialog(null, "已存在" + fileNameField.getText(),
                    "文件名重复", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            // 进入新建的文件并全局刷新
            Memory.setChosenWordListFile(file);
            Memory.globalRefresh();
        }
        setVisible(false);
    }

    /**
     * clearText用于清除文本框的文字
     */
    public void clearText()
    {
        fileNameField.setText("");
    }
}
