import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RemoveMaterial {
    private JPanel removeMaterialPanel;
    private JTextField inputMaterialID;
    private JButton acceptButton;
    private JButton cancelButton;

    public JPanel getRemoveMaterialPanel() {
        return removeMaterialPanel;
    }

    public RemoveMaterial(final JPanel panel) {
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
                String theMaterial = "";
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

                }
                finally
                {
                    if(conn != null)
                    {
                        System.out.println("Connection success!");
                    }
                }
                confirmRemove remove = new confirmRemove(inputMaterialID);
                remove.setMaterial_name_data(theMaterial);
                remove.pack();
                remove.show();

//                removeMaterial();


            }
        });
    }
}
