package com.potato.GUI.Dialog;

import com.potato.GUI.MainFrame;

import javax.swing.*;

public class PreferencesDialog extends JDialog
{
    private String[] configs = {"语言", "输出文件名", "输出文件作者", "输出文件标题"};

    private JPanel panel;

    public PreferencesDialog(MainFrame owner)
    {
        super(owner, "偏好设置", true);
        panel = new JPanel();
        // TODO 写完
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
