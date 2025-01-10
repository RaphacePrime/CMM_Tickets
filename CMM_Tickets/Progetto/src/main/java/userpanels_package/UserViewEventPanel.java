package userpanels_package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
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

    public UserViewEventPanel() throws ParseException {
        LookAndFeelUtil.setCrossPlatformLookAndFeel();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Titolo sopra i filtri
        JLabel filterTitleLabel = new JLabel("Filtra Eventi", JLabel.CENTER);
        filterTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        filterTitleLabel.setForeground(new Color(60, 63, 65)); 
        add(filterTitleLabel, BorderLayout.NORTH);

        // Pannello dei filtri
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.setBackground(new Color(240, 240, 240));

        // Filtro "Acquistabile Ora"
        availableNowCheckBox = new JCheckBox("Acquistabile Ora");
        availableNowCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.add(availableNowCheckBox);

        // Filtro "Nome Evento"
        eventSearchField = new JTextField(15);
        eventSearchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        eventSearchField.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.add(createLabeledField("Nome Evento", eventSearchField));

        // Filtro "Luogo"
        luogoComboBox = new JComboBox<>();
        luogoComboBox.addItem("Seleziona Luogo");
        for (Luogo luogo : AdminLuoghiDatabase.getAllLuoghi()) {
            luogoComboBox.addItem(luogo.getNome());
        }
        luogoComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.add(createLabeledField("Luogo", luogoComboBox));

        // Filtro "Max Biglietti Acquistabili"
        maxTicketsField = new JTextField(15);
        maxTicketsField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        maxTicketsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.add(createLabeledField("Max Biglietti Acquistabili", maxTicketsField));

        // Bottone "Applica Filtri"
        applyButton = new JButton("Applica Filtri");
        applyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        applyButton.setBackground(new Color(33, 150, 243)); 
        applyButton.setForeground(Color.WHITE);
        applyButton.setFocusPainted(false);
        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        applyButton.setOpaque(true);
        applyButton.setBorderPainted(false);
        applyButton.addActionListener(e -> applyFilters());
        filterPanel.add(applyButton);

        // Aggiunta del pannello dei filtri al pannello principale
        add(filterPanel, BorderLayout.WEST);

        // Carica e mostra gli eventi iniziali
        fetchAndDisplayEvents();
    }

    public void fetchAndDisplayEvents() throws ParseException {
        eventi = AdminEventsDatabase.getAllEvents();
        luoghi = AdminLuoghiDatabase.getAllLuoghi();
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

    private JPanel createLabeledField(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER); // Rimosso lo spazio verticale tra label e component
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); 
        return panel;
    }
}
