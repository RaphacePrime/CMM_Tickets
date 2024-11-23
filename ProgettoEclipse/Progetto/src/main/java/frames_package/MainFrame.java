package frames_package;

import javax.swing.*;
import database_package.Database;
import panels_package.AdminAddLuogoPanel;
import panels_package.AdminDetailsLuogoPanel;
import panels_package.AdminHomePanel;
import panels_package.AdminModifyEventPanel;
import panels_package.AdminModifyLuogoPanel;
import panels_package.LoginPanel;
import panels_package.RegistrationPanel;
import panels_package.UserHomePanel;

import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    public AdminHomePanel adminHomePanel;
    private UserHomePanel userHomePanel;
    private AdminModifyEventPanel adminModifyEventPanel;
    private AdminModifyLuogoPanel adminModifyLuogoPanel;
    private AdminAddLuogoPanel adminAddLuogoPanel;

    public MainFrame() {
        Database.createTables();
        setTitle("Sistema di Accesso e Registrazione");

        // Configurazione della finestra principale
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout principale
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inizializzazione dei pannelli
        loginPanel = new LoginPanel(this.cardLayout, mainPanel, e -> cardLayout.show(mainPanel, "Registration"));
        registrationPanel = new RegistrationPanel(e -> cardLayout.show(mainPanel, "Login"));
        adminHomePanel = new AdminHomePanel();
        adminModifyLuogoPanel = new AdminModifyLuogoPanel();
        adminAddLuogoPanel = new AdminAddLuogoPanel();
        userHomePanel = new UserHomePanel();

        // Aggiunta dei pannelli al layout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(adminHomePanel, "Admin Home");
        mainPanel.add(adminModifyLuogoPanel, "Admin Modify Luogo");
        mainPanel.add(adminAddLuogoPanel, "Admin Add Luogo");
        mainPanel.add(userHomePanel, "User Home");

        // Configurazione delle azioni nei pulsanti
        adminHomePanel.setSwitchToModifyEventAction(e -> {
            adminModifyEventPanel = new AdminModifyEventPanel();
            mainPanel.add(adminModifyEventPanel, "Admin Modify Event");
            adminHomePanel.setContentPanel(adminModifyEventPanel);
        });

        adminHomePanel.setSwitchToModifyLuogoAction(e -> {
            adminModifyLuogoPanel = new AdminModifyLuogoPanel();
            adminModifyLuogoPanel.setSwitchToDetailsEventAction(this); // Passaggio del riferimento al MainFrame
            mainPanel.add(adminModifyLuogoPanel, "Admin Modify Luogo");
            adminHomePanel.setContentPanel(adminModifyLuogoPanel);
        });

        adminHomePanel.setSwitchToAddLuogoAction(e -> adminHomePanel.setContentPanel(adminAddLuogoPanel));

        adminHomePanel.setLogoutAction(e -> {
            loginPanel.resetFields();
            cardLayout.show(mainPanel, "Login");
        });

        add(mainPanel);

        // Mostra la schermata di login iniziale
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
