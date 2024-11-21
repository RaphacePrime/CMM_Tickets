
package panels_package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import classes_package.Luogo;
import database_package.AdminGetEventsDatabase;
import database_package.AdminGetLuoghiDatabase;

public class AdminModifyLuogoPanel extends JPanel {
    private List<Luogo> luoghi;
    private JTable luogoTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public AdminModifyLuogoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        
        JLabel titleLabel = new JLabel("Modifica Luoghi", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 63, 65));  
        add(titleLabel, BorderLayout.NORTH);


        fetchAndDisplayLuoghi();


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


    public void fetchAndDisplayLuoghi() {
        luoghi = AdminGetLuoghiDatabase.getAllLuoghi();
        String[] columnNames = {"Nome", "Indirizzo"}; 
        Object[][] data = new Object[luoghi.size()][2]; 

        for (int i = 0; i < luoghi.size(); i++) {
        	Luogo e = luoghi.get(i);

            
            data[i][0] = e.getNome();     
            data[i][1] = e.getIndirizzo();            
        }

        tableModel = new DefaultTableModel(data, columnNames);
        luogoTable = new JTable(tableModel) {
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

        
        luogoTable.setRowHeight(40);  
        luogoTable.setFont(new Font("Arial", Font.PLAIN, 14));  
        luogoTable.setSelectionBackground(new Color(75, 110, 175));  
        luogoTable.setSelectionForeground(Color.WHITE); 

        
        luogoTable.setCellSelectionEnabled(false);
        luogoTable.setRowSelectionAllowed(true);
        luogoTable.setColumnSelectionAllowed(false);

        
        luogoTable.setDefaultRenderer(Object.class, new ButtonRenderer());

       
        JTableHeader header = luogoTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(75, 110, 175));
        header.setForeground(Color.WHITE);

        
        luogoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = luogoTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    
                    String nomeLuogo = (String) tableModel.getValueAt(row, 0);
                    System.out.println("Luogo cliccato: " + nomeLuogo);
                    
                }
            }
        });

        
        JScrollPane scrollPane = new JScrollPane(luogoTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Luoghi"));
        add(scrollPane, BorderLayout.CENTER);
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
