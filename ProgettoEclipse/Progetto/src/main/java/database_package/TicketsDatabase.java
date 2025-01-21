package database_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classes_package.Biglietto;

public class TicketsDatabase {
    private static final Logger logger = LogManager.getLogger(TicketsDatabase.class);

    public static List<Biglietto> getAllUserTickets(int idUser) {
        List<Biglietto> bigliettiList = new ArrayList<>();
        String query = "SELECT * FROM biglietti WHERE idAcquirente = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Biglietto biglietto = new Biglietto();
                biglietto.setIdBiglietto(rs.getInt("idBiglietto"));
                biglietto.setNomeUtilizzatore(rs.getString("nomeUtilizzatore"));
                biglietto.setCognomeUtilizzatore(rs.getString("cognomeUtilizzatore"));
                biglietto.setPosto(rs.getInt("posto"));
                biglietto.setIdSettore(rs.getInt("idSettore"));
                biglietto.setIdUtente(rs.getInt("idAcquirente"));

                bigliettiList.add(biglietto);
            }
        } catch (SQLException e) {
            logger.error("[TicketssDatabase.java] Error while fetching tickets for user with id " + idUser, e);
        }

        return bigliettiList;
    }

    public static boolean addTicket(Biglietto biglietto) {
        String query = "INSERT INTO biglietti (nomeUtilizzatore, cognomeUtilizzatore, posto, idSettore, idAcquirente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, biglietto.getNomeUtilizzatore());
            stmt.setString(2, biglietto.getCognomeUtilizzatore());
            stmt.setInt(3, biglietto.getPosto());
            stmt.setInt(4, biglietto.getIdSettore());
            stmt.setInt(5, biglietto.getIdUtente());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0)
            {
            	SectorsDatabase.ticketAcquired(biglietto.getIdSettore());
            }
            logger.info("[TicketssDatabase.java] Aggiunto biglietto al database con ID: ", biglietto.getIdBiglietto());
            return rowsAffected > 0; 

        } catch (SQLException e) {
            logger.error("[TicketssDatabase.java] Error while adding a new ticket", e);
        }

        return false;
    }


    public static boolean updateTicket(Biglietto biglietto, int vecchioId) {
        String query = "UPDATE biglietti SET nomeUtilizzatore = ?, cognomeUtilizzatore = ?, posto = ?, idSettore = ?, idAcquirente = ? WHERE idBiglietto = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, biglietto.getNomeUtilizzatore());
            stmt.setString(2, biglietto.getCognomeUtilizzatore());
            stmt.setInt(3, biglietto.getPosto());
            stmt.setInt(4, biglietto.getIdSettore());
            stmt.setInt(5, biglietto.getIdUtente());
            stmt.setInt(6, vecchioId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 

        } catch (SQLException e) {
            logger.error("[TicketssDatabase.java] Error while updating ticket with id " + vecchioId, e);
        }

        return false;
    }
}
