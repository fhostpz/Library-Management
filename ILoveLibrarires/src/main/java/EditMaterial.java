import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.lang.String;

public class EditMaterial {
    private JTextField inputTitle;
    private JTextField inputISBN;
    private JTextField inputPublisher;
    private JTextField inputDate;
    private JTextField inputEdition;
    //private JTextField inputShelf;
    private JTextField inputPrice;
    private JTextField inputType;
    private JButton cancelButton;
    private JButton acceptButton;
    private JPanel editMaterial;
    private JTextField inputMaterialID;
    private JButton verifyButton;
    private JComboBox shelfCombo;

    public EditMaterial(final JPanel panel) {

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifymaterial();

                //panel.revalidate();
                //CardLayout cardLayout = (CardLayout) panel.getLayout();
                //cardLayout.show(panel, "material main");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputMaterialID.setText("");
                inputTitle.setText("");
                inputISBN.setText("");
                inputPublisher.setText("");
                inputDate.setText("");
                inputEdition.setText("");
                shelfCombo.setSelectedIndex(0);
                inputPrice.setText("");
                inputType.setText("");
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
                    MessageDialog success = new MessageDialog();
                    success.setLocationRelativeTo(editMaterial);
                    success.setMessage1("Success");
                    success.setMessage2("Info Successfully Updated");
                    success.pack();
                    success.show();
                }
            }
        });
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


        String mahtext = "fe", mahtext2 = "fe", mahtext3 = "fe" ,
                mahtext4 = "fe", mahtext6 = "fe",
                 mahtext8 = "fe", mahtext5 = "fe" , mahtext7 = "fe";

        String material = inputMaterialID.getText();
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
            String sql = ("SELECT MT_TITLE, MT_ISBN, MT_PUBLISHER, MT_PUBLISH_DATE, MT_EDITION, MT_SH_ID, MT_PRICE,MT_TYPE from material where MT_ID = '" + material + "'");
            ResultSet rs = st.executeQuery(sql);

            while(rs.next())
            {
                mahtext = rs.getString(1);
                mahtext2 = rs.getString(2);
                mahtext3 = rs.getString(3);
                mahtext4 = rs.getString(4);
                mahtext5 = rs.getString(5);
                mahtext6 = rs.getString(6);
                mahtext7 = rs.getString(7);
                mahtext8 = rs.getString(8);
            }



            System.out.println( "Title: " + mahtext + " ISBN: " +mahtext2 + " Publisher: "+ mahtext3 +
                                "Publish Date: " + mahtext4 + "Edition: " + mahtext5 + "SHELF: " + mahtext6 +
                                        "PRICE: " + mahtext7 + "Type: " + mahtext8 );

            if (inputTitle.getText().equals(""))
            {
                toTitle = mahtext;
                System.out.println("Do not update Title");
            }
            else
            {
                toTitle = inputTitle.getText();
                System.out.println("Do update Title");
            }

            if (inputISBN.getText().equals(""))
            {
                toISBN = mahtext2;
                System.out.println("Do not update Email");
            }
            else
            {
                toISBN = inputISBN.getText();
                System.out.println("Do update Email");

            }

            if (inputPublisher.getText().equals(""))
            {
                toPublisher = mahtext3;
                System.out.println("Do not update ContactNo");
            }
            else
            {
                toPublisher = inputPublisher.getText();
                System.out.println("Do update Contact");

            }

            if (inputDate.getText().equals(""))
            {
                toDate = mahtext4;
                System.out.println("Do not update ContactNo");
            }
            else
            {
                toDate = inputDate.getText();
                System.out.println("Do update Contact");

            }

            if (inputEdition.getText().equals(""))
            {
                toEdition = mahtext5;
                System.out.println("Do not update ContactNo");
            }
            else
            {
                toEdition = inputEdition.getText();
                System.out.println("Do update Contact");

            }

            toShelf = shelfCombo.getSelectedItem().toString();



            if (inputPrice.getText().equals(""))
            {
                toPrice = mahtext7;
                System.out.println("Do not update ContactNo");
            }
            else
            {
                toPrice = inputPrice.getText();
                System.out.println("Do update Contact");

            }

            if (inputType.getText().equals(""))
            {
                toType = mahtext8;
                System.out.println("Do not update ContactNo");
            }
            else
            {
                toType = inputType.getText();
                System.out.println("Do update Contact");

            }

            PreparedStatement updatePass = null;
            String updateString = "UPDATE MATERIAL SET MT_TITLE = ?, MT_ISBN = ?, MT_PUBLISHER = ?, MT_PUBLISH_DATE = ?," +
                    "MT_EDITION = ? , MT_SH_ID = ? , MT_PRICE = ? , MT_TYPE = ? where MT_ID = ? ";
            System.out.println( "Title: " + toTitle + " ISBN: " +toISBN + " Publisher: "+toPublisher +
                    "Publish Date: " + toDate + "Edition: " + toEdition + "SHELF: " + toShelf +
                            "PRICE: " + toPrice + "Type: " + toType );
            updatePass = conn.prepareStatement(updateString);
            updatePass.setString(1, toTitle);
            updatePass.setString(2, toISBN);
//            updatePass.setInt(3, Integer.parseInt(toContact));
            updatePass.setString(3, toPublisher);
            updatePass.setString(4, toDate);
            updatePass.setString(5, toEdition);
            updatePass.setString(6, toShelf);
            updatePass.setString(7, toPrice);
            updatePass.setString(8, toType);
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
        String mahtext = "", mahtext2 = "", mahtext3 = "" ,
                mahtext4 = "", mahtext6 = "",
                mahtext8 = "", mahtext5 = "" , mahtext7 = "";

        String material = inputMaterialID.getText();
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
            String sql = ("SELECT MT_TITLE, MT_ISBN, MT_PUBLISHER, MT_PUBLISH_DATE, MT_EDITION, MT_SH_ID, MT_PRICE,MT_TYPE from material where MT_ID = '" + material + "'");
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                mahtext = rs.getString(1);
                mahtext2 = rs.getString(2);
                mahtext3 = rs.getString(3);
                mahtext4 = rs.getString(4);
                mahtext5 = rs.getString(5);
                mahtext6 = rs.getString(6);
                mahtext7 = rs.getString(7);
                mahtext8 = rs.getString(8);
            }


            inputTitle.setText(mahtext);
            inputISBN.setText(mahtext2);
            inputPublisher.setText(mahtext3);
            inputDate.setText(mahtext4);
            inputEdition.setText(mahtext5);
            shelfCombo.setSelectedItem(mahtext6);
            inputPrice.setText(mahtext7);
            inputType.setText(mahtext8);
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
