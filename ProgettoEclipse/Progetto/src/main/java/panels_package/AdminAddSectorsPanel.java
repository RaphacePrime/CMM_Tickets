package panels_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminAddSectorsPanel extends JPanel {
    private JLabel imageLabel;
    private final int IMAGE_WIDTH = 500;
    private final int IMAGE_HEIGHT = 300;
    private JButton lastSelectedButton = null;

    public AdminAddSectorsPanel() {
        setLayout(new BorderLayout());

        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton calcioButton = new JButton("Partita di calcio");
        JButton concertoButton = new JButton("Concerto");

        topButtonPanel.add(calcioButton);
        topButtonPanel.add(concertoButton);
        add(topButtonPanel, BorderLayout.NORTH);

        JPanel centralWrapper = new JPanel(new BorderLayout());
        centralWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setPreferredSize(new Dimension(1000, gridPanel.getPreferredSize().height));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String path = "src/main/resources/Immagini/campocalcio.jpg";
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        updateImage(path);

        Dimension buttonSize = new Dimension(50, 50);

        addButton(gridPanel, gbc, 4, 1, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 2, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 3, new Dimension(IMAGE_WIDTH, 50));

        addButton(gridPanel, gbc, 1, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 2, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 3, 4, new Dimension(50, IMAGE_HEIGHT));

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gridPanel.add(imageLabel, gbc);

        addButton(gridPanel, gbc, 5, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 6, 4, new Dimension(50, IMAGE_HEIGHT));
        addButton(gridPanel, gbc, 7, 4, new Dimension(50, IMAGE_HEIGHT));

        addButton(gridPanel, gbc, 4, 5, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 6, new Dimension(IMAGE_WIDTH, 50));
        addButton(gridPanel, gbc, 4, 7, new Dimension(IMAGE_WIDTH, 50));

        centralWrapper.add(gridPanel, BorderLayout.CENTER);
        add(centralWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(5, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bottomPanel.add(new JLabel("Nome Settore:"));
        JTextField nomeField = new JTextField();
        bottomPanel.add(nomeField);

        bottomPanel.add(new JLabel("Prezzo:"));
        JTextField prezzoField = new JTextField();
        bottomPanel.add(prezzoField);

        bottomPanel.add(new JLabel("Posti Totali:"));
        JTextField postiTotaliField = new JTextField();
        bottomPanel.add(postiTotaliField);

        JButton eliminaButton = new JButton("Elimina settore selezionato");
        eliminaButton.addActionListener(e -> {
            if (lastSelectedButton != null) {
                lastSelectedButton.setText("+");
                lastSelectedButton = null;
            }
        });
        bottomPanel.add(eliminaButton);

        add(bottomPanel, BorderLayout.SOUTH);

        calcioButton.addActionListener(e -> updateImage("src/main/resources/Immagini/campocalcio.jpg"));
        concertoButton.addActionListener(e -> updateImage("src/main/resources/Immagini/concerto.jpg"));
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, int x, int y, Dimension size) {
        JButton button = new JButton("+");
        button.setPreferredSize(size);
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
            lastSelectedButton = button;
            button.setText("X");
            JOptionPane.showMessageDialog(this, "Pulsante cliccato: Posizione (" + x + ", " + y + ")");
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
