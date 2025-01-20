package test_package;

import database_package.TicketsDatabase;
import classes_package.Biglietto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestTicketDatabase {

   
    // Test: Aggiungere un nuovo biglietto
    @Test
    void testAddTicket() {
        Biglietto biglietto = new Biglietto();
        biglietto.setNomeUtilizzatore("Giovanni");
        biglietto.setCognomeUtilizzatore("Rossi");
        biglietto.setPosto(10);
        biglietto.setIdSettore(2);  // Esegui il test con un idSettore valido
        biglietto.setIdUtente(1);   // Esegui il test con un idUtente valido

        boolean result = TicketsDatabase.addTicket(biglietto);

        assertTrue(result, "Il biglietto dovrebbe essere aggiunto con successo.");
    }

    // Test: Recuperare tutti i biglietti di un determinato utente
    @Test
    void testGetAllUserTickets() {
        int idUser = 1;  // Usa l'id di un utente presente nel database per il test
        List<Biglietto> biglietti = TicketsDatabase.getAllUserTickets(idUser);

        // Verifica che ci siano dei biglietti restituiti
        assertNotNull(biglietti, "La lista dei biglietti non dovrebbe essere nulla.");
        assertTrue(biglietti.size() > 0, "L'utente dovrebbe avere almeno un biglietto.");
    }

   
}
