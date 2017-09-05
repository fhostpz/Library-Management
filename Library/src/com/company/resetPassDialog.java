package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class resetPassDialog extends JDialog {
    String mahtext = "default";
    int mahInt = 0;
    static ArrayList<String> usernameArray = new ArrayList<String>();
    static ArrayList<Integer> securityPinArray = new ArrayList<Integer>();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userName;
    private JTextField securityPin;

    public resetPassDialog() {
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
                    System.out.println("What2");
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
                    ResultSet rs = stmt.executeQuery("SELECT username, securityPin FROM member;");
                    while(rs.next())
                    {
                        System.out.println(rs.getString("username"));
                        mahtext = rs.getString(1);
                        mahInt = rs.getInt(2);
                        usernameArray.add(mahtext);
                        securityPinArray.add(mahInt);
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

                String text = userName.getText();
                String text1 = securityPin.getText();
                int results = Integer.parseInt(text1);

                Boolean foundIt = false;
                for (int i = 0; i < usernameArray.size(); i++)
                {
                    if (text.equals(usernameArray.get(i)) && results == securityPinArray.get(i))
                    {
                        System.out.println("Sucess");
                        resetPassEnterNewPass aDialog = new resetPassEnterNewPass();
                        aDialog.setUsername(text);
                        aDialog.pack();
                        aDialog.setVisible(true);
                        foundIt = true;
                    }
                }
                if (foundIt == false)
                {
                    System.out.println("Cucked");
                    loginFail okay = new loginFail();
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
