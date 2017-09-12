import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword {
    private JPanel changePassword;
    private JTextField inputCurPass;
    private JTextField inputNewPass;
    private JTextField inputRePass;
    private JButton acceptButton;
    private JButton cancelButton;

    public ChangePassword(JPanel panel) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.previous(panel);

            }
        });
    }

    public ChangePassword() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getChangePassword() {
        return changePassword;
    }
}
