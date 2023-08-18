package com.potato.GUI.Dialog;

import com.potato.Translate.Translate;

import javax.swing.*;
import java.awt.*;

public class TranslateDialog extends JDialog
{
    /*
    翻译模式
    0为汉译英
    1为英译汉
     */
    private int translateMode;
    private JPanel radioPanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private ButtonGroup group = new ButtonGroup();
    private JButton translateButton = new JButton("翻译");
    private JTextArea fromArea = new JTextArea();
    private JTextArea toArea = new JTextArea();

    /**
     * TranslateDialog是翻译器工具的对话框
     * @param owner 对话框所属的JFrame
     */
    public TranslateDialog(JFrame owner)
    {
        super(owner, "翻译器", true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        add(radioPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(translateButton, BorderLayout.SOUTH);

        textPanel.setLayout(new GridLayout(2, 1));
        JScrollPane fromScrollPane = new JScrollPane(fromArea);
        JScrollPane toScrollPane = new JScrollPane(toArea);

        fromArea.setText("翻译源");
        toArea.setText("翻译结果");

        textPanel.add(fromScrollPane);
        textPanel.add(toScrollPane);

        addTransRadio("汉译英", true, 0);
        addTransRadio("英译汉", false, 1);

        translateButton.addActionListener(e -> translateButtonAction());
    }

    /**
     * translateButtonAction是translateButton的动作
     */
    private void translateButtonAction()
    {
        Translate translate = new Translate();
        String queryText = fromArea.getText();
        String resultText = "";

        if (translateMode == 0)  // 汉译英
        {
            resultText = Translate.filter(translate.translate(queryText, "zh", "en"));
        }
        else if (translateMode == 1)  // 英译汉
        {
            resultText = Translate.filter(translate.translate(queryText, "en", "zh"));
        }

        toArea.setText(resultText);
    }

    /**
     * addTransRadio用于设置翻译单选按钮
     * @param text 按钮的提示文本
     * @param selected 是否默认选择该按钮
     * @param mode 翻译模式
     */
    private void addTransRadio(String text, boolean selected, int mode)
    {
        JRadioButton radioButton = new JRadioButton(text, selected);

        group.add(radioButton);
        radioPanel.add(radioButton);

        radioButton.addActionListener(event ->
        {
            this.translateMode = mode;  // 切换翻译模式
        });
    }
}
