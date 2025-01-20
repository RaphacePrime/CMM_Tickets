package userpanels_package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import classes_package.Evento;
import classes_package.Luogo;
import database_package.EventsDatabase;
import database_package.LuoghiDatabase;
import frames_package.MainFrame;
import utils_package.LookAndFeelUtil;

public class UserViewEventPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private List<Evento> eventi;
    private List<Luogo> luoghi;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JButton applyButton;
    private JCheckBox availableNowCheckBox;
    private JTextField eventSearchField;
    private JComboBox<String> luogoComboBox;
    private JTextField maxTicketsField;
    private static Logger logger = LogManager.getLogger(UserViewEventPanel.class);

    public UserViewEventPanel() throws ParseException {
        LookAndFeelUtil.setCrossPlatformLookAndFeel();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel filterTitleLabel = new JLabel("Filtra Eventi", JLabel.CENTER);
        filterTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        filterTitleLabel.setForeground(new Color(60, 63, 65)); 
        add(filterTitleLabel, BorderLayout.NORTH);
        
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridBagLayout()); 
        filterPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        availableNowCheckBox = new JCheckBox("Acquistabile Ora");
        availableNowCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(availableNowCheckBox, gbc);

        JLabel eventSearchLabel = new JLabel("Nome Evento");
        eventSearchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        filterPanel.add(eventSearchLabel, gbc);

        eventSearchField = new JTextField(15);
        gbc.gridx = 2;
        filterPanel.add(eventSearchField, gbc);

        JLabel luogoLabel = new JLabel("Luogo");
        luogoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 3;
        filterPanel.add(luogoLabel, gbc);

        luogoComboBox = new JComboBox<>();
        luogoComboBox.addItem("Seleziona Luogo");
        for (Luogo luogo : LuoghiDatabase.getAllLuoghi()) {
            luogoComboBox.addItem(luogo.getNome());
        }
        gbc.gridx = 4;
        filterPanel.add(luogoComboBox, gbc);

        JLabel maxTicketsLabel = new JLabel("Max Biglietti");
        maxTicketsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 5;
        filterPanel.add(maxTicketsLabel, gbc);

        maxTicketsField = new JTextField(10);
        gbc.gridx = 6;
        filterPanel.add(maxTicketsField, gbc);

        applyButton = new JButton("Applica Filtri");
        applyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        applyButton.setBackground(new Color(33, 150, 243));
        applyButton.setForeground(Color.WHITE);
        applyButton.setFocusPainted(false);
        applyButton.setOpaque(true);
        applyButton.setBorderPainted(false);
        applyButton.addActionListener(e -> applyFilters());
        gbc.gridx = 7;
        gbc.gridy = 0;
        filterPanel.add(applyButton, gbc);

        add(filterPanel, BorderLayout.NORTH);

        fetchAndDisplayEvents();
    }

    public void fetchAndDisplayEvents() throws ParseException {
        eventi = EventsDatabase.getAllEvents();
        luoghi = LuoghiDatabase.getAllLuoghi();
        String[] columnNames = {"Nome Evento", "Data", "Luogo", "Città", "Indirizzo"};
        Object[][] data = new Object[eventi.size()][5]; 

        for (int i = 0; i < eventi.size(); i++) {
            Evento e = eventi.get(i);
            Luogo l;
            
            for (int y = 0; y < luoghi.size(); y++) {
                data[i][0] = e.getNome();     
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                data[i][1] = sdf.format(e.getData());

                l = luoghi.get(y);
                
                if (e.getIdLuogo() == l.getIdLuogo()) {
                    data[i][2] = l.getNome();
                    data[i][3] = l.getIndirizzo(); 
                    data[i][4] = l.getCittà();
                }
            }
        }

        tableModel = new DefaultTableModel(data, columnNames);
        eventTable = new JTable(tableModel) {

			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;  
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                int dateColumnIndex = 1; 
                try {
                    String dateStr = (String) getValueAt(row, dateColumnIndex);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date eventDate = sdf.parse(dateStr);
                    Date today = new Date();

                    if (eventDate.before(today)) {
                        comp.setBackground(new Color(255, 204, 204)); 
                        
                    } else {
                        comp.setBackground(new Color(204, 255, 204)); 
                    }
                } catch (ParseException e) {
                    logger.error("[UserViewEventPanel] Errore nel parsing della data: " + e.getMessage());
                }

                if (isCellSelected(row, column)) {
                    comp.setBackground(new Color(75, 110, 175));
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };

        eventTable.setRowHeight(40);  
        eventTable.setFont(new Font("Arial", Font.PLAIN, 14));  
        eventTable.setSelectionBackground(new Color(75, 110, 175));  
        eventTable.setSelectionForeground(Color.WHITE);

        eventTable.setCellSelectionEnabled(false);
        eventTable.setRowSelectionAllowed(true);
        eventTable.setColumnSelectionAllowed(false);

        JTableHeader header = eventTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(75, 110, 175));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Eventi"));
        add(scrollPane, BorderLayout.CENTER);
        
        eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = eventTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    
                    String nomeEvento = (String) tableModel.getValueAt(row, 0);
                   logger.info("[UserViewEventPanel] Evento cliccato: " + nomeEvento);
                    
                }
            }
        });
    }
    
    public void setSwitchToDetailsEventAction() {
        eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = eventTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    String nomeEvento = (String) tableModel.getValueAt(row, 0);
                    String dataEventoStr = (String) tableModel.getValueAt(row, 1);
                    logger.info("[UserViewEventPanel] Evento cliccato: " + nomeEvento);

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dataEvento = sdf.parse(dataEventoStr);

                        for (Evento evento : eventi) {
                            if (evento.getNome().equals(nomeEvento) && dataEvento.equals(evento.getData())) {
                                Date today = new Date();

                                if (!dataEvento.before(today)) {
                                    UserDetailsEventPanel detailsPanel = new UserDetailsEventPanel(evento);
                                    MainFrame.setUserHomeContentPanel(detailsPanel);
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(
                                        null,
                                        "L'evento si è svolto in data " + dataEventoStr + "! Non è disponibile all'acquisto",
                                        "Evento bloccato",
                                        JOptionPane.WARNING_MESSAGE
                                    );
                                }
                            }
                        }
                    } catch (ParseException e) {
                        logger.error("Errore nel parsing della data: " + e.getMessage());
                    }
                }
            }
        });
    }


    private void applyFilters() {
        String nomeEvento = eventSearchField.getText();
        String luogo = (String) luogoComboBox.getSelectedItem();
        boolean acquistabileOra = availableNowCheckBox.isSelected();
        String maxBigliettiStr = maxTicketsField.getText();
        int maxBiglietti = (maxBigliettiStr.isEmpty()) ? Integer.MAX_VALUE : Integer.parseInt(maxBigliettiStr);
        List<Evento> filteredEvents;

        if(acquistabileOra)
        {
        	filteredEvents = EventsDatabase.getAllEvents().stream()
                    .filter(event -> 
                        (nomeEvento.isEmpty() || event.getNome().toLowerCase().contains(nomeEvento.toLowerCase())) &&
                        (luogo.equals("Seleziona Luogo") || event.getIdLuogo() == luoghi.stream().filter(l -> l.getNome().equals(luogo)).findFirst().get().getIdLuogo()) &&
                        (acquistabileOra ? event.getDataInizioVendita().before(new Date()) : true) && 
                        (event.getData().after(new Date())) &&
                        (event.getMaxBigliettiAPersona() <= maxBiglietti)
                    )
                    .toList();
        }
        else
        {
        	filteredEvents = EventsDatabase.getAllEvents().stream()
                    .filter(event -> 
                        (nomeEvento.isEmpty() || event.getNome().toLowerCase().contains(nomeEvento.toLowerCase())) &&
                        (luogo.equals("Seleziona Luogo") || event.getIdLuogo() == luoghi.stream().filter(l -> l.getNome().equals(luogo)).findFirst().get().getIdLuogo()) &&
                        (acquistabileOra ? event.getDataInizioVendita().before(new Date()) : true) && 
                        (event.getMaxBigliettiAPersona() <= maxBiglietti)
                    )
                    .toList();
        }
        
        
        updateEventTable(filteredEvents);
    }

    private void updateEventTable(List<Evento> events) {
        String[] columnNames = {"Nome Evento", "Data", "Luogo", "Città", "Indirizzo"};
        Object[][] data = new Object[events.size()][5]; 

        for (int i = 0; i < events.size(); i++) {
            Evento e = events.get(i);
            Luogo l = luoghi.stream().filter(lu -> lu.getIdLuogo() == e.getIdLuogo()).findFirst().get();
            
            data[i][0] = e.getNome();     
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data[i][1] = sdf.format(e.getData());
            data[i][2] = l.getNome();
            data[i][3] = l.getIndirizzo();
            data[i][4] = l.getCittà();
        }

        tableModel.setDataVector(data, columnNames);
    }
}