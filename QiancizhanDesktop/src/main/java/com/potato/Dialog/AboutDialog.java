package com.potato.GUI.Dialog;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog
{
    private JPanel aboutPanel = new JPanel();
    private JLabel aboutLabel = new JLabel();
    private JLabel logoLabel = new JLabel();
    private JButton okButton = new JButton("确定");

    /**
     * AboutDialog是关于界面对话框
     * @param owner 对话框所属的JFrame
     */
    public AboutDialog(JFrame owner)
    {
        super(owner, "关于我们", true);
        setResizable(false);

        aboutPanel.setLayout(new GridLayout(2, 1));

        add(aboutPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);

        aboutLabel.setText(getAboutText());
        aboutPanel.add(aboutLabel);

        // 查找照片并按照比例缩放（比例需自己计算）
        Image logoImage = new ImageIcon("./resources/西宁二中字.png")
                .getImage().getScaledInstance(384, 95, Image.SCALE_DEFAULT);
        logoLabel.setIcon(new ImageIcon(logoImage));
        aboutPanel.add(logoLabel);

        okButton.addActionListener(event -> setVisible(false));

        pack();  // 自动设置大小
        setLocationRelativeTo(null);
    }

    @SneakyThrows
    private String getAboutText()
    {
        StringBuilder version = new StringBuilder();
        version.append("<html><h3>此应用由碳烤黄蜂、chuan_zhi开发</h3>");
        version.append("<br><h3>开源地址：https://github.com/Potato-Yao/Qiancizhan2</h3>");
        version.append("<br><h3>版本");

        version.append("1.0.0b");

//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        File pom = new File(".", "pom.xml");
//        Document document = builder.parse(pom);
//        Element root = document.getDocumentElement();
//        NodeList nodeList = root.getChildNodes();
//
//        for (int i = 0; i < nodeList.getLength(); i++)
//        {
//            Node child = nodeList.item(i);
//
//            if (child.getNodeName().equals("version"))
//            {
//                version.append(child.getTextContent());
//                break;
//            }
//        }

        version.append("</h3></html>");

        return version.toString();
    }
}
