package adminpanels_package;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import classes_package.Settore;
import utils_package.LookAndFeelUtil;

public class AdminAddSectorsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel imageLabel;
	private static final int IMAGE_WIDTH = 500;
	private static final int IMAGE_HEIGHT = 300;
	private JButton lastSelectedButton = null;
	JTextField nomeField = new JTextField();
	JTextField prezzoField = new JTextField();
	JTextField postiTotaliField = new JTextField();
	private int xlastselected = 0;
	private int ylastselected = 0;
	private int anello = 0;
	private String posizione = "";
	private static final Logger logger = LogManager.getLogger(AdminAddSectorsPanel.class);
	private AdminAddEventPanel aaep;

	public AdminAddSectorsPanel(AdminAddEventPanel aaep) {
		this.aaep = aaep;
		LookAndFeelUtil.setCrossPlatformLookAndFeel();
		setLayout(new BorderLayout());

		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton calcioButton = new JButton("Partita di calcio");
		JButton concertoButton = new JButton("Concerto");

		topButtonPanel.add(calcioButton);
		topButtonPanel.add(concertoButton);
		add(topButtonPanel, BorderLayout.NORTH);

		JPanel centralWrapper = new JPanel(new BorderLayout());
		centralWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JPanel gridPanel = new JPanel(new GridBagLayout());
		gridPanel.setPreferredSize(new Dimension(1000, gridPanel.getPreferredSize().height));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		String path = "src/main/resources/Immagini/campocalcio.jpg";
		imageLabel = new JLabel();
		imageLabel.setMinimumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);
		updateImage(path);

		addButton(gridPanel, gbc, 4, 1, new Dimension(IMAGE_WIDTH, 30));
		addButton(gridPanel, gbc, 4, 2, new Dimension(IMAGE_WIDTH, 30));
		addButton(gridPanel, gbc, 4, 3, new Dimension(IMAGE_WIDTH, 30));

		addButton(gridPanel, gbc, 1, 4, new Dimension(60, IMAGE_HEIGHT));
		addButton(gridPanel, gbc, 2, 4, new Dimension(60, IMAGE_HEIGHT));
		addButton(gridPanel, gbc, 3, 4, new Dimension(60, IMAGE_HEIGHT));

		gbc.gridx = 4;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gridPanel.add(imageLabel, gbc);

		addButton(gridPanel, gbc, 5, 4, new Dimension(60, IMAGE_HEIGHT));
		addButton(gridPanel, gbc, 6, 4, new Dimension(60, IMAGE_HEIGHT));
		addButton(gridPanel, gbc, 7, 4, new Dimension(60, IMAGE_HEIGHT));

		addButton(gridPanel, gbc, 4, 5, new Dimension(IMAGE_WIDTH, 30));
		addButton(gridPanel, gbc, 4, 6, new Dimension(IMAGE_WIDTH, 30));
		addButton(gridPanel, gbc, 4, 7, new Dimension(IMAGE_WIDTH, 30));

		centralWrapper.add(gridPanel, BorderLayout.CENTER);
		add(centralWrapper, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(5, 2, 10, 10));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		bottomPanel.add(new JLabel("Nome Settore:"));
		nomeField = new JTextField();
		bottomPanel.add(nomeField);

		bottomPanel.add(new JLabel("Prezzo:"));
		prezzoField = new JTextField();
		bottomPanel.add(prezzoField);

		bottomPanel.add(new JLabel("Posti Totali:"));
		postiTotaliField = new JTextField();
		bottomPanel.add(postiTotaliField);

		JButton eliminaButton = new JButton("Elimina settore selezionato");
		eliminaButton.setOpaque(true);
		eliminaButton.addActionListener(e -> {
			if (lastSelectedButton != null) {
				lastSelectedButton.setText("+");
				lastSelectedButton.setBackground(Color.WHITE);

				boolean controllo = false;
				setAnelloPosizione(xlastselected, ylastselected);
				for (int i = 0; i < aaep.settori.size(); i++) {
					Settore s = aaep.settori.get(i);
					if (s.getAnello() == anello && s.getPosizione() == posizione) {
						aaep.settori.remove(i);

						controllo = true;
					}
				}
				if (controllo == false) {
					JOptionPane.showMessageDialog(this, "Nessun settore trovato da eliminare");
				} else {
					JOptionPane.showMessageDialog(this, "Settore eliminato correttamente");
					logger.info("Lista settori :");
					for (int i = 0; i < aaep.settori.size(); i++) {
						Settore s = aaep.settori.get(i);
						s.showLoggerSettore();

					}
				}
				resetFields();
				lastSelectedButton = null;
			} else {
				JOptionPane.showMessageDialog(this, "Seleziona un evento da eliminare");
			}
		});
		bottomPanel.add(eliminaButton);
		add(bottomPanel, BorderLayout.SOUTH);
		calcioButton.addActionListener(e -> updateImage("src/main/resources/Immagini/campocalcio.jpg"));
		concertoButton.addActionListener(e -> updateImage("src/main/resources/Immagini/concerto.jpg"));
		JButton salvaSettoriButton = new JButton("Salva Settori");
		salvaSettoriButton.setOpaque(true);
		salvaSettoriButton.setBackground(new Color(0, 128, 0));
		salvaSettoriButton.setForeground(Color.WHITE);
		salvaSettoriButton.setFont(new Font("Arial", Font.BOLD, 14));
		salvaSettoriButton.addActionListener(e -> goBack());
		bottomPanel.add(salvaSettoriButton);

	}

	private void goBack() {
		AdminHomePanel.switchToAddEventButton.doClick();
	}

	private void addButton(JPanel panel, GridBagConstraints gbc, int x, int y, Dimension size) {
		JButton button = new JButton("+");
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setBorderPainted(false);
		button.setPreferredSize(size);
		button.setFont(new Font("Arial", Font.PLAIN, 14));
		setAnelloPosizione(x, y);
		for (int j = 0; j < aaep.settori.size(); j++) {
			Settore s = aaep.settori.get(j);
			if (s.getAnello() == anello && s.getPosizione().equals(posizione)) {
				logger.info("Settore trovato!");
				button.setText("X");
				button.setBackground(new Color(33, 150, 243));
				button.setOpaque(true);
			}

		}

		GridBagConstraints localGbc = new GridBagConstraints();
		localGbc.gridx = x;
		localGbc.gridy = y;
		localGbc.gridwidth = 1;
		localGbc.gridheight = 1;
		localGbc.weightx = 0.0;
		localGbc.weighty = 0.0;
		localGbc.anchor = GridBagConstraints.CENTER;
		localGbc.insets = new Insets(5, 5, 5, 5);

		button.addActionListener(e -> {
			resetBorders();
			String errorMessage = "";
			String nome = this.nomeField.getText();
			float prezzo = 0;
			int postiTotali = 0;
			boolean controllo = true;
			xlastselected = x;
			ylastselected = y;

			if (button.getText().equals("+")) {
				if (nome.isEmpty()) {
					errorMessage += "- Il nome non può essere vuoto\n";
					nomeField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					controllo = false;
				}

				try {
					prezzo = Float.parseFloat(this.prezzoField.getText());
					if (prezzo < 0) {
						errorMessage += "- Il prezzo non può essere negativo \n";
						prezzoField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
						controllo = false;
					}
				} catch (Exception e2) {
					errorMessage += "- Il prezzo deve essere numerico\n";
					prezzoField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					controllo = false;
				}

				try {
					postiTotali = Integer.parseInt(this.postiTotaliField.getText());
					if (postiTotali < 0) {
						errorMessage += "- Il prezzo non può essere negativo \n";
						postiTotaliField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
						controllo = false;
					}
				} catch (Exception e2) {
					errorMessage += "- I posti totali devono essere un numero intero \n";
					postiTotaliField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					controllo = false;
				}

				if (controllo) {
					if (lastSelectedButton != null) {

						if (!lastSelectedButton.getText().equals("+")) {
							lastSelectedButton.setBackground(new Color(33, 150, 243));
						}
					}
					lastSelectedButton = null;
					button.setText("X");
					button.setBackground(new Color(33, 150, 243));
					button.setOpaque(true);
					logger.info("Pulsante cliccato: Posizione (" + x + ", " + y + ")");

					setAnelloPosizione(x, y);

					Settore nuovosettore = new Settore(nome, prezzo, posizione, anello, postiTotali, 0, 0);
					nuovosettore.showLoggerSettore();
					aaep.settori.add(nuovosettore);
					resetFields();
				} else {
					if (!errorMessage.isEmpty()) {
						JOptionPane.showMessageDialog(this, errorMessage, "Errore nell'aggiunta del settore",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

			} else {
				if (lastSelectedButton != null) {

					lastSelectedButton.setBackground(new Color(33, 150, 243));
				}
				lastSelectedButton = button;
				button.setBackground(Color.BLUE);
				setAnelloPosizione(x, y);
				for (int i = 0; i < aaep.settori.size(); i++) {
					Settore s = aaep.settori.get(i);
					if (s.getAnello() == anello && s.getPosizione() == posizione) {
						nomeField.setText(s.getNome());
						prezzoField.setText(String.valueOf(s.getPrezzo()));
						postiTotaliField.setText(String.valueOf(s.getPostiTotali()));
					}
				}

			}

		});

		panel.add(button, localGbc);
	}

	private void updateImage(String imagePath) {
		ImageIcon imageIcon = new ImageIcon(imagePath);
		Image scaledImage = imageIcon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(scaledImage));
		revalidate();
		repaint();
	}

	private void resetBorders() {
		nomeField.setBorder(UIManager.getBorder("TextField.border"));
		prezzoField.setBorder(UIManager.getBorder("TextField.border"));
		postiTotaliField.setBorder(UIManager.getBorder("TextField.border"));

	}

	private void resetFields() {
		nomeField.setText("");
		prezzoField.setText("");
		postiTotaliField.setText("");

	}

	private void setAnelloPosizione(int x, int y) {
		if (x == 4) {
			if (y == 1) {
				posizione = "nord";
				anello = 3;
			} else if (y == 2) {
				posizione = "nord";
				anello = 2;
			} else if (y == 3) {
				posizione = "nord";
				anello = 1;
			} else if (y == 5) {
				posizione = "sud";
				anello = 1;
			} else if (y == 6) {
				posizione = "sud";
				anello = 2;
			} else if (y == 7) {
				posizione = "sud";
				anello = 3;
			}
		} else if (y == 4) {
			if (x == 1) {
				posizione = "ovest";
				anello = 3;
			} else if (x == 2) {
				posizione = "ovest";
				anello = 2;
			} else if (x == 3) {
				posizione = "ovest";
				anello = 1;
			} else if (x == 5) {
				posizione = "est";
				anello = 1;
			} else if (x == 6) {
				posizione = "est";
				anello = 2;
			} else if (x == 7) {
				posizione = "est";
				anello = 3;
			}
		}

	}
}
