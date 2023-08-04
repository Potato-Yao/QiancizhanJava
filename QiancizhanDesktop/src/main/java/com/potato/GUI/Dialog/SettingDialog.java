package com.potato.GUI.Dialog;

import com.potato.Config;
import com.potato.OptionType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingDialog extends JDialog
{
    private Map<String, String> setting;
    private Map<JTextArea, String> pair;

    private JPanel settingPanel;
    private JPanel buttonPanel;
    private JButton confirmButton;
    private JButton cancelButton;

    public SettingDialog(JFrame owner, OptionType optionType)
    {
        super(owner, "", true);
        setLayout(new BorderLayout());

        setting = Config.getOptions(optionType);
        pair = new HashMap<>();
        settingPanel = new JPanel(new GridLayout(setting.size(), 2));
        buttonPanel = new JPanel(new GridLayout(1, 2));
        confirmButton = new JButton("确认");
        cancelButton = new JButton("取消");

        confirmButton.addActionListener(e -> confirmButtonAction());
        cancelButton.addActionListener(e -> setVisible(false));

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        for (String t : setting.keySet())
        {
            addPair(t, setting.get(t));
        }

        add(settingPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void confirmButtonAction()
    {
        for (Object o : settingPanel.getComponents())
        {
            if (o instanceof JTextArea)
            {
                Config.update(pair.get(o), ((JTextArea) o).getText());
            }
        }

        setVisible(false);
    }

    private void addPair(String text, String value)
    {
        JLabel textLabel = new JLabel(text);
        JTextArea textArea = new JTextArea(value);

        pair.put(textArea, text);

        settingPanel.add(textLabel);
        settingPanel.add(textArea);
    }
}
