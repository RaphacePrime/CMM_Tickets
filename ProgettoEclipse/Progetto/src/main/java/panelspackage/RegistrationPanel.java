package panelspackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import classespackage.Utente;
import loginpackage.Registrazione;

public class RegistrationPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField codiceFiscaleField;
    private JTextField telefonoField;
    private JTextField emailField;
    //private JCheckBox adminCheckBox;
    private JButton registerButton;
    private JButton switchToLoginButton;

    private ActionListener switchToLoginAction;

    public RegistrationPanel(ActionListener switchToLoginAction) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Imposta margini esterni per spaziatura

        // Pannello contenitore per centralizzare e stilizzare
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        setBackground(new Color(240, 240, 240)); // Sfondo grigio chiaro per l'effetto "card"
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo
        JLabel titleLabel = new JLabel("Registrazione");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20)); // Spazio tra titolo e campi

        // Campo Username
        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Username", usernameField));

        // Campo Password
        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Password", passwordField));

        // Campo Codice Fiscale
        codiceFiscaleField = new JTextField(15);
        codiceFiscaleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        codiceFiscaleField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Codice Fiscale", codiceFiscaleField));

        // Campo Telefono
        telefonoField = new JTextField(15);
        telefonoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        telefonoField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Telefono", telefonoField));

        // Campo Email
        emailField = new JTextField(15);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createLabeledField("Email", emailField));

        // Checkbox Admin
        /*adminCheckBox = new JCheckBox("Vuoi diventare admin?");
        adminCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminCheckBox.setBackground(Color.WHITE);
        mainPanel.add(adminCheckBox);*/
        
        mainPanel.add(Box.createVerticalStrut(20)); // Spazio tra i campi e pulsanti

        // Pulsante Registrati
        registerButton = new JButton("Registrati");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        registerButton.setBackground(new Color(33, 150, 243)); // Colore blu
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerButton.addActionListener(e -> {
			try {
				validateAndRegister();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        mainPanel.add(registerButton);

        mainPanel.add(Box.createVerticalStrut(10)); // Spazio tra pulsanti

        // Pulsante Accedi
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

        // Aggiungi il mainPanel al centro del pannello principale
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mainPanel, gbc);
        
     // Imposta l'ActionListener del bottone di switch
        this.switchToLoginAction = switchToLoginAction;  // Salva l'ActionListener per poterlo usare successivamente
        switchToLoginButton.addActionListener(switchToLoginAction);  // Aggiungi l'ActionListener al bottone
    }

    // Metodo per creare un campo con etichetta e spaziatura
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(5), BorderLayout.CENTER); // Spazio tra label e campo
        panel.add(textField, BorderLayout.SOUTH);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setOpaque(false); // Trasparente per mantenere il colore di sfondo del contenitore
        return panel;
    }

    // Funzione per validare i campi e registrare l'utente
    private void validateAndRegister() throws HeadlessException, Exception {
        resetBorders();

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String codiceFiscale = codiceFiscaleField.getText().toUpperCase();
        String telefono = telefonoField.getText();
        String email = emailField.getText();
        //boolean admin = adminCheckBox.isSelected();
        boolean admin = false; // non è piu possibile registrare un admin

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
            switchToLoginAction.actionPerformed(null); // Esegui il cambio di pannello
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
        //adminCheckBox.setSelected(false);
    }

    private void resetBorders() {
        usernameField.setBorder(UIManager.getBorder("TextField.border"));
        passwordField.setBorder(UIManager.getBorder("TextField.border"));
        codiceFiscaleField.setBorder(UIManager.getBorder("TextField.border"));
        telefonoField.setBorder(UIManager.getBorder("TextField.border"));
        emailField.setBorder(UIManager.getBorder("TextField.border"));
    }
}
