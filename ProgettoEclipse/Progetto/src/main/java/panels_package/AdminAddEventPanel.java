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
    private static final Logger logger = LogManager.getLogger(AdminAddEventPanel.class);

    public AdminAddEventPanel() {
    	List<String> nomiluoghi= new ArrayList<>();
    	List<Luogo> listaluoghi = AdminLuoghiDatabase.getAllLuoghi();
    	for(int i=0; i<listaluoghi.size(); i++)
    	{
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
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(manageSectorsButton, gbc);

        addButton = new JButton("Aggiungi Evento");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(new Color(75, 175, 110));
        addButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(addButton, gbc);
        
        
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEvento();
            }
        });

        add(formPanel, BorderLayout.CENTER);
    }

    private void addEvento() {
        String nome = nameField.getText().trim();
        String data = new SimpleDateFormat("dd/MM/yyyy").format((Date) dateSpinner.getValue());
        String ora = new SimpleDateFormat("HH:mm").format((Date) timeSpinner.getValue());
        int maxBiglietti = Integer.parseInt(maxTicketsField.getText().trim());
        boolean postoNumerato = seatNumberedCheckbox.isSelected();
        String dataInizioVendita = new SimpleDateFormat("dd/MM/yyyy").format((Date) saleStartSpinner.getValue());
        String idLuogo = (String) locationDropdown.getSelectedItem();

        if (nome.isEmpty() || maxTicketsField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Evento nuovoEvento = new Evento(0, nome, data, ora, maxBiglietti, postoNumerato, dataInizioVendita, Integer.parseInt(idLuogo.split(" ")[1]));
        JOptionPane.showMessageDialog(this, "Evento aggiunto con successo!\n" + nuovoEvento, "Successo", JOptionPane.INFORMATION_MESSAGE);
        logger.info("Evento aggiunto: " + nuovoEvento);
    }

	public void setSwitchToAddSectorsAction(MainFrame mainFrame) {
		manageSectorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AdminAddSectorsPanel adminAddSectorsPanel= new AdminAddSectorsPanel();
        		mainFrame.adminHomePanel.setContentPanel(adminAddSectorsPanel);
            }
        });
		
		
	}
}
