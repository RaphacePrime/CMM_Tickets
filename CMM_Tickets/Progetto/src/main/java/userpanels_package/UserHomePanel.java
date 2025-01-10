package userpanels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserHomePanel extends JPanel {
    private JButton logoutButton;
    private JLabel titleLabel;
    private JList<String> eventsList;
    private JButton switchToViewLuogoButton;
    private JButton switchToViewEventButton;
    private JPanel contentPanel;
	private JButton switchToCartButton;
	private JButton switchToMyOrderButton;

    public UserHomePanel() {
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

       
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));

        
        logoutButton = new JButton("<html><u>Logout</u></html>");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setBackground(null);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorder(null);
        topPanel.add(logoutButton, BorderLayout.WEST); 

        add(topPanel, BorderLayout.NORTH); 

        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));

        
        titleLabel = new JLabel("Biglietti in vendita");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
        centerPanel.add(titleLabel, BorderLayout.NORTH); 

        // Lista degli eventi
        eventsList = new JList<>();
        eventsList.setFont(new Font("Arial", Font.PLAIN, 16));
        eventsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventsList.setBackground(Color.WHITE);
        eventsList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

       
        JScrollPane scrollPane = new JScrollPane(eventsList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Seleziona un'opzione"));
        scrollPane.setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        
        
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(60, 63, 65));
        navBar.setPreferredSize(new Dimension(150, 200));
        navBar.setLayout(new GridLayout(0, 1, 0, 10));

        String[] buttonLabels = {"Visualizza eventi", "Visualizza luoghi", "Carrello", "I miei ordini"};
        for (String label : buttonLabels) {
            JButton button = createNavBarButton(label);
            navBar.add(button);

            
            if (label.equals("Visualizza eventi")) {
                switchToViewEventButton = button;
            }
            
            if (label.equals("Visualizza luoghi")) {
                switchToViewLuogoButton = button;
            }
            
            if(label.equals("Carrello")) {
            	switchToCartButton = button;
            }
                        
            if(label.equals("I miei ordini")) {
            	switchToMyOrderButton = button;
            }
        }

        add(navBar, BorderLayout.WEST);

        
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new CardLayout()); 
        add(contentPanel, BorderLayout.CENTER);
        
        
        JLabel initialLabel = new JLabel("Benvenuto nella Home User", JLabel.CENTER);
        initialLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(initialLabel, "Initial");
    }


    public void setLogoutAction(ActionListener action) {
        logoutButton.addActionListener(action);
    }


    public void setEventsListData(String[] events) {
        eventsList.setListData(events);
    }


    public String getSelectedEvent() {
        return eventsList.getSelectedValue();
    }
    
    private JButton createNavBarButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBackground(new Color(75, 110, 175));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    public void setContentPanel(JPanel newPanel) {
        contentPanel.removeAll(); 
        contentPanel.add(newPanel, BorderLayout.CENTER); 
        contentPanel.revalidate(); 
        contentPanel.repaint(); 
    }
    
    
    public void setSwitchToViewEventAction(ActionListener action) {
        switchToViewEventButton.addActionListener(action);
    }
    
    public void setSwitchToViewLuogoAction(ActionListener action) {
        switchToViewLuogoButton.addActionListener(action);
    }
    
    public void setSwitchToCart(ActionListener action) {
    	switchToCartButton.addActionListener(action);
    }
    
    public void setSwitchToMyOrder(ActionListener action) {
    	switchToMyOrderButton.addActionListener(action);
    }
}
