package framespackage;

import javax.swing.*;
import java.awt.*;
import panelspackage.AdminHomePanel;
import panelspackage.LoginPanel;
import panelspackage.RegistrationPanel;
import panelspackage.AdminNewEventPanel;
import panelspackage.AdminMyEventsPanel;

public class MainFrame extends JFrame {
    public CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private AdminHomePanel adminHomePanel;
    private AdminNewEventPanel adminNewEventPanel;
    private AdminMyEventsPanel adminMyEventsPanel;

    public MainFrame() {
        setTitle("Sistema di Accesso e Registrazione");

        // Imposta il layout per il full-screen su macOS, Windows e Linux
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inizializzazione del CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Creazione dei pannelli di Login, Registrazione, AdminHome, AdminNewEvent e AdminMyEvents
        loginPanel = new LoginPanel(this.cardLayout, mainPanel);
        registrationPanel = new RegistrationPanel(e -> cardLayout.show(mainPanel, "Login"));
        adminHomePanel = new AdminHomePanel();
        adminNewEventPanel = new AdminNewEventPanel();
        adminMyEventsPanel = new AdminMyEventsPanel();

        // Aggiunta dei pannelli al CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");
        mainPanel.add(adminHomePanel, "Admin Home");
        mainPanel.add(adminNewEventPanel, "Admin New Event");
        mainPanel.add(adminMyEventsPanel, "Admin My Events");

        // Imposta l'ascoltatore per la navigazione tra i pannelli
        loginPanel.setSwitchToRegisterAction(e -> cardLayout.show(mainPanel, "Registration"));

        // Gestione dell'azione di passaggio dal pannello AdminHomePanel
        adminHomePanel.setSwitchToNewEventAction(e -> cardLayout.show(mainPanel, "Admin New Event"));
        adminHomePanel.setSwitchToMyEventsAction(e -> cardLayout.show(mainPanel, "Admin My Events"));

        // Aggiunta del listener per il pulsante "Torna alla Home"
        adminNewEventPanel.setBackToAdminHomeAction(e -> cardLayout.show(mainPanel, "Admin Home"));
        adminMyEventsPanel.setBackToAdminHomeAction(e -> cardLayout.show(mainPanel, "Admin Home"));

        // Aggiunta dell'azione per il pulsante "Logout"
        adminHomePanel.setLogoutAction(e -> cardLayout.show(mainPanel, "Login"));

        // Aggiunta del mainPanel alla finestra
        add(mainPanel);

        // Mostra il pannello di login all'avvio
        cardLayout.show(mainPanel, "Login");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
