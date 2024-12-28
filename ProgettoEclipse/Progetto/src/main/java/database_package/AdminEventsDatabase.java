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

public class AdminEventsDatabase {
    private static final Logger logger = LogManager.getLogger(AdminEventsDatabase.class);

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
                logger.info("Evento aggiunto alla lista: " + evento.getNome());
            }
        } catch (SQLException e) {
            logger.error("Errore durante il recupero degli eventi: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Errore durante il parsing delle date: " + e.getMessage(), e);
        }
        return events;
    }

    public static boolean addEvento(Evento evento) {
        String sql = "INSERT INTO eventi (nome, data, ora, maxBigliettiAPersona, postoNumerato, dataInizioVendita, idLuogo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, evento.getNome());
            pstmt.setDate(2, new java.sql.Date(evento.getData().getTime()));
            pstmt.setString(3, evento.getOra());
            pstmt.setInt(4, evento.getMaxBigliettiAPersona());
            pstmt.setBoolean(5, evento.getPostoNumerato());
            pstmt.setDate(6, new java.sql.Date(evento.getDataInizioVendita().getTime()));
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
}
