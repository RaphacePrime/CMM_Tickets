package test_package;
import static org.junit.jupiter.api.Assertions.*;
import classes_package.Luogo;
import database_package.LuoghiDatabase;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import java.sql.*;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestLuoghiDatabase {
    

    @Test
    @Order(1)
    public void testGetAllLuoghi() throws SQLException {
        List<Luogo> luoghi = LuoghiDatabase.getAllLuoghi();
        assertNotNull(luoghi); 
    }

    @Test
    @Order(2)
    public void testAddLuogo() throws SQLException {
        Luogo luogo = new Luogo("Colosseo", "Roma", "Piazza del Colosseo", "colosseo.jpg");
        boolean result = LuoghiDatabase.addLuogo(luogo);
        assertTrue(result);       
    }

    @Test
    @Order(3)
    public void testUpdateAndDeleteEvento() throws SQLException {
        Luogo updatedLuogo = new Luogo("Colosseo Aggiornato", "Roma", "Piazza del Colosseo", "colosseo.jpg");
        List<Luogo> evs = LuoghiDatabase.getAllLuoghi();
        int id= evs.getLast().getIdLuogo();
        boolean result = LuoghiDatabase.updateLuogo(updatedLuogo, id); 
        assertTrue(result);       
    }

    @Test
    @Order(4)
    public void testDeleteEvento() throws SQLException {
        Luogo updatedLuogo = new Luogo("Colosseo Aggiornato", "Roma", "Piazza del Colosseo", "colosseo.jpg");
        boolean result = LuoghiDatabase.deleteLuogo(updatedLuogo); 
        assertTrue(result);    
    }
    
}
