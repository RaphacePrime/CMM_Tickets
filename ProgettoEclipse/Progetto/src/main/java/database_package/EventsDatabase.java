package database_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import classes_package.Evento;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventsDatabase {
    private static final Logger logger = LogManager.getLogger(EventsDatabase.class);

    public static List<Evento> getAllEvents() {
        List<Evento> events = new ArrayList<>();
        String sql = "SELECT * FROM eventi";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("Eseguo query: " + sql);

            while (rs.next()) {
                int idEvento = rs.getInt("idEvento");
                String nome = rs.getString("nome");

                // Recupero e parsing della colonna data
                Date data = null;
                String dataString = rs.getString("data");
                if (dataString != null) {
                    data = dateFormat.parse(dataString);
                }

                String ora = rs.getString("ora");
                int numMaxBigliettiAcquistabili = rs.getInt("maxBigliettiAPersona");
                boolean postoNumerato = rs.getBoolean("postoNumerato");

                // Recupero e parsing della colonna dataInizioVendita
                Date dataInizioVendita = null;
                String dataInizioVenditaString = rs.getString("dataInizioVendita");
                if (dataInizioVenditaString != null) {
                    dataInizioVendita = dateFormat.parse(dataInizioVenditaString);
                }

                int idLuogo = rs.getInt("idLuogo");

                Evento evento = new Evento(idEvento, nome, data, ora, numMaxBigliettiAcquistabili, postoNumerato, dataInizioVendita, idLuogo);
                events.add(evento);
                //logger.info("Evento aggiunto alla lista: " + evento.getNome());
            }
        } catch (SQLException e) {
            logger.error("Errore durante il recupero degli eventi: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Errore durante il parsing delle date: " + e.getMessage(), e);
        }
        return events;
    }

   /* public static boolean addEvento(Evento evento) {
        String sql = "INSERT INTO eventi (nome, data, ora, maxBigliettiAPersona, postoNumerato, dataInizioVendita, idLuogo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, evento.getNome());
            String dataFormatted = dateFormat.format(evento.getData());
            pstmt.setString(2, dataFormatted);
            pstmt.setString(3, evento.getOra());
            pstmt.setInt(4, evento.getMaxBigliettiAPersona());
            pstmt.setBoolean(5, evento.getPostoNumerato());
            String dataInizioVenditaFormatted = dateFormat.format(evento.getDataInizioVendita());
            pstmt.setString(6, dataInizioVenditaFormatted);
            pstmt.setInt(7, evento.getIdLuogo());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("DB Evento aggiunto con successo: " + evento.getNome());
                return true;
            } else {
                logger.warn("DB Nessun evento aggiunto al database.");
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'inserimento dell'evento: " + e.getMessage(), e);
        }
        return false;
    }
    */
    public static boolean addEvento(Evento evento) {
        // SQL per verificare se esiste già un evento con lo stesso nome e data
        String checkSql = "SELECT COUNT(*) FROM eventi WHERE nome = ? AND data = ?";
        String insertSql = "INSERT INTO eventi (nome, data, ora, maxBigliettiAPersona, postoNumerato, dataInizioVendita, idLuogo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = Database.getConnection(); 
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            // Impostiamo i parametri per la query di controllo
            checkStmt.setString(1, evento.getNome());
            String dataFormatted = dateFormat.format(evento.getData());
            checkStmt.setString(2, dataFormatted);

            // Eseguiamo la query di controllo
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Se esiste già un evento con lo stesso nome e data, ritorniamo false
                    logger.warn("Evento con lo stesso nome e data già presente nel database.");
                    return false;
                }
            }

            // Procediamo con l'inserimento dell'evento se non esiste già
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, evento.getNome());
                pstmt.setString(2, dataFormatted);
                pstmt.setString(3, evento.getOra());
                pstmt.setInt(4, evento.getMaxBigliettiAPersona());
                pstmt.setBoolean(5, evento.getPostoNumerato());
                String dataInizioVenditaFormatted = dateFormat.format(evento.getDataInizioVendita());
                pstmt.setString(6, dataInizioVenditaFormatted);
                pstmt.setInt(7, evento.getIdLuogo());
                
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    logger.info("DB Evento aggiunto con successo: " + evento.getNome());
                    return true;
                } else {
                    logger.warn("DB Nessun evento aggiunto al database.");
                }
            }

        } catch (SQLException e) {
            logger.error("Errore durante l'inserimento dell'evento: " + e.getMessage(), e);
        }

        return false;
    }

    public static boolean updateEvento(Evento evento, int id) {
        String sql = "UPDATE eventi SET nome = ?, data = ?, ora = ?, maxBigliettiAPersona = ?, postoNumerato = ?, dataInizioVendita = ?, idLuogo = ? WHERE idEvento = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, evento.getNome());
            String dataFormatted = dateFormat.format(evento.getData());
            pstmt.setString(2, dataFormatted);
            pstmt.setString(3, evento.getOra());
            pstmt.setInt(4, evento.getMaxBigliettiAPersona());
            pstmt.setBoolean(5, evento.getPostoNumerato());
            String dataInizioVenditaFormatted = dateFormat.format(evento.getDataInizioVendita());
            pstmt.setString(6, dataInizioVenditaFormatted);
            pstmt.setInt(7, evento.getIdLuogo());
            pstmt.setInt(8, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("DB Evento aggiornato con successo: " + evento.getNome());
                return true;
            } else {
                logger.warn("DB Nessun evento aggiornato nel database.");
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'aggiornamento dell'evento: " + e.getMessage(), e);
        }
        return false;
    }
    
    public static boolean deleteEvento(int idEvento) {
        String sql = "DELETE FROM eventi WHERE idEvento = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEvento);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("DB Evento eliminato con successo, idEvento: " + idEvento);
                SectorsDatabase.deleteSettori(idEvento);
                return true;
            } else {
                logger.warn("DB Nessun evento trovato con id: " + idEvento);
            }
        } catch (SQLException e) {
            logger.error("Errore durante l'eliminazione dell'evento con id: " + idEvento + ": " + e.getMessage(), e);
        }
        return false;
    }

	public static boolean updateEventWithDefaultLuogo(int idLuogo) {
		String sql = "UPDATE eventi SET idLuogo = 0 WHERE idLuogo = ?";

	    try (Connection conn = Database.getConnection(); 
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Impostiamo il parametro per l'ID del luogo da aggiornare
	        pstmt.setInt(1, idLuogo);

	        // Eseguiamo l'update
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            logger.info("Aggiornamento Luogo default completato con successo. " + rowsUpdated + " eventi aggiornati.");
	            return true;
	        } else {
	            logger.warn("default luogo Nessun evento trovato con idLuogo = " + idLuogo);
	        }
	    } catch (SQLException e) {
	        logger.error("Errore durante l'aggiornamento dell'evento per inserire default luogo: " + e.getMessage(), e);
	    }
	    return false;
	}


}
