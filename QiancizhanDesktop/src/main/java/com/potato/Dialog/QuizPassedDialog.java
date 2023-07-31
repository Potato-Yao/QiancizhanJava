package com.potato.GUI.Dialog;

import javax.swing.*;
import java.awt.*;

public class QuizPassedDialog extends JDialog
{
    private JButton confirmButton = new JButton("确定");
    private JButton exportAsPDFButton = new JButton("导出为PDF");
    private JButton exportAsExcelButton = new JButton("导出为Excel");
    private JLabel textLabel = new JLabel();
    private JPanel buttonPanel = new JPanel();

    public QuizPassedDialog(JFrame owner)
    {
        super(owner, "恭喜通过测验", true);
        setLayout(new BorderLayout());

        buttonPanel.add(exportAsPDFButton);
        buttonPanel.add(exportAsExcelButton);
        buttonPanel.add(confirmButton);

        confirmButton.addActionListener(e -> setVisible(false));

        add(textLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void exportAsPDFButtonAction()
    {
        // TODO 完成
    }
}
