import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class RemoveUser {

    private JPanel removeUserPanel;
    private JTextField inputUserID;
    private JButton cancelButton;
    private JButton acceptButton;
    private JButton refreshButton;
    private JTable userTable;
    private String loggedUsername;
    private String userType;

    public JPanel getRemoveUserPanel() {
        return removeUserPanel;
    }

    public RemoveUser(final JPanel panel, String name) {
        //Update table in RemoveMaterial Card
        loggedUsername = name;

        updateUserTable(userTable);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputUserID.setText("");
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "main");
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean IDValid = false;
                ArrayList<String> usernames = new ArrayList<String>();
                //Check if ID is Valid
                MessageDialog message = new MessageDialog();
                String theUser = "";
                String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
                String url = "jdbc:db2:testlib";
                Connection conn = null;

                try
                {
                    Class.forName(jdbcClassName);
                    conn = DriverManager.getConnection(url);
                    Statement st = conn.createStatement();
                    //Select Title from Material Table given the Material ID
                    String sql = ("SELECT MB_ID FROM member");
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next())
                    {
                        usernames.add(rs.getString(1));
                    }
                }
                catch(ClassNotFoundException w){
                    w.printStackTrace();
                }
                catch(SQLException w){
                    w.printStackTrace();
                    message.setLocationRelativeTo(removeUserPanel);
                    message.setMessage1("Input Error");
                    switch(w.getErrorCode()){
                        case (-180):
                            message.setMessage1("SQL Error Code -180");
                            message.setMessage2("Data Format is incorrect (YYYY/MM/DD)");
                            break;
                        case (-420):
                            message.setMessage1("SQL Error Code -420");
                            message.setMessage2("A string is entered, when an integer is required.");
                            break;
                        default:
                            message.setMessage1("SQL Error Code " + w.getErrorCode());
                            message.setMessage2("SQL Error");
                            break;
                    }
                    message.pack();
                    message.show();
                }
                finally{
                    if(conn != null){
                        System.out.println("Connection success!");
                    }
                }

                for (int i = 0; i < usernames.size(); i++){
                    if (usernames.get(i).equals(inputUserID.getText())){
                        IDValid = true;
                    } else {
                        IDValid = false;
                    }
                }

                //JTextField is empty
                if (inputUserID.getText().equals("")){
                    MessageDialog dialog = new MessageDialog();
                    dialog.setMessage1("Error");
                    dialog.setMessage2("No ID in text field.");
                    dialog.setLocationRelativeTo(inputUserID);
                    dialog.pack();
                    dialog.show();
                } else {
                    if (IDValid.equals(true)) {
                        try {
                            Class.forName(jdbcClassName);
                            conn = DriverManager.getConnection(url);
                            Statement st = conn.createStatement();
                            //Select Title from Material Table given the Material ID
                            String sql = ("SELECT MB_TYPE_ID FROM member WHERE MB_ID = '" + inputUserID.getText() + "'");
                            ResultSet rs = st.executeQuery(sql);
                            while (rs.next()) {
                                userType = rs.getString(1);
                            }
                        } catch (ClassNotFoundException w) {
                            w.printStackTrace();

                        } catch (SQLException w) {
                            w.printStackTrace();
                            message.setLocationRelativeTo(removeUserPanel);
                            message.setMessage1("Input Error");
                            switch (w.getErrorCode()) {
                                case (-180):
                                    message.setMessage1("SQL Error Code -180");
                                    message.setMessage2("Data Format is incorrect (YYYY/MM/DD)");
                                    break;
                                case (-420):
                                    message.setMessage1("SQL Error Code -420");
                                    message.setMessage2("A string is entered, when an integer is required.");
                                    break;
                                default:
                                    message.setMessage1("SQL Error Code " + w.getErrorCode());
                                    message.setMessage2("SQL Error");
                                    break;
                            }
                            message.pack();
                            message.show();
                        } finally {
                            if (conn != null) {
                                System.out.println("Connection success!");
                            }
                        }

                        if (isLibrian(loggedUsername) && (userType.equals("UT03") || userType.equals("UT04"))) {
                            JOptionPane.showMessageDialog(null, "Requires higher authorization level to execute action");
                        } else {
                            try {
                                Class.forName(jdbcClassName);
                                conn = DriverManager.getConnection(url);
                                Statement st = conn.createStatement();
                                //Select Title from Material Table given the Material ID
                                String sql = ("SELECT MB_NAME FROM member WHERE MB_ID = '" + inputUserID.getText() + "'");
                                ResultSet rs = st.executeQuery(sql);
                                while (rs.next()) {
                                    theUser = rs.getString(1);
                                }
                            } catch (ClassNotFoundException w) {
                                w.printStackTrace();

                            } catch (SQLException w) {
                                w.printStackTrace();
                                message.setLocationRelativeTo(removeUserPanel);
                                message.setMessage1("Input Error");
                                switch (w.getErrorCode()) {
                                    case (-180):
                                        message.setMessage1("SQL Error Code -180");
                                        message.setMessage2("Data Format is incorrect (YYYY/MM/DD)");
                                        break;
                                    case (-420):
                                        message.setMessage1("SQL Error Code -420");
                                        message.setMessage2("A string is entered, when an integer is required.");
                                        break;
                                    default:
                                        message.setMessage1("SQL Error Code " + w.getErrorCode());
                                        message.setMessage2("SQL Error");
                                        break;
                                }
                                message.pack();
                                message.show();
                            } finally {
                                if (conn != null) {
                                    System.out.println("Connection success!");
                                }
                            }
                            //Call a Dialog to confirm removal
                            confirmRemove remove = new confirmRemove(inputUserID);
                            remove.setDialogPurpose("remove member");
                            remove.setDataToDelete(theUser);
                            remove.pack();
                            remove.show();
                            //Update table in MaterMainPage Card
                            //Update table in RemoveMaterial Card
                            updateUserTable(userTable);
                        }
                    } else {
                        MessageDialog dialog = new MessageDialog();
                        dialog.setMessage1("Error");
                        dialog.setMessage2("Invalid ID in text field.");
                        dialog.setLocationRelativeTo(inputUserID);
                        dialog.pack();
                        dialog.show();
                    }
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserTable(userTable);
            }
        });
    }

    public void updateUserTable(JTable userTable){
        Vector<Vector<String>> data = new Vector<Vector<String>>(11);
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;
        int counter = 0;
        try {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);
            System.out.println("Creating statement...");
            Statement st = conn.createStatement();
            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("SELECT MB_ID, " +
                    "MB_NAME, " +
                    "MB_GENDER, " +
                    "MB_EMAIL, " +
                    "MB_CONTACT_NO, " +
                    "MB_PASSWORD, " +
                    "MB_DOB, " +
                    "MB_PIN, " +
                    "MB_MATERIAL_BORROWED, " +
                    "MB_TYPE_ID " +
                    "from member");
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                data.add(counter,new Vector<String>(10));
                Vector<String> rowVector = data.get(counter);
                rowVector.add(rs.getString(1));
                rowVector.add(rs.getString(2));
                rowVector.add(rs.getString(3));
                rowVector.add(rs.getString(4));
                rowVector.add(rs.getString(5));
                rowVector.add(rs.getString(6));
                rowVector.add(rs.getString(7));
                rowVector.add(rs.getString(8));
                rowVector.add(rs.getString(9));
                rowVector.add(rs.getString(10));
                counter++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                System.out.println("Connection success!");
            }
            try{
                conn.close();
            }catch (SQLException w){}

        }

        Vector<String> columnNames = new Vector<String>(10);
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Gender");
        columnNames.add("Email");
        columnNames.add("Contact No.");
        columnNames.add("Password");
        columnNames.add("Date of Birth");
        columnNames.add("PIN");
        columnNames.add("Materials Borrowed");
        columnNames.add("User Type");
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        userTable.setModel(model);
        TableColumn column = userTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(500);
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
}
