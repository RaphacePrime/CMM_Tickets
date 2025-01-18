package test_package;
import static org.junit.jupiter.api.Assertions.*;

import classes_package.Evento;
import database_package.AdminEventsDatabase;
import database_package.Database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAdminEventsDatabase {
   // private AdminEventsDatabase adminEventsDatabase;

    

    @Test
    @Order(1)
    public void testGetAllEvents() throws SQLException {

        List<Evento> events = AdminEventsDatabase.getAllEvents();
        assertNotNull(events);
        
    }

    @Test
    @Order(2)
    public void testAddEvento() throws SQLException {
        Evento evento = new Evento("Concerto", Date.valueOf("2025-01-18"), "20:00", 100, true, Date.valueOf("2025-01-10"), 1);
        
        // Aggiungi l'evento al database
        boolean result = AdminEventsDatabase.addEvento(evento);
        assertTrue(result);
        
        
        
    }

    @Test
    @Order(3)
    public void testUpdateEvento() throws SQLException {
        
        // Crea un evento aggiornato
        Evento updatedEvento = new Evento("Concerto aggiornato", Date.valueOf("2025-01-19"), "21:00", 150, false, Date.valueOf("2025-01-12"), 2);
        
        // Aggiorna l'evento
        boolean result = AdminEventsDatabase.updateEvento(updatedEvento, 111); 
        assertTrue(result);

       
    }

    @Test
    @Order(4)
    public void testDeleteEvento() throws SQLException {
       
        // Elimina l'evento
        boolean result = AdminEventsDatabase.deleteEvento(111); 
        assertTrue(result);

       
    }

    

}
