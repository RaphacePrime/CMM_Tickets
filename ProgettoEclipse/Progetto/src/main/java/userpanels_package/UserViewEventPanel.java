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
import interfaces_package.NavigationListener;
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
    private NavigationListener navigationListener;

    public UserViewEventPanel(NavigationListener navigationListener) throws ParseException {
    	this.navigationListener = navigationListener;
        LookAndFeelUtil.setCrossPlatformLookAndFeel();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel filterTitleLabel = new JLabel("Filtra Eventi", JLabel.CENTER);
        filterTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        filterTitleLabel.setForeground(new Color(60, 63, 65)); 
        add(filterTitleLabel, BorderLayout.NORTH);

        JPanel filterPanel = createFilterPanel();
        add(filterPanel, BorderLayout.NORTH);

        fetchAndDisplayEvents();
    }

    private JPanel createFilterPanel() {
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

        return filterPanel;
    }

    public void fetchAndDisplayEvents() throws ParseException {
        eventi = EventsDatabase.getAllEvents();
        luoghi = LuoghiDatabase.getAllLuoghi();
        String[] columnNames = {"Nome Evento", "Data", "Luogo", "Città", "Indirizzo"};
        Object[][] data = populateEventData();

        tableModel = new DefaultTableModel(data, columnNames);
        eventTable = createEventTable();

        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Eventi"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private Object[][] populateEventData() throws ParseException {
        Object[][] data = new Object[eventi.size()][5];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < eventi.size(); i++) {
            Evento e = eventi.get(i);
            Luogo l = luoghi.stream().filter(lu -> lu.getIdLuogo() == e.getIdLuogo()).findFirst().orElse(null);

            data[i][0] = e.getNome();
            data[i][1] = sdf.format(e.getData());
            if (l != null) {
                data[i][2] = l.getNome();
                data[i][3] = l.getIndirizzo();
                data[i][4] = l.getCittà();
            }
        }

        return data;
    }

    private JTable createEventTable() {
        JTable table = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                colorizeRowByDate(comp, row, column);
                return comp;
            }
        };

        styleEventTable(table);
        return table;
    }

    private void colorizeRowByDate(Component comp, int row, int column) {
        int dateColumnIndex = 1;
        try {
            String dateStr = (String) tableModel.getValueAt(row, dateColumnIndex);
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

        if (eventTable.isCellSelected(row, column)) {
            comp.setBackground(new Color(75, 110, 175));
            comp.setForeground(Color.WHITE);
        } else {
            comp.setForeground(Color.BLACK);
        }
    }

    private void styleEventTable(JTable table) {
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(75, 110, 175));
        table.setSelectionForeground(Color.WHITE);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(75, 110, 175));
        header.setForeground(Color.WHITE);
    }

    public void setSwitchToDetailsEventAction() {
        eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = eventTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    handleEventSelection(row);
                }
            }
        });
    }

    private void handleEventSelection(int row) {
        String nomeEvento = (String) tableModel.getValueAt(row, 0);
        String dataEventoStr = (String) tableModel.getValueAt(row, 1);
        logger.info("[UserViewEventPanel] Evento cliccato: " + nomeEvento);
        boolean controllo=false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataEvento = sdf.parse(dataEventoStr);
           
            for (Evento evento : eventi) {
                if (evento.getNome().equals(nomeEvento) && dataEvento.equals(evento.getData())) {
                    if (!dataEvento.before(new Date())) {
                        UserDetailsEventPanel detailsPanel = new UserDetailsEventPanel(evento);
                        navigationListener.UserNavigateTo(detailsPanel);
                    } else {
                    	controllo=true;
                    	
                    }
                    break;
                }
            }
            if(controllo==true)
            {
            	showEventExpiredMessage(dataEventoStr);
            }
        } catch (ParseException e) {
            logger.error("Errore nel parsing della data: " + e.getMessage());
        }
    }

    private void showEventExpiredMessage(String dataEventoStr) {
        JOptionPane.showMessageDialog(
            null,
            "L'evento si è svolto in data " + dataEventoStr + "! Non è disponibile all'acquisto",
            "Evento bloccato",
            JOptionPane.WARNING_MESSAGE
        );
    }

    private void applyFilters() {
        String nomeEvento = eventSearchField.getText();
        String luogo = (String) luogoComboBox.getSelectedItem();
        boolean acquistabileOra = availableNowCheckBox.isSelected();
        int maxBiglietti = parseMaxTickets(maxTicketsField.getText());

        List<Evento> filteredEvents = EventsDatabase.getAllEvents().stream()
            .filter(event -> filterByNome(event, nomeEvento))
            .filter(event -> filterByLuogo(event, luogo))
            .filter(event -> filterByDisponibilita(event, acquistabileOra))
            .filter(event -> filterByMaxBiglietti(event, maxBiglietti))
            .toList();

        updateEventTable(filteredEvents);
    }

    private int parseMaxTickets(String maxTicketsStr) {
        return maxTicketsStr.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxTicketsStr);
    }

    private boolean filterByNome(Evento event, String nomeEvento) {
        return nomeEvento.isEmpty() || event.getNome().toLowerCase().contains(nomeEvento.toLowerCase());
    }

    private boolean filterByLuogo(Evento event, String luogo) {
        return luogo.equals("Seleziona Luogo") || 
               luoghi.stream().anyMatch(l -> l.getNome().equals(luogo) && l.getIdLuogo() == event.getIdLuogo());
    }

    private boolean filterByDisponibilita(Evento event, boolean acquistabileOra) {
        Date today = new Date();
        return !acquistabileOra || (event.getDataInizioVendita().before(today) && event.getData().after(today));
    }

    private boolean filterByMaxBiglietti(Evento event, int maxBiglietti) {
        return event.getMaxBigliettiAPersona() <= maxBiglietti;
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
