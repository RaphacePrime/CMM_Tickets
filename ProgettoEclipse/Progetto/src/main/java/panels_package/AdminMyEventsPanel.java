package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMyEventsPanel extends JPanel {
    private JButton backToAdminHomeButton;
    private JLabel titleLabel;
    private JList<String> eventsList;
    private JLabel imageLabel;
    private ImageIcon imageIcon;

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
        
        /*String imagePath = "../../Immagini/mappa_stadio.jpg";
        imageIcon = new ImageIcon(imagePath);
        imageLabel = new JLabel(imageIcon);
        add(imageLabel,BorderLayout.SOUTH);
        */
        
        // Creazione del pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);
        
        // Barra di navigazione (verticale)
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color (60, 63, 65));
        navBar.setPreferredSize(new Dimension(150, 200));
        navBar.setLayout(new GridLayout(0,1,0,10));
        
        String[] buttonLabels = {"Aggiungi evento", "Aggiungi luogo", "Modifica evento", "Modifica luogo"};
        for( String label: buttonLabels) {
        	JButton button= createNavBarButton(label);
        	navBar.add(button);
        }
        
        mainPanel.add(navBar, BorderLayout.WEST);
        
        
        //Area centrale per il contenuto (andrà modificato per le varie azioni scelte sui bottoni
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Azioni sui bottoni
        Component [] components = navBar.getComponents();
        for(Component comp : components) {
        	if(comp instanceof JButton) {
        		JButton button = (JButton) comp;
        		button.addActionListener(new ActionListener(){
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			contentPanel.removeAll();
        			JLabel label = new JLabel("Hai cliccato:" + button.getText(), JLabel.CENTER);
        			label.setFont(new Font("Arial", Font.BOLD, 20));
        			contentPanel.add(label, BorderLayout.CENTER);
        			contentPanel.revalidate();
        			contentPanel.repaint();
        		}
        	});
        }
    }
   }
    
    

    // Metodo per impostare l'azione del pulsante "Torna alla Home"
    public void setBackToAdminHomeAction(ActionListener action) {
        backToAdminHomeButton.addActionListener(action);
    }
    
    private static JButton createNavBarButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false); // Rimuovi il bordo di focus
        button.setBackground(new Color(75, 110, 175)); // Colore blu scuro
        button.setForeground(Color.WHITE); // Colore del testo
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margini interni
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore al passaggio
        return button;
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
