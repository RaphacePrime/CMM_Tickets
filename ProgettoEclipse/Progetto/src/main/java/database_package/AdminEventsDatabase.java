package database_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.util.Date;
import classes_package.Evento;
import classes_package.Luogo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AdminEventsDatabase {
    private static final Logger logger = LogManager.getLogger(AdminEventsDatabase.class);

    public static List<Evento> getAllEvents() throws ParseException {
        List<Evento> events = new ArrayList<>();
        String sql = "SELECT * FROM eventi";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("Eseguo query: " + sql);

            while (rs.next()) {
                int idEvento = rs.getInt("idEvento");
                String nome = rs.getString("nome");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd"); // Definisci il formato
                Date data = sdf.parse(rs.getString("data"));
                String ora = rs.getString("ora");
                int numMaxBigliettiAcquistabili = rs.getInt("maxBigliettiAPersona");
                boolean postoNumerato = rs.getBoolean("postoNumerato");
                //String dataInizioVendita = rs.getString("dataInizioVendita");
                Date dataInizioVendita = sdf.parse(rs.getString("dataInizioVendita"));
                int idLuogo = rs.getInt("idLuogo");

                Evento evento = new Evento(idEvento, nome, data, ora, numMaxBigliettiAcquistabili, postoNumerato, dataInizioVendita, idLuogo);
                events.add(evento);
                logger.debug("Evento aggiunto alla lista: " + evento.getNome());
            }
        } catch (SQLException e) {
            logger.error("Errore durante il recupero degli eventi: " + e.getMessage(), e);
        }
        return events;
    }
    
    public static boolean addEvento(Evento evento) {
        String sql = "INSERT INTO eventi (nome, data, ora, maxBigliettiAPersona, postoNumerato, dataInizioVendita, idLuogo) VALUES (?, ?, ?, ?, ?, ?)";

       
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, evento.getNome());
            pstmt.setDate(2, (java.sql.Date) evento.getData());
            pstmt.setString(3, evento.getOra());
            pstmt.setInt(4, evento.getMaxBigliettiAPersona());
            pstmt.setBoolean(5, evento.getPostoNumerato());
            pstmt.setDate(6, (java.sql.Date) evento.getDataInizioVendita());
            pstmt.setInt(7, evento.getIdLuogo());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("DB Evento aggiunto con successo: " + evento.getNome());
                return true;
            } else {
                logger.warn("DB Nessun evento aggiunto al database.");
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante l'inserimento dell' evento: " + evento.getNome() + ".DB  Dettagli: " + e.getMessage(), e);
        }
        return false;
    }
    
    public static boolean deleteEvento(Evento evento) {
        String sql = "DELETE FROM evento WHERE idEvento = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, evento.getIdEvento());

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("DB Evento eliminato con successo: " + evento.getNome());
                return true;
            } else {
                logger.warn("DB Nessun evento trovato con l' id specificato: " + evento.getIdEvento());
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante l'eliminazione dell' evento id: " + evento.getIdEvento() + ".DB  Dettagli: " + e.getMessage(), e);
        }
        return false;
    }
    
    public static boolean updateEvento(Evento evento, int vecchioId) {
        String sql = "UPDATE eventi SET nome = ?, data = ?, ora = ?, maxBigliettiAPersona = ?, postoNumerato= ?, dataInizioVendita=? WHERE idEvento = ?";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, evento.getNome());
            pstmt.setDate(2, (java.sql.Date) evento.getData());
            pstmt.setString(3, evento.getOra());
            pstmt.setInt(4, evento.getMaxBigliettiAPersona());
            pstmt.setBoolean(5, evento.getPostoNumerato());
            pstmt.setDate(6, (java.sql.Date) evento.getDataInizioVendita());
            pstmt.setInt(7, evento.getIdLuogo());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("DB Evento aggiornato con successo id: " + evento.getIdEvento());
                return true;
            } else {
                logger.warn("DB Nessun evento trovato con l'id specificato: " + evento.getIdEvento());
            }
        } catch (SQLException e) {
            logger.error("DB Errore durante l'aggiornamento dell' evento: " + evento.getIdEvento() + ".DB Dettagli: " + e.getMessage(), e);
        }
        return false;
    }
}
