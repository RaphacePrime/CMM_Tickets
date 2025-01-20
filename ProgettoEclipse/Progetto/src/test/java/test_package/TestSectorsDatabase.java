package test_package;

import static org.junit.jupiter.api.Assertions.assertTrue;


import java.sql.SQLException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import database_package.SectorsDatabase;

public class TestSectorsDatabase {
	@Test
    @Order(1)
    public void testTicketAcquired() throws SQLException {
        boolean result = SectorsDatabase.ticketAcquired(180);
        assertTrue(result); 
    }
}
