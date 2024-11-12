package ProgettoIngegneriaDelSoftware.Progetto;


public class Utente {
    private int id;
    private String username;
    private String password;
    private String codiceFiscale;
    private boolean admin;

    public Utente(int id, String username, String password, String codiceFiscale, boolean admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.codiceFiscale = codiceFiscale;
        this.admin = admin;
    }

    // Costruttore senza id per creazione utente
    public Utente(String username, String password, String codiceFiscale, boolean admin) {
        this.username = username;
        this.password = password;
        this.codiceFiscale = codiceFiscale;
        this.admin = admin;
    }

    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}
