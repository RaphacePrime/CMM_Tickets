package login_package;

import javax.swing.*;

import classes_package.Utente;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegistrationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField codiceFiscaleField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton switchToLoginButton;

    private ActionListener switchToLoginAction;

    public RegistrationPanel(ActionListener switchToLoginAction) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        setBackground(new Color(240, 240, 240)); 
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        JLabel titleLabel = new JLabel("Registrazione");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20)); 

        
        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Username", usernameField));

        
        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Password", passwordField));

        
        codiceFiscaleField = new JTextField(15);
        codiceFiscaleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        codiceFiscaleField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Codice Fiscale", codiceFiscaleField));

        
        telefonoField = new JTextField(15);
        telefonoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        telefonoField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Telefono", telefonoField));

        
        emailField = new JTextField(15);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Email", emailField));
        
        mainPanel.add(Box.createVerticalStrut(20)); 

        
        registerButton = new JButton("Registrati");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        registerButton.setBackground(new Color(33, 150, 243)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerButton.addActionListener(e -> {
			try {
				validateAndRegister();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        mainPanel.add(registerButton);

        mainPanel.add(Box.createVerticalStrut(10)); 

        
        switchToLoginButton = new JButton("Accedi");
        switchToLoginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        switchToLoginButton.setBackground(new Color(33, 150, 243));
        switchToLoginButton.setForeground(Color.WHITE);
        switchToLoginButton.setFocusPainted(false);
        switchToLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToLoginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        switchToLoginButton.addActionListener(switchToLoginAction);
        switchToLoginButton.setOpaque(true);
        switchToLoginButton.setBorderPainted(false);
        mainPanel.add(switchToLoginButton);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainPanel, gbc);
        
     
        this.switchToLoginAction = switchToLoginAction;  
        switchToLoginButton.addActionListener(switchToLoginAction);  
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

    
    private void validateAndRegister() throws HeadlessException, Exception {
        resetBorders();

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String codiceFiscale = codiceFiscaleField.getText().toUpperCase();
        String telefono = telefonoField.getText();
        String email = emailField.getText();
        
        boolean admin = false; 

        String errorMessage = "";

        if (password.length() < 8) {
            errorMessage += "- La password deve essere di almeno 8 caratteri.\n";
            passwordField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        if (codiceFiscale.length() != 16) {
            errorMessage += "- Il codice fiscale deve essere di 16 caratteri.\n";
            codiceFiscaleField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        if (telefono.length() != 10) {
            errorMessage += "- Il numero di telefono deve essere di 10 cifre.\n";
            telefonoField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        if (!isEmailValid(email)) {
            errorMessage += "- L'email non è valida.\n";
            emailField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        if (!errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessage, "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utente utente = new Utente(username, password, codiceFiscale, telefono, email, admin);
        if (Registrazione.registraUtente(utente)) {
            JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!");
            resetFields();
            switchToLoginAction.actionPerformed(null); 
        } else {
            JOptionPane.showMessageDialog(this, "Username già in uso", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
        codiceFiscaleField.setText("");
        telefonoField.setText("");
        emailField.setText("");
        
    }

    private void resetBorders() {
        usernameField.setBorder(UIManager.getBorder("TextField.border"));
        passwordField.setBorder(UIManager.getBorder("TextField.border"));
        codiceFiscaleField.setBorder(UIManager.getBorder("TextField.border"));
        telefonoField.setBorder(UIManager.getBorder("TextField.border"));
        emailField.setBorder(UIManager.getBorder("TextField.border"));
    }
}
