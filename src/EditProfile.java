import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfile {
    private JPanel editProfile;
    private JButton cancelButton;
    private JButton acceptButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public EditProfile(JPanel panel) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "profile");

            }
        });
    }

    public JPanel getEditProfile() {
        return editProfile;
    }
}
