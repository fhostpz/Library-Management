import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditProfile{
    private String loggedUser;
    private JPanel editProfile;
    private JButton cancelButton;
    private JButton acceptButton;
    private JTextField inputName;
    private JTextField inputEmail;
    private JTextField inputContactNo;

    public JPanel getEditProfile() {
        return editProfile;
    }

    public EditProfile(String username,final JPanel panel, final Profile profile) {
        loggedUser = username;
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputName.setText("");
                inputEmail.setText("");
                inputContactNo.setText("");
                panel.revalidate();
            CardLayout cardLayout = (CardLayout) panel.getLayout();
            cardLayout.show(panel, "profile");
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIfEmpty();
                updateMember();
                updateProfile(profile);
                profile.getProfilePanel().revalidate();
                profile.getProfilePanel().repaint();
                MessageDialog message = new MessageDialog();
                message.setLocationRelativeTo(editProfile);
                message.setMessage1("Success");
                message.setMessage2("Info Successfully Updated");
                message.pack();
                message.show();
                profile.getName_data().revalidate();
                profile.getName_data().repaint();
            }
        });
    }

    public void checkIfEmpty(){
        MessageDialog fail = new MessageDialog();
        if (inputName.getText().equals("") && inputEmail.getText().equals("") && inputContactNo.getText().equals(""))
        {
            fail.setLocationRelativeTo(editProfile);
            fail.setMessage2("Text Fields are empty");
            fail.pack();
            fail.show();
        }
        else
        {
            System.out.println("Not Empty");
        }
    }

    public void updateMember(){
        //String Variable to hold information to send to database.
        String toName = "fe", toEmail = "fe", toContact = "fe";
        //Temporary String Varaible to hold information obtain from database query
        String tempText = "fe", tempText2 = "fe", tempText3 = "fe";
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
            String sql = ("SELECT MB_NAME, MB_EMAIL, MB_CONTACT_NO from member where MB_NAME = '" + loggedUser + "'");
            ResultSet rs = st.executeQuery(sql);

            while(rs.next())
            {
                tempText = rs.getString(1);
                tempText2 = rs.getString(2);
                tempText3 = rs.getString(3);
            }

            System.out.println( "Name: " + tempText + " EMail: " +tempText2 + " Contact: "+tempText3);

            if (inputName.getText().equals(""))
            {
                toName = tempText;
                System.out.println("Do not update Name");
            }
            else
            {
                toName = inputName.getText();
                System.out.println("Do update Name");
            }

            if (inputEmail.getText().equals(""))
            {
                toEmail = tempText2;
                System.out.println("Do not update Email");
            }
            else
            {
                toEmail = inputEmail.getText();
                System.out.println("Do update Email");

            }

            if (inputContactNo.getText().equals(""))
            {
                toContact = tempText3;
                System.out.println("Do not update ContactNo");
            }
            else
            {
                toContact = inputContactNo.getText();
                System.out.println("Do update Contact");

            }
            PreparedStatement updatePass = null;
            String updateString = "UPDATE member SET MB_NAME = ?, MB_EMAIL = ?, MB_CONTACT_NO = ? WHERE MB_NAME = ? ";
            System.out.println( "Name: " + toName + " EMail: " + toEmail + " Contact: " + toContact);
            updatePass = conn.prepareStatement(updateString);
            updatePass.setString(1, toName);
            updatePass.setString(2, toEmail);
            updatePass.setString(3, toContact);
            updatePass.setString(4, loggedUser);
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

                System.out.println("Connection success!");
            }
        }

    }

    public void updateProfile(Profile profile)
    {
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        System.out.println(loggedUser);

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();

            String sql = ("SELECT MB_ID," +
                    "MB_NAME, " +
                    "MB_GENDER, " +
                    "MB_EMAIL, " +
                    "MB_CONTACT_NO, " +
                    "UT_ROLE " +
                    "from member, usertype where member.MB_TYPE_ID = usertype.UT_ID and MB_NAME = '" + loggedUser + "'");
            ResultSet rs = st.executeQuery(sql);

            while(rs.next())
            {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                profile.getUsername_data().setText(rs.getString(1));
                profile.getName_data().setText(rs.getString(2));
                profile.getGender_data().setText(rs.getString(3));

                if (profile.getGender_data().getText().equals("M"))
                {
                    profile.getGender_data().setText("Male");
                }
                else if (profile.getGender_data().getText().equals("F"))
                {
                    profile.getGender_data().setText("Female");
                }
                else
                {
                    profile.getGender_data().setText("semiqueer-bi");
                }

                profile.getEmail_data().setText(rs.getString(4));
                profile.getContactNo_data().setText(rs.getString(5));
                profile.getType_data().setText(rs.getString(6));
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

