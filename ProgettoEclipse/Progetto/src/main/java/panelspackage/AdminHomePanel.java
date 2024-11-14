package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import loginpackage.Login;
import classespackage.Utente;
import framespackage.AdminFrame;
import framespackage.UtenteFrame;

public class AdminHomePanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton switchToNewEventButton;
    private JButton switchToRegisterButton;

    public AdminHomePanel() {
    	
    	
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Aggiusta gli spazi esterni

        // Pannello contenitore per centralizzare e migliorare lo stile
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margine interno
        mainPanel.setBackground(Color.WHITE);
        
        // Aggiungi un bordo ombra per far risaltare il pannello
        setBackground(new Color(240, 240, 240)); // Grigio chiaro di sfondo
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etichetta del titolo
        JLabel titleLabel = new JLabel("Seleziona un opzione");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20)); // Spaziatura
        
        // Pulsante New Event
        switchToNewEventButton = new JButton("Nuovo evento");
        switchToNewEventButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToNewEventButton.setBackground(new Color(33, 150, 243)); // Colore blu per il bottone
        switchToNewEventButton.setForeground(Color.WHITE);
        switchToNewEventButton.setFocusPainted(false);
        switchToNewEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToNewEventButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Larghezza e altezza pulsante
        switchToNewEventButton.setOpaque(true);
        switchToNewEventButton.setBorderPainted(false);
        
        mainPanel.add(switchToNewEventButton);
        mainPanel.add(Box.createVerticalStrut(10)); // Spaziatura tra pulsanti

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
        
        mainPanel.add(switchToRegisterButton);

        // Aggiungi il mainPanel al centro del pannello principale con layout GridBag
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainPanel, gbc);
    }


    public void setSwitchToNewEventAction(ActionListener action) {
        switchToNewEventButton.addActionListener(action);
    }
}
