package classes_package;

public class Utente {
    private int id;
    private String username;
    private String password;
    private String codiceFiscale;
    private String telefono;
    private String email;
    private boolean admin;

    public Utente(int id, String username, String password, String codiceFiscale, String telefono, String email,  boolean admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.codiceFiscale = codiceFiscale;
        this.telefono= telefono;
        this.email=email;
        this.admin = admin;
    }
    public Utente(String username, String password, String codiceFiscale, String telefono, String email, boolean admin) {
        this.username = username;
        this.password = password;
        this.codiceFiscale = codiceFiscale;
        this.telefono=telefono;
        this.email=email;
        this.admin = admin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}
