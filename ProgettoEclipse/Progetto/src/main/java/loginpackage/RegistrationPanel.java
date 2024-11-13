package loginpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import ProgettoIngegneriaDelSoftware.Progetto.Utente;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegistrationPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField codiceFiscaleField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JCheckBox adminCheckBox;
    private JButton registerButton;
    private JButton switchToLoginButton;
    
    private ActionListener switchToLoginAction;

    public RegistrationPanel(ActionListener switchToLoginAction) {
        setLayout(new GridLayout(8, 2, 5, 5));

        // Configurazione dei campi di input
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Codice Fiscale:"));
        codiceFiscaleField = new JTextField();
        add(codiceFiscaleField);

        add(new JLabel("Telefono:"));
        telefonoField = new JTextField();
        add(telefonoField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Admin:"));
        adminCheckBox = new JCheckBox("Vuoi diventare admin?");
        add(adminCheckBox);

        
        // Bottone di registrazione con listener per la validazione dei campi
        registerButton = new JButton("Registrati");
        registerButton.addActionListener(e -> validateAndRegister());
        add(registerButton);

        // Bottone per passare alla schermata di login
        switchToLoginButton = new JButton("Accedi");
        add(switchToLoginButton);

        // Imposta l'ActionListener del bottone di switch
        this.switchToLoginAction = switchToLoginAction;  // Salva l'ActionListener per poterlo usare successivamente
        switchToLoginButton.addActionListener(switchToLoginAction);  // Aggiungi l'ActionListener al bottone
    }

    // Metodo per impostare l'azione del bottone di switch
    public void setSwitchToLoginAction(ActionListener action) {
        switchToLoginButton.addActionListener(action);
    }

    // Funzione per validare i campi e registrare l'utente
    private void validateAndRegister() {
        // Ripristina i bordi dei campi al colore predefinito
        resetBorders();

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String codiceFiscale = codiceFiscaleField.getText().toUpperCase();
        String telefono = telefonoField.getText();
        String email = emailField.getText();
        boolean admin = adminCheckBox.isSelected();

        String errorMessage = "";

        // Validazione del campo password
        if (password.length() < 8) {
            errorMessage += "- La password deve essere di almeno 8 caratteri.\n";
            passwordField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        // Validazione del campo codice fiscale
        if (codiceFiscale.length() != 16) {
            errorMessage += "- Il codice fiscale deve essere di 16 caratteri.\n";
            codiceFiscaleField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        // Validazione del campo telefono
        if (telefono.length() != 10) {
            errorMessage += "- Il numero di telefono deve essere di 10 cifre.\n";
            telefonoField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        // Validazione del campo email
        if (!isEmailValid(email)) {
            errorMessage += "- L'email non è valida.\n";
            emailField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        // Se c'è un errore, mostra un messaggio e termina
        if (!errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessage, "Errore di registrazione", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crea un nuovo oggetto Utente e tenta di registrarlo
        Utente utente = new Utente(username, password, codiceFiscale, telefono, email, admin);
        if (Registrazione.registraUtente(utente)) {
            JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!");
            resetFields();
            // Passa al pannello di login dopo una registrazione riuscita
            switchToLoginAction.actionPerformed(null);  // Esegui il cambio di pannello
        } else {
            JOptionPane.showMessageDialog(this, "Username già in uso", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Funzione per validare il formato dell'email
    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Funzione per resettare i campi di testo
    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
        codiceFiscaleField.setText("");
        telefonoField.setText("");
        emailField.setText("");
        adminCheckBox.setSelected(false);
    }

    // Funzione per ripristinare i bordi dei campi di testo al colore predefinito
    private void resetBorders() {
        usernameField.setBorder(UIManager.getBorder("TextField.border"));
        passwordField.setBorder(UIManager.getBorder("TextField.border"));
        codiceFiscaleField.setBorder(UIManager.getBorder("TextField.border"));
        telefonoField.setBorder(UIManager.getBorder("TextField.border"));
        emailField.setBorder(UIManager.getBorder("TextField.border"));
    }
}
