

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class main {
    public static void main (String [] args){
        String USER_ID = "null";
        System.out.println("System");
        login();
    }

    public static void login(){
        final JFrame window1 = new JFrame("Libray Login");
        window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(0,2);
        final CardLayout cardLayout = new CardLayout();
        final JPanel cardPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        mainPanel.setLayout(gridLayout);

        JLabel usernameLabel = new JLabel("Username ");
        JLabel passwordLabel = new JLabel("Password ");

        JButton acceptButton = new JButton("Accept");
        JButton fypButton = new JButton("Forgot Your Password");

        final JTextField inputUsername = new JTextField();
        final JTextField inputPassword = new JTextField();

        ForgotPassword forgotPasswordPage = new ForgotPassword(cardPanel);
        ForgotPassword_2 forgotPassword_2Page = new ForgotPassword_2(cardPanel);
        final JPanel forgotPasswordCard = forgotPasswordPage.getForgotPass_1();
        final JPanel forgotPassword_2Card = forgotPassword_2Page.getForgotPass_2();

        mainPanel.add(usernameLabel);
        mainPanel.add(inputUsername);
        mainPanel.add(passwordLabel);
        mainPanel.add(inputPassword);
        mainPanel.add(fypButton);
        mainPanel.add(acceptButton);

        cardPanel.add(mainPanel, "login");
        cardPanel.add(forgotPasswordCard, "forgotPassword");
        cardPanel.add(forgotPassword_2Card, "forgotPassword2");

        window1.add(cardPanel);
        window1.pack();
        window1.setSize(400,200);
        window1.setVisible(true);

        fypButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "forgotPassword");
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mahtext, mahtext2;
                ArrayList<String> usernameArray = new ArrayList<String>();
                ArrayList<String> passwordArray =new ArrayList<String>();
                String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
                String url = "jdbc:db2://localhost:50001/testlib";
                String user = "User";
                String password = "ting970926";
                Connection conn = null;

                try
                {
                    Class.forName(jdbcClassName);
                    conn = DriverManager.getConnection(url,user,password);

                    System.out.println("Creating statement...");
                    Statement st = conn.createStatement();

                    // Extract records in ascending order by first name.
                    System.out.println("Fetching records in ascending order...");
                    String sql = ("SELECT MB_NAME, MB_PASSWORD from member");
                    ResultSet rs = st.executeQuery(sql);

                    while(rs.next())
                    {
                        mahtext = rs.getString(1);
                        mahtext2 = rs.getString(2);
                        usernameArray.add(mahtext);
                        passwordArray.add(mahtext2);
                    }

                    for (int i = 0; i < usernameArray.size(); i++)
                    {
                        String text = inputUsername.getText();
                        String text1 = inputPassword.getText();

                        if (text.equals(usernameArray.get(i)) && text1.equals(passwordArray.get(i)))
                        {
                            window1.dispose();
                            JFrame window = new JFrame("Library Management System");
                            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            final CardLayout cardLayout = new CardLayout();
                            final JPanel mainPanel = new JPanel();

                            mainPanel.setLayout(cardLayout);
                            Login loginPage = new Login(inputUsername.getText(), mainPanel);

                            MainPage mainPage = new MainPage(inputUsername.getText(), mainPanel);
                            Profile profilePage = new Profile(inputUsername.getText(), mainPanel, window);
                            ChangePassword changePasswordPage = new ChangePassword(inputUsername.getText() ,mainPanel);
                            EditProfile editProfilePage = new EditProfile(inputUsername.getText(), mainPanel);
                            MaterialMainPage materialMainPage = new MaterialMainPage(mainPanel);
                            AddMaterial addMaterialPage = new AddMaterial(mainPanel, materialMainPage.getMaterialTable());
                            RemoveMaterial removeMaterialPage = new RemoveMaterial(mainPanel, materialMainPage.getMaterialTable());
                            Search searchPage = new Search(mainPanel);

                            JPanel loginCard = loginPage.getLoginPanel();
                            JPanel mainPageCard = mainPage.getMainPage();
                            JPanel profileCard = profilePage.getProfilePanel();
                            JPanel changePasswordCard = changePasswordPage.getChangePassword();
                            JPanel editProfileCard = editProfilePage.getEditProfile();
                            JPanel materialMainPageCard = materialMainPage.getMaterialMainPagePanel();
                            JPanel addMaterialPageCard = addMaterialPage.getAddMaterialPanel();
                            JPanel removeMaterialPageCard = removeMaterialPage.getRemoveMaterialPanel();
                            JPanel searchPageCard = searchPage.getSearchPanel();

                            mainPanel.add(mainPageCard, "main");
                            mainPanel.add(profileCard, "profile");
                            mainPanel.add(changePasswordCard, "changePassword");
                            mainPanel.add(editProfileCard, "editProfile");
                            mainPanel.add(forgotPasswordCard, "forgotPassword_1");
                            mainPanel.add(forgotPassword_2Card, "forgotPassword_2");
                            mainPanel.add(materialMainPageCard, "material main");
                            mainPanel.add(addMaterialPageCard, "add material");
                            mainPanel.add(removeMaterialPageCard, "remove material");
                            mainPanel.add(loginCard, "login");
                            mainPanel.add(searchPageCard, "search");

                            window.add(mainPanel);
                            window.setSize(800, 500);
                            window.setVisible(true);
                            System.out.println("Correct");
                        }
                        else
                        {
                            loginFail fail = new loginFail();

                            System.out.println("Hidung");
                        }
                    }
                }
                catch(ClassNotFoundException w)
                {
                    w.printStackTrace();
                }
                catch(SQLException w)
                {
                    w.printStackTrace();

                }
                finally
                {
                    if(conn != null)
                    {
                        try {
                            conn.close();
                        } catch (SQLException w){}
                        System.out.println("Connection success!");
                    }
                }

            }
        });
    }
}


