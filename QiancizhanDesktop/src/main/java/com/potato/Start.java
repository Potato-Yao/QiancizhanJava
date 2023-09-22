package com.potato;

import com.formdev.flatlaf.FlatDarkLaf;
import com.potato.GUI.MainFrame;
import com.potato.Log.Log;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Start
{
    @SneakyThrows
    public static void main(String[] args)
    {
       if (args.length == 0)
       {
           GUIStart();
       }

       Log.i(Start.class.toString(), "启动千词斩成功");
    }

    @SneakyThrows
    private static void GUIStart()
    {
        File configFile = new File(".", "config.json");
        Config.initial(configFile, null, null, null, null);
        UIManager.setLookAndFeel(new FlatDarkLaf());
        EventQueue.invokeLater(() ->
        {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });

        Log.i(Start.class.toString(), "以GUI模式启动软件");
    }
}
