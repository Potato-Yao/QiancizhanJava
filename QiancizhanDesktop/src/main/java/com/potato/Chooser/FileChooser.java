package com.potato.GUI.Chooser;

import com.potato.Config;

import javax.swing.*;
import java.io.File;

public class FileChooser extends JFileChooser
{
    private Config config;

    /**
     * FileChooser是一个文件选择器，可以用于打开或者保存文件
     * 举例来说，fileChooser.showOpenDialog(null)可以将FileChooser对象设置为打开文件模式
     * showSaveDialog(null)设置为保存文件模式
     */
    public FileChooser()
    {
        setMultiSelectionEnabled(false);
        setCurrentDirectory(new File(Config.normalWordListPath));
    }
}
