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

public class AdminLuoghiDatabase {
    private static final Logger logger = LogManager.getLogger(AdminLuoghiDatabase.class);

    public static List<Luogo> getAllLuoghi() {
        List<Luogo> luoghi = new ArrayList<>();
        String sql = "SELECT * FROM luoghi";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("Eseguo query: " + sql);

            while (rs.next()) {
                int idLuogo = rs.getInt("idLuogo");
                String nome = rs.getString("nome");
                String città =rs.getString("citta");
                String indirizzo = rs.getString("indirizzo");
                
                
                Luogo luogo = new Luogo(idLuogo, nome, città, indirizzo);
                luoghi.add(luogo);
                logger.debug("Luogo aggiunto alla lista: " + luogo.getNome());
            }
        } catch (SQLException e) {
            logger.error("Errore durante il recupero dei luoghi: " + e.getMessage(), e);
        }
        return luoghi;
    }
    
    public static boolean addLuogo(Luogo luogo) {
        String sql = "INSERT INTO luoghi (nome, citta, indirizzo) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, luogo.getNome());
            pstmt.setString(2, luogo.getCittà());
            pstmt.setString(3, luogo.getIndirizzo());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Luogo aggiunto con successo: " + luogo.getNome());
                return true;
            } else {
                logger.warn("Nessun luogo aggiunto al database.");
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'inserimento del luogo: " + luogo.getNome() + ". Dettagli: " + e.getMessage(), e);
        }
        return false;
    }
}
