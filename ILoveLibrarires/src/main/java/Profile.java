import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Profile {
    private String loggedUsername;
    private JPanel profilePanel;
    private JButton changePasswordButton;
    private JButton backButton;
    private JButton editButton;
    private JLabel username_data;
    private JLabel name_data;
    private JButton logoutButton;
    private JLabel type_data;
    private JLabel email_data;
    private JLabel contactNo_data;
    private JLabel gender_data;

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    //Constructor with Action Listener
    public Profile(String input, final JPanel panel, final JFrame die){
        loggedUsername = input;
        initUserData();
        System.out.println("User is " + loggedUsername);
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
                //Dispose Current Frame
                die.dispose();
                //Call main to Display Login
                main.main(null);
            }
        });
    }

    public Profile() {

    }

    public JPanel getProfilePanel() {
        return profilePanel;
    }

    public void initUserData()
    {
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
            String sql = ("SELECT MB_ID," +
                    "MB_NAME, " +
                    "MB_GENDER, " +
                    "MB_EMAIL, " +
                    "MB_CONTACT_NO, " +
                    "UT_ROLE " +
                    "from member, usertype where member.MB_TYPE_ID = usertype.UT_ID and MB_NAME = '" + loggedUsername + "'");
            ResultSet rs = st.executeQuery(sql);

            while(rs.next())
            {
                username_data.setText(rs.getString(1));
                name_data.setText(rs.getString(2));
                gender_data.setText(rs.getString(3));
                if (gender_data.getText().equals("M"))
                {
                    gender_data.setText("Male");
                }
                else if (gender_data.getText().equals("F"))
                {
                    gender_data.setText("Female");
                }
                else
                {
                    gender_data.setText("semiqueer-bi");
                }

                email_data.setText(rs.getString(4));
                contactNo_data.setText(Integer.toString(rs.getInt(5)));
                type_data.setText(rs.getString(6));
            }
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
                System.out.println("Connection success!");
            }
        }

    }

}
