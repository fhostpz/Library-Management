package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class Profile {
    static String mahtext, mahtext2;
    static ArrayList<String> usernameArray = new ArrayList<String>();
    static ArrayList<String> passwordArray = new ArrayList<String>();
    private JLabel name;
    private JLabel username_output;
    private JLabel name_output;
    private JButton EditButton;
    private JButton loginButton;
    private JTextField enterUsername;
    public JPanel panelwow;
    private JPasswordField enterPassword;
    private JButton resetPasswordButton;


    public Profile() {
        enterUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = enterUsername.getText();
                System.out.println(text);
                if (text.equals(mahtext))
                {
                    System.out.println("Sucess");
                    loginSuccess okay = new loginSuccess();
                    okay.setTitle("Success");
                }
                else
                {
                    System.out.println("Cucked");
                }
                username_output.setText(text);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = enterUsername.getText();
                String text1 = enterPassword.getText();

                System.out.println("input: " +text);
                System.out.println("mahtext: " + mahtext);
                Boolean foundIt = false;
                for (int i = 0; i < usernameArray.size(); i++)
                {
                        if (text.equals(usernameArray.get(i)) && text1.equals(passwordArray.get(i)))
                        {
                            System.out.println("Sucess");
                            loginSuccess okay = new loginSuccess();
                            foundIt = true;
                        }
                }
                if (foundIt == false)
                {
                    System.out.println("Cucked");
                    loginFail okay = new loginFail();
                }
            }
        });

        enterPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = enterPassword.getText();
                System.out.println("input: " +text);
                System.out.println("mahtext: " + mahtext2);
                if (text.equals(mahtext2))
                {
                    System.out.println("Sucess");
                }
                else
                {
                    System.out.println("Cucked");
                }

            }
        });

        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPassDialog aDialog = new resetPassDialog();


            }
        });
    }

    public JPanel getPanelwow() {
        return panelwow;
    }

    public JLabel getName() {
        return name;
    }

    public JLabel getName_output() {
        return name_output;
    }

    public JButton getEditButton() {
        return EditButton;
    }

    public void setName_output(String name) {
        name_output.setText(name);
    }

    public static void main(String[] args) {

        String hisName;
        Profile por = new Profile();
        ResetPassword resetPass = new ResetPassword();
        JFrame frame = new JFrame("Profile");

        String url = "jdbc:sqlite:C://Users//User//testlib.db";

        Connection connection = null;

       try {
            //Load class into memory
            System.out.println("**** Loaded the JDBC driver");
            //Establish connection
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("What2");
            e.printStackTrace();
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
            System.out.println("Cuck boy_0");
            stmt = connection.createStatement();
            System.out.println("Cuck boy_1");
            ResultSet rs = stmt.executeQuery("SELECT username, password FROM member;");
            System.out.println("Cuck boy_2");

            while(rs.next())
            {
                System.out.println(rs.getString("username"));
                mahtext = rs.getString(1);
                mahtext2 = rs.getString(2);
                usernameArray.add(mahtext);
                passwordArray.add(mahtext2);
            }

            System.out.println("Cuck boy_3");
        }
        catch(Exception e)
        {
            System.out.println("General Cuck");
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        frame.setContentPane(new Profile().panelwow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
