import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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

    public ForgotPassword_2(final JPanel panel, String username) {
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
                setNewPassword(panel);
                System.out.println("Username: " + username);

            }
        });
    }

    public Boolean setNewPassword(JPanel panel){
        String newPass = inputNewPassword.getText();
        String newPass2 = inputNewPassword2.getText();

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
            String sql = ("SELECT MB_NAME, MB_PIN from member");
            ResultSet rs = st.executeQuery(sql);

            if (newPass.equals(newPass2))
            {
                PreparedStatement updatePass = null;
                String updateString = "UPDATE member SET MB_PASSWORD = ? WHERE MB_NAME = ?";
                updatePass = conn.prepareStatement(updateString);
                updatePass.setString(1, newPass);
                updatePass.setString(2, username);
                updatePass.executeUpdate();

                MessageDialog message = new MessageDialog();
                message.setLocationRelativeTo(forgotPass_2);
                message.setMessage2("Success");
                message.setMessage1("Password is Successfully changed");
                message.pack();
                message.show();
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "login");

            }
            else
            {
                MessageDialog message = new MessageDialog();
                message.setLocationRelativeTo(forgotPass_2);
                message.pack();
                message.show();
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
