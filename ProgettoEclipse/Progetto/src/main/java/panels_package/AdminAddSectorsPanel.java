package panels_package;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import classes_package.Evento;
import classes_package.Settore;

public class AdminAddSectorsPanel extends JPanel {
    private JLabel imageLabel;
    private final int IMAGE_WIDTH = 500;
    private final int IMAGE_HEIGHT = 300;
    private JButton lastSelectedButton = null;
    JTextField nomeField = new JTextField();
    JTextField prezzoField = new JTextField();
    JTextField postiTotaliField = new JTextField();
    private List<Settore> settori= new ArrayList<>();
    
    private static final Logger logger = LogManager.getLogger(AdminAddSectorsPanel.class);

    public AdminAddSectorsPanel(List<Settore> settori) {
    	this.settori=settori;
        setLayout(new BorderLayout());

        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton calcioButton = new JButton("Partita di calcio");
        JButton concertoButton = new JButton("Concerto");

        topButtonPanel.add(calcioButton);
        topButtonPanel.add(concertoButton);
        add(topButtonPanel, BorderLayout.NORTH);

        JPanel centralWrapper = new JPanel(new BorderLayout());
        centralWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setPreferredSize(new Dimension(1000, gridPanel.getPreferredSize().height));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String path = "src/main/resources/Immagini/campocalcio.jpg";
        imageLabel = new JLabel();
        imageLabel.setMinimumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        //imageLabel.setMinimumSize(getPreferredSize());
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        updateImage(path);

        Dimension buttonSize = new Dimension(50, 50);

        addButton(gridPanel, gbc, 4, 1, new Dimension(IMAGE_WIDTH, 30));
        addButton(gridPanel, gbc, 4, 2, new Dimension(IMAGE_WIDTH, 30));
        addButton(gridPanel, gbc, 4, 3, new Dimension(IMAGE_WIDTH, 30));

        addButton(gridPanel, gbc, 1, 4, new Dimension(60, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 2, 4, new Dimension(60, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 3, 4, new Dimension(60, IMAGE_HEIGHT));

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gridPanel.add(imageLabel, gbc);

        addButton(gridPanel, gbc, 5, 4, new Dimension(60, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 6, 4, new Dimension(60, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 7, 4, new Dimension(60, IMAGE_HEIGHT));

        addButton(gridPanel, gbc, 4, 5, new Dimension(IMAGE_WIDTH, 30));
        addButton(gridPanel, gbc, 4, 6, new Dimension(IMAGE_WIDTH, 30));
        addButton(gridPanel, gbc, 4, 7, new Dimension(IMAGE_WIDTH, 30));

        centralWrapper.add(gridPanel, BorderLayout.CENTER);
        add(centralWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(5, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        bottomPanel.add(new JLabel("Nome Settore:"));
        nomeField = new JTextField();
        bottomPanel.add(nomeField);

        bottomPanel.add(new JLabel("Prezzo:"));
        prezzoField = new JTextField();
        bottomPanel.add(prezzoField);

        bottomPanel.add(new JLabel("Posti Totali:"));
        postiTotaliField = new JTextField();
        bottomPanel.add(postiTotaliField);

        JButton eliminaButton = new JButton("Elimina settore selezionato");
        eliminaButton.setOpaque(true);
        eliminaButton.addActionListener(e -> {
            if (lastSelectedButton != null) {
                lastSelectedButton.setText("+");
                lastSelectedButton.setBackground(Color.WHITE);
                lastSelectedButton = null;
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un evento da eliminare");
            }
        });
        bottomPanel.add(eliminaButton);

        add(bottomPanel, BorderLayout.SOUTH);

        calcioButton.addActionListener(e -> updateImage("src/main/resources/Immagini/campocalcio.jpg"));
        concertoButton.addActionListener(e -> updateImage("src/main/resources/Immagini/concerto.jpg"));
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, int x, int y, Dimension size) {
        JButton button = new JButton("+");
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMinimumSize(size);
        button.setFont(new Font("Arial", Font.PLAIN, 14));

        GridBagConstraints localGbc = new GridBagConstraints();
        localGbc.gridx = x;
        localGbc.gridy = y;
        localGbc.gridwidth = 1;
        localGbc.gridheight = 1;
        localGbc.weightx = 0.0;
        localGbc.weighty = 0.0;
        localGbc.anchor = GridBagConstraints.CENTER;
        localGbc.insets = new Insets(5, 5, 5, 5);

        button.addActionListener(e -> {
            if (lastSelectedButton != null) {
                //lastSelectedButton.setText("+");
                lastSelectedButton.setBackground(Color.WHITE);
            }
            lastSelectedButton = button;
            button.setText("X");
            button.setBackground(new Color(33, 150, 243));
            button.setOpaque(true);
            logger.info("Pulsante cliccato: Posizione (" + x + ", " + y + ")");
            /*
            String nome=this.nomeField.getText();
            float prezzo=Float.parseFloat(this.prezzoField.getText());
            int postiTotali=Integer.parseInt(this.postiTotaliField.getText());
            */
            
            String posizione;
            int anello;
            if(x==4)
            {
            	if(y==1)
            	{
            		posizione="nord";
            		anello=3;
            	}
            	else if(y==2)
            	{
            		posizione="nord";
            		anello=2;
            	}
            	else if(y==3)
            	{
            		posizione="nord";
            		anello=1;
            	}
            	else if(y==5)
            	{
            		posizione="sud";
            		anello=1;
            	}
            	else if(y==6)
            	{
            		posizione="sud";
            		anello=2;
            	}
            	else if(y==7)
            	{
            		posizione="sud";
            		anello=3;
            	}
            }
            else if(y==4)
            {
            	if(x==1)
            	{
            		posizione="ovest";
            		anello=3;
            	}
            	else if(x==2)
            	{
            		posizione="ovest";
            		anello=2;
            	}
            	else if(x==3)
            	{
            		posizione="ovest";
            		anello=1;
            	}
            	else if(x==5)
            	{
            		posizione="est";
            		anello=1;
            	}
            	else if(x==6)
            	{
            		posizione="est";
            		anello=2;
            	}
            	else if(x==7)
            	{
            		posizione="est";
            		anello=3;
            	}
            }
        });

        panel.add(button, localGbc);
    }

    private void updateImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        revalidate();
        repaint();
    }
}
