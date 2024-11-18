package login_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classes_package.Utente;
import database_package.Database;

public class Login {
    public static Utente autentica(String username, String password) {
        try {
            RSAUtils.generateKeys(); 

            String sql = "SELECT * FROM utenti WHERE username = ?";

            try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String encryptedPassword = rs.getString("password");
                    String decryptedPassword = RSAUtils.decrypt(encryptedPassword);

                    if (decryptedPassword.equals(password)) {
                        int id = rs.getInt("id");
                        String codiceFiscale = rs.getString("codiceFiscale");
                        String telefono = rs.getString("telefono");
                        String email = rs.getString("email");
                        boolean admin = rs.getBoolean("admin");
                        return new Utente(id, username, password, codiceFiscale, telefono, email, admin);
                    } else {
                        System.out.println("Autenticazione fallita: credenziali errate.");
                        return null;
                    }
                } else {
                    System.out.println("Autenticazione fallita: nessun utente trovato.");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Errore durante l'autenticazione: " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Errore nella decrittografia della password: " + e.getMessage());
            return null;
        }
    }
}
