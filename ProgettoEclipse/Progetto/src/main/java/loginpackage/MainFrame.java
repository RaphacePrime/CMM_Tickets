package loginpackage;

import javax.swing.*;
import java.awt.*;
import ProgettoIngegneriaDelSoftware.Progetto.Utente;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;

    public MainFrame() {
        setTitle("Sistema di Accesso e Registrazione");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inizializzazione del CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Creazione dei pannelli di Login e Registrazione
        loginPanel = new LoginPanel();
        registrationPanel = new RegistrationPanel(e -> cardLayout.show(mainPanel, "Login"));

        // Aggiunta dei pannelli al CardLayout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrationPanel, "Registration");

        add(mainPanel);

        // Mostra il pannello di login all'avvio
        cardLayout.show(mainPanel, "Login");

        // Gestione dell'azione di cambio pannello
        loginPanel.setSwitchToRegisterAction(e -> cardLayout.show(mainPanel, "Registration"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
