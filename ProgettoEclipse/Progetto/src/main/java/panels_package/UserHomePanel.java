package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserHomePanel extends JPanel {
    private JButton logoutButton;
    private JLabel titleLabel;
    private JList<String> eventsList;

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
}
