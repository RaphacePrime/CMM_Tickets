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

public class AdminGetLuoghiDatabase {
    private static final Logger logger = LogManager.getLogger(AdminGetLuoghiDatabase.class);

    public static List<Luogo> getAllLuoghi() {
        List<Luogo> luoghi = new ArrayList<>();
        String sql = "SELECT * FROM luoghi";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("Eseguo query: " + sql);

            while (rs.next()) {
                int idLuogo = rs.getInt("idLuogo");
                String nome = rs.getString("nome");
                String indirizzo = rs.getString("indirizzo");
                
                Luogo luogo = new Luogo(idLuogo, nome, indirizzo);
                luoghi.add(luogo);
                logger.debug("Luogo aggiunto alla lista: " + luogo.getNome());
            }
        } catch (SQLException e) {
            logger.error("Errore durante il recupero dei luoghi: " + e.getMessage(), e);
        }
        return luoghi;
    }
}
