package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminAddEventPanel extends JPanel {
    private JLabel imageLabel;
    private final int IMAGE_WIDTH = 500;
    private final int IMAGE_HEIGHT = 300;

    public AdminAddEventPanel() {
        setLayout(new BorderLayout());

        // Panel for top buttons
        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton calcioButton = new JButton("Partita di calcio");
        JButton concertoButton = new JButton("Concerto");

        topButtonPanel.add(calcioButton);
        topButtonPanel.add(concertoButton);
        add(topButtonPanel, BorderLayout.NORTH);

        // Central panel with GridBagLayout
        JPanel gridPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridPanel.setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components

        // Load initial image
        String path = "src/main/resources/Immagini/campocalcio.jpg";
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        updateImage(path);

        // Uniform button proportions
        Dimension buttonSize = new Dimension(50, 50); // Square buttons

        // Add top buttons (rows 1, 2, 3, column 4)
        addButton(gridPanel, gbc, 4, 1, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 2, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 3, new Dimension(IMAGE_WIDTH, 50));

        // Add left buttons (columns 1, 2, 3, row 4)
        addButton(gridPanel, gbc, 1, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 2, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 3, 4, new Dimension(50, IMAGE_HEIGHT));

        // Add the image (row 4, column 4)
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gridPanel.add(imageLabel, gbc);

        // Add right buttons (columns 5, 6, 7, row 4)
        addButton(gridPanel, gbc, 5, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 6, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 7, 4, new Dimension(50, IMAGE_HEIGHT));

        // Add bottom buttons (rows 5, 6, 7, column 4)
        addButton(gridPanel, gbc, 4, 5, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 6, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 7, new Dimension(IMAGE_WIDTH, 50));

        add(gridPanel, BorderLayout.CENTER);

        // Bottom panel for Settore input fields
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns

        bottomPanel.add(new JLabel("Nome Settore:"));
        JTextField nomeField = new JTextField();
        bottomPanel.add(nomeField);

        bottomPanel.add(new JLabel("Prezzo:"));
        JTextField prezzoField = new JTextField();
        bottomPanel.add(prezzoField);

        bottomPanel.add(new JLabel("Posti Totali:"));
        JTextField postiTotaliField = new JTextField();
        bottomPanel.add(postiTotaliField);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button action listeners for image change
        calcioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateImage("src/main/resources/Immagini/campocalcio.jpg");
            }
        });

        concertoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateImage("src/main/resources/Immagini/concerto.jpg");
            }
        });
    }

    /**
     * Helper method to add a button to the grid panel.
     * 
     * @param panel     The panel where the button will be added.
     * @param gbc       GridBagConstraints instance.
     * @param x         Column position for the button.
     * @param y         Row position for the button.
     * @param size      Preferred size for the button.
     */
    private void addButton(JPanel panel, GridBagConstraints gbc, int x, int y, Dimension size) {
        JButton button = new JButton("+");
        button.setPreferredSize(size); // Set the button to a fixed size
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel.add(button, gbc);
    }

    // Funzione per aggiornare l'immagine
    private void updateImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        // Scala l'immagine per adattarla alle dimensioni
        Image scaledImage = imageIcon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        revalidate();
        repaint();
    }
}
