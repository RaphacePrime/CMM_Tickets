package adminpanels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import classes_package.Luogo;
import database_package.LuoghiDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminAddLuogoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
	private JTextField cityField;
	private JTextField addressField;
	private JButton addButton;
	private JButton uploadImageButton;
	private JLabel imagePreview;
	private String nomeFile = null;
	private static final Logger logger = LogManager.getLogger(AdminAddLuogoPanel.class);

	public AdminAddLuogoPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(230, 230, 250));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);
		formPanel.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
						BorderFactory.createEmptyBorder(20, 20, 20, 20)));

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

		uploadImageButton = new JButton("Carica immagine");
		uploadImageButton.setFont(new Font("Arial", Font.PLAIN, 16));
		uploadImageButton.setBackground(Color.LIGHT_GRAY);
		uploadImageButton.setForeground(Color.BLACK);
		uploadImageButton.setFocusPainted(false);
		uploadImageButton.setOpaque(true);
		uploadImageButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		formPanel.add(uploadImageButton, gbc);

		imagePreview = new JLabel();
		imagePreview.setPreferredSize(new Dimension(350, 200));
		imagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		gbc.gridx = 1;
		gbc.gridy = 4;
		formPanel.add(imagePreview, gbc);

		uploadImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(AdminAddLuogoPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String destinationPath = "src/main/resources/Immagini/" + selectedFile.getName();
					File destinationFile = new File(destinationPath);
					try {
						Files.copy(selectedFile.toPath(), destinationFile.toPath(),
								StandardCopyOption.REPLACE_EXISTING);
						nomeFile = selectedFile.getName();
						ImageIcon icon = new ImageIcon(destinationFile.getAbsolutePath());
						Image scaledImage = icon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
						imagePreview.setIcon(new ImageIcon(scaledImage));

						JOptionPane.showMessageDialog(AdminAddLuogoPanel.this, "Immagine caricata con successo!");

					} catch (IOException ex) {
						JOptionPane.showMessageDialog(AdminAddLuogoPanel.this,
								"Errore nel caricamento dell'immagine: " + ex.getMessage(), "Errore",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		});

		addButton = new JButton("Aggiungi");
		addButton.setFont(new Font("Arial", Font.PLAIN, 16));
		addButton.setBackground(new Color(75, 175, 110));
		addButton.setForeground(Color.WHITE);
		addButton.setFocusPainted(false);
		addButton.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 85), 2));
		addButton.setOpaque(true);
		addButton.setPreferredSize(new Dimension(200, 40));
		gbc.gridx = 0;
		gbc.gridy = 5;
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

		if (nome.isEmpty() || indirizzo.isEmpty() || citta.isEmpty() || nomeFile == null) {
			JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Luogo nuovoLuogo = new Luogo(nome, citta, indirizzo, nomeFile);
		boolean success = LuoghiDatabase.addLuogo(nuovoLuogo);

		if (success) {
			JOptionPane.showMessageDialog(this, "Luogo aggiunto con successo!", "Successo",
					JOptionPane.INFORMATION_MESSAGE);
			logger.info("Luogo aggiunto: " + nome);
			nameField.setText("");
			cityField.setText("");
			addressField.setText("");
			imagePreview.setIcon(null);
			nomeFile = null;
		} else {
			JOptionPane.showMessageDialog(this, "Errore durante l'aggiunta del luogo. Verifica che il nome sia unico.",
					"Errore", JOptionPane.ERROR_MESSAGE);
			logger.error("Errore durante l'aggiunta del luogo: " + nome);
		}
	}
}
