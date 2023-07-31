package com.potato

import com.formdev.flatlaf.FlatDarkLaf
import com.potato.GUI.MainFrame
import lombok.SneakyThrows
import java.awt.EventQueue
import javax.swing.UIManager

object Main
{
    @SneakyThrows
    @JvmStatic
    fun main(args: Array<String>)
    {
        println("Hello world!")
        Config.initial()
        UIManager.setLookAndFeel(FlatDarkLaf())
        EventQueue.invokeLater {
            val mainFrame = MainFrame()
            mainFrame.isVisible = true
        }
    }
}
