package panels_package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import classes_package.Evento;
import database_package.AdminGetEventsDatabase;

public class AdminModifyEventPanel extends JPanel {
    private List<Evento> eventi;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public AdminModifyEventPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Title label for the panel
        JLabel titleLabel = new JLabel("Modifica Eventi", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 63, 65));  // Dark color for the title
        add(titleLabel, BorderLayout.NORTH);

        // Load and display events in the table
        fetchAndDisplayEvents();

        // Button to go back to Admin Home
        backButton = new JButton("Torna alla Home Admin");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(75, 110, 175));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        add(backButton, BorderLayout.SOUTH);
    }

    public void setBackToAdminHomeAction(ActionListener action) {
        backButton.addActionListener(action);
    }

    // Method to load and display events in the table
    public void fetchAndDisplayEvents() {
        eventi = AdminGetEventsDatabase.getAllEvents();
        String[] columnNames = {"Nome Evento", "Data", "Luogo", "ID"}; // Added ID column
        Object[][] data = new Object[eventi.size()][4]; // Array for 4 columns

        for (int i = 0; i < eventi.size(); i++) {
            Evento e = eventi.get(i);

            // Populate data for table
            data[i][0] = e.getNome();     // Event Name
            data[i][1] = e.getData();     // Event Date
            data[i][2] = e.getLuogo();    // Event Location
            data[i][3] = e.getId();       // Event ID
        }

        tableModel = new DefaultTableModel(data, columnNames);
        eventTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Disable cell editing
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (isCellSelected(row, column)) {
                    comp.setBackground(new Color(75, 110, 175)); // Selected row background
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setBackground(Color.WHITE); // Default background
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };

        // Styling the table similar to AdminMyEventsPanel
        eventTable.setRowHeight(40);  // Set row height for better readability
        eventTable.setFont(new Font("Arial", Font.PLAIN, 14));  // Font to Arial, size 14
        eventTable.setSelectionBackground(new Color(75, 110, 175));  // Selection color for rows
        eventTable.setSelectionForeground(Color.WHITE); // Text color for selected rows

        // Disable cell selection but allow row selection
        eventTable.setCellSelectionEnabled(false);
        eventTable.setRowSelectionAllowed(true);
        eventTable.setColumnSelectionAllowed(false);

        // Set a custom renderer to make rows look like buttons without borders
        eventTable.setDefaultRenderer(Object.class, new ButtonRenderer());

        // Set table header styles
        JTableHeader header = eventTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(75, 110, 175));
        header.setForeground(Color.WHITE);

        // Add a MouseListener to handle row clicks
        eventTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = eventTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    // Perform an action when a row is clicked
                    String nomeEvento = (String) tableModel.getValueAt(row, 0);
                    System.out.println("Evento cliccato: " + nomeEvento);
                    // You can add further action here, like showing the event details
                }
            }
        });

        // Add the table inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Eventi"));
        add(scrollPane, BorderLayout.CENTER);
    }

    // ButtonRenderer class to make table rows look like buttons
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder());  // Remove the border of the button
            setFocusPainted(false);  // Disable focus painting
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText(value.toString());
            if (isSelected) {
                setBackground(new Color(75, 110, 175));  // Background color when selected
                setForeground(Color.WHITE);
            } else {
                setBackground(Color.WHITE);  // Background color when not selected
                setForeground(Color.BLACK);
            }
            return this;
        }
    }
}
