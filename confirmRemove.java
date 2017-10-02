import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class confirmRemove extends JDialog {
    private JTextField inputMaterialID;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel material_name_data;
    private JLabel message1;

    public void setMessage1(String message1) {
        this.message1.setText(message1);
    }

    public void setMaterial_name_data(String material_name_data) {
        this.material_name_data.setText(material_name_data);
    }

    public confirmRemove(JTextField inputMaterialID) {
        this.inputMaterialID = inputMaterialID;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        removeMaterial();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void removeMaterial(){
        String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2:testlib";
        Connection conn = null;

        try
        {
            Class.forName(jdbcClassName);
            conn = DriverManager.getConnection(url);

            String sql = ("DELETE FROM material WHERE MT_ID = '" + inputMaterialID.getText()+ "'");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
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
}
