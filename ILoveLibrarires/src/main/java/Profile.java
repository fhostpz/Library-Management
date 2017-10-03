import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

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
    private JTable userTransactionTable;

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
        updateUserTable(userTransactionTable);
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
                    String sql = ("UPDATE MEMBER SET MB_AVAILABILITY = 'Offline' WHERE MB_NAME = '" + loggedUsername + "'");
                    st.executeUpdate(sql);
                }
                catch(ClassNotFoundException event)
                {
                    event.printStackTrace();
                }
                catch(SQLException event)
                {
                    event.printStackTrace();
                }
                finally
                {
                    if(conn != null)
                    {
                        System.out.println("Connection success!");
                    }
                }
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
        String url = "jdbc:db2://localhost:50001/testlib";
        String user = "User";
        String password = "ting970926";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url,user,password);
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

    public void updateUserTable(JTable userTable){
        Vector<Vector<String>> data = new Vector<Vector<String>>(11);
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://localhost:50001/testlib";
        String user = "User";
        String password = "ting970926";
        Connection conn = null;
        int counter = 0;
        try {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Creating statement...");
            Statement st = conn.createStatement();
            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("SELECT IS_MC_ID, " +
                    "MT_TITLE, " +
                    "IS_ISSUE_DATE, " +
                    "IS_DUE_DATE, " +
                    "IS_RETURN_DATE, " +
                    "IS_FINE " +
                    "from issue, member, material, material_copy" +
                    " WHERE ISSUE.IS_MB_ID = MEMBER.MB_ID" +
                    " AND ISSUE.IS_MC_ID = MATERIAL_COPY.MC_ID" +
                    " AND MATERIAL.MT_ID = MATERIAL_COPY.MC_MT_ID" +
                    " AND MEMBER.MB_NAME = '" + loggedUsername + "'" +
                    "order by ISSUE.IS_ISSUE_DATE desc");
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
        columnNames.add("Material Copy ID");
        columnNames.add("Material Title");
        columnNames.add("Issue Date");
        columnNames.add("Due Date");
        columnNames.add("Return Date");
        columnNames.add("Fine");
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        userTable.setModel(model);
        TableColumn column = userTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(500);
    }

}
