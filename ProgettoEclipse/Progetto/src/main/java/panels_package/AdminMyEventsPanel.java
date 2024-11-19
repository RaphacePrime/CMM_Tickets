package panels_package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import classes_package.Evento;
import database_package.AdminGetEventsDatabase;

public class AdminMyEventsPanel extends JPanel {
    private JButton backToAdminHomeButton;
    private JPanel contentPanel;
    private List<Evento> eventi;

    public AdminMyEventsPanel() {
        System.out.println("Inizializzazione del pannello AdminMyEventsPanel...");
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Creazione del pannello superiore per contenere il pulsante "Torna alla Home"
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));

        // Pulsante "Torna alla Home" in alto a sinistra
        backToAdminHomeButton = new JButton("<html><u>Torna alla Home</u></html>");
        backToAdminHomeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backToAdminHomeButton.setBackground(null);
        backToAdminHomeButton.setForeground(Color.BLACK);
        backToAdminHomeButton.setFocusPainted(false);
        backToAdminHomeButton.setBorderPainted(false);
        backToAdminHomeButton.setOpaque(false);
        backToAdminHomeButton.setContentAreaFilled(false);
        backToAdminHomeButton.setBorder(null);
        topPanel.add(backToAdminHomeButton, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        // Creazione del pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Barra di navigazione (verticale)
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(60, 63, 65));
        navBar.setPreferredSize(new Dimension(150, 200));
        navBar.setLayout(new GridLayout(0, 1, 0, 10));

        String[] buttonLabels = {"Aggiungi evento", "Aggiungi luogo", "Modifica evento", "Modifica luogo"};
        for (String label : buttonLabels) {
            JButton button = createNavBarButton(label);
            navBar.add(button);
        }

        mainPanel.add(navBar, BorderLayout.WEST);

        // Area centrale per il contenuto
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Azioni sui bottoni
        Component[] components = navBar.getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(e -> {
                    String action = button.getText();
                    System.out.println("Bottone cliccato: " + action);
                    contentPanel.removeAll();  // Pulisce il pannello centrale ad ogni clic

                    if ("Modifica evento".equals(action)) {
                        showEventListTable();  // Mostra la lista eventi solo quando "Modifica evento" è cliccato
                    } else {
                        JLabel label = new JLabel("Hai cliccato: " + action, JLabel.CENTER);
                        label.setFont(new Font("Arial", Font.BOLD, 20));
                        contentPanel.add(label, BorderLayout.CENTER);
                    }

                    contentPanel.revalidate();
                    contentPanel.repaint();
                });
            }
        }

        // Recupera e imposta gli eventi
        fetchAndSetEvents();
    }

    private void fetchAndSetEvents() {
        System.out.println("Recupero eventi dal database...");
        eventi = AdminGetEventsDatabase.getAllEvents();
        if (eventi == null || eventi.isEmpty()) {
            System.out.println("Nessun evento trovato nel database.");
        }
    }

    private void showEventListTable() {
        System.out.println("Mostro la tabella degli eventi...");
        contentPanel.removeAll();  // Rimuove il contenuto precedente prima di aggiungere la tabella

        // Creazione del modello della tabella
        String[] columnNames = {"Nome", "Data", "Ora", "ID Luogo"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Aggiungi gli eventi al modello
        if (eventi != null && !eventi.isEmpty()) {
            for (Evento evento : eventi) {
                System.out.println("Aggiungo evento alla tabella: " + evento.getNome());
                Object[] row = {
                    evento.getNome(),
                    evento.getData(),
                    evento.getOra(),
                    evento.getLuogo()
                };
                tableModel.addRow(row);
            }
        } else {
            System.out.println("La lista degli eventi è vuota.");
        }

        // Crea la tabella
        JTable eventTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Disabilita la modifica delle celle
            }
        };

        eventTable.setRowHeight(30);  // Altezza riga maggiore per una visualizzazione migliore
        eventTable.setFont(new Font("Arial", Font.PLAIN, 14));  // Font più leggibile
        eventTable.setSelectionBackground(new Color(75, 110, 175));  // Colore di selezione

        // Impedisce che l'utente possa selezionare le celle ma consente di selezionare le righe
        eventTable.setCellSelectionEnabled(false);
        eventTable.setRowSelectionAllowed(true);
        eventTable.setColumnSelectionAllowed(false);

        // Impostiamo il Renderer per ogni riga come pulsante
        eventTable.setDefaultRenderer(Object.class, new ButtonRenderer());

        // Aggiungiamo un MouseListener per rilevare il clic su una riga
        eventTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                int row = eventTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    // Esegui azione quando si clicca sulla riga
                    String nomeEvento = (String) tableModel.getValueAt(row, 0);
                    System.out.println("Evento cliccato: " + nomeEvento);
                    // Puoi aggiungere un'azione qui, come mostrare i dettagli dell'evento
                    showEventDetails(eventi.get(row));  // Funzione per mostrare i dettagli
                }
            }
        });

        // Imposta un bordo e lo stile della tabella
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Eventi"));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Classe Renderer per creare un "pulsante" su ogni riga
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText(value.toString());
            if (isSelected) {
                setBackground(new Color(75, 110, 175));  // Colore di sfondo per la selezione
                setForeground(Color.WHITE);
            } else {
                setBackground(Color.WHITE);  // Colore di sfondo per righe non selezionate
                setForeground(Color.BLACK);
            }
            return this;
        }
    }

    // Funzione per mostrare i dettagli dell'evento cliccato
    private void showEventDetails(Evento evento) {
        // Aggiungi codice per mostrare i dettagli dell'evento
        JLabel label = new JLabel("Dettagli evento: " + evento.getNome(), JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.removeAll();
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Metodo per creare i pulsanti nel menu di navigazione
    private static JButton createNavBarButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBackground(new Color(75, 110, 175));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Metodo per gestire l'azione del pulsante "Torna alla Home"
    public void setBackToAdminHomeAction(ActionListener action) {
        backToAdminHomeButton.addActionListener(action);
    }
}
