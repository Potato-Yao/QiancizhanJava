package com.potato.GUI.Dialog;

import javax.swing.*;
import java.awt.*;

public class TextShownDialog extends JDialog
{
    private JTextArea textArea = new JTextArea();
    private JButton confirmButton = new JButton("确认");
    private JButton copyButton = new JButton("复制");
    private JPanel buttonPanel = new JPanel();

    public TextShownDialog(JFrame owner, String title)
    {
        super(owner, title, true);
        setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(copyButton);
        buttonPanel.add(confirmButton);

        add(textArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public void setText(String text)
    {
        textArea.setText(text);
    }
}
