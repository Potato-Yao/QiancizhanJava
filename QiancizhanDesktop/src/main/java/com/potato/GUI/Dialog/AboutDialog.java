package com.potato.GUI.Dialog;

import com.potato.Config;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog
{
    private JPanel aboutPanel = new JPanel();
    private JLabel aboutLabel = new JLabel();
    private JTextArea linkArea = new JTextArea();
    private JLabel logoLabel = new JLabel();
    private JButton okButton = new JButton("确定");

    /**
     * AboutDialog是关于界面对话框
     * @param owner 对话框所属的JFrame
     */
    public AboutDialog(JFrame owner)
    {
        super(owner, "关于我们", true);
        setResizable(false);

        linkArea.setText("开源地址：https://github.com/Potato-Yao/QiancizhanJava");
        linkArea.setEditable(false);
        aboutPanel.setLayout(new GridLayout(2, 1));

        add(linkArea, BorderLayout.NORTH);
        add(aboutPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);

        aboutLabel.setText(getAboutText());
        aboutPanel.add(aboutLabel);

        // 查找照片并按照比例缩放（比例需自己计算）
        Image logoImage = new ImageIcon("./resources/西宁二中字.png")
                .getImage().getScaledInstance(384, 95, Image.SCALE_DEFAULT);
        logoLabel.setIcon(new ImageIcon(logoImage));
        aboutPanel.add(logoLabel);

        okButton.addActionListener(event -> setVisible(false));

        pack();  // 自动设置大小
        setLocationRelativeTo(null);
    }

    @SneakyThrows
    private String getAboutText()
    {
        StringBuilder about = new StringBuilder();
        about.append("<html><h3>千词斩由碳烤黄蜂、chuan_zhi开发</h3>");
        about.append("<br><h3>内核版本：");
        about.append(Config.version1);
        about.append("<br><h3>桌面界面版本：");
        about.append(Config.version2);

        about.append("</h3></html>");

        return about.toString();
    }
}
