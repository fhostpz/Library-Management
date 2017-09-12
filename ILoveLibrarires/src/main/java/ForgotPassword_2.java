import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ForgotPassword_2 {
    String username;
    private JPanel forgotPass_2;
    private JTextField inputNewPassword;
    private JTextField inputNewPassword2;
    private JButton acceptButton;
    private JButton cancelButton;

    public void setUsername(String username) {
        this.username = username;
    }

    public JPanel getForgotPass_2() {
        return forgotPass_2;
    }

    public ForgotPassword_2(String username){
        this.username = username;
        System.out.println(username);
    }

    public ForgotPassword_2(final JPanel panel) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "forgotPassword");
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setNewPassword();

            }
        });
    }

    public Boolean setNewPassword(){
        String mahtext;
        String newPass = inputNewPassword.getText();
        String newPass2 = inputNewPassword2.getText();

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
            String sql = ("SELECT MB_NAME, MB_PIN from member");
            ResultSet rs = st.executeQuery(sql);

            if (newPass.equals(newPass2) )
            {
                System.out.println(username);
                PreparedStatement updatePass = null;
                String updateString = "UPDATE member SET MB_PASSWORD = ? WHERE MB_NAME = ? ;";
                updatePass = conn.prepareStatement(updateString);
                updatePass.setString(1, newPass);
                updatePass.setString(2, username);
                updatePass.executeUpdate();
            }
            else
            {
                loginFail fail = new loginFail();
                fail.setLocationRelativeTo(forgotPass_2);
                fail.pack();
                fail.show();
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
