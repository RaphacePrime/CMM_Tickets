package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMyEventsPanel extends JPanel {

    private JButton backToAdminHomeButton;

    public AdminMyEventsPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Creazione del pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        setBackground(new Color(240, 240, 240));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo del pannello
        JLabel titleLabel = new JLabel("Gestisci Eventi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20));

        // Pulsante per gestire l'evento
        JButton manageEventButton = new JButton("Gestisci Evento");
        manageEventButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageEventButton.setBackground(new Color(33, 150, 243)); 
        manageEventButton.setForeground(Color.WHITE);
        manageEventButton.setFocusPainted(false);
        manageEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(manageEventButton);

        // Pannello per il pulsante "Torna alla Home" in alto a sinistra
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Pulsante "Torna alla Home" con lo stesso stile del "Logout"
        backToAdminHomeButton = new JButton("<html><u>Torna alla Home</u></html>"); // Aggiungi sottolineato al testo
        backToAdminHomeButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Non corsivo
        backToAdminHomeButton.setBackground(null); // Nessun sfondo
        backToAdminHomeButton.setForeground(Color.BLACK); // Colore del testo nero
        backToAdminHomeButton.setFocusPainted(false); // Nessun bordo quando si clicca
        backToAdminHomeButton.setBorderPainted(false); // Nessun bordo
        backToAdminHomeButton.setOpaque(false); // Non opaco
        backToAdminHomeButton.setBorder(null); // Nessun bordo
        backToAdminHomeButton.setContentAreaFilled(false); // Nessun riempimento
        backPanel.add(backToAdminHomeButton);

        // Aggiungi il pannello "backPanel" nel GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Occupare tutta la larghezza
        add(backPanel, gbc);

        // Aggiungi il pannello principale sotto il pulsante
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset gridwidth
        add(mainPanel, gbc);
    }

    // Metodo per impostare l'azione del pulsante "Torna alla Home"
    public void setBackToAdminHomeAction(ActionListener action) {
        backToAdminHomeButton.addActionListener(action);
    }
}
