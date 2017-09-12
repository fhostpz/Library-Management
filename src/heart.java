import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;

public class heart {


    public static void main (String [] args){
        String USER_ID = "null";
        Boolean done = false, flag = false;
        JFrame window = new JFrame("Library Management System");
        JFrame loginWindow = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final CardLayout cardLayout = new CardLayout();
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);

        //Pages
        MainPage mainPage = new MainPage(mainPanel);
        Login loginPage = new Login(USER_ID, mainPanel);
        System.out.println("Logged " + loginPage.getLoggedUsername());
        Profile profilePage = new Profile(USER_ID, mainPanel);
        ChangePassword changePasswordPage = new ChangePassword(mainPanel);
        EditProfile editProfilePage = new EditProfile(mainPanel);
        ForgotPassword forgotPasswordPage = new ForgotPassword(mainPanel);
        ForgotPassword_2 forgotPassword_2Page = new ForgotPassword_2(mainPanel);

        //Cards
        JPanel loginCard = loginPage.getLoginPanel();
        JPanel mainPageCard = mainPage.getMainPage();
        JPanel profileCard = profilePage.getProfilePanel();
        JPanel changePasswordCard = changePasswordPage.getChangePassword();
        JPanel editProfileCard = editProfilePage.getEditProfile();
        JPanel forgotPasswordCard = forgotPasswordPage.getForgotPass_1();
        JPanel forgotPassword_2Card = forgotPassword_2Page.getForgotPass_2();

        //Add Cards to mainPanel
        mainPanel.add(loginCard, "login");
        mainPanel.add(mainPageCard, "main");
        mainPanel.add(profileCard, "profile");
        mainPanel.add(changePasswordCard, "changePassword");
        mainPanel.add(editProfileCard, "editProfile");
        mainPanel.add(forgotPasswordCard, "forgotPassword_1");
        mainPanel.add(forgotPassword_2Card, "forgotPassword_2");

        window.add(mainPanel);
        window.setSize(500, 300);
        window.setVisible(true);
    }

}


