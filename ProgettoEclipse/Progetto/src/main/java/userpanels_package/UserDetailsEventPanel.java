package userpanels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classes_package.Biglietto;
import classes_package.Evento;
import classes_package.Luogo;
import classes_package.Settore;
import database_package.LuoghiDatabase;
import database_package.SectorsDatabase;
import login_package.Login;

public class UserDetailsEventPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField maxTicketsField;
    private JTextField seatNumberedField;
    private JTextField saleStartField;
    private JTextField locationField;
    private JButton updateButton;
    private JComboBox<String> sectorsDropdown;
    private JComboBox<Integer> ticketCountDropdown;
    private List<Settore> settori = new ArrayList<>();
    private List<Settore> settoriDiEvento = new ArrayList<>();
    private List<Luogo> listaluoghi = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(UserDetailsEventPanel.class);
    private Evento evento;

    public UserDetailsEventPanel(Evento e) throws ParseException {
        this.evento = e;
        listaluoghi = LuoghiDatabase.getAllLuoghi();

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

        JLabel titleLabel = new JLabel("Dettagli Evento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        JLabel nameLabel = new JLabel("Nome evento:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);

        JLabel dateLabel = new JLabel("Data:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);

        dateField = new JTextField(20);
        dateField.setFont(new Font("Arial", Font.PLAIN, 14));
        dateField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(dateField, gbc);

        JLabel timeLabel = new JLabel("Ora:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(timeLabel, gbc);

        timeField = new JTextField(20);
        timeField.setFont(new Font("Arial", Font.PLAIN, 14));
        timeField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(timeField, gbc);

        JLabel maxTicketsLabel = new JLabel("Max biglietti a persona:");
        maxTicketsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(maxTicketsLabel, gbc);

        maxTicketsField = new JTextField(10);
        maxTicketsField.setFont(new Font("Arial", Font.PLAIN, 14));
        maxTicketsField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(maxTicketsField, gbc);

        JLabel seatNumberedLabel = new JLabel("Posto numerato:");
        seatNumberedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(seatNumberedLabel, gbc);

        seatNumberedField = new JTextField(10);
        seatNumberedField.setFont(new Font("Arial", Font.PLAIN, 14));
        seatNumberedField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(seatNumberedField, gbc);

        JLabel saleStartLabel = new JLabel("Data inizio vendita:");
        saleStartLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(saleStartLabel, gbc);

        saleStartField = new JTextField(20);
        saleStartField.setFont(new Font("Arial", Font.PLAIN, 14));
        saleStartField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(saleStartField, gbc);

        JLabel locationLabel = new JLabel("Luogo:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(locationLabel, gbc);

        locationField = new JTextField(20);
        locationField.setFont(new Font("Arial", Font.PLAIN, 14));
        locationField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(locationField, gbc);

        JLabel sectorsLabel = new JLabel("Settori aggiunti:");
        sectorsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(sectorsLabel, gbc);

        sectorsDropdown = new JComboBox<>();
        sectorsDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(sectorsDropdown, gbc);

        JLabel ticketCountLabel = new JLabel("Numero di biglietti:");
        ticketCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(ticketCountLabel, gbc);

        ticketCountDropdown = new JComboBox<>();
        ticketCountDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 9;
        formPanel.add(ticketCountDropdown, gbc);

        updateButton = new JButton("Aggiungi al carrello");
        updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        updateButton.setBackground(new Color(0, 128, 0));
        updateButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(updateButton, gbc);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					addToCart();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
            }

			
        });

        add(formPanel, BorderLayout.CENTER);
        updateFields(e);
    }

	public void addToCart() throws ParseException {
		int num=Integer.parseInt(ticketCountDropdown.getSelectedItem().toString());
		Settore s=findSettore();
		if(s==null) {logger.info("[UserDetailsEventPanel] Settore null");};
		if(s.getPostiTotali()-s.getPostiAcquistati()<num)
		{
			JOptionPane.showMessageDialog(
	  		        null, 
	  		        "Non puoi acquistare " + num + " biglietti perchè nel settore ne sono rimasti " + (s.getPostiTotali()-s.getPostiAcquistati()) +" disponibili.", 
	  		        "Limite raggiunto", 
	  		        JOptionPane.WARNING_MESSAGE
	  		    );
		}
		else
		{
			boolean control=UserCarrelloPanel.ticketCountControl(new Biglietto("","",0,Login.loginId,s.getIdSettore(),this.evento.getIdEvento()),num);
			if(control==true)
			{
				for(int i=0; i<num; i++)
				{
					
					UserCarrelloPanel.addBiglietto(new Biglietto("","",0,Login.loginId,s.getIdSettore(),this.evento.getIdEvento()),num);
					logger.info("[UserDetailsEventPanel] IdUTENTE: "+Login.loginId+" IdSETTORE: "+s.getIdSettore()+ "evento.idevento: "+this.evento.getIdEvento() + "s.idevento: "+s.getIdEvento());
				}
				JOptionPane.showMessageDialog(null,"Biglietti aggiunti al carrello con successo", "Successo",JOptionPane.INFORMATION_MESSAGE);
				UserHomePanel.switchToCartButton.doClick();
			  		
			}
		}
	}
	private Settore findSettore() {
        int selectedIndex = sectorsDropdown.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < settoriDiEvento.size()) {
            return settoriDiEvento.get(selectedIndex);
        }
        return null;
    }

	public void updateFields(Evento e) throws ParseException {
        nameField.setText(e.getNome());
        dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(e.getData()));
        timeField.setText(e.getOra());
        maxTicketsField.setText(String.valueOf(e.getMaxBigliettiAPersona()));
        seatNumberedField.setText(e.getPostoNumerato() ? "Sì" : "No");
        saleStartField.setText(new SimpleDateFormat("dd/MM/yyyy").format(e.getDataInizioVendita()));

        for (Luogo luogo : listaluoghi) {
            if (luogo.getIdLuogo() == e.getIdLuogo()) {
                locationField.setText(luogo.getNome());
                break;
            }
        }

        settori = SectorsDatabase.getAllSectors();
        sectorsDropdown.removeAllItems();
        for (Settore s : settori) {
            if (s.getIdEvento() == e.getIdEvento()) {
            	settoriDiEvento.add(s);
            	String output=outputSettori(s);
                sectorsDropdown.addItem(output);
            }
        }

        ticketCountDropdown.removeAllItems();
        int maxTickets = e.getMaxBigliettiAPersona();
        for (int i = 1; i <= maxTickets; i++) {
            ticketCountDropdown.addItem(i);
        }
    }
    
    public static String outputSettori(Settore s)
    {
    	return s.getNome()+" "+s.getPosizione()+ " anello "+s.getAnello()+" - "+s.getPrezzo()+"€ - "+(s.getPostiTotali()-s.getPostiAcquistati())+" rimanenti";
    }
    
    public static String outputSettoriSmall(Settore s)
    {
    	return s.getNome()+" "+s.getPosizione()+ " anello "+s.getAnello()+" - "+s.getPrezzo()+"€";
    }
}
