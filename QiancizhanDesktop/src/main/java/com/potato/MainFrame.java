package com.potato.GUI;

import com.potato.GUI.MenuBar.MainFrameMenuBar;
import com.potato.GUI.Panel.FunctionPanel;
import com.potato.GUI.Panel.ListPanel;
import com.potato.GUI.Panel.WordPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    @Getter
    private final int WIDTH = 1920;

    @Getter
    private final int HEIGHT = 1090;

    private final double factor = 0.5;

    private JPanel functionPanel;
    private JPanel wordPanel;
    private JPanel listPanel;
    private JMenuBar menuBar;
    private static JLabel pathLabel;

    /**
     * MainFrame是主界面
     */
    public MainFrame()
    {
        setSize((int) (factor * WIDTH), (int) (factor * HEIGHT));  // 原始尺寸过大，因此设置一个缩放因子
        setTitle("千词斩");
        setLayout(new BorderLayout());
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // 居中

        functionPanel = new FunctionPanel(this);
        wordPanel = new WordPanel(this);
        listPanel = new ListPanel(this);
        pathLabel = new JLabel("单词本目录将显示于此");
        menuBar = new MainFrameMenuBar(this);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./resources/千词斩logo.png");
        setIconImage(image);

        add(functionPanel, BorderLayout.WEST);
        add(wordPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.EAST);
        add(pathLabel, BorderLayout.NORTH);
        setJMenuBar(menuBar);
    }

    /**
     * 设置pathLabel的文本
     * @param text 设置的文本
     */
    public static void setPathLabelText(String text)
    {
        pathLabel.setText(text);
    }
}
