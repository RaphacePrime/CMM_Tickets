package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminHomePanel extends JPanel {
    private JButton switchToNewEventButton;
    private JButton switchToMyEventsButton;
    private JButton logoutButton; // Aggiungi il pulsante di logout

    public AdminHomePanel() {
        // Usare GridBagLayout per il layout principale
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Aggiusta gli spazi esterni

        // Pannello principale per allineare i componenti verticalmente
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        setBackground(new Color(240, 240, 240));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etichetta del titolo
        JLabel titleLabel = new JLabel("Seleziona un'opzione");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20)); // Spaziatura tra il titolo e il primo pulsante

        // Pulsante "Nuovo evento"
        switchToNewEventButton = new JButton("Nuovo evento");
        switchToNewEventButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToNewEventButton.setBackground(new Color(33, 150, 243)); // Colore blu
        switchToNewEventButton.setForeground(Color.WHITE);
        switchToNewEventButton.setFocusPainted(false);
        switchToNewEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToNewEventButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        switchToNewEventButton.setOpaque(true);
        switchToNewEventButton.setBorderPainted(false);
        mainPanel.add(switchToNewEventButton);

        mainPanel.add(Box.createVerticalStrut(10)); // Spaziatura tra i pulsanti

        // Pulsante "Gestisci eventi"
        switchToMyEventsButton = new JButton("Gestisci eventi");
        switchToMyEventsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToMyEventsButton.setBackground(new Color(33, 150, 243)); // Colore blu
        switchToMyEventsButton.setForeground(Color.WHITE);
        switchToMyEventsButton.setFocusPainted(false);
        switchToMyEventsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToMyEventsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        switchToMyEventsButton.setOpaque(true);
        switchToMyEventsButton.setBorderPainted(false);

        mainPanel.add(switchToMyEventsButton);

        // Aggiungi il pannello principale al layout di GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 1; // Posizioniamo il mainPanel sotto il pulsante di logout
        add(mainPanel, gbc);

        // Posizioniamo il pulsante "Logout" fuori dal GridBagLayout (in alto a sinistra)
        logoutButton = new JButton("<html><u>Logout</u></html>"); // Aggiungi sottolineato al testo
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Non corsivo
        logoutButton.setBackground(null); // Nessun sfondo
        logoutButton.setForeground(Color.BLACK); // Colore del testo nero
        logoutButton.setFocusPainted(false); // Nessun bordo quando si clicca
        logoutButton.setBorderPainted(false); // Nessun bordo
        logoutButton.setOpaque(false); // Non opaco
        logoutButton.setBorder(null); // Nessun bordo
        logoutButton.setContentAreaFilled(false); // Nessun riempimento

        // Aggiungi logoutButton manualmente al pannello
        logoutButton.setBounds(10, 10, 100, 30); // Posiziona il bottone manualmente
        add(logoutButton); // Aggiungi direttamente al pannello
    }

    // Aggiungi i gestori per le azioni sui pulsanti
    public void setSwitchToNewEventAction(ActionListener action) {
        switchToNewEventButton.addActionListener(action);
    }

    public void setSwitchToMyEventsAction(ActionListener action) {
        switchToMyEventsButton.addActionListener(action);
    }

    // Aggiungi il gestore per l'azione di logout
    public void setLogoutAction(ActionListener action) {
        logoutButton.addActionListener(action);
    }
}
