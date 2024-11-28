package panels_package;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
    private ImageIcon imageIcon=null;
    private ImageIcon imageIconSmall=null;
    private JButton uploadImageButton;
    private JLabel imagePreview;
    private String nomeFile= null;
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
        detailsPanel.add(Box.createVerticalStrut(15));
        uploadImageButton = new JButton("Carica nuova immagine");
        uploadImageButton.setFont(new Font("Arial", Font.PLAIN, 16));
        uploadImageButton.setBackground(Color.LIGHT_GRAY);
        uploadImageButton.setForeground(Color.BLACK); // Testo bianco per contrasto migliore
        uploadImageButton.setFocusPainted(false);
        uploadImageButton.setOpaque(true); // Garantisce che il colore sia visibile su macOS
        uploadImageButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Bordo visibile per evidenza
        
        detailsPanel.add(uploadImageButton);

        detailsPanel.add(Box.createVerticalStrut(15));
        
        imagePreview = new JLabel();
        imagePreview.setPreferredSize(new Dimension(350,200));
        imagePreview.setMaximumSize(new Dimension(350,200));
        imagePreview.setHorizontalAlignment(JLabel.CENTER);
        imagePreview.setVerticalAlignment(JLabel.CENTER);
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        String path3="src/main/resources/Immagini/default.png"; 
    	imageIconSmall = new ImageIcon(path3);
        imagePreview.setIcon(imageIconSmall);
        
        detailsPanel.add(imagePreview);
        
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(AdminDetailsLuogoPanel.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String destinationPath = "src/main/resources/Immagini/"+selectedFile.getName();
                    File destinationFile = new File(destinationPath);
                    nomeFile = selectedFile.getName(); 
                    try {
                    	Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    	nomeFile=selectedFile.getName();
                    	ImageIcon icon = new ImageIcon(destinationFile.getAbsolutePath());
                        Image scaledImage = icon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
                        imagePreview.setIcon(new ImageIcon(scaledImage));

                        JOptionPane.showMessageDialog(AdminDetailsLuogoPanel.this, "Immagine caricata con successo!");
                        //ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                        //Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        //imagePreview.setIcon(new ImageIcon(scaledImage));
                    } catch (IOException ex) {
                    	JOptionPane.showMessageDialog(AdminDetailsLuogoPanel.this, "Errore nel caricamento dell'immagine: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        mainPanel.add(detailsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
        
        //InputStream imageStream;
        if(luogo.getNomeFile()==null)
        {
        	String path="src/main/resources/Immagini/default.png"; 
        	imageIcon = new ImageIcon(path);
        }
        else
        {
        	String path="src/main/resources/Immagini/"+luogo.getNomeFile(); 
            File file = new File(path);

            if (file.exists()) {
                logger.info("File trovato nelle risorse!");
                logger.info("Path file: "+ path);
            	//imageStream = getClass().getClassLoader().getResourceAsStream(path);
            	imageIcon = new ImageIcon(path);
            } else {
                logger.warn("Il file non esiste.");
                String path2="src/main/resources/Immagini/default.png"; 
            	imageIcon = new ImageIcon(path2);
            }
        }
        
        imageLabel = new JLabel();
        int labelWidth = 650; 
        int labelHeight = 400; 
        Image originalImage = imageIcon.getImage();
        double originalWidth = originalImage.getWidth(null);
        double originalHeight = originalImage.getHeight(null);
        double widthRatio = labelWidth / originalWidth;
        double heightRatio = labelHeight / originalHeight;
        double scale = Math.max(widthRatio, heightRatio); 
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);
        Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 50));
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
        //

        if (nome.isEmpty() || indirizzo.isEmpty() || citta.isEmpty() || nomeFile==null) {
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
