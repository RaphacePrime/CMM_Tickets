package loginpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import loginpackage.Login;
import ProgettoIngegneriaDelSoftware.Progetto.Utente;
import framespackage.AdminFrame;
import framespackage.UtenteFrame;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchToRegisterButton;

    public LoginPanel() {
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Accedi");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Utente utente = Login.autentica(username, password);
            if (utente != null) {
                JOptionPane.showMessageDialog(this, "Benvenuto " + utente.getUsername());
                if (utente.isAdmin()) {
                    new AdminFrame().setVisible(true);
                } else {
                    new UtenteFrame().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(loginButton);

        switchToRegisterButton = new JButton("Registrati");
        add(switchToRegisterButton);
    }

    public void setSwitchToRegisterAction(ActionListener action) {
        switchToRegisterButton.addActionListener(action);
    }
}
