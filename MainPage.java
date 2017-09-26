import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainPage {
    private String loggedUsername;
    private JPanel mainPage;
    private JButton profileButton;
    private JButton libraryMaterialsButton;
    private JButton searchButton;


    public MainPage(String name, final JPanel panel) {
        loggedUsername = name;
        if (isLibrian(loggedUsername)){
            libraryMaterialsButton.setEnabled(true);
        }
        else{
            libraryMaterialsButton.setEnabled(false);
        }
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "profile");
            }
        });
        libraryMaterialsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "material main");
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "search");
            }
        });
    }

    public JPanel getMainPage() {
        return mainPage;
    }

    public Boolean isLibrian(String loggedUsername) {
        String input;
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://localhost:50001/testlib";
        String user = "User";
        String password = "ting970926";
        Connection conn = null;

        try {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url,user,password);
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
