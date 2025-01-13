package classes_package;

public class Biglietto {

    private int idBiglietto;
    private String nomeUtilizzatore;
    private String cognomeUtilizzatore;
    int posto;
    private int idUtente;
    private int idSettore;
    private int idEvento;

    public Biglietto( String nomeUtilizzatore, String cognomeUtilizzatore, int posto, int idUtente, int idSettore, int idEvento) {
        this.idBiglietto = 0;
        this.nomeUtilizzatore = nomeUtilizzatore;
        this.cognomeUtilizzatore = cognomeUtilizzatore;
        this.posto = posto;
        this.idUtente = idUtente;
        this.idSettore = idSettore;
        this.idEvento = idEvento;
    }

    public int getIdBiglietto() { return idBiglietto; }
    public void setIdBiglietto(int idBiglietto) { this.idBiglietto = idBiglietto; }

    public String getNomeUtilizzatore() { return nomeUtilizzatore; }
    public void setNomeUtilizzatore(String nomeUtilizzatore) { this.nomeUtilizzatore = nomeUtilizzatore; }

    public String getCognomeUtilizzatore() { return cognomeUtilizzatore; }
    public void setCognomeUtilizzatore(String cognomeUtilizzatore) { this.cognomeUtilizzatore = cognomeUtilizzatore; }

    public int getPosto() { return posto; }
    public void setPosto(int posto) { this.posto = posto; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public int getIdSettore() { return idSettore; }
    public void setIdSettore(int idSettore) { this.idSettore = idSettore; }
    
    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }
}
