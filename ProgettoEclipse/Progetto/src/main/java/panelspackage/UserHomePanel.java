package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserHomePanel extends JPanel {
    private JButton logoutButton;
    private JLabel titleLabel;
    private JList<String> eventsList;

    public UserHomePanel() {
        // Imposta il layout principale del pannello
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Creazione del pannello superiore per contenere il pulsante di logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));

        // Pulsante di logout in alto a sinistra
        logoutButton = new JButton("<html><u>Logout</u></html>");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setBackground(null);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorder(null);
        topPanel.add(logoutButton, BorderLayout.WEST); // Aggiungi il pulsante logout a sinistra

        add(topPanel, BorderLayout.NORTH); // Aggiungi il pannello superiore al bordo nord

        // Creazione di un pannello centrale per contenere il titolo e la lista
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));

        // Etichetta del titolo sopra la lista
        titleLabel = new JLabel("Biglietti in vendita");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Aggiungi margini per spaziatura
        centerPanel.add(titleLabel, BorderLayout.NORTH); // Aggiungi il titolo in alto nel pannello centrale

        // Lista degli eventi
        eventsList = new JList<>();
        eventsList.setFont(new Font("Arial", Font.PLAIN, 16));
        eventsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventsList.setBackground(Color.WHITE);
        eventsList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Aggiungi uno scroll pane per la lista
        JScrollPane scrollPane = new JScrollPane(eventsList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Seleziona un'opzione"));
        scrollPane.setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER); // Aggiungi lo scroll pane al centro del pannello centrale

        add(centerPanel, BorderLayout.CENTER); // Aggiungi il pannello centrale al centro del layout principale
    }

    // Metodo per impostare il gestore del pulsante di logout
    public void setLogoutAction(ActionListener action) {
        logoutButton.addActionListener(action);
    }

    // Metodo per impostare i dati della lista degli eventi
    public void setEventsListData(String[] events) {
        eventsList.setListData(events);
    }

    // Metodo per ottenere l'evento selezionato
    public String getSelectedEvent() {
        return eventsList.getSelectedValue();
    }
}
