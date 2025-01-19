
package adminpanels_package;

import javax.swing.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import classes_package.Luogo;
import database_package.EventsDatabase;
import database_package.LuoghiDatabase;
import database_package.Database;
import frames_package.MainFrame;
import utils_package.LookAndFeelUtil;


public class AdminModifyLuogoPanel extends JPanel {
    private List<Luogo> luoghi;
    private JTable luogoTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private static Logger logger = LogManager.getLogger(AdminModifyLuogoPanel.class);

    public AdminModifyLuogoPanel() {
    	LookAndFeelUtil.setCrossPlatformLookAndFeel();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        
        JLabel titleLabel = new JLabel("Modifica Luoghi", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 63, 65));  
        add(titleLabel, BorderLayout.NORTH);


        fetchAndDisplayLuoghi();

    }

    public void setBackToAdminHomeAction(ActionListener action) {
        backButton.addActionListener(action);
    }


    public void fetchAndDisplayLuoghi() {
        luoghi = LuoghiDatabase.getAllLuoghi();
        String[] columnNames = {"Nome", "Città", "Indirizzo"}; 
        Object[][] data = new Object[luoghi.size()][3]; 

        for (int i = 0; i < luoghi.size(); i++) {
        	Luogo e = luoghi.get(i);

            
            data[i][0] = e.getNome();
            data[i][1] = e.getIndirizzo(); 
            data[i][2] = e.getCittà();
            
                       
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

        JScrollPane scrollPane = new JScrollPane(luogoTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista Luoghi"));
        add(scrollPane, BorderLayout.CENTER);
        
    }
    
    public void setSwitchToDetailsLuogoAction() {
        luogoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me) {
                int row = luogoTable.rowAtPoint(me.getPoint());
                if (row >= 0) {
                    String nomeLuogo = (String) tableModel.getValueAt(row, 0);
                    logger.info("Luogo cliccato: " + nomeLuogo);
                    
                    for (Luogo luogo : luoghi) {
                        if (luogo.getNome().equals(nomeLuogo)) {
                            AdminDetailsLuogoPanel detailsPanel = new AdminDetailsLuogoPanel(luogo);
                            MainFrame.setAdminHomeContentPanel(detailsPanel);
                            break;
                        }
                    }
                }
            }
        });
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
    
    public void refreshTable() {
    	fetchAndDisplayLuoghi();
    	revalidate();
    	repaint();
    }
}
