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

    public JLabel getUsername_data() {
        return username_data;
    }

    public JLabel getContactNo_data() {
        return contactNo_data;
    }

    public JLabel getEmail_data() {
        return email_data;
    }

    public JLabel getGender_data() {
        return gender_data;
    }

    public JLabel getName_data() {
        return name_data;
    }

    public JLabel getType_data() {
        return type_data;
    }

    public JPanel getProfilePanel() {
        return profilePanel;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public Profile (){}

    //Constructor with Action Listener
    public Profile(String input, final JPanel panel, final JFrame die){
        loggedUsername = input;
        //Initialize User Data
        initUserData();
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

    public void initUserData()
    {
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();

            System.out.println("Fetching User Records From Member Table");
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
                email_data.setText(rs.getString(4));
                contactNo_data.setText(rs.getString(5));
                type_data.setText(rs.getString(6));

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
                try {
                    conn.close();
                } catch (SQLException e){}
                System.out.println("Connection success!");
            }
        }

    }

}
