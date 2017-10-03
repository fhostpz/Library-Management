import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.lang.String;
import java.util.Vector;

public class EditMaterial {
    private JTextField inputTitle;
    private JTextField inputISBN;
    private JTextField inputPublisher;
    private JTextField inputDate;
    private JTextField inputEdition;
    private JTextField inputShelf;
    private JTextField inputPrice;
    private JTextField inputType;
    private JButton cancelButton;
    private JButton acceptButton;
    private JPanel editMaterial;
    private JTextField inputMaterialID;
    private JButton verifyButton;
    private JComboBox shelfCombo;

    public EditMaterial(final JPanel panel, final JTable table) {

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(table);
                verifymaterial();

                //panel.revalidate();
                //CardLayout cardLayout = (CardLayout) panel.getLayout();
                //cardLayout.show(panel, "material main");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearJTextField();
                panel.revalidate();
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "material main");
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkIfEmptyMaterial() == true) {
                }
                else if (checkIfEmpty() == true) {
                }
                else {
                    //System.out.println(theMaterial);
                    editMaterial1();
                    updateTable(table);
                    MessageDialog success = new MessageDialog();
                    success.setLocationRelativeTo(editMaterial);
                    success.setMessage1("Success");
                    success.setMessage2("Info Successfully Updated");
                    success.pack();
                    success.show();
                    CardLayout cardLayout = (CardLayout) panel.getLayout();
                    cardLayout.show(panel, "material main");
                    clearJTextField();
                    panel.revalidate();

                }
            }
        });
    }

    public void clearJTextField(){
        inputMaterialID.setText("");
        inputTitle.setText("");
        inputISBN.setText("");
        inputPublisher.setText("");
        inputDate.setText("");
        inputEdition.setText("");
        shelfCombo.setSelectedIndex(0);
        inputPrice.setText("");
        inputType.setText("");
    }

    public JPanel getEditMaterial() {
        return editMaterial;
    }

    public boolean checkIfEmptyMaterial(){
        MessageDialog fail = new MessageDialog();
        if(inputMaterialID.getText().equals("")){
            fail.setLocationRelativeTo(editMaterial);
            fail.setMessage2("Enter the material ID!!");
            fail.pack();
            fail.show();
            return true;
        }
        return false;
    }

    public boolean checkIfEmpty(){
        MessageDialog fail = new MessageDialog();
        if (inputTitle.getText().equals("") && inputISBN.getText().equals("")
                && inputPublisher.getText().equals("") && inputDate.getText().equals("") && inputEdition.getText().equals("")
                 && inputPrice.getText().equals("") && inputType.getText().equals(""))
        {
            fail.setLocationRelativeTo(editMaterial);
            fail.setMessage2("Text Fields are empty");
            fail.pack();
            fail.show();
            return true;
        }
        else
        {
            System.out.println("Not Empty");
            return false;
        }
    }

    public void editMaterial1() {
        String toTitle = "fe", toISBN = "fe", toPublisher = "fe",
                toDate = "fe",  toShelf ="",
                 toType = "fe", toEdition= "fe" , toPrice = "fe";

        String material = inputMaterialID.getText();
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
            String sql = ("SELECT MT_TITLE, MT_ISBN, MT_PUBLISHER, MT_PUBLISH_DATE, MT_EDITION, MT_SH_ID, MT_PRICE,MT_TYPE from material where MT_ID = '" + material + "'");
            ResultSet rs = st.executeQuery(sql);

            while(rs.next())
            {
                toTitle = rs.getString(1);
                toISBN = rs.getString(2);
                toPublisher = rs.getString(3);
                toDate = rs.getString(4);
                toEdition = rs.getString(5);
                toShelf = rs.getString(6);
                toPrice = rs.getString(7);
                toType = rs.getString(8);
            }

            toShelf = shelfCombo.getSelectedItem().toString();

            PreparedStatement updatePass = null;
            String updateString = "UPDATE MATERIAL SET MT_TITLE = ?, MT_ISBN = ?, MT_PUBLISHER = ?, MT_PUBLISH_DATE = ?," +
                    "MT_EDITION = ? , MT_SH_ID = ? , MT_PRICE = ? , MT_TYPE = ? where MT_ID = ? ";
            System.out.println( "Title: " + toTitle + " ISBN: " +toISBN + " Publisher: "+toPublisher +
                    "Publish Date: " + toDate + "Edition: " + toEdition + "SHELF: " + toShelf +
                            "PRICE: " + toPrice + "Type: " + toType );
            updatePass = conn.prepareStatement(updateString);
            updatePass.setString(1, inputTitle.getText());
            updatePass.setString(2, inputISBN.getText());
//            updatePass.setInt(3, Integer.parseInt(toContact));
            updatePass.setString(3, inputPublisher.getText());
            updatePass.setString(4, inputDate.getText());
            updatePass.setString(5, inputEdition.getText());
            updatePass.setString(6, toShelf);
            updatePass.setString(7, inputPrice.getText());
            updatePass.setString(8, inputType.getText());
            updatePass.setString(9, material);
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

    public void verifymaterial(){
        String title = "", ISBN = "", publisher = "" ,
                bookDate = "", shelf = "",
                type = "", edition = "" , price = "";

        String material = inputMaterialID.getText();
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
            String sql = ("SELECT MT_TITLE, MT_ISBN, MT_PUBLISHER, MT_PUBLISH_DATE, MT_EDITION, MT_SH_ID, MT_PRICE,MT_TYPE from material where MT_ID = '" + material + "'");
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                title = rs.getString(1);
                ISBN = rs.getString(2);
                publisher = rs.getString(3);
                bookDate = rs.getString(4);
                edition = rs.getString(5);
                shelf = rs.getString(6);
                price = rs.getString(7);
                type = rs.getString(8);
            }

            inputTitle.setText(title);
            inputISBN.setText(ISBN);
            inputPublisher.setText(publisher);
            inputDate.setText(bookDate);
            inputEdition.setText(edition);
            shelfCombo.setSelectedItem(shelf);
            inputPrice.setText(price);
            inputType.setText(type);
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

    public void updateTable(JTable materialTable){
        Vector<Vector<String>> data = new Vector<Vector<String>>(10);
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
