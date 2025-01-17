package userpanels_package;

import javax.swing.*;

import ch.qos.logback.classic.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import classes_package.Biglietto;
import classes_package.Evento;
import classes_package.Luogo;
import classes_package.Settore;
import database_package.AdminEventsDatabase;
import database_package.AdminLuoghiDatabase;
import database_package.AdminSectorsDatabase;
import login_package.LoginPanel;

public class UserCarrelloPanel extends JPanel {
    private static  List<Biglietto> biglietti = new ArrayList<>();
    private  static List<Settore> settori = new ArrayList<>();
    private  static List<Evento> eventi = new ArrayList<>();
    private JButton acquistaButton;

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
            for (int i=0; i<biglietti.size();i++) {
            	Biglietto biglietto= biglietti.get(i);
                JPanel bigliettoPanel = createBigliettoPanel(biglietto);
                System.out.println(biglietto.getIdEvento()+ " "+biglietto.getIdSettore());
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
                JOptionPane.showMessageDialog(UserCarrelloPanel.this, "Acquisto completato!");
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(230, 230, 250));
        buttonPanel.add(acquistaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createBigliettoPanel(Biglietto biglietto) throws ParseException {
        EventoSettoreResult esr = this.findEventoSettore(biglietto);
        Evento ev = esr.getEvento();
        System.out.print(ev.getIdEvento());
        Settore s = esr.getSettore();

        if (ev == null || s == null) {
            JOptionPane.showMessageDialog(this, 
                "Errore: Evento o Settore non trovati per il biglietto con ID evento " 
                + biglietto.getIdEvento() + " e ID settore " + biglietto.getIdSettore(), 
                "Errore", JOptionPane.ERROR_MESSAGE);
            return new JPanel();
        }

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Left side - Image

        String pathplace="a";
        List<Luogo> luoghi=AdminLuoghiDatabase.getAllLuoghi();
        for(int i=0; i<luoghi.size();i++)
        {
        	System.out.println(ev.getIdLuogo() + " lista:"+luoghi.get(i).getIdLuogo());
        	if(ev.getIdLuogo()==luoghi.get(i).getIdLuogo())
        	{
        		pathplace=luoghi.get(i).getNomeFile();
        	}
        }
        String path = "src/main/resources/Immagini/"+pathplace; 
        System.out.println(path);
        JLabel imageLabel = new JLabel();
        updateImage(path, imageLabel);
        
        // Set gridx and gridy for image
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;  // Image spans 5 rows vertically
        panel.add(imageLabel, gbc);

        // Form elements - Labels and Text Fields
        gbc.gridheight = 1; // Reset gridheight for the form elements

        JLabel idUtenteLabel = new JLabel("Evento:");
        idUtenteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(idUtenteLabel, gbc);

        JTextField idUtenteField = new JTextField(ev.getNome());
        idUtenteField.setFont(new Font("Arial", Font.PLAIN, 14));
        idUtenteField.setEditable(false);
        gbc.gridx = 2;
        panel.add(idUtenteField, gbc);

        JLabel idSettoreLabel = new JLabel("Settore: ");
        idSettoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(idSettoreLabel, gbc);

        JTextField idSettoreField = new JTextField(UserDetailsEventPanel.outputSettori(s));
        idSettoreField.setFont(new Font("Arial", Font.PLAIN, 14));
        idSettoreField.setEditable(false);
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
        gbc.gridx = 2;
        panel.add(cognomeField, gbc);

        JLabel postoLabel = new JLabel("Acquistatore:");
        postoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(postoLabel, gbc);

        JTextField postoField = new JTextField(String.valueOf(LoginPanel.usernameUtente));
        postoField.setFont(new Font("Arial", Font.PLAIN, 14));
        postoField.setEditable(false);
        gbc.gridx = 2;
        panel.add(postoField, gbc);

        // Delete button
        JButton deleteButton = new JButton("Elimina Biglietto");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                biglietti.remove(biglietto);
                // Refresh the panel after deletion
                removeAll();
                revalidate();
                repaint();
                
            }
        });

        // Set position for delete button
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        panel.add(deleteButton, gbc);

        return panel;
    }

    private void updateImage(String path, JLabel label) {
      ImageIcon imageIcon = new ImageIcon(path);
      Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize image
      label.setIcon(new ImageIcon(image));
  }
  	public static void addBiglietto(Biglietto biglietto, int numselected) throws ParseException {
		try {
				biglietti.add(biglietto);
				System.out.println ("Da UserDetail "+biglietto.getIdEvento()+" "+biglietto.getIdSettore());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
  
  	public static boolean ticketCountControl(Biglietto biglietto, int numselected) throws ParseException
  	{  		
  		EventoSettoreResult esr=findEventoSettore(biglietto);
  		int nmax=esr.getEvento().getMaxBigliettiAPersona();
  		int attuali=0;
  		for(int i=0; i<biglietti.size();i++)
  		{
  			EventoSettoreResult esr2=findEventoSettore(biglietti.get(i));
  			if(esr.getEvento().getIdEvento()==esr2.getEvento().getIdEvento())
  			{
  				attuali++;
  			}
  		}
  		if((numselected+attuali)>nmax)
  		{
  			int restante = nmax - attuali; // Calcolo quanti elementi possono essere ancora aggiunti.
  		    JOptionPane.showMessageDialog(
  		        null, 
  		        "Non puoi acquistare " + numselected + ".  Biglietti dell'evento nel carrello: " + attuali + 
  		        " dell'evento nel carrello. Il massimo consentito è " + nmax + ". Puoi aggiungere al massimo altri " + restante + " elementi.", 
  		        "Limite raggiunto", 
  		        JOptionPane.WARNING_MESSAGE
  		    );
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

        eventi = AdminEventsDatabase.getAllEvents();
        if(eventi.isEmpty())
        {
        	System.out.println("Eventi è vuota");
        }
        for (Evento evento : eventi) {
            if (b.getIdEvento() == evento.getIdEvento()) {
            	System.out.println("Confronto eventi nel carrello: "+b.getIdEvento()+" "+evento.getIdEvento());
                ev = evento;
                break;
            }
        }
        if (ev == null) {
            System.err.println("Evento non trovato per ID: " + b.getIdEvento());
        }

        settori = AdminSectorsDatabase.getAllSectors();
        for (Settore settore : settori) {
            if (ev != null && ev.getIdEvento() == settore.getIdEvento() && b.getIdSettore() == settore.getIdSettore()) {
                s = settore;
                break;
            }
        }
        if (s == null) {
            System.err.println("Settore non trovato per ID evento: " + (ev != null ? ev.getIdEvento() : "null") +
                    " e ID settore: " + b.getIdSettore());
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
    public Evento getEvento() {return evento;}
    public Settore getSettore() {return settore;}
}
