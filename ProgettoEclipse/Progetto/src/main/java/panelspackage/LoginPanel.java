package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import loginpackage.Login;
import classespackage.Utente;
import framespackage.AdminFrame;
import framespackage.UtenteFrame;

public class LoginPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel; // Variabile di classe
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchToRegisterButton;
    
    // Costruttore che accetta CardLayout e mainPanel
    public LoginPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;  // Usa il mainPanel passato come parametro
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Aggiusta gli spazi esterni

        // Pannello contenitore per centralizzare e migliorare lo stile
        JPanel innerMainPanel = new JPanel();
        innerMainPanel.setLayout(new BoxLayout(innerMainPanel, BoxLayout.Y_AXIS));
        innerMainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margine interno
        innerMainPanel.setBackground(Color.WHITE);
        
        // Aggiungi un bordo ombra per far risaltare il pannello
        setBackground(new Color(240, 240, 240)); // Grigio chiaro di sfondo
        innerMainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etichetta del titolo
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerMainPanel.add(titleLabel);

        innerMainPanel.add(Box.createVerticalStrut(20)); // Spaziatura

        // Campo Username
        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerMainPanel.add(createLabeledField("Username", usernameField));

        // Campo Password
        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerMainPanel.add(createLabeledField("Password", passwordField));

        innerMainPanel.add(Box.createVerticalStrut(20)); // Spaziatura tra campi e pulsanti

        // Pulsante Accedi
        loginButton = new JButton("Accedi");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBackground(new Color(33, 150, 243)); // Colore blu per il bottone
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Larghezza e altezza pulsante
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Utente utente = Login.autentica(username, password);
            if (utente != null) {
                JOptionPane.showMessageDialog(this, "Benvenuto " + utente.getUsername());
                if (utente.isAdmin()) {
                    // Se è admin, mostra il pannello AdminHomePanel
                    cardLayout.show(mainPanel, "Admin Home");
                } else {
                    // Per l'utente normale, apri una finestra separata
                    new UtenteFrame().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        innerMainPanel.add(loginButton);

        innerMainPanel.add(Box.createVerticalStrut(10)); // Spaziatura tra pulsanti

        // Pulsante Registrati
        switchToRegisterButton = new JButton("Registrati");
        switchToRegisterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToRegisterButton.setBackground(new Color(33, 150, 243));
        switchToRegisterButton.setForeground(Color.WHITE);
        switchToRegisterButton.setFocusPainted(false);
        switchToRegisterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToRegisterButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        switchToRegisterButton.setOpaque(true);
        switchToRegisterButton.setBorderPainted(false);
        
        innerMainPanel.add(switchToRegisterButton);

        // Aggiungi il mainPanel al centro del pannello principale con layout GridBag
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(innerMainPanel, gbc);
    }

    // Metodo per creare un campo con etichetta e spaziatura
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(5), BorderLayout.CENTER); // Spazio tra label e campo
        panel.add(textField, BorderLayout.SOUTH);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); // Trasparente per mantenere il colore di sfondo del contenitore
        return panel;
    }

    public void setSwitchToRegisterAction(ActionListener action) {
        switchToRegisterButton.addActionListener(action);
    }
}
