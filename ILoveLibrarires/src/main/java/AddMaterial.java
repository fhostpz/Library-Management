import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AddMaterial {
    private JPanel addMaterialPanel;
    private JTextField inputTitle;
    private JTextField inputISBN;
    private JTextField inputPublisher;
    private JTextField inputPublishDate;
    private JTextField inputEdition;
    private JTextField inputPrice;
    private JButton acceptButton;
    private JButton cancelButton;
    private JComboBox shelfCombo;
    private JTextField inputType;
    private JTextField inputID;

    public JPanel getAddMaterialPanel() {
        return addMaterialPanel;
    }

    public AddMaterial(final JPanel panel, final JTable table) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "material main");

            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIfEmpty();
                addMaterial(table);
                updateTable(table);
            }
        });
    }

    public void checkIfEmpty(){
        loginFail fail = new loginFail();
        if (    inputTitle.getText().equals("")
                && inputISBN.getText().equals("")
                && inputPublisher.getText().equals("")
                && inputPublishDate.getText().equals("")
                && inputEdition.getText().equals("")
                && inputPrice.getText().equals("")
                && inputEdition.getText().equals(""))
        {
            fail.setLocationRelativeTo(addMaterialPanel);
            fail.setMessage2("Text Fields are empty");
            fail.pack();
            fail.show();
        }
        else
        {
            System.out.println("Not Empty");
        }


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


    public void addMaterial(JTable table){
        String toTitle = " ", toISBN = " ", toPublisher = " ", toPublishDate = " ", toEditon= " "
                , toPrice = " ", toType = " ";
        String mahtext = " ", mahtext2 = " ", mahtext3 = " ";
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);

            System.out.println("Creating statement...");
            Statement st = conn.createStatement();

            if (inputTitle.getText().equals(""))
            {
                toTitle = mahtext;
                System.out.println("Do not insert Title");
            }
            else
            {
                toTitle = inputTitle.getText();
                System.out.println("Do insert Title");
            }

            if (inputISBN.getText().equals(""))
            {
                toISBN = mahtext2;
                System.out.println("Do not insert ISBN");
            }
            else
            {
                toISBN = inputISBN.getText();
                System.out.println("Do insert ISBN");
            }

            if (inputPublisher.getText().equals(""))
            {
                toPublisher = mahtext3;
                System.out.println("Do not insert Publisher");
            }
            else
            {
                toPublisher = inputPublisher.getText();
                System.out.println("Do insert Publisher");
            }

            if (inputEdition.getText().equals(""))
            {
                toEditon = mahtext3;
                System.out.println("Do not insert Edition");
            }
            else
            {
                toEditon = inputEdition.getText();
                System.out.println("Do insert Edition");
            }

            if (inputPublishDate.getText().equals(""))
            {
                toPublishDate = mahtext3;
                System.out.println("Do not insert Publish Date");
            }
            else
            {
                toPublishDate = inputPublishDate.getText();
                System.out.println("Do insert Publish Date");
            }

            if (inputPrice.getText().equals(""))
            {
                toPrice = mahtext3;
                System.out.println("Do not insert Price");
            }
            else
            {
                toPrice = inputPrice.getText();
                System.out.println("Do insert Price");
            }

            if (inputType.getText().equals(""))
            {
                toType = mahtext3;
                System.out.println("Do not insert Type");
            }
            else
            {
                toType = inputType.getText();
                System.out.println("Do insert Type");
            }

            PreparedStatement insertMaterial = null;
            String updateString = "INSERT INTO material " +
                    "(MT_ID, MT_TITLE, MT_ISBN, MT_PUBLISHER, MT_PUBLISH_DATE, MT_EDITION, MT_PRICE, MT_SH_ID, MT_TYPE) " +
                    "VALUES ('" +
                    inputID.getText() + "', '" +
                    inputTitle.getText() + "', '" +
                    inputISBN.getText() + "', '" +
                    inputPublisher.getText() + "', '" +
                    inputPublishDate.getText() + "', '" +
                    inputEdition.getText() + "', '" +
                    //input Integer
                    inputPrice.getText() + "', '" +
                    shelfCombo.getSelectedItem() + "', '" +
                    inputType.getText() + "')";
            insertMaterial = conn.prepareStatement(updateString);
//            insertMaterial.setString(1, toTitle);
//            insertMaterial.setString(2, toISBN);
//            insertMaterial.setString(3, toPublisher);
//            insertMaterial.setString(4, toPublishDate);
//            insertMaterial.setString(5, toEditon);
//            insertMaterial.setString(6, toPrice);
//            insertMaterial.setString(7, toType);
            insertMaterial.executeUpdate();
            DefaultTableModel dm = (DefaultTableModel)table.getModel();
            dm.fireTableDataChanged();
            table.updateUI();
            table.repaint();
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
