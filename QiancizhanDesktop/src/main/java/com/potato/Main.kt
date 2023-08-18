package com.potato

import com.formdev.flatlaf.FlatDarkLaf
import com.potato.GUI.MainFrame
import com.potato.Log.Log
import lombok.SneakyThrows
import java.awt.EventQueue
import java.io.File
import javax.swing.UIManager

object Main
{
    @SneakyThrows
    @JvmStatic
    fun main(args: Array<String>)
    {
        println("Hello world!")

        val configFile = File(".", "config.json")

        Config.initial(configFile, null, null, null, null)

        UIManager.setLookAndFeel(FlatDarkLaf())
        EventQueue.invokeLater {
            val mainFrame = MainFrame()
            mainFrame.isVisible = true
        }

        Log.i(javaClass.name, "创建主界面成功")
    }
}
