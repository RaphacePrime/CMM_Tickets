package adminpanels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import classes_package.Evento;
import classes_package.Luogo;
import classes_package.Settore;
import database_package.AdminEventsDatabase;
import database_package.AdminLuoghiDatabase;
import database_package.AdminSectorsDatabase;
import frames_package.MainFrame;

public class AdminDetailsEventPanel extends JPanel {
    private JTextField nameField;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JTextField maxTicketsField;
    private JCheckBox seatNumberedCheckbox;
    private JSpinner saleStartSpinner;
    private JComboBox<String> locationDropdown;
    private JButton manageSectorsButton;
    private JButton deleteButton;
    private JComboBox<String> sectorsDropdown;
    public List<Settore> settori = new ArrayList<>();
    private List<Luogo> listaluoghi = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(AdminDetailsEventPanel.class);
    private Evento evento;
    private List<Settore> settoriUpdate = new ArrayList<>();
    private List<String> nomiSettori = new ArrayList<>();
    private JButton updateButton;

    public AdminDetailsEventPanel(Evento e) throws ParseException {
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    	this.evento=e;
        List<String> nomiluoghi = new ArrayList<>();
        listaluoghi = AdminLuoghiDatabase.getAllLuoghi();
        for (int i = 0; i < listaluoghi.size(); i++) {
            nomiluoghi.add(listaluoghi.get(i).getNome());
        }

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

        JLabel titleLabel = new JLabel("Modifica Evento");
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
        //nameField.setText(e.getNome());
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);

        JLabel dateLabel = new JLabel("Data:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(dateSpinner, gbc);

        JLabel timeLabel = new JLabel("Ora:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(timeLabel, gbc);

        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(timeSpinner, gbc);

        JLabel maxTicketsLabel = new JLabel("Max biglietti a persona:");
        maxTicketsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(maxTicketsLabel, gbc);

        maxTicketsField = new JTextField(10);
        maxTicketsField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(maxTicketsField, gbc);

        JLabel seatNumberedLabel = new JLabel("Posto numerato:");
        seatNumberedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(seatNumberedLabel, gbc);

        seatNumberedCheckbox = new JCheckBox();
        seatNumberedCheckbox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(seatNumberedCheckbox, gbc);

        JLabel saleStartLabel = new JLabel("Data inizio vendita:");
        saleStartLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(saleStartLabel, gbc);

        saleStartSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor saleStartEditor = new JSpinner.DateEditor(saleStartSpinner, "dd/MM/yyyy");
        saleStartSpinner.setEditor(saleStartEditor);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(saleStartSpinner, gbc);

        JLabel locationLabel = new JLabel("Luogo:");
        //updateLocationDropdown();
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(locationLabel, gbc);

        locationDropdown = new JComboBox<>(nomiluoghi.toArray(new String[0]));
        locationDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(locationDropdown, gbc);

        /*manageSectorsButton = new JButton("Gestisci settori");
        manageSectorsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageSectorsButton.setBackground(new Color(255, 223, 0));
        manageSectorsButton.setForeground(Color.BLACK);
        manageSectorsButton.setOpaque(true);
        manageSectorsButton.setBorderPainted(false);
        manageSectorsButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(manageSectorsButton, gbc);*/

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

        updateButton=new JButton("Aggiorna Evento");
        updateButton.setFont(new Font("Arial",Font.PLAIN, 16)); 
        updateButton.setBackground(new Color(255, 140, 0)); 
        updateButton.setForeground(Color.WHITE); 
        updateButton.setOpaque(true); 
        updateButton.setBorderPainted(false); 
        updateButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(updateButton, gbc) ; 
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEvento();
            }

			
        });
        
        deleteButton = new JButton("Elimina Evento (e i settori associati)");
        deleteButton.setFont(new Font("Arial",Font.PLAIN,16));
        deleteButton.setBackground(new Color(200, 50, 50)); 
        deleteButton.setForeground(Color.WHITE); 
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false); 
        deleteButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(deleteButton, gbc);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEvento();
            }

			

			
        });
        updateFields(e);
        
        add(formPanel, BorderLayout.CENTER);
        
        //updateLocationDropdown();
    }
    
    private void updateEvento() {
    	
        String nome = nameField.getText();
        if (nome.isEmpty() || maxTicketsField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date data = (Date) dateSpinner.getValue();
        String ora = new SimpleDateFormat("HH:mm").format((Date) timeSpinner.getValue());
        int maxBigliettiAPersona = Integer.parseInt(maxTicketsField.getText());
        boolean postoNumerato = seatNumberedCheckbox.isSelected();
        Date dataInizioVendita = (Date) saleStartSpinner.getValue();
        int idLuogo = listaluoghi.get(locationDropdown.getSelectedIndex()).getIdLuogo();
        
        if (data.before(new Date())) {
            JOptionPane.showMessageDialog(this, "La data dell'evento deve essere futura.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(dataInizioVendita);
        calendario.add(Calendar.DAY_OF_MONTH, 1);
        Date giornoSuccessivoInizioVendita = calendario.getTime();
        if (data.before(giornoSuccessivoInizioVendita)) {
            JOptionPane.showMessageDialog(this, "La data di inizio vendita deve essere almeno un giorno prima rispetto alla data dell'evento.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Update the Evento object
        
        
        Evento e2= new Evento(nome,data,ora,maxBigliettiAPersona,postoNumerato,dataInizioVendita,idLuogo);
        

        // Call the method to update the event in the database
        if (AdminEventsDatabase.updateEvento(e2,evento.getIdEvento())) {
            logger.info("Evento aggiornato con successo: " + e2.getNome());
            JOptionPane.showMessageDialog(this, "Evento aggiornato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logger.error("Errore durante l'aggiornamento dell'evento: " + e2.getNome());
            JOptionPane.showMessageDialog(this, "Errore durante l'aggiornamento dell'evento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        AdminHomePanel.switchToModifyEventButton.doClick();
    }
    
    private void deleteEvento() {
    	if (AdminEventsDatabase.deleteEvento(evento.getIdEvento())) {
            logger.info("Evento eliminato con successo: " + evento.getIdEvento());
            JOptionPane.showMessageDialog(this, "Evento eliminato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logger.error("Errore durante l'eliminazione dell'evento: " + evento.getIdEvento());
            JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione dell'evento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    	AdminHomePanel.switchToModifyEventButton.doClick();
		
		
	}


    
    public void updateFields(Evento e) throws ParseException {
        nameField.setText(e.getNome());
        //dateSpinner.setValue(new Date());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        JSpinner.DateEditor saleStartEditor = new JSpinner.DateEditor(saleStartSpinner, "dd/MM/yyyy");
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        
        SimpleDateFormat timeFormat= new SimpleDateFormat("HH:mm");
        Date ora = timeFormat.parse(e.getOra());
        
        dateSpinner.setValue(e.getData());        
        saleStartSpinner.setValue(e.getDataInizioVendita());
        timeSpinner.setValue(ora);
        maxTicketsField.setText(String.valueOf(e.getMaxBigliettiAPersona()));
        seatNumberedCheckbox.setSelected(e.getPostoNumerato());
        
        int indice=0;
       
        for(int i=0; i<listaluoghi.size(); i++)
        {
        	Luogo l = listaluoghi.get(i);
        	if(l.getIdLuogo()==e.getIdLuogo())
        	{
        		indice=i;
        	}
        }
        locationDropdown.setSelectedIndex(indice);
        
        
        settori = AdminSectorsDatabase.getAllSectors();
        sectorsDropdown.removeAllItems();
        int index=0;
        for (int k = 0; k < settori.size(); k++) 
        {
        	
        	Settore s= settori.get(k);
        	if(s.getIdEvento()==e.getIdEvento())
        	{
        		//index=k;
        		settoriUpdate.add(s);
        		sectorsDropdown.addItem(s.getNome()+ " " + s.getPosizione()+", anello "+String.valueOf(s.getAnello()));
        	}
            
            //sectorsDropdown.addItem(nomiSettori.get(index));
        	
        }
        
       
        
    }
    
    public int findIdEvento(Evento ev) throws ParseException
    {
    	int id=0;
    	List<Evento> evs= AdminEventsDatabase.getAllEvents();
    	for(int i=0; i<evs.size();i++)
    	{
    		if(ev.getNome().equals(evs.get(i).getNome()) && ev.getData().equals(evs.get(i).getData())
    			&& ev.getOra().equals(evs.get(i).getOra())	)
    		{
    			id=evs.get(i).getIdEvento();
    		}
    	}
    	return id;
    }
    
    

    
}

