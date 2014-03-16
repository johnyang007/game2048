package com.john.yang.e2048.gui;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;


public class GameGui extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.W");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameGui frame = new GameGui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GameGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("\u7528\u6237\u540D");
        label.setBounds(79, 33, 54, 15);
        contentPane.add(label);

        textField = new JTextField();
        textField.setBounds(143, 30, 206, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel label_1 = new JLabel("\u5BC6  \u7801");
        label_1.setBounds(79, 89, 54, 15);
        contentPane.add(label_1);

        textField_1 = new JTextField();
        textField_1.setBounds(143, 86, 206, 21);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnNe = new JButton("\u767B\u9646");
        btnNe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNe.setBounds(107, 155, 93, 23);
        contentPane.add(btnNe);

        JButton button_1 = new JButton("\u5173\u95ED");
        button_1.setBounds(243, 155, 93, 23);
        contentPane.add(button_1);
    }
}
