package com.potato.GUI.Dialog;

import com.potato.Config;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class ExportFileDialog extends JDialog
{
    private JLabel authorLabel;
    private JTextArea authorArea;
    private JLabel titleLabel;
    private JTextArea titleArea;
    private JButton normalButton;
    private JButton confirmButton;

    @Getter
    String title;

    @Getter
    String author;

    public ExportFileDialog(JFrame owner, String fileType)
    {
        super(owner, String.format("导出%s文件", fileType), true);
        setLayout(new GridLayout(3, 2));

        authorLabel = new JLabel("输出文件作者：");
        authorArea = new JTextArea();
        titleLabel = new JLabel("输出文件标题：");
        titleArea = new JTextArea();
        normalButton = new JButton("使用默认配置");
        confirmButton = new JButton("确认");

        normalButton.addActionListener(e -> normalButtonAction());
        confirmButton.addActionListener(e -> confirmButtonAction());

        add(authorLabel);
        add(authorArea);
        add(titleLabel);
        add(titleArea);
        add(normalButton);
        add(confirmButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void normalButtonAction()
    {
        title = Config.title;
        author = Config.author;

        setVisible(false);
    }

    private void confirmButtonAction()
    {
        if (authorArea.getText().isEmpty() || titleArea.getText().isEmpty())
        {
            return;
        }

        title = titleArea.getText();
        author = authorArea.getText();

        setVisible(false);
    }
}
