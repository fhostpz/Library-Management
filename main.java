import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class main {
  
  //main()
    public static void main(String[] args) {
    String USER_ID = "null";
    System.out.println("System");

    //Invokes login(), a function in this class.
    login();
    }

    public static void login() {
        final JFrame mainFrame = new JFrame("University Library Management System (ULMS v1.0)");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Sets up the panels for the main panel.
        // usernamePanel (a)
        // passwordPanel (b)
        // buttonsPanel  (c)
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(new Dimension(400,75));

        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernamePanel.setPreferredSize(new Dimension(400, 50));

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.setPreferredSize(new Dimension(400, 50));

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setPreferredSize(new Dimension(400, 50));

        // ( ) Sets up the label in the blank panel.
        JLabel welcomeMessage = new JLabel("<html><center>Welcome to the<br>University Library Management System<br>(ULMS v1.0)</center><html>");
        blankPanel.add(welcomeMessage);

        // (a) Sets up the label in the username panel.
        JLabel usernameLabel = new JLabel("Username ");
        usernamePanel.add(usernameLabel);

        // (a) Sets up the text field for the user to input his username.
        final JTextField inputUsername = new JTextField();
        inputUsername.setPreferredSize(new Dimension(200, 30));
        usernamePanel.add(inputUsername);

        // (b) Sets up the label in the password panel.
        JLabel passwordLabel = new JLabel("Password ");
        passwordPanel.add(passwordLabel);

        // (b) Sets up the text field for the user to input his password.
        final JTextField inputPassword = new JTextField();
        inputPassword.setPreferredSize(new Dimension(200, 30));
        passwordPanel.add(inputPassword);

        // (c) Sets up the component in the buttons panel.
        JButton fypButton = new JButton("Forgot Password");
        JButton accButton = new JButton("Accept");
        fypButton.setPreferredSize(new Dimension(135, 25));
        accButton.setPreferredSize(new Dimension(135, 25));
        buttonsPanel.add(fypButton);
        buttonsPanel.add(accButton);

        //Set row to be unknown.
        //Set the mainPanel to use the grid layout.
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(500, 300));
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(blankPanel);
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(buttonsPanel);

        //Sets a card layout.
        final CardLayout cardLayout = new CardLayout();

        JPanel LoginPanel = new JPanel(new FlowLayout());
        LoginPanel.setPreferredSize(new Dimension(1024, 640));

        //Set the cardPanel to use the card layout.
        final JPanel cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        LoginPanel.add(cardPanel);

        ForgotPassword forgotPasswordPage = new ForgotPassword(cardPanel);
        ForgotPassword_2 forgotPassword_2Page = new ForgotPassword_2(cardPanel);
        final JPanel forgotPasswordCard = forgotPasswordPage.getForgotPass_1();
        final JPanel forgotPassword_2Card = forgotPassword_2Page.getForgotPass_2();

        cardPanel.add(mainPanel, "login");
        cardPanel.add(forgotPasswordCard, "forgotPassword");
        cardPanel.add(forgotPassword_2Card, "forgotPassword2");

        mainFrame.add(LoginPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);

        fypButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "forgotPassword");
            }
        });

        //Sets up the action listeners for the accept button.
        accButton.addActionListener(new ActionListener() {
        @Override
                public void actionPerformed(ActionEvent e) {
                String memberName;
                String memberPIN;

                ArrayList<String> usernames = new ArrayList<String>();
                ArrayList<String> passwords = new ArrayList<String>();

                String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
                String url = "jdbc:db2:testlib";
                Connection conn = null;

                try {
                    Class.forName(jdbcClassName);
                    conn = DriverManager.getConnection(url);

                    System.out.println("DEBUG: Creating statement.");
                    Statement st = conn.createStatement();

                    //Extract records in ascending order by first name.
                    //Stores SQL command in a String variable.
                    //Execute the SQL command.
                    System.out.println("DEBUG: Fetching records in ascending order.");
                    String sqlCommand = ("SELECT MB_NAME, MB_PASSWORD from member");
                    ResultSet rs = st.executeQuery(sqlCommand);

                    while (rs.next()) {
                    //Gets the MB_NAME, and MB_PIN of a row to these two String variable.
                    //Stores the two String variables to these two String ArrayList.
                    memberName = rs.getString(1);
                    memberPIN = rs.getString(2);
                    usernames.add(memberName);
                    passwords.add(memberPIN);
                }

                for(int i = 0; i < usernames.size(); i++) {
                    //Gets the username, and password typed by the user.
                    String typedUsername = inputUsername.getText();
                    String typedPassword = inputPassword.getText();

                    if ((typedUsername.equals(usernames.get(i))) && (typedPassword.equals(passwords.get(i)))) {
                        //Once user credentials are validated, terminate the login windows.
                        //User are granted access.
                        mainFrame.dispose();

                        JFrame window = new JFrame("ULMS v1.0");
                        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        //final CardLayout cardLayout = new CardLayout();
                        final JPanel mainPanel = new JPanel();

                        mainPanel.setLayout(cardLayout);

                        MainPage mainPage = new MainPage(inputUsername.getText(), mainPanel);
                        Profile profilePage = new Profile(inputUsername.getText(), mainPanel, window);
                        ChangePassword changePasswordPage = new ChangePassword(inputUsername.getText(), mainPanel);
                        EditProfile editProfilePage = new EditProfile(inputUsername.getText(), mainPanel);
                        MaterialMainPage materialMainPage = new MaterialMainPage(mainPanel);
                        AddMaterial addMaterialPage = new AddMaterial(mainPanel, materialMainPage.getMaterialTable());
                        RemoveMaterial removeMaterialPage = new RemoveMaterial(mainPanel, materialMainPage.getMaterialTable());

                        JPanel mainPageCard = mainPage.getMainPage();
                        JPanel profileCard = profilePage.getProfilePanel();
                        JPanel changePasswordCard = changePasswordPage.getChangePassword();
                        JPanel editProfileCard = editProfilePage.getEditProfile();
                        JPanel materialMainPageCard = materialMainPage.getMaterialMainPagePanel();
                        JPanel addMaterialPageCard = addMaterialPage.getAddMaterialPanel();
                        JPanel removeMaterialPageCard = removeMaterialPage.getRemoveMaterialPanel();

                        mainPanel.add(mainPageCard, "main");
                        mainPanel.add(profileCard, "profile");
                        mainPanel.add(changePasswordCard, "changePassword");
                        mainPanel.add(editProfileCard, "editProfile");
                        mainPanel.add(forgotPasswordCard, "forgotPassword_1");
                        mainPanel.add(forgotPassword_2Card, "forgotPassword_2");
                        mainPanel.add(materialMainPageCard, "material main");
                        mainPanel.add(addMaterialPageCard, "add material");
                        mainPanel.add(removeMaterialPageCard, "remove material");

                        window.add(mainPanel);
                        window.setSize(1024, 640);
                        window.setVisible(true);
                        System.out.println("DEBUG: Homepage is now showing.");
                    }
                    else {
                        loginFail fail = new loginFail();

                        System.out.println("DEBUG: User Failed to sign in.");
                    }
                }
                }   catch (ClassNotFoundException w) {
                    w.printStackTrace();
                } catch (SQLException w) {
                    w.printStackTrace();
                }
                finally {
                        if (conn != null) {
                            try {
                            conn.close();
                        } catch (SQLException w) {
                        }
                        System.out.println("DEBUG: Connection close is successful!");
                    }
                }
            }
        });
    }
}


