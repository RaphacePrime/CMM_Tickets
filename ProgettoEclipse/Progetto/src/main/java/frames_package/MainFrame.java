package frames_package;

import javax.swing.*;
import database_package.Database;
import panels_package.AdminAddLuogoPanel;
import panels_package.AdminHomePanel;
import panels_package.AdminModifyEventPanel;
import panels_package.AdminModifyLuogoPanel;
//import panels_package.AdminNewEventPanel;
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
    //private AdminNewEventPanel adminNewEventPanel;
    private AdminModifyEventPanel adminModifyEventPanel;
    private AdminModifyLuogoPanel adminModifyLuogoPanel;
    private AdminAddLuogoPanel adminAddLuogoPanel;

    public MainFrame() {
        Database.createTables();
        setTitle("Sistema di Accesso e Registrazione");

        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        
        loginPanel = new LoginPanel(this.cardLayout, mainPanel, e -> cardLayout.show(mainPanel, "Registration"));
        registrationPanel = new RegistrationPanel(e -> cardLayout.show(mainPanel, "Login"));
        adminHomePanel = new AdminHomePanel();
        //adminNewEventPanel = new AdminNewEventPanel();
        adminModifyEventPanel = new AdminModifyEventPanel();
        adminModifyLuogoPanel = new AdminModifyLuogoPanel();
        adminAddLuogoPanel = new AdminAddLuogoPanel();
        userHomePanel = new UserHomePanel();

        
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(adminHomePanel, "Admin Home");
        mainPanel.add(adminModifyEventPanel, "Admin Modify Event");
        mainPanel.add(adminModifyLuogoPanel, "Admin Modify Luogo");
        mainPanel.add(adminAddLuogoPanel, "Admin Add Luogo");
        mainPanel.add(userHomePanel, "User Home");

        
        adminHomePanel.setSwitchToModifyEventAction(e -> adminHomePanel.setContentPanel(adminModifyEventPanel));
        adminHomePanel.setSwitchToModifyLuogoAction(e -> adminHomePanel.setContentPanel(adminModifyLuogoPanel));
        adminHomePanel.setSwitchToAddLuogoAction(e -> adminHomePanel.setContentPanel(adminAddLuogoPanel));
       // adminHomePanel.setLogoutAction(e -> cardLayout.show(mainPanel, "Login"));
        
        adminHomePanel.setLogoutAction(e -> {
            loginPanel.resetFields(); // Usa direttamente il riferimento
            cardLayout.show(mainPanel, "Login");
        });
         
        add(mainPanel);

        
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
