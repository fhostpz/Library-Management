import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ConfirmRemoveUser extends JDialog{
    private JButton acceptButton;
    private JButton cancelButton;
    private JPanel removeUserPane;
    private JLabel userIdData;
    private JTextField inputUserId;

    public void setUserIdData(String userIdData) {
        this.userIdData.setText(userIdData);
    }

    public ConfirmRemoveUser(JTextField inputUserId) {
        this.inputUserId = inputUserId;
        setContentPane(removeUserPane);
        setModal(true);
        getRootPane().setDefaultButton(acceptButton);

        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
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
        removeUserPane.registerKeyboardAction(new ActionListener() {
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

            System.out.println("Creating statement...");
            Statement st = conn.createStatement();

            // Extract records in ascending order by first name.
            System.out.println("Fetching records in ascending order...");
            String sql = ("DELETE FROM member WHERE MB_ID = '" + inputUserId.getText()+ "'");
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
                System.out.println("Connection success!");
            }
        }

    }
}
