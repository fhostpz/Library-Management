package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginSuccess extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;


    public loginSuccess() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        pack();
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

//    public static void main(String[] args) {
//        loginSuccess dialog = new loginSuccess();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
