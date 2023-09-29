package com.potato.GUI.Dialog;

import com.potato.OCRUtil.BaiduOCRParser;
import com.potato.OCRUtil.OCRParser;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;

public class TextShownDialog extends JDialog
{
    private OCRParser parser;

    private JTextArea textArea = new JTextArea();
    private JButton confirmButton = new JButton("确认");
    private JButton copyButton = new JButton("复制");
    private JPanel buttonPanel = new JPanel();

    public TextShownDialog(JFrame owner, String title)
    {
        super(owner, title, true);
        setLayout(new BorderLayout());
        setSize(400, 500);
        setLocationRelativeTo(null);
        parser = new BaiduOCRParser();

        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(copyButton);
        buttonPanel.add(confirmButton);

        JScrollPane pane = new JScrollPane(textArea);
        confirmButton.addActionListener(e -> confirmButtonAction());
        copyButton.addActionListener(e -> copyButtonAction());

        add(pane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setText(File image)
    {
        List<String> textList = parser.recognizeImage(image);
        StringBuilder text = new StringBuilder();

        for (String s : textList)
        {
            text.append(s);
            text.append("\n");
        }

        textArea.setText(text.toString());
    }

    private void confirmButtonAction()
    {
        textArea.setText("");
        setVisible(false);
    }

    private void copyButtonAction()
    {
        String selectedText = textArea.getSelectedText();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(selectedText);
        clipboard.setContents(stringSelection, null);
    }
}
