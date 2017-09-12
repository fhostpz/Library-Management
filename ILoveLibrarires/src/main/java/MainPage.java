import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    private JPanel mainPage;
    private JButton profileButton;

    public MainPage(final JPanel panel) {
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.show(panel, "profile");

            }
        });
    }

    public JPanel getMainPage() {
        return mainPage;
    }
}
