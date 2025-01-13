package login_package;

import javax.swing.*;
import classes_package.Utente;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel; 
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchToRegisterButton;
    public static String usernameUtente;
    
    private ActionListener switchToRegisterAction;
    
    public LoginPanel(CardLayout cardLayout, JPanel mainPanel, ActionListener switchToRegisterAction) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;  
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        JPanel innerMainPanel = new JPanel();
        innerMainPanel.setLayout(new BoxLayout(innerMainPanel, BoxLayout.Y_AXIS));
        innerMainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        innerMainPanel.setBackground(Color.WHITE);

        setBackground(new Color(240, 240, 240)); 
        innerMainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerMainPanel.add(titleLabel);

        innerMainPanel.add(Box.createVerticalStrut(20)); 

        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerMainPanel.add(createLabeledField("Username", usernameField));

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerMainPanel.add(createLabeledField("Password", passwordField));

        innerMainPanel.add(Box.createVerticalStrut(20)); 

        // Creazione del pulsante di login
        loginButton = new JButton("Accedi");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBackground(new Color(33, 150, 243)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> loginAction());
        innerMainPanel.add(loginButton);

        // Aggiunta del Key Binding per il tasto Invio
        // Imposta l'azione di premere "Accedi" quando premi Invio nella passwordField
        passwordField.addActionListener(e -> loginButton.doClick());
        usernameField.addActionListener(e -> loginButton.doClick());

        innerMainPanel.add(Box.createVerticalStrut(10)); 

        switchToRegisterButton = new JButton("Registrati");
        switchToRegisterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToRegisterButton.setBackground(new Color(33, 150, 243));
        switchToRegisterButton.setForeground(Color.WHITE);
        switchToRegisterButton.setFocusPainted(false);
        switchToRegisterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToRegisterButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        switchToRegisterButton.setOpaque(true);
        switchToRegisterButton.setBorderPainted(false);

        innerMainPanel.add(switchToRegisterButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(innerMainPanel, gbc);

        this.switchToRegisterAction = switchToRegisterAction;  
        switchToRegisterButton.addActionListener(switchToRegisterAction);  
    }

    // Metodo per gestire l'azione di login
    private void loginAction() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Utente utente = Login.autentica(username, password);

        if (utente != null) {
            JOptionPane.showMessageDialog(this, "Benvenuto " + utente.getUsername());
            if (utente.isAdmin()) {
                cardLayout.show(mainPanel, "Admin Home");
            } else {
                cardLayout.show(mainPanel, "User Home");
                usernameUtente=utente.getUsername();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    	//cardLayout.show(mainPanel, "User Home"); //usernameUtente="Raphael";
    }

    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(5), BorderLayout.CENTER); 
        panel.add(textField, BorderLayout.SOUTH);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); 
        return panel;
    }

    public void setSwitchToRegisterAction(ActionListener action) {
        switchToRegisterButton.addActionListener(action);
    }

    public void resetFields() {
        usernameField.setText("");
        passwordField.setText("");        
    }
}
