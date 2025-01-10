package login_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classes_package.Utente;
import database_package.Database;

public class Registrazione {
    private static Logger logger = LogManager.getLogger(Registrazione.class);

    public static boolean registraUtente(Utente utente) {
        try {
            RSAUtils.generateKeys(); 
            String encryptedPassword = RSAUtils.encrypt(utente.getPassword());

            String sql = "INSERT INTO utenti (username, password, codiceFiscale, telefono, email, admin) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, utente.getUsername());
                pstmt.setString(2, encryptedPassword);
                pstmt.setString(3, utente.getCodiceFiscale());
                pstmt.setString(4, utente.getTelefono());
                pstmt.setString(5, utente.getEmail());
                pstmt.setBoolean(6, utente.isAdmin());
                pstmt.executeUpdate();
                logger.info("Registrazione avvenuta con successo!");
                return true;
            } catch (SQLException e) {
                logger.error("Errore durante la registrazione: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            logger.error("Errore durante la crittografia della password: " + e.getMessage());
            return false;
        }
    }
}
