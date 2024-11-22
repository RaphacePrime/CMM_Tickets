package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import classes_package.Luogo;
import database_package.AdminLuoghiDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminAddLuogoPanel extends JPanel {
    private JTextField nameField;
    private JTextField cityField;
    private JTextField addressField;
    private JButton addButton;
    private static final Logger logger = LogManager.getLogger(AdminAddLuogoPanel.class);

    public AdminAddLuogoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250)); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); 
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1), 
                BorderFactory.createEmptyBorder(20, 20, 20, 20) 
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        
        JLabel titleLabel = new JLabel("Aggiungi Nuovo Luogo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        
        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14)); 
        nameField.setPreferredSize(new Dimension(0, 30)); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);

       
        JLabel cityLabel = new JLabel("Città:");
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(cityLabel, gbc);

        cityField = new JTextField(20);
        cityField.setFont(new Font("Arial", Font.PLAIN, 14));
        cityField.setPreferredSize(new Dimension(0, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(cityField, gbc);

        
        JLabel addressLabel = new JLabel("Indirizzo:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(addressLabel, gbc);

        addressField = new JTextField(20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        addressField.setPreferredSize(new Dimension(0, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(addressField, gbc);

        
        addButton = new JButton("Aggiungi");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(new Color(75, 175, 110)); // Colore verde chiaro
        addButton.setForeground(Color.WHITE); // Testo bianco
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 85), 2)); // Bordo visibile verde scuro
        addButton.setOpaque(true); // Forza il rendering del colore di sfondo
        addButton.setPreferredSize(new Dimension(200, 40)); // Altezza maggiore per il pulsante
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);


        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLuogo();
            }
        });

        
        add(formPanel, BorderLayout.CENTER);
    }

    
    private void addLuogo() {
        String nome = nameField.getText().trim();
        String indirizzo = addressField.getText().trim();
        String citta = cityField.getText().trim();

        if (nome.isEmpty() || indirizzo.isEmpty() || citta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Luogo nuovoLuogo = new Luogo(nome, citta, indirizzo);
        boolean success = AdminLuoghiDatabase.addLuogo(nuovoLuogo);

        if (success) {
            JOptionPane.showMessageDialog(this, "Luogo aggiunto con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Luogo aggiunto: " + nome);
            nameField.setText("");
            cityField.setText("");
            addressField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Errore durante l'aggiunta del luogo. Verifica che il nome sia unico.", "Errore", JOptionPane.ERROR_MESSAGE);
            logger.error("Errore durante l'aggiunta del luogo: " + nome);
        }
    }
}
