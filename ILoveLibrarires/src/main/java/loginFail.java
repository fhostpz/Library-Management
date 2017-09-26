import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginFail extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JPanel infoPanel;
    private JLabel message2;
    private JLabel message1;

    public void setMessage1(String theMessage) {
        message1.setText(theMessage);
    }

    public void setMessage2(String theMessage) {
        message2.setText(theMessage);
    }

    public loginFail() {
        setContentPane(contentPane);
        setModal(true);

        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public static void main(String[] args) {
        loginFail dialog = new loginFail();
        dialog.pack();
        dialog.setSize(100,100);
        dialog.setVisible(true);
        System.exit(0);
    }
}
