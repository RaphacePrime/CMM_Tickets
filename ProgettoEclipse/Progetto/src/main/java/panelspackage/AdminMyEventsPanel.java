package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMyEventsPanel extends JPanel {
    private JButton backToAdminHomeButton;
    private JLabel titleLabel;
    private JList<String> eventsList;

    public AdminMyEventsPanel() {
        // Imposta il layout principale del pannello
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
        topPanel.add(backToAdminHomeButton, BorderLayout.WEST); // Aggiungi il pulsante a sinistra

        add(topPanel, BorderLayout.NORTH); // Aggiungi il pannello superiore al bordo nord

        // Creazione di un pannello centrale per contenere il titolo e la lista
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));

        // Etichetta del titolo sopra la lista
        titleLabel = new JLabel("I tuoi eventi");
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
        scrollPane.setBorder(BorderFactory.createTitledBorder("Seleziona evento da gestire"));
        scrollPane.setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER); // Aggiungi lo scroll pane al centro del pannello centrale

        add(centerPanel, BorderLayout.CENTER); // Aggiungi il pannello centrale al centro del layout principale
    }

    // Metodo per impostare l'azione del pulsante "Torna alla Home"
    public void setBackToAdminHomeAction(ActionListener action) {
        backToAdminHomeButton.addActionListener(action);
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
