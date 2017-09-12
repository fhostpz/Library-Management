import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Profile {
    private String loggedUsername;
    private JPanel profilePanel;
    private JButton changePasswordButton;
    private JButton backButton;
    private JButton editButton;
    private JLabel username_data;
    private JLabel name_data;
    private JButton logoutButton;

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public Profile(String loggedUsername, JPanel panel){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "main");

            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "changePassword");

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "editProfile");
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "login");

            }
        });

    }

    public Profile() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public JPanel getProfilePanel() {
        return profilePanel;
    }

    public void initUserData()
    {
        String userID, userName, userEmail;
        ArrayList<String> usernameArray = new ArrayList<String>();
        ArrayList<String> securityPinArray =new ArrayList<String>();
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

//        try
//        {
////            Class.forName(jdbcClassName);
////            conn = DriverManager.getConnection(url);
////
////            System.out.println("Creating statement...");
////            Statement st = conn.createStatement();
////
////            // Extract records in ascending order by first name.
////            System.out.println("Fetching records in ascending order...");
////            String sql = ("SELECT MB_ID, MB_NAME, MB_EMAIL from member");
////            ResultSet rs = st.executeQuery(sql);
////
////            while(rs.next())
////            {
////                userID = rs.getString(1);
////                userName = rs.getString(2);
////                userEmail = rs.getString(3);
//////                usernameArray.add(mahtext);
//////                securityPinArray.add(mahtext2);
////            }
////
////            for (int i = 0; i < usernameArray.size(); i++)
////            {
////                String text = inputUserName.getText();
////                String text1 = inputSecurityPin.getText();
////
////                if (text.equals(usernameArray.get(i)) && text1.equals(securityPinArray.get(i)))
////                {
////                    ForgotPassword_2 thePass = new ForgotPassword_2(text);
////                    System.out.println(text);
////                    thePass.setUsername(text);
////
////                    System.out.println("Correct");
////
////                }
////                else
////                {
////                    System.out.println("Hidung");
////                }
////            }
//        }
//        catch(ClassNotFoundException e)
//        {
//            e.printStackTrace();
//
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//
//        }
//        finally
//        {
//            if(conn != null)
//            {
//                System.out.println("Connection success!");
//            }
//        }
//
    }
}
