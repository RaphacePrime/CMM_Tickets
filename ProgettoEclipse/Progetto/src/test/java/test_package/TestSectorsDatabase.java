package test_package;

import static org.junit.jupiter.api.Assertions.*;
import classes_package.Settore;
import database_package.SectorsDatabase;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestSectorsDatabase {
	int id;
    @Test
    @Order(1)
    public void testGetAllSectors() throws SQLException, ParseException {
        List<Settore> sectors = SectorsDatabase.getAllSectors();
        assertNotNull(sectors);
    }

    @Test
    @Order(2)
    public void testAddSettore() throws SQLException {
        Settore settore = new Settore("Anello 1", 50.0f, "nord", 1, 200, 0, 1);
        boolean result = SectorsDatabase.addSettore(settore);
        assertTrue(result);
    }
    
    @Test
    @Order(3)
    public void testTicketAcquired() throws SQLException, ParseException {
    	List<Settore> sectors = SectorsDatabase.getAllSectors();
    	id= sectors.getLast().getIdSettore();
        boolean result = SectorsDatabase.ticketAcquired(id);
        assertTrue(result);
    }
    
    @Test
    @Order(4)
    public void testGetIdEvento() throws SQLException, ParseException {
    	List<Settore> sectors = SectorsDatabase.getAllSectors();
    	id= sectors.getLast().getIdSettore();
        int idEvento = SectorsDatabase.getIdEvento(id);
        assertNotEquals(0, idEvento); 
    }

    @Test
    @Order(5)
    public void testDeleteSettori() throws SQLException, ParseException {
    	List<Settore> sectors = SectorsDatabase.getAllSectors();
    	id= sectors.getLast().getIdEvento();
        boolean result = SectorsDatabase.deleteSettori(id);
        assertTrue(result);
    }

}  

    
