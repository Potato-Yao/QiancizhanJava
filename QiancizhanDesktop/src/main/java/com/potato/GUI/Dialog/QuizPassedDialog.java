package com.potato.GUI.Dialog;

import com.potato.GUI.Memory;

import javax.swing.*;
import java.awt.*;

public class QuizPassedDialog extends JDialog
{
    private QuizDialog quizDialog;
    private JButton confirmButton = new JButton("确定");
    private JButton exportAsPDFButton = new JButton("导出为PDF");
    private JButton exportAsExcelButton = new JButton("导出为Excel");
    private JLabel textLabel = new JLabel();
    private JPanel buttonPanel = new JPanel();

    public QuizPassedDialog(JFrame owner, QuizDialog quizDialog)
    {
        super(owner, "恭喜通过测试", true);
        setLayout(new BorderLayout());
        this.quizDialog = quizDialog;

        buttonPanel.add(exportAsPDFButton);
        buttonPanel.add(exportAsExcelButton);
        buttonPanel.add(confirmButton);

        confirmButton.addActionListener(e -> setVisible(false));

        add(textLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void initial()
    {
        String text = "<html><h3>恭喜通过" + Memory.chosenWordListFile.getName()
            + "的" + quizDialog.getDialogName() + "</h3><p>" +
            "<h3>用时" + quizDialog.getTimeCost() + "s，正确率" + quizDialog.getStatistic().get(3) +
            "%</h3></html>";

        textLabel.setText(text);

        pack();
        setLocationRelativeTo(null);
    }

    private void exportAsPDFButtonAction()
    {
        // TODO 完成
    }
}
