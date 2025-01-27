package userpanels_package;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import classes_package.Biglietto;
import classes_package.Evento;
import classes_package.Luogo;
import classes_package.Settore;
import database_package.EventsDatabase;
import database_package.LuoghiDatabase;
import database_package.SectorsDatabase;
import database_package.TicketsDatabase;
import login_package.Login;
import login_package.LoginPanel;

public class UserCarrelloPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static List<Biglietto> biglietti = new ArrayList<>();
	private static List<Settore> settori = new ArrayList<>();
	private static List<Evento> eventi = new ArrayList<>();
	private JButton acquistaButton;
	private static final Logger logger = LogManager.getLogger(UserCarrelloPanel.class);

	public UserCarrelloPanel() throws ParseException {
		setLayout(new BorderLayout());
		setBackground(new Color(230, 230, 250));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel titleLabel = new JLabel("Carrello");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		if (biglietti.isEmpty()) {
			JLabel emptyLabel = new JLabel("Nessun biglietto aggiunto al carrello");
			emptyLabel.setFont(new Font("Arial", Font.BOLD, 24));
			emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
			emptyLabel.setForeground(Color.GRAY);
			contentPanel.add(emptyLabel);
		} else {
			for (int i = 0; i < biglietti.size(); i++) {
				Biglietto biglietto = biglietti.get(i);
				EventoSettoreResult esr=  UserCarrelloPanel.findEventoSettore(biglietto);
				JPanel bigliettoPanel = createBigliettoPanel(biglietto);
				logger.info(esr.getEvento().getIdEvento() + " " + biglietto.getIdSettore());
				bigliettoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				contentPanel.add(bigliettoPanel);
				contentPanel.add(Box.createVerticalStrut(10));
			}
		}

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		acquistaButton = new JButton("Acquista");
		acquistaButton.setFont(new Font("Arial", Font.PLAIN, 16));
		acquistaButton.setBackground(new Color(0, 128, 0));
		acquistaButton.setForeground(Color.WHITE);
		acquistaButton.setFocusPainted(false);
		acquistaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buyCart();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(new Color(230, 230, 250));
		buttonPanel.add(acquistaButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void buyCart() throws ParseException {

		if (biglietti.isEmpty()) {
			JOptionPane.showMessageDialog(UserCarrelloPanel.this, "Non hai nessun biglietto nel carrello");
			return;
		}
		
		float costotot = 0;
		EventoSettoreResult esr;

		for (Biglietto bi : biglietti) {
		    esr = UserCarrelloPanel.findEventoSettore(bi);
		    costotot += esr.getSettore().getPrezzo();
		}
		int response = JOptionPane.showConfirmDialog(
		    null, 
		    "Il totale del costo è: " + costotot + "€. Vuoi confermare?", 
		    "Conferma Costo Totale", 
		    JOptionPane.YES_NO_OPTION
		);
		if (response == JOptionPane.NO_OPTION) {
		    return;
		}
		
		boolean hasError = false;
		for (Component comp : getComponents()) {
			if (comp instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) comp;
				JViewport viewport = scrollPane.getViewport();
				Component view = viewport.getView();
				if (view instanceof JPanel) {
					JPanel contentPanel = (JPanel) view;
					for (Component bigliettoPanel : contentPanel.getComponents()) {
						if (bigliettoPanel instanceof JPanel) {
							JPanel panel = (JPanel) bigliettoPanel;
							for (Component fieldComp : panel.getComponents()) {
								if (fieldComp instanceof JTextField) {
									JTextField textField = (JTextField) fieldComp;
									if (textField.getText() == null) {
										JOptionPane.showMessageDialog(UserCarrelloPanel.this,
												"Per favore, compila tutti i campi Nome e Cognome Utilizzatore.",
												"Errore", JOptionPane.ERROR_MESSAGE);
										return;
									}
									if (textField.getText().equals("") || textField.getText().isEmpty()) {
										textField.setBorder(BorderFactory.createLineBorder(Color.RED));
										hasError = true;
									} else {
										textField.setBorder(UIManager.getBorder("TextField.border"));
									}
								}
							}
						}
					}
				}
			}
		}
		if (hasError) {
			JOptionPane.showMessageDialog(UserCarrelloPanel.this,
					"Per favore, compila tutti i campi Nome e Cognome Utilizzatore.", "Errore",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		int b = 0;
		int nome0cognome1 = 0;
		for (Component comp : getComponents()) {
			if (comp instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) comp;
				JViewport viewport = scrollPane.getViewport();
				Component view = viewport.getView();
				if (view instanceof JPanel) {
					JPanel contentPanel = (JPanel) view;
					for (Component bigliettoPanel : contentPanel.getComponents()) {
						if (bigliettoPanel instanceof JPanel) {
							int editable = 0;
							JPanel panel = (JPanel) bigliettoPanel;
							for (Component fieldComp : panel.getComponents()) {
								if (fieldComp instanceof JTextField) {
									JTextField textField = (JTextField) fieldComp;
									logger.info(textField.getName());
									if (textField.isEditable()) {
										editable++;
										if (nome0cognome1 == 0) {
											biglietti.get(b).setNomeUtilizzatore(textField.getText());
											nome0cognome1++;
										} else {
											biglietti.get(b).setCognomeUtilizzatore(textField.getText());
											nome0cognome1--;
										}
									}
								}
							}
							logger.info("[UserCarrelloPanel] IN QUESTO PANELLO CI SONO " + editable + " ELEMeNTI EDITABILI");
							TicketsDatabase.addTicket(biglietti.get(b));
							b++;
						}
					}
				}
			}
		}
		JOptionPane.showMessageDialog(UserCarrelloPanel.this, "Acquisto completato!");
		UserCarrelloPanel.clearCarrello();
		UserHomePanel.switchToMyOrdersButton.doClick();

	}

	private JPanel createBigliettoPanel(Biglietto biglietto) throws ParseException {
		EventoSettoreResult esr = UserCarrelloPanel.findEventoSettore(biglietto);
		Evento ev = esr.getEvento();
		logger.info("[UserCarrelloPanel] "+ ev.getIdEvento());
		Settore s = esr.getSettore();

		if (ev == null || s == null) {
			JOptionPane.showMessageDialog(
					this, "Errore: Evento o Settore non trovati per il biglietto" + " e ID settore " + biglietto.getIdSettore(),
					"Errore", JOptionPane.ERROR_MESSAGE);
			return new JPanel();
		}

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(173, 216, 230));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		String pathplace = "a";
		List<Luogo> luoghi = LuoghiDatabase.getAllLuoghi();
		for (int i = 0; i < luoghi.size(); i++) {
			logger.info("[UserCarrelloPanel] "+ev.getIdLuogo() + " lista:" + luoghi.get(i).getIdLuogo());
			if (ev.getIdLuogo() == luoghi.get(i).getIdLuogo()) {
				pathplace = luoghi.get(i).getNomeFile();
			}
		}
		String path = "src/main/resources/Immagini/" + pathplace;
		logger.info("[UserCarrelloPanel] "+path);
		JLabel imageLabel = new JLabel();
		
		updateImage(path, imageLabel);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		gbc.gridheight = 5;
		panel.add(imageLabel, gbc);

		gbc.gridheight = 1;
		gbc.gridheight = 1;

		JLabel idUtenteLabel = new JLabel("Evento:");
		idUtenteLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(idUtenteLabel, gbc);

		JTextField idUtenteField = new JTextField(
				ev.getNome() + " del " + new SimpleDateFormat("dd/MM/yyyy").format(esr.getEvento().getData()));
		idUtenteField.setFont(new Font("Arial", Font.PLAIN, 14));
		idUtenteField.setEditable(false);
		idUtenteField.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		panel.add(idUtenteField, gbc);

		JLabel idSettoreLabel = new JLabel("Settore: ");
		idSettoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(idSettoreLabel, gbc);

		JTextField idSettoreField = new JTextField(UserDetailsEventPanel.outputSettoriSmall(s));
		idSettoreField.setFont(new Font("Arial", Font.PLAIN, 14));
		idSettoreField.setEditable(false);
		idSettoreField.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		panel.add(idSettoreField, gbc);

		JLabel nomeLabel = new JLabel("Nome Utilizzatore:");
		nomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(nomeLabel, gbc);

		JTextField nomeField = new JTextField(biglietto.getNomeUtilizzatore());
		nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
		nomeField.setEditable(biglietto.getNomeUtilizzatore().isEmpty());
		nomeField.setEditable(true);
		nomeField.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		panel.add(nomeField, gbc);

		JLabel cognomeLabel = new JLabel("Cognome Utilizzatore:");
		cognomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		gbc.gridy = 3;
		panel.add(cognomeLabel, gbc);

		JTextField cognomeField = new JTextField(biglietto.getCognomeUtilizzatore());
		cognomeField.setFont(new Font("Arial", Font.PLAIN, 14));
		cognomeField.setEditable(biglietto.getCognomeUtilizzatore().isEmpty());
		cognomeField.setEditable(true);
		cognomeField.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		panel.add(cognomeField, gbc);

		JLabel postoLabel = new JLabel("Acquirente:");
		postoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		gbc.gridy = 4;
		panel.add(postoLabel, gbc);

		JTextField postoField = new JTextField(String.valueOf(LoginPanel.usernameUtente));
		postoField.setFont(new Font("Arial", Font.PLAIN, 14));
		postoField.setEditable(false);
		postoField.setPreferredSize(new Dimension(300, 30));
		gbc.gridx = 2;
		panel.add(postoField, gbc);

		JButton deleteButton = new JButton("Elimina Biglietto");
		deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				biglietti.remove(biglietto);
				removeAll();
				revalidate();
				repaint();
				UserHomePanel.switchToCartButton.doClick();
			}
		});

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		panel.add(deleteButton, gbc);

		return panel;
	}

	private void updateImage(String path, JLabel label) {
	    label.setPreferredSize(new Dimension(350, 200));
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setVerticalAlignment(JLabel.CENTER);
	    label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    ImageIcon imageIcon = new ImageIcon(path);
	    Image originalImage = imageIcon.getImage();
	    Image scaledImage = originalImage.getScaledInstance(350, 200, Image.SCALE_SMOOTH);
	    label.setIcon(new ImageIcon(scaledImage));
	}


	public static void addBiglietto(Biglietto biglietto, int numselected) throws ParseException {
		try {
			EventoSettoreResult esr = findEventoSettore(biglietto);
			biglietti.add(biglietto);
			logger.info("[UserCarrelloPanel] Da UserDetail " + esr.getEvento().getIdEvento() + " " + biglietto.getIdSettore());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean ticketCountControl(Biglietto biglietto, int numselected) throws ParseException {
		EventoSettoreResult esr = findEventoSettore(biglietto);
		int nmax = esr.getEvento().getMaxBigliettiAPersona();
		int attuali = 0;
		for (int i = 0; i < biglietti.size(); i++) {
			EventoSettoreResult esr2 = findEventoSettore(biglietti.get(i));
			if (esr.getEvento().getIdEvento() == esr2.getEvento().getIdEvento()) {
				attuali++;
			}
		}
		List<Biglietto> bigliettiAcquistati=TicketsDatabase.getAllUserTickets(Login.loginId);
		int acquistati=0;
		for(Biglietto bacq : bigliettiAcquistati)
		{
			EventoSettoreResult esr2 = findEventoSettore(bacq);
			if(esr2.getEvento().getIdEvento()==esr.getEvento().getIdEvento())
			{
				acquistati++;
			}
		}
		if ((numselected + attuali + acquistati) > nmax) {
			int restante = nmax - attuali - acquistati; 
			JOptionPane.showMessageDialog(null,
					"Non puoi acquistare " + numselected + " biglietti perchè il massimo consentito è " + nmax
							+ ". Puoi aggiungere al massimo altri " + restante + " elementi. (In 'Carrello' "
							+ "attualmente ne hai " + attuali + " e in 'I Miei Ordini' ne hai già acquistati "+acquistati+"  dello stesso evento)",
					"Limite raggiunto", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;

	}

	public static void clearCarrello() {
		biglietti.clear();
	}

	public static EventoSettoreResult findEventoSettore(Biglietto b) throws ParseException {
		Evento ev = null;
		Settore s = null;

		eventi = EventsDatabase.getAllEvents();
		if (eventi.isEmpty()) {
			logger.info("[UserCarrelloPanel] Eventi è vuota");
		}
		for (Evento evento : eventi) {
			if (SectorsDatabase.getIdEvento(b.getIdSettore()) == evento.getIdEvento()) {
				logger.info("[UserCarrelloPanel] Confronto eventi nel carrello: " + SectorsDatabase.getIdEvento(b.getIdSettore()) + " " + evento.getIdEvento());
				ev = evento;
				break;
			}
		}
		if (ev == null) {
			logger.error("[UserCarrelloPanel] Evento non trovato per ID: " + SectorsDatabase.getIdEvento(b.getIdSettore()));
		}

		settori = SectorsDatabase.getAllSectors();
		for (Settore settore : settori) {
			if (ev != null && ev.getIdEvento() == settore.getIdEvento() && b.getIdSettore() == settore.getIdSettore()) {
				s = settore;
				break;
			}
		}
		if (s == null) {
			logger.error("[UserCarrelloPanel] Settore non trovato per ID evento: " + (ev != null ? ev.getIdEvento() : "null")
					+ " e ID settore: " + b.getIdSettore());
		}

		return new EventoSettoreResult(ev, s);
	}

}

class EventoSettoreResult {
	Evento evento;
	Settore settore;

	EventoSettoreResult(Evento evento, Settore settore) {
		this.evento = evento;
		this.settore = settore;
	}

	public Evento getEvento() {
		return evento;
	}

	public Settore getSettore() {
		return settore;
	}
}