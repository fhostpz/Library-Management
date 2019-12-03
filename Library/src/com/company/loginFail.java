package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginFail extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public loginFail() {
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

}
