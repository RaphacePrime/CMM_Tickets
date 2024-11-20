package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminHomePanel extends JPanel {
    private JButton logoutButton; // Logout button
    private JButton switchToModifyEventButton;
    private JPanel contentPanel; // The panel where content changes

    public AdminHomePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Create top panel for logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));

        // Logout button
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

        // Side navigation bar
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(60, 63, 65));
        navBar.setPreferredSize(new Dimension(150, 200));
        navBar.setLayout(new GridLayout(0, 1, 0, 10));

        String[] buttonLabels = {"Aggiungi evento", "Aggiungi luogo", "Modifica evento", "Modifica luogo"};
        for (String label : buttonLabels) {
            JButton button = createNavBarButton(label);
            navBar.add(button);

            // Action for "Modifica evento"
            if (label.equals("Modifica evento")) {
                switchToModifyEventButton = button;
            }
        }

        add(navBar, BorderLayout.WEST);

        // Main content panel where other panels are loaded
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new CardLayout()); // Using CardLayout for content switching
        add(contentPanel, BorderLayout.CENTER);

        // Empty initial label in the center
        JLabel initialLabel = new JLabel("Benvenuto nella Home Admin", JLabel.CENTER);
        initialLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(initialLabel, "Initial");
    }

    // Method to handle logout button action
    public void setLogoutAction(ActionListener action) {
        logoutButton.addActionListener(action);
    }

    // Method to handle "Modifica evento" button action
    public void setSwitchToModifyEventAction(ActionListener action) {
        switchToModifyEventButton.addActionListener(action);
    }

    // Method to change the content in the center panel
    public void setContentPanel(JPanel newPanel) {
        contentPanel.removeAll(); // Clear current content
        contentPanel.add(newPanel, BorderLayout.CENTER); // Add new panel
        contentPanel.revalidate(); // Refresh layout
        contentPanel.repaint(); // Redraw content
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
