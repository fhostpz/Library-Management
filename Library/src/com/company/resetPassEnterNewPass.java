package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class resetPassEnterNewPass extends JDialog {
    String inputPassword;
    String inputPassword2;

    private String username;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField passwordInput;
    private JTextField passwordInput2;

    public void setUsername(String username) {
        this.username = username;
    }

    public resetPassEnterNewPass() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:sqlite:C://Users//User//testlib.db";
                Connection connection = null;
                try {
                    //Load class into memory
                    System.out.println("**** Loaded the JDBC driver");
                    //Establish connection
                    connection = DriverManager.getConnection(url);
                } catch (SQLException h) {

                }finally{
                    if(connection!=null){
                        System.out.println("Connected successfully.");
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();

//                }
                    }
                }

                Statement stmt = null;

                try{
                    stmt = connection.createStatement();
                    inputPassword = passwordInput.getText();
                    inputPassword2 = passwordInput2.getText();  

                    if (inputPassword.equals(inputPassword2))
                    {
                        System.out.println("Password the same");
                        PreparedStatement updatePass = null;
                        String updateString = "UPDATE member SET password = ? WHERE username = ? ;";
                        updatePass = connection.prepareStatement(updateString);
                        updatePass.setString(1, inputPassword2);
                        updatePass.setString(2, username);
                        updatePass.executeUpdate();
//                        ResultSet rs = stmt.executeQuery("UPDATE member SET password = ? +  WHERE username = ? ;");
                    }
                    else
                    {
                        System.out.println("Try again");
                    }


                }
                catch(Exception h)
                {
                    h.printStackTrace();
                }
                finally {
                    try {
                        connection.close();
                    } catch (SQLException h) {
                        h.printStackTrace();
                    }
                }

                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
