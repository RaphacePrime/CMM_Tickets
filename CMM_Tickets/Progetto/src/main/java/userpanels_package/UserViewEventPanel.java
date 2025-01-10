package userpanels_package;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import classes_package.Evento;
import classes_package.Luogo;
import database_package.AdminEventsDatabase;
import database_package.AdminLuoghiDatabase;
import database_package.Database;
import utils_package.LookAndFeelUtil;

public class UserViewEventPanel extends JPanel {
    private List<Evento> eventi;
    private List<Luogo> luoghi;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JButton backButton, applyButton;
    private JCheckBox availableNowCheckBox;
    private JTextField eventSearchField;
    private JComboBox<String> luogoComboBox;
    private JTextField maxTicketsField;
    private static Logger logger = LogManager.getLogger(UserViewEventPanel.class);

    public UserViewEventPanel() throws ParseException {
        LookAndFeelUtil.setCrossPlatformLookAndFeel();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Visualizza Eventi", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 63, 65)); 
        add(titleLabel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2, 4, 10, 10));
        
        availableNowCheckBox = new JCheckBox("Acquistabile Ora");
        filterPanel.add(availableNowCheckBox);

        eventSearchField = new JTextField();
        filterPanel.add(new JLabel("Ricerca Evento:"));
        filterPanel.add(eventSearchField);

        luogoComboBox = new JComboBox<>();
        luogoComboBox.addItem("Seleziona Luogo");
        for (Luogo luogo : AdminLuoghiDatabase.getAllLuoghi()) {
            luogoComboBox.addItem(luogo.getNome());
        }
        filterPanel.add(luogoComboBox);

        maxTicketsField = new JTextField();
        filterPanel.add(new JLabel("Max Biglietti Acquistabili:"));
        filterPanel.add(maxTicketsField);

        applyButton = new JButton("Applica Filtri");
        filterPanel.add(applyButton);
        add(filterPanel, BorderLayout.NORTH);

        fetchAndDisplayEvents();

        applyButton.addActionListener(e -> applyFilters());
    }

    public void setBackToAdminHomeAction(ActionListener action) {
        backButton.addActionListener(action);
    }

    public void fetchAndDisplayEvents() throws ParseException {
        eventi = AdminEventsDatabase.getAllEvents();
        luoghi = AdminLuoghiDatabase.getAllLuoghi();
        String[] columnNames = {"Nome Evento", "Data", "Luogo", "Città", "Indirizzo"};
        Object[][] data = new Object[eventi.size()][5]; 

        for (int i = 0; i < eventi.size(); i++) {
            Evento e = eventi.get(i);
            Luogo l;
            
            for(int y=0; y<luoghi.size(); y++) {
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
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  
            }
            
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (isCellSelected(row, column)) {
                    comp.setBackground(new Color(75, 110, 175)); 
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setBackground(Color.WHITE); 
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

        eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = eventTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    String nomeEvento = (String) tableModel.getValueAt(row, 0);
                    logger.info("Evento cliccato: " + nomeEvento);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Eventi"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void applyFilters() {
        String nomeEvento = eventSearchField.getText();
        String luogo = (String) luogoComboBox.getSelectedItem();
        boolean acquistabileOra = availableNowCheckBox.isSelected();
        String maxBigliettiStr = maxTicketsField.getText();
        int maxBiglietti = (maxBigliettiStr.isEmpty()) ? Integer.MAX_VALUE : Integer.parseInt(maxBigliettiStr);

        // Filtro eventi
        List<Evento> filteredEvents = AdminEventsDatabase.getAllEvents().stream()
            .filter(event -> 
                (nomeEvento.isEmpty() || event.getNome().toLowerCase().contains(nomeEvento.toLowerCase())) &&
                (luogo.equals("Seleziona Luogo") || event.getIdLuogo() == luoghi.stream().filter(l -> l.getNome().equals(luogo)).findFirst().get().getIdLuogo()) &&
                (acquistabileOra ? event.getDataInizioVendita().before(new Date()) : true) &&
                (event.getMaxBigliettiAPersona() <= maxBiglietti)
            )
            .toList();
        
        // Refresh the event table
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
            data[i][3] = l.getCittà();
            data[i][4] = l.getIndirizzo();
        }

        tableModel.setDataVector(data, columnNames);
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder());  
            setFocusPainted(false);  
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText(value.toString());
            if (isSelected) {
                setBackground(new Color(75, 110, 175));  
                setForeground(Color.WHITE);
            } else {
                setBackground(Color.WHITE);  
                setForeground(Color.BLACK);
            }
            return this;
        }
    }
}
