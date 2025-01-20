package database_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import classes_package.Settore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.ParseException;


public class SectorsDatabase {
    private static final Logger logger = LogManager.getLogger(SectorsDatabase.class);

    public static List<Settore> getAllSectors() throws ParseException {
        List<Settore> sectors = new ArrayList<>();
        String sql = "SELECT * FROM settori";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("[SectorsDatabase.java] Eseguo query: " + sql);

            while (rs.next()) {
                int idSettore = rs.getInt("idSettore");
                String nome = rs.getString("nome");
                Float prezzo = Float.parseFloat(rs.getString("prezzo"));
                String posizione = rs.getString("posizione");
                int anello = rs.getInt("anello");
                int postiTotali = rs.getInt("postiTotali");
                int postiAcquistati = rs.getInt("postiAcquistati");
                int idEvento = rs.getInt("idEvento");

                Settore settore = new Settore(idSettore, nome, prezzo, posizione, anello, postiTotali, postiAcquistati, idEvento);
                sectors.add(settore);
                logger.debug("[SectorsDatabase.java] Settore aggiunto alla lista: " + settore.getNome());
            }
        } catch (SQLException e) {
            logger.error("[SectorsDatabase.java] Errore durante il recupero degli eventi: " + e.getMessage(), e);
        }
        return sectors;
    }
    
    public static int getIdEvento(int idSettore) {
        String sql = "SELECT idEvento FROM settori WHERE idSettore = ?";
        int idEvento=0;

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idSettore);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idEvento = rs.getInt("idEvento");
                    logger.info("[SectorsDatabase.java] DB Recuperato idEvento: " + idEvento + " per idSettore: " + idSettore);
                } else {
                    logger.warn("[SectorsDatabase.java] DB Nessun risultato trovato per idSettore: " + idSettore);
                }
            }
        } catch (SQLException e) {
            logger.error("[SectorsDatabase.java] DB Errore durante il recupero dell'idEvento per idSettore: " + idSettore + ". Dettagli: " + e.getMessage(), e);
        }

        return idEvento;
    }

    
    public static boolean addSettore(Settore settore) {
        String sql = "INSERT INTO settori (nome, prezzo, posizione, anello, postiTotali, postiAcquistati, idEvento) VALUES (?, ?, ?, ?, ?, ?, ?)";

       
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, settore.getNome());
            pstmt.setFloat(2, settore.getPrezzo());
            pstmt.setString(3, settore.getPosizione());
            pstmt.setInt(4, settore.getAnello());
            pstmt.setInt(5, settore.getPostiTotali());
            pstmt.setInt(6, settore.getPostiAcquistati());
            pstmt.setInt(7, settore.getIdEvento());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("[SectorsDatabase.java] DB Settore aggiunto con successo: " + settore.getNome());
                return true;
            } else {
                logger.warn("[SectorsDatabase.java] DB Nessun settore aggiunto al database.");
            }
        } catch (SQLException e) {
            logger.error("[SectorsDatabase.java] DB Errore durante l'inserimento del settore: " + settore.getNome() + ".DB  Dettagli: " + e.getMessage(), e);
        }
        return false;
    }
    
    public static boolean deleteSettori(int idEvento) {
        String sql = "DELETE FROM settori WHERE idEvento = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEvento);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("[SectorsDatabase.java] DB Tutti i settori eliminati con successo per idEvento: " + idEvento);
                return true;
            } else {
                logger.warn("[SectorsDatabase.java] DB Nessun settore trovato per idEvento: " + idEvento);
            }
        } catch (SQLException e) {
            logger.error("[SectorsDatabase.java] DB Errore durante l'eliminazione dei settori per idEvento: " + idEvento + ". Dettagli: " + e.getMessage(), e);
        }
        return false;
    }

    
    public static boolean updateSettore(Settore settore, int vecchioId) {
        String sql = "UPDATE settori SET nome = ?,  WHERE idSettore = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, settore.getNome());
            pstmt.setFloat(2, settore.getPrezzo());
            pstmt.setString(3, settore.getPosizione());
            pstmt.setInt(4, settore.getAnello());
            pstmt.setInt(5, settore.getPostiTotali());
            pstmt.setInt(6, settore.getPostiAcquistati());
            pstmt.setInt(7, settore.getIdEvento());
            pstmt.setInt(8, vecchioId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("[SectorsDatabase.java] DB Settore aggiornato con successo id: " + settore.getIdEvento());
                return true;
            } else {
                logger.warn("[SectorsDatabase.java] B Nessun settore trovato con l'id specificato: " + settore.getIdSettore());
            }
        } catch (SQLException e) {
            logger.error("[SectorsDatabase.java] DB Errore durante l'aggiornamento del settore: " + settore.getIdSettore() + ".DB Dettagli: " + e.getMessage(), e);
        }
        return false;
    }

	public static boolean ticketAcquired(int idSettore) {
		String sql = "UPDATE settori SET postiAcquistati = postiAcquistati + 1 WHERE idSettore = ?;";
		try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, idSettore);
        	
        	int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("[SectorsDatabase.java] DB postiAcquistati aggiornato con successo id: " + idSettore);
                return true;
            } else {
                logger.warn("[SectorsDatabase.java] DB Nessun settore trovato con l'id specificato: " + idSettore);
            }
        } catch (SQLException e) {
            logger.error("[SectorsDatabase.java] DB Errore durante l'aggiornamento dei postiAcquistati con idSettore: " + idSettore + ".DB Dettagli: " + e.getMessage(), e);
        }
        return false;
		
	}
}
