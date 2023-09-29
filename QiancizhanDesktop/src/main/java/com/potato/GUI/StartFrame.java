package com.potato.GUI;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame
{
    @Getter
    private final int WIDTH = 300;

    @Getter
    private final int HEIGHT = 400;

    private final double factor = 0.7;

    public StartFrame()
    {
        setSize((int) (factor * WIDTH), (int) (factor * HEIGHT));  // 原始尺寸过大，因此设置一个缩放因子
        setTitle("千词斩");
        setLayout(new BorderLayout());
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // 居中
    }
}
