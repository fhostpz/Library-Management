import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ChangePassword {
    private String loggedUser;
    private JPanel changePassword;
    private JTextField inputCurPass;
    private JTextField inputNewPass;
    private JTextField inputRePass;
    private JButton acceptButton;
    private JButton cancelButton;


    public ChangePassword(String username, final JPanel panel) {
        loggedUser = username;
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.previous(panel);


            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if the Current password Entered is correct
                //Check if the two new password entered match
                if (credentialsValid())
                {
                    System.out.println("Credentials Valid");
                    if (passwordEqual())
                    {
                        setNewPassword();
                    }
                    else
                    {
                        loginFail fail = new loginFail();
                        fail.setLocationRelativeTo(changePassword);
                        fail.setMessage2("New Password or Reentered Password is incorrect");
                        fail.pack();
                        fail.show();
                        System.out.println("Error Password Entered does not match.");
                    }
                }
                else
                {
                    loginFail fail = new loginFail();
                    fail.setLocationRelativeTo(changePassword);
                    fail.setMessage2("Current Password Entered is incorrect.");
                    fail.pack();
                    fail.show();
                    System.out.println("Error Password Entered not Correct.");
                }
            }
        });
    }

    public JPanel getChangePassword() {
        return changePassword;
    }

    public Boolean passwordEqual() {
        if (inputNewPass.getText().equals(inputRePass.getText()) && !inputNewPass.getText().equals(""))
        {
            System.out.println("Password Match");
            return true;
        }
        else
        {
            System.out.println("Password Doesn't Match");
            return false;
        }
    }

    public Boolean credentialsValid(){
        String mahtext, mahtext2;
        ArrayList<String> usernameArray = new ArrayList<String>();
        ArrayList<String> passwordArray =new ArrayList<String>();
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
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
                String text = loggedUser;
                System.out.println("Logged User is " + text);
                String text1 = inputCurPass.getText();

                if (text.equals(usernameArray.get(i)) && text1.equals(passwordArray.get(i)))
                {
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

    public void setNewPassword(){
        String mahtext, mahtext2;
        ArrayList<String> usernameArray = new ArrayList<String>();
        ArrayList<String> passwordArray =new ArrayList<String>();
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
            System.out.println("Creating statement...");
            Statement st = conn.createStatement();

            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");

            PreparedStatement updatePass = null;
            String updateString = "UPDATE member SET MB_PASSWORD = ? WHERE MB_NAME = ? ";
            updatePass = conn.prepareStatement(updateString);
            updatePass.setString(1, inputNewPass.getText());
            System.out.println(loggedUser);
            updatePass.setString(2, loggedUser);
            updatePass.executeUpdate();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
            {
                try {
                    conn.close();
                } catch (SQLException e){}
                System.out.println("Connection success!");
            }
        }
    }

}
