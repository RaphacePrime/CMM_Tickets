package frames_package;

import javax.swing.*;
import database_package.Database;
import panels_package.AdminHomePanel;
import panels_package.AdminModifyEventPanel;
import panels_package.AdminNewEventPanel;
import panels_package.LoginPanel;
import panels_package.RegistrationPanel;
import panels_package.UserHomePanel;

import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private AdminHomePanel adminHomePanel;
    private UserHomePanel userHomePanel;
    private AdminNewEventPanel adminNewEventPanel;
    private AdminModifyEventPanel adminModifyEventPanel;

    public MainFrame() {
        Database.createTables();
        setTitle("Sistema di Accesso e Registrazione");

        // Set layout for full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        loginPanel = new LoginPanel(this.cardLayout, mainPanel);
        registrationPanel = new RegistrationPanel(e -> cardLayout.show(mainPanel, "Login"));
        adminHomePanel = new AdminHomePanel();
        adminNewEventPanel = new AdminNewEventPanel();
        adminModifyEventPanel = new AdminModifyEventPanel(); // The Modify Event Panel
        userHomePanel = new UserHomePanel();

        // Add panels to CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(adminHomePanel, "Admin Home");
        mainPanel.add(adminNewEventPanel, "Admin New Event");
        mainPanel.add(adminModifyEventPanel, "Admin Modify Event"); // Add Modify Event panel
        mainPanel.add(userHomePanel, "User Home");

        // Set action listeners
        adminHomePanel.setSwitchToModifyEventAction(e -> adminHomePanel.setContentPanel(adminModifyEventPanel));
        adminHomePanel.setLogoutAction(e -> cardLayout.show(mainPanel, "Login"));
        
        // Add the main panel to the frame
        add(mainPanel);

        // Show the login panel at startup
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
