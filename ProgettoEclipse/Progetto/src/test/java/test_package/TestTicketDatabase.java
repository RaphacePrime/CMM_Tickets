package test_package;

import database_package.TicketsDatabase;
import classes_package.Biglietto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestTicketDatabase {

    @Test
    void testAddTicket() {
        Biglietto biglietto = new Biglietto();
        biglietto.setNomeUtilizzatore("Giovanni");
        biglietto.setCognomeUtilizzatore("Rossi");
        biglietto.setPosto(10);
        biglietto.setIdSettore(30);  
        biglietto.setIdUtente(1);   

        boolean result = TicketsDatabase.addTicket(biglietto);

        assertTrue(result, "Il biglietto dovrebbe essere aggiunto con successo.");
    }

    @Test
    void testGetAllUserTickets() {
        int idUser = 29; 
        List<Biglietto> biglietti = TicketsDatabase.getAllUserTickets(idUser);
        assertNotNull(biglietti, "La lista dei biglietti non dovrebbe essere nulla.");
        assertTrue(biglietti.size() > 0, "L'utente dovrebbe avere almeno un biglietto.");
    }

   
}
