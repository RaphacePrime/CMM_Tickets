package loginpackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ProgettoIngegneriaDelSoftware.Progetto.Utente;
import databasepackage.Database; 

public class Registrazione {
    public static boolean registraUtente(Utente utente) {
        String sql = "INSERT INTO utenti (username, password, codice_fiscale, admin) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, utente.getUsername());
            pstmt.setString(2, utente.getPassword());
            pstmt.setString(3, utente.getCodiceFiscale());
            pstmt.setBoolean(4, utente.isAdmin());
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
