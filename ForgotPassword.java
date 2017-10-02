import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ForgotPassword {
    private JPanel forgotPass_1;
    private JTextField inputUserName;
    private JTextField inputSecurityPin;
    private JButton acceptButton;
    private JButton cancelButton;

    public JPanel getForgotPass_1() {
        return forgotPass_1;
    }

    public ForgotPassword(final JPanel panel) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputUserName.setText("");
                inputSecurityPin.setText("");
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "login");

            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (credentialsValid() == true)
                {
                    CardLayout cardLayout = (CardLayout) panel.getLayout();
                    cardLayout.show(panel, "forgotPassword2");
                }
                else
                {
                    MessageDialog message = new MessageDialog();
                    message.setMessage2("Username or Security Pin Fail");
                    message.setLocationRelativeTo(forgotPass_1);
                    message.pack();
                    message.show();
                }

            }
        });
    }

    public Boolean credentialsValid(){
        String mb_name, mb_pin;
        ArrayList<String> usernameArray = new ArrayList<String>();
        ArrayList<String> securityPinArray =new ArrayList<String>();
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

            while(rs.next())
            {
                mb_name = rs.getString(1);
                mb_pin = rs.getString(2);
                usernameArray.add(mb_name);
                securityPinArray.add(mb_pin);
            }

            for (int i = 0; i < usernameArray.size(); i++)
            {
                String text = inputUserName.getText();
                String text1 = inputSecurityPin.getText();

                if (text.equals(usernameArray.get(i)) && text1.equals(securityPinArray.get(i)))
                {
                    ForgotPassword_2 thePass = new ForgotPassword_2(text);
                    System.out.println(text);
                    thePass.setUsername(text);

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
