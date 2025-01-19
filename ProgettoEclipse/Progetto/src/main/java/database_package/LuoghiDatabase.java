package database_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes_package.Luogo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LuoghiDatabase {
    private static final Logger logger = LogManager.getLogger(LuoghiDatabase.class);

    public static List<Luogo> getAllLuoghi() {
        List<Luogo> luoghi = new ArrayList<>();
        String sql = "SELECT * FROM luoghi";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("DB Eseguo query: " + sql);

            while (rs.next()) {
                int idLuogo = rs.getInt("idLuogo");
                String nome = rs.getString("nome");
                String città = rs.getString("citta");
                String indirizzo = rs.getString("indirizzo");
                String nomeFile = rs.getString("nomeFile");



                Luogo luogo = new Luogo(idLuogo, nome, città, indirizzo, nomeFile);
                luoghi.add(luogo);
                logger.debug("DB Luogo aggiunto alla lista: " + luogo.getNome());
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante il recupero dei luoghi: " + e.getMessage(), e);
        }
        if (luoghi == null) {
            luoghi = new ArrayList<>(); // Restituisci una lista vuota se nulla
        }

        return luoghi;
    }

    public static boolean addLuogo(Luogo luogo) {
        String sql = "INSERT INTO luoghi (nome, citta, indirizzo, nomeFile) VALUES (?, ?, ?, ?)";

       
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, luogo.getNome());
            pstmt.setString(2, luogo.getCittà());
            pstmt.setString(3, luogo.getIndirizzo());
            pstmt.setString(4, luogo.getNomeFile());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("DB Luogo aggiunto con successo: " + luogo.getNome());
                return true;
            } else {
                logger.warn("DB Nessun luogo aggiunto al database.");
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante l'inserimento del luogo: " + luogo.getNome() + ".DB  Dettagli: " + e.getMessage(), e);
        }
        return false;
    }

    public static boolean deleteLuogo(Luogo luogo) { //quando viene eliminato un luogo, mettere il luogo di default per gli eventi che hanno quell'id luogo
        String sql = "DELETE FROM luoghi WHERE nome = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, luogo.getNome());

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("DB Luogo eliminato con successo: " + luogo.getNome());
                EventsDatabase.updateEventWithDefaultLuogo(luogo.getIdLuogo());
                return true;
            } else {
                logger.warn("DB Nessun luogo trovato con il nome specificato: " + luogo.getNome());
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante l'eliminazione del luogo: " + luogo.getNome() + ".DB  Dettagli: " + e.getMessage(), e);
        }
        return false;
    }
    
    public static boolean updateLuogo(Luogo luogo, int vecchioId) {
        String sql = "UPDATE luoghi SET nome = ?, citta = ?, indirizzo = ?, nomeFile = ? WHERE idLuogo = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, luogo.getNome());
            pstmt.setString(2, luogo.getCittà());
            pstmt.setString(3, luogo.getIndirizzo());
            pstmt.setString(4, luogo.getNomeFile());
            pstmt.setInt(5, vecchioId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("DB Luogo aggiornato con successo: " + luogo.getNome());
                return true;
            } else {
                logger.warn("DB Nessun luogo trovato con l'id specificato: " + luogo.getIdLuogo());
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante l'aggiornamento del luogo: " + luogo.getNome() + ".DB Dettagli: " + e.getMessage(), e);
        }
        return false;
    }

}
