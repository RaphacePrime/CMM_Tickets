package loginpackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import classespackage.Utente;
import databasepackage.Database; 

public class Registrazione {
    public static boolean registraUtente(Utente utente) {
        String sql = "INSERT INTO utenti (username, password, codiceFiscale, telefono, email, admin) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, utente.getUsername());
            pstmt.setString(2, utente.getPassword());
            pstmt.setString(3, utente.getCodiceFiscale());
            pstmt.setString(4, utente.getTelefono());
            pstmt.setString(5, utente.getEmail());
            pstmt.setBoolean(6, utente.isAdmin());
            pstmt.executeUpdate();
            System.out.println("Registrazione avvenuta con successo!");
            return true;
        } catch (SQLException e) {
            System.out.println("Errore durante la registrazione: " + e.getMessage());
            System.out.println("Username già in uso");
            return false;
        }
    }
}
