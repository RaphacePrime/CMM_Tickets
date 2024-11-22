package database_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes_package.Evento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminEventsDatabase {
    private static final Logger logger = LogManager.getLogger(AdminEventsDatabase.class);

    public static List<Evento> getAllEvents() {
        List<Evento> events = new ArrayList<>();
        String sql = "SELECT * FROM eventi";

        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logger.info("Eseguo query: " + sql);

            while (rs.next()) {
                int idEvento = rs.getInt("idEvento");
                String nome = rs.getString("nome");
                String data = rs.getString("data");
                String ora = rs.getString("ora");
                int numMaxBigliettiAcquistabili = rs.getInt("numMaxBigliettiAcquistabili");
                boolean postoNumerato = rs.getBoolean("postoNumerato");
                String dataInizioVendita = rs.getString("dataInizioVendita");
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
}
