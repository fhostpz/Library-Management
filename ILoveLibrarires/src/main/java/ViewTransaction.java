import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class ViewTransaction {
    private JPanel viewTransactionPanel;
    private JTable transactionTable;
    private JButton backButton;
    private JButton refreshButton;

    public JPanel getViewTransactionPanel() {
        return viewTransactionPanel;
    }

    public ViewTransaction(final JPanel panel) {
        //Update table in RemoveMaterial Card

        updateUserTable(transactionTable);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "main");
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserTable(transactionTable);
            }
        });
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
            String sql = ("SELECT IS_ID, " +
                    "IS_MC_ID, " +
                    "MT_TITLE, " +
                    "IS_MB_ID, " +
                    "MB_NAME, " +
                    "IS_ISSUE_DATE, " +
                    "IS_DUE_DATE, " +
                    "IS_RETURN_DATE, " +
                    "IS_FINE " +
                    "from issue, member, material, material_copy" +
                    " WHERE ISSUE.IS_MB_ID = MEMBER.MB_ID" +
                    " AND ISSUE.IS_MC_ID = MATERIAL_COPY.MC_ID" +
                    " AND MATERIAL.MT_ID = MATERIAL_COPY.MC_MT_ID");
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
        columnNames.add("Issue ID");
        columnNames.add("Material Copy ID");
        columnNames.add("Material Title");
        columnNames.add("Member ID");
        columnNames.add("Member Name");
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
