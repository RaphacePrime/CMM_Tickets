package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminHomePanel extends JPanel {
    private JButton switchToNewEventButton;
    private JButton switchToRegisterButton;

    public AdminHomePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Aggiusta gli spazi esterni

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        
        setBackground(new Color(240, 240, 240));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Seleziona un'opzione");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20));

        switchToNewEventButton = new JButton("Nuovo evento");
        switchToNewEventButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToNewEventButton.setBackground(new Color(33, 150, 243)); // Colore blu per il bottone
        switchToNewEventButton.setForeground(Color.WHITE);
        switchToNewEventButton.setFocusPainted(false);
        switchToNewEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(switchToNewEventButton);

        mainPanel.add(Box.createVerticalStrut(10));

        switchToRegisterButton = new JButton("Gestisci eventi");
        switchToRegisterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToRegisterButton.setBackground(new Color(33, 150, 243));
        switchToRegisterButton.setForeground(Color.WHITE);
        switchToRegisterButton.setFocusPainted(false);
        switchToRegisterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(switchToRegisterButton);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainPanel, gbc);
    }

    // Aggiungi i gestori per le azioni sui pulsanti
    public void setSwitchToNewEventAction(ActionListener action) {
        switchToNewEventButton.addActionListener(action);
    }

    public void setSwitchToRegisterAction(ActionListener action) {
        switchToRegisterButton.addActionListener(action);
    }
}
