package panels_package;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import classes_package.Luogo;
import database_package.AdminLuoghiDatabase;

public class AdminDetailsLuogoPanel extends JPanel {
    private JLabel nameTextLabel;
    private JTextField nameValueField; // Modificato da JLabel a JTextField
    private JLabel cityTextLabel;
    private JTextField cityValueField; // Modificato da JLabel a JTextField
    private JLabel addressTextLabel;
    private JTextField addressValueField; // Modificato da JLabel a JTextField
    //private JButton backButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    private Luogo luogo;
    private static final Logger logger = LogManager.getLogger(AdminDetailsLuogoPanel.class);

    public AdminDetailsLuogoPanel(Luogo luogo) {
    	this.luogo=luogo;
    	logger.info("Luogo in dettaglio: "+this.luogo.getIdLuogo());
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
        
        InputStream imageStream;
        if(luogo.getNomeFile()==null)
        {
        	imageStream = getClass().getClassLoader().getResourceAsStream("Immagini/default.png");
        }
        else
        {
        	String path="Immagini/"+luogo.getNomeFile();
        	logger.info("Path file: "+ path);
        	imageStream = getClass().getClassLoader().getResourceAsStream(path);
        }

        try {
            imageIcon = new ImageIcon(ImageIO.read(imageStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageLabel = new JLabel(imageIcon);
        add(imageLabel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //backButton = createButton("Torna Indietro", new Color(75, 110, 175)); // Blu
        deleteButton = createButton("Elimina", new Color(200, 50, 50)); // Rosso
        updateButton = createButton("Aggiorna", new Color(255, 140, 0)); // Giallo scuro

        //buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLuogo();
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLuogo();
            }
        });
    }
    
    private void updateLuogo() {
        String nome = nameValueField.getText().trim();
        String indirizzo = addressValueField.getText().trim();
        String citta = cityValueField.getText().trim();
        String nomeFile="juventus_stadium.png";

        if (nome.isEmpty() || indirizzo.isEmpty() || citta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Luogo nuovoLuogo = new Luogo(nome, citta, indirizzo, nomeFile);
        boolean success = AdminLuoghiDatabase.updateLuogo(nuovoLuogo,this.luogo.getIdLuogo());

        if (success) {
            JOptionPane.showMessageDialog(this, "Luogo aggiornato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Luogo aggiornato: " + nuovoLuogo.getNome());

        } else {
            JOptionPane.showMessageDialog(this, "Errore durante aggiornamento del luogo. Verifica che il nome sia corretto.", "Errore", JOptionPane.ERROR_MESSAGE);
            logger.error("Errore durante l'aggiornamento del luogo: " + nuovoLuogo.getNome());
        }
    }
    
    private void deleteLuogo() {
    	boolean success=AdminLuoghiDatabase.deleteLuogo(luogo);
    	if(success)
    	{
    		JOptionPane.showMessageDialog(this, "Elemento correttamente eliminato", "Eliminazione confermata", JOptionPane.PLAIN_MESSAGE);
    		logger.info("Luogo eliminato: "+luogo.getNome());
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione del luogo.", "Errore", JOptionPane.ERROR_MESSAGE);
            logger.error("Errore durante l'eliminazione del luogo: " + luogo.getNome());

    	}
    }

    private JLabel createTextLabel(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private JTextField createEditableField(String value) {
        JTextField textField = new JTextField(value);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setEditable(true);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return textField;
    }

    private JPanel createLabeledField(JLabel label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

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
        button.setOpaque(true); // Garantisce che il colore sia visibile su macOS
        return button;
    }

    /*public void setBackButtonAction(ActionListener action) {
        backButton.addActionListener(action);
    }*/

    public void setDeleteButtonAction(ActionListener action) {
        deleteButton.addActionListener(action);
    }

    public void setUpdateButtonAction(ActionListener action) {
        updateButton.addActionListener(action);
    }
}
