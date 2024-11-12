package databasepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:utenti.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {

        String sql = "CREATE TABLE IF NOT EXISTS utenti (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "codice_fiscale TEXT NOT NULL UNIQUE," +
                "telefono TEXT NOT NULL UNIQUE," +
                "email TEXT NOT NULL UNIQUE,"+
                "admin BOOLEAN NOT NULL CHECK(admin IN (0, 1))" +
                ");";

        
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabella utenti creata (se non esisteva già).");
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella: " + e.getMessage());
        }
    }
}
