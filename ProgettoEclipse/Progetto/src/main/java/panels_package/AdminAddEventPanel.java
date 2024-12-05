package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import classes_package.Evento;
import classes_package.Luogo;
import classes_package.Settore;
import database_package.AdminLuoghiDatabase;
import frames_package.MainFrame;

public class AdminAddEventPanel extends JPanel {
    private JTextField nameField;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JTextField maxTicketsField;
    private JCheckBox seatNumberedCheckbox;
    private JSpinner saleStartSpinner;
    private JComboBox<String> locationDropdown;
    private JButton manageSectorsButton;
    private JButton addButton;
    private JComboBox<String> sectorsDropdown;
    public List<Settore> settori = new ArrayList<>();
    private List<Luogo> listaluoghi = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(AdminAddEventPanel.class);

    public AdminAddEventPanel() {
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        List<String> nomiluoghi = new ArrayList<>();
        listaluoghi = AdminLuoghiDatabase.getAllLuoghi();
        for (int i = 0; i < listaluoghi.size(); i++) {
            nomiluoghi.add(listaluoghi.get(i).getNome());
        }

        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Aggiungi Nuovo Evento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        JLabel nameLabel = new JLabel("Nome evento:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);

        JLabel dateLabel = new JLabel("Data:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(dateSpinner, gbc);

        JLabel timeLabel = new JLabel("Ora:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(timeLabel, gbc);

        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(timeSpinner, gbc);

        JLabel maxTicketsLabel = new JLabel("Max biglietti a persona:");
        maxTicketsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(maxTicketsLabel, gbc);

        maxTicketsField = new JTextField(10);
        maxTicketsField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(maxTicketsField, gbc);

        JLabel seatNumberedLabel = new JLabel("Posto numerato:");
        seatNumberedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(seatNumberedLabel, gbc);

        seatNumberedCheckbox = new JCheckBox();
        seatNumberedCheckbox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(seatNumberedCheckbox, gbc);

        JLabel saleStartLabel = new JLabel("Data inizio vendita:");
        saleStartLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(saleStartLabel, gbc);

        saleStartSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor saleStartEditor = new JSpinner.DateEditor(saleStartSpinner, "dd/MM/yyyy");
        saleStartSpinner.setEditor(saleStartEditor);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(saleStartSpinner, gbc);

        JLabel locationLabel = new JLabel("Luogo:");
        //updateLocationDropdown();
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(locationLabel, gbc);

        locationDropdown = new JComboBox<>(nomiluoghi.toArray(new String[0]));
        locationDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(locationDropdown, gbc);

        manageSectorsButton = new JButton("Gestisci settori");
        manageSectorsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageSectorsButton.setBackground(new Color(255, 223, 0));
        manageSectorsButton.setForeground(Color.BLACK);
        manageSectorsButton.setOpaque(true);
        manageSectorsButton.setBorderPainted(false);
        manageSectorsButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(manageSectorsButton, gbc);

        JLabel sectorsLabel = new JLabel("Settori aggiunti:");
        sectorsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(sectorsLabel, gbc);

        sectorsDropdown = new JComboBox<>();
        sectorsDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 9;
        formPanel.add(sectorsDropdown, gbc);

        addButton = new JButton("Aggiungi Evento");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(new Color(75, 175, 110));
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEvento();
            }
        });

        updateSectorsDropdown();
        
        add(formPanel, BorderLayout.CENTER);
        
        //updateLocationDropdown();
    }

    private void addEvento() {
        String nome = nameField.getText().trim();
        Date data = (Date) dateSpinner.getValue();
        String ora = new SimpleDateFormat("HH:mm").format((Date) timeSpinner.getValue());
        int maxBigliettiAPersona;
        try {
            maxBigliettiAPersona = Integer.parseInt(maxTicketsField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserire un numero valido per Max Biglietti.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean postoNumerato = seatNumberedCheckbox.isSelected();
        Date dataInizioVendita = (Date) saleStartSpinner.getValue();
        String selectedLuogo = (String) locationDropdown.getSelectedItem();

        if (nome.isEmpty() || maxTicketsField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idLuogo=0;
        for(int i=0;i<listaluoghi.size();i++)
        {
        	Luogo l= listaluoghi.get(i);
        	if(l.getNome().equals(selectedLuogo))
        	{
        		idLuogo=l.getIdLuogo();
        	}
        }

        Evento nuovoEvento = new Evento(nome, data, ora, maxBigliettiAPersona, postoNumerato, dataInizioVendita, idLuogo);
        JOptionPane.showMessageDialog(this, "Evento aggiunto con successo!\n" + nuovoEvento, "Successo", JOptionPane.INFORMATION_MESSAGE);
        logger.info("Evento aggiunto: " + nuovoEvento);
    }

    public void updateSectorsDropdown() {
    	logger.info("num settori in update: "+AdminAddEventPanel.this.settori.size());
        if (settori.isEmpty()) {
            sectorsDropdown.addItem("Nessun settore aggiunto");
        } else {
        	sectorsDropdown.removeAllItems();
            for (Settore s : settori) {
                sectorsDropdown.addItem(s.getNome()+ " " + s.getPosizione()+", anello "+String.valueOf(s.getAnello()));
            }
        }
    }
    
    public void updateLocationDropdown() {
    	List<String> nomiluoghi = new ArrayList<>();
        listaluoghi = AdminLuoghiDatabase.getAllLuoghi();
        locationDropdown.removeAllItems();
        for (int i = 0; i < listaluoghi.size(); i++) {
            nomiluoghi.add(listaluoghi.get(i).getNome());
            locationDropdown.addItem(nomiluoghi.get(i));
        }
        
        
    }

    public void setSwitchToAddSectorsAction(MainFrame mainFrame) {
        manageSectorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	logger.info("num settori: "+AdminAddEventPanel.this.settori.size());
                AdminAddSectorsPanel adminAddSectorsPanel = new AdminAddSectorsPanel(AdminAddEventPanel.this);
                mainFrame.adminHomePanel.setContentPanel(adminAddSectorsPanel);
            }
        });
    }
    
}
