package loginpackage;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classespackage.Utente;
import databasepackage.Database;

public class Login {
    public static Utente autentica(String username, String password) {
        String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";
        //String decryptedPassword=RSAUtils.decrypt(password);
    	
        //if(decryptedPassword.equals(password)) {
        	try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String codiceFiscale = rs.getString("codiceFiscale");
                String telefono= rs.getString("telefono");
                String email=rs.getString("email");
                boolean admin = rs.getBoolean("admin");
                return new Utente(id, username, password, codiceFiscale, telefono, email, admin);
            } else {
                System.out.println("Autenticazione fallita: credenziali errate.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'autenticazione: " + e.getMessage());
            return null;
        }
        }
        //else {
        	//return null;
        //}
        
    }

