package com.potato;

import com.formdev.flatlaf.FlatDarkLaf;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

public class Main
{
    @SneakyThrows
    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        Config.initial();

        UIManager.setLookAndFeel(new FlatDarkLaf());

        EventQueue.invokeLater(() ->
        {
            com.potato.GUI.MainFrame mainFrame = new com.potato.GUI.MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
