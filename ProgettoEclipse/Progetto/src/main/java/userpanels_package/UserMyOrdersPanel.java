package userpanels_package;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import classes_package.Biglietto;
import database_package.TicketsDatabase;
import login_package.Login;

public class UserMyOrdersPanel extends JPanel {


    public UserMyOrdersPanel() throws ParseException {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("I miei ordini");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Biglietto> biglietti = TicketsDatabase.getAllUserTickets(Login.loginId);
        if (biglietti.isEmpty()) {
            JLabel emptyLabel = new JLabel("Non hai biglietti acquistati.");
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 18));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setForeground(Color.GRAY);
            contentPanel.add(emptyLabel);
        } else {
            for (Biglietto biglietto : biglietti) {
                JPanel bigliettoPanel = createBigliettoPanel(biglietto);
                bigliettoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(bigliettoPanel);
                contentPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createBigliettoPanel(Biglietto biglietto) throws ParseException {
        JPanel panel = new JPanel(new GridBagLayout());
        EventoSettoreResult esr=UserCarrelloPanel.findEventoSettore(biglietto);
        Date today = new Date();
        if(esr.getEvento().getData().before(today)) 
        {
        	panel.setBackground(new Color(255, 204, 204));
        }
        else
        {
        	panel.setBackground(new Color(204, 255, 204)); 
        }
        
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel eventoLabel = new JLabel("Evento:");
        eventoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(eventoLabel, gbc);
        
        JTextField eventoField = new JTextField(esr.getEvento().getNome()+" del "+new SimpleDateFormat("dd/MM/yyyy").format(esr.getEvento().getData()));
        eventoField.setFont(new Font("Arial", Font.PLAIN, 14));
        eventoField.setEditable(false);
        eventoField.setPreferredSize(new Dimension(300,30));
        gbc.gridx = 1;
        panel.add(eventoField, gbc);

        JLabel nomeLabel = new JLabel("Nome Utilizzatore:");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nomeLabel, gbc);

        JTextField nomeField = new JTextField(biglietto.getNomeUtilizzatore());
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeField.setEditable(false);
        nomeField.setPreferredSize(new Dimension(300,30));
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        JLabel cognomeLabel = new JLabel("Cognome Utilizzatore:");
        cognomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(cognomeLabel, gbc);

        JTextField cognomeField = new JTextField(biglietto.getCognomeUtilizzatore());
        cognomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        cognomeField.setEditable(false);
        cognomeField.setPreferredSize(new Dimension(300,30));
        gbc.gridx = 1;
        panel.add(cognomeField, gbc);

        JLabel postoLabel = new JLabel("Posto:");
        postoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(postoLabel, gbc);

        JTextField postoField = new JTextField(String.valueOf(biglietto.getPosto()));
        postoField.setFont(new Font("Arial", Font.PLAIN, 14));
        postoField.setEditable(false);
        postoField.setPreferredSize(new Dimension(300,30));
        gbc.gridx = 1;
        panel.add(postoField, gbc);

        JLabel settoreLabel = new JLabel("Settore:");
        settoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(settoreLabel, gbc);

        JTextField settoreField = new JTextField(String.valueOf(UserDetailsEventPanel.outputSettoriSmall(esr.getSettore())));
        settoreField.setFont(new Font("Arial", Font.PLAIN, 14));
        settoreField.setEditable(false);
        settoreField.setPreferredSize(new Dimension(300,30));
        gbc.gridx = 1;
        panel.add(settoreField, gbc);

        return panel;
    }
}
