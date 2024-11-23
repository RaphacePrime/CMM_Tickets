package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import classes_package.Luogo;

public class AdminDetailsLuogoPanel extends JPanel {
    private JLabel nameTextLabel;
    private JTextField nameValueField; // Modificato da JLabel a JTextField
    private JLabel cityTextLabel;
    private JTextField cityValueField; // Modificato da JLabel a JTextField
    private JLabel addressTextLabel;
    private JTextField addressValueField; // Modificato da JLabel a JTextField
    private JButton backButton;
    private JButton deleteButton;
    private JButton updateButton;

    public AdminDetailsLuogoPanel(Luogo luogo) {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 240, 240)); // A light background color

        JLabel titleLabel = new JLabel("Dettagli del Luogo", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Etichette per "Nome", "Città", "Indirizzo" con JTextField
        nameTextLabel = createTextLabel("Nome:");
        nameValueField = createEditableField(luogo.getNome());
        cityTextLabel = createTextLabel("Città:");
        cityValueField = createEditableField(luogo.getCittà());
        addressTextLabel = createTextLabel("Indirizzo:");
        addressValueField = createEditableField(luogo.getIndirizzo());

        // Aggiungi le etichette e i campi al pannello dei dettagli
        detailsPanel.add(createLabeledField(nameTextLabel, nameValueField));
        detailsPanel.add(Box.createVerticalStrut(15));
        detailsPanel.add(createLabeledField(cityTextLabel, cityValueField));
        detailsPanel.add(Box.createVerticalStrut(15));
        detailsPanel.add(createLabeledField(addressTextLabel, addressValueField));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        mainPanel.add(detailsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backButton = createButton("Torna Indietro", new Color(75, 110, 175)); // Blu
        deleteButton = createButton("Elimina", new Color(200, 50, 50)); // Rosso
        updateButton = createButton("Aggiorna", new Color(255, 140, 0)); // Giallo scuro

        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Metodo per creare l'etichetta di testo (es. "Nome:")
    private JLabel createTextLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    // Metodo per creare il campo di testo modificabile
    private JTextField createEditableField(String value) {
        JTextField textField = new JTextField(value);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setEditable(true); // Rendi il campo di testo modificabile
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Light border for clarity
        return textField;
    }

    // Metodo per creare il pannello con etichetta e campo di testo
    private JPanel createLabeledField(JLabel label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false); // Set the background of the panel to be transparent

        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50));
        return button;
    }

    public void setBackButtonAction(ActionListener action) {
        backButton.addActionListener(action);
    }

    public void setDeleteButtonAction(ActionListener action) {
        deleteButton.addActionListener(action);
    }

    public void setUpdateButtonAction(ActionListener action) {
        updateButton.addActionListener(action);
    }
}
