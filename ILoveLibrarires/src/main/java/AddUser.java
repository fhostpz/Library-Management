import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddUser {

    private JPanel addUserPanel;
    private JTextField inputID;
    private JTextField inputGender;
    private JTextField inputName;
    private JTextField inputEmail;
    private JTextField inputContact;
    private JTextField inputPassword;
    private JTextField inputDob;
    private JTextField inputPin;
    private JTextField inputType;
    private JButton cancelButton;
    private JButton acceptButton;
    private JComboBox typeCombo;
    private String loggedUsername;

    public JPanel getAddUserPanel() {
        return addUserPanel;
    }

    public AddUser(final JPanel panel, String name) {

        loggedUsername = name;
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "main");

            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIfEmpty();
                addUser();
            }
        });

        if(isLibrian(loggedUsername))
        {
            typeCombo.removeAllItems();
            typeCombo.addItem("UT01");
            typeCombo.addItem("UT02");
        }

        if(isAdmin(loggedUsername))
        {
            typeCombo.removeAllItems();
            typeCombo.addItem("UT01");
            typeCombo.addItem("UT02");
            typeCombo.addItem("UT03");
            typeCombo.addItem("UT04");
        }
    }

    public void checkIfEmpty(){
        MessageDialog fail = new MessageDialog();
        if (    inputID.getText().equals("")
                && inputName.getText().equals("")
                && inputGender.getText().equals("")
                && inputEmail.getText().equals("")
                && inputContact.getText().equals("")
                && inputPassword.getText().equals("")
                && inputDob.getText().equals("")
                && inputPin.getText().equals("")
                && inputType.getText().equals(""))
        {
            fail.setLocationRelativeTo(addUserPanel);
            fail.setMessage2("Text Fields are empty");
            fail.pack();
            fail.show();
        } else if (
                inputID.getText().equals("")
                || inputName.getText().equals("")
                || inputGender.getText().equals("")
                || inputEmail.getText().equals("")
                || inputContact.getText().equals("")
                || inputPassword.getText().equals("")
                || inputDob.getText().equals("")
                || inputPin.getText().equals("")
        )
        {
            fail.setLocationRelativeTo(addUserPanel);
            fail.setMessage2("A Text Field is empty");
            fail.pack();
            fail.show();
        }
        else
        {
            System.out.println("Not Empty");
        }
    }

    public void addUser(){
        MessageDialog fail = new MessageDialog();
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);

            System.out.println("Creating statement...");
            Statement st = conn.createStatement();

            if (inputID.getText().equals(""))
            {
                System.out.println("Do not insert ID");
            }
            else
            {
                System.out.println("Do insert ID");
            }

            if (inputName.getText().equals(""))
            {
                System.out.println("Do not insert Name");
            }
            else
            {
                System.out.println("Do insert Name");
            }

            if (inputGender.getText().equals(""))
            {
                System.out.println("Do not insert Gender");
            }
            else
            {
                System.out.println("Do insert Gender");
            }

            if (inputEmail.getText().equals(""))
            {
                System.out.println("Do not insert Email");
            }
            else
            {
                System.out.println("Do insert Email");
            }

            if (inputContact.getText().equals(""))
            {
                System.out.println("Do not insert Contact");
            }
            else
            {
                System.out.println("Do insert Contact");
            }

            if (inputPassword.getText().equals(""))
            {
                System.out.println("Do not insert Password");
            }
            else
            {
                System.out.println("Do insert Password");
            }

            if (inputDob.getText().equals(""))
            {
                System.out.println("Do not insert DOB");
            }
            else
            {
                System.out.println("Do insert Dob");
            }
            if (inputPin.getText().equals(""))
            {
                System.out.println("Do not insert Pin");
            }
            else
            {
                System.out.println("Do insert Pin");
            }

            String defaultPageNo = "0";
            PreparedStatement insertUser = null;
            String updateString = "INSERT INTO member " +
                    "(MB_ID, MB_NAME, MB_GENDER, MB_EMAIL, MB_CONTACT_NO, MB_PASSWORD, MB_DOB, MB_PIN, MB_MATERIAL_BORROWED, MB_TYPE_ID) " +
                    "VALUES ('" +
                    inputID.getText() + "', '" +
                    inputName.getText() + "', '" +
                    inputGender.getText() + "', '" +
                    inputEmail.getText() + "', '" +
                    inputContact.getText() + "', '" +
                    inputPassword.getText() + "', '" +
                    inputDob.getText() + "', '" +
                    inputPin.getText() + "', '" +
                    defaultPageNo + "', '" +
                    typeCombo.getSelectedItem() + "')";
            insertUser = conn.prepareStatement(updateString);
            insertUser.executeUpdate();
            JOptionPane.showMessageDialog(null,
                    "User has been added to the database.",
                    "Add complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            fail.setLocationRelativeTo(addUserPanel);
            fail.setMessage1("Input Error");
            switch(e.getErrorCode()){
                case (-180):
                    fail.setMessage1("SQL Error Code -180");
                    fail.setMessage2("Data Format is incorrect (YYYY/MM/DD)");
                    break;
                case (-420):
                    fail.setMessage1("SQL Error Code -420");
                    fail.setMessage2("A string is entered, when an integer is required.");
                    break;
                default:
                    fail.setMessage1("SQL Error Code " + e.getErrorCode());
                    fail.setMessage2("SQL Error");
                    break;
            }
            fail.pack();
            fail.show();

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

    public Boolean isLibrian(String loggedUsername) {
        String input;
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
            System.out.println("Creating statement...");
            Statement st = conn.createStatement();
            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("SELECT UT_ROLE from member, usertype where member.MB_TYPE_ID = usertype.UT_ID and MB_NAME = '" + loggedUsername + "'");
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                input = rs.getString(1);
                if (input.equals("librarian"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                System.out.println("Connection success!");
            }
        }
        return false;
    }

    public Boolean isAdmin(String loggedUsername) {
        String input;
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
            System.out.println("Creating statement...");
            Statement st = conn.createStatement();
            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("SELECT UT_ROLE from member, usertype where member.MB_TYPE_ID = usertype.UT_ID and MB_NAME = '" + loggedUsername + "'");
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                input = rs.getString(1);
                if (input.equals("administrative staff"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                System.out.println("Connection success!");
            }
        }
        return false;
    }
}
