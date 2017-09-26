import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class RemoveMaterial {
    private JPanel removeMaterialPanel;
    private JTextField inputMaterialID;
    private JButton acceptButton;
    private JButton cancelButton;
    private JTable materialTable;

    public JPanel getRemoveMaterialPanel() {
        return removeMaterialPanel;
    }

    public RemoveMaterial(final JPanel panel, final JTable table) {
        //Update table in RemoveMaterial Card
        updateTable(materialTable);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputMaterialID.setText("");
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "material main");
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFail fail = new loginFail();
                String theMaterial = "";
                String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
                String url = "jdbc:db2:testlib";
                Connection conn = null;

                try
                {
                    Class.forName(jdbcClassName);
                    conn = DriverManager.getConnection(url);
                    Statement st = conn.createStatement();
                    //Select Title from Material Table given the Material ID
                    String sql = ("SELECT MT_TITLE FROM material WHERE MT_ID = '" + inputMaterialID.getText()+ "'");
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next())
                    {
                        theMaterial = rs.getString(1);
                    }
                }
                catch(ClassNotFoundException w)
                {
                    w.printStackTrace();

                }
                catch(SQLException w)
                {
                    w.printStackTrace();
                    fail.setLocationRelativeTo(removeMaterialPanel);
                    fail.setMessage1("Input Error");
                    switch(w.getErrorCode()){
                        case (-180):
                            fail.setMessage1("SQL Error Code -180");
                            fail.setMessage2("Data Format is incorrect (YYYY/MM/DD)");
                            break;
                        case (-420):
                            fail.setMessage1("SQL Error Code -420");
                            fail.setMessage2("A string is entered, when an integer is required.");
                            break;
                        default:
                            fail.setMessage1("SQL Error Code " + w.getErrorCode());
                            fail.setMessage2("SQL Error");
                            break;
                    }
                    fail.pack();
                    fail.show();

                }
                finally
                {
                    if(conn != null)
                    {
                        System.out.println("Connection success!");
                    }
                }
                //Call a Dialog to confirm removal
                confirmRemove remove = new confirmRemove(inputMaterialID);
                remove.setMaterial_name_data(theMaterial);
                remove.pack();
                remove.show();
                //Update table in MaterMainPage Card
                updateTable(table);
                //Update table in RemoveMaterial Card
                updateTable(materialTable);
            }
        });
    }

    public void updateTable(JTable materialTable){
        Vector<Vector<String>> data = new Vector<Vector<String>>(10);
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
            String sql = ("SELECT MT_ID, " +
                    "MT_TITLE, " +
                    "MT_ISBN, " +
                    "MT_PUBLISHER, " +
                    "MT_PUBLISH_DATE, " +
                    "MT_EDITION, " +
                    "MT_SH_ID, " +
                    "MT_PRICE, " +
                    "MT_TYPE " +
                    "from material");
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
        columnNames.add("ID");
        columnNames.add("Title");
        columnNames.add("ISBN");
        columnNames.add("Publisher");
        columnNames.add("Publish Date");
        columnNames.add("Edition");
        columnNames.add("Shelf");
        columnNames.add("Price");
        columnNames.add("Type");
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        materialTable.setModel(model);
        TableColumn column = materialTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(500);
    }
}
