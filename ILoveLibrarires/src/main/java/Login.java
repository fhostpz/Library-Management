import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Login {
    private String loggedUsername = "null";
    private Boolean loginComplete = true;
    private JPanel LoginPanel;
    private JTextField inputUsername;
    private JTextField inputPassword;
    private JButton forgotYourPasswordButton;
    private JButton loginButton;
    private JLabel userName;
    private JLabel passWord;

    public Boolean getLoginComplete() {
        return loginComplete;
    }

    public void setInputUsername(JTextField inputUsername) {
        this.inputUsername = inputUsername;
    }

    public JTextField getInputUsername() {
        return inputUsername;
    }

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public Login(String username, final JPanel panel) {
        loggedUsername = username;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (credentialsValid() == true)
                {
                    Profile f = new Profile();
                    f.setLoggedUsername(loggedUsername);
                    System.out.println(loggedUsername);
                    inputUsername.setText("");
                    inputPassword.setText("");
                    CardLayout cardLayout = (CardLayout) panel.getLayout();
                    cardLayout.show(panel, "profile");
                }
                else
                {
                    MessageDialog fail = new MessageDialog();
                    fail.setLocationRelativeTo(userName);
                    fail.pack();
                    fail.show();
                }
            }
        });
        forgotYourPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "forgotPassword_1");
            }
        });
    }

    public JPanel getLoginPanel() {
        return LoginPanel;
    }

    public Boolean credentialsValid(){
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
                    setLoggedUsername(text);
                    System.out.println("Correct");
                    return true;
                }
                else
                {
                    System.out.println("Hidung");
                    return false;
                }
            }
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            if(conn != null)
            {
                System.out.println("Connection success!");
            }
        }
        return false;
    }
}
