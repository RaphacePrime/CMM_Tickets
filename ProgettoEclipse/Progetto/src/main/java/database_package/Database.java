package database_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
    
    
        /*String sql = "CREATE TABLE IF NOT EXISTS utenti (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "codiceFiscale TEXT NOT NULL UNIQUE," +
                "telefono TEXT NOT NULL UNIQUE," +
                "email TEXT NOT NULL UNIQUE,"+
                "admin BOOLEAN NOT NULL CHECK(admin IN (0, 1))" +
                ");";
        
    
        String sql = "CREATE TABLE IF NOT EXISTS luoghi (" +
        		"idLuogo INTEGER PRIMARY KEY AUTOINCREMENT," +
        		"nome TEXT NOT NULL UNIQUE, " +
        		"indirizzo TEXT NOT NULL"+
        		");";*/

        
    	
        String sql = "CREATE TABLE IF NOT EXISTS eventi (" +
                "idEvento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "data DATE NOT NULL, " +
                "ora TEXT NOT NULL, " +
                "numMaxBigliettiAcquistabili INTEGER NOT NULL, " +
                "postoNumerato BOOLEAN NOT NULL CHECK (postoNumerato IN (0, 1)), " +
                "dataInizioVendita DATE NULL, " +
                "idLuogo INTEGER NOT NULL, " +
                "FOREIGN KEY (idLuogo) REFERENCES luoghi(idLuogo)" +
                ");";

    			
        /*
    	
    	
    	String sql = "CREATE TABLE IF NOT EXISTS settori (" +
    			"idSettore INTEGER PRIMARY KEY AUTOINCREMENT, " +
    			"nome TEXT NOT NULL UNIQUE, " +
    			"prezzo NUMERIC(6,2) NOT NULL, " +
    			"postiTotali INTEGER, " +
    			"postiOccupati INTEGER" +
    			");";
    			
        
        
    	String sql = "CREATE TABLE IF NOT EXISTS posti (" +
    			"idPosto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
    			"num INTEGER " +
    			");";
       
    	
    	
    	String sql = "CREATE TABLE IF NOT EXISTS biglietti (" +
    			"idBiglietto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
    			");";
    	
    */

    			
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabella creata (se non esisteva già).");
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella: " + e.getMessage());
        }
    }
}
