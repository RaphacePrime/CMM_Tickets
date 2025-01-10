package adminpanels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminHomePanel extends JPanel {
    private JButton logoutButton; 
    private JButton switchToModifyEventButton;
    private JButton switchToModifyLuogoButton;
    private JButton switchToAddLuogoButton;
    private JButton switchToAddEventButton;
    private JPanel contentPanel; 

    public AdminHomePanel() {
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

        
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(60, 63, 65));
        navBar.setPreferredSize(new Dimension(150, 200));
        navBar.setLayout(new GridLayout(0, 1, 0, 10));

        String[] buttonLabels = {"Aggiungi evento", "Aggiungi luogo", "Modifica evento", "Modifica luogo"};
        for (String label : buttonLabels) {
            JButton button = createNavBarButton(label);
            navBar.add(button);

            
            if (label.equals("Modifica evento")) {
                switchToModifyEventButton = button;
            }
            
            if (label.equals("Modifica luogo")) {
                switchToModifyLuogoButton = button;
            }
            
            if (label.equals("Aggiungi luogo")) {
                switchToAddLuogoButton = button;
            }
            
            if (label.equals("Aggiungi evento")) {
                switchToAddEventButton = button;
            }
        }

        add(navBar, BorderLayout.WEST);

        
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new CardLayout()); 
        add(contentPanel, BorderLayout.CENTER);

        
        JLabel initialLabel = new JLabel("Benvenuto nella Home Admin", JLabel.CENTER);
        initialLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(initialLabel, "Initial");
    }

    
    public void setLogoutAction(ActionListener action) {
        logoutButton.addActionListener(action);
    }

    
    public void setSwitchToModifyEventAction(ActionListener action) {
        switchToModifyEventButton.addActionListener(action);
    }
    
    public void setSwitchToModifyLuogoAction(ActionListener action) {
        switchToModifyLuogoButton.addActionListener(action);
    }
    
    public void setSwitchToAddLuogoAction(ActionListener action) {
        switchToAddLuogoButton.addActionListener(action);
    }
    
    public void setSwitchToAddEventAction(ActionListener action) {
        switchToAddEventButton.addActionListener(action);
    }
        
    
    public void setContentPanel(JPanel newPanel) {
        contentPanel.removeAll(); 
        contentPanel.add(newPanel, BorderLayout.CENTER); 
        contentPanel.revalidate(); 
        contentPanel.repaint(); 
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
}
