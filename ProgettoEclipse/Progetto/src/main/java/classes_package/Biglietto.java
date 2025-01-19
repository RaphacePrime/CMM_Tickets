package classes_package;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import database_package.EventsDatabase;
import database_package.LuoghiDatabase;
import database_package.SectorsDatabase;
import userpanels_package.UserDetailsEventPanel;

public class Biglietto {

    private int idBiglietto;
    private String nomeUtilizzatore;
    private String cognomeUtilizzatore;
    private int posto;
    private int idUtente;
    private int idSettore;
    private static final Logger logger = LogManager.getLogger(Biglietto.class);

    public Biglietto( String nomeUtilizzatore, String cognomeUtilizzatore, int posto, int idUtente, int idSettore, int idEvento) {
        this.idBiglietto = 0;
        this.nomeUtilizzatore = nomeUtilizzatore;
        this.cognomeUtilizzatore = cognomeUtilizzatore;
        this.posto = posto;
        this.idUtente = idUtente;
        this.idSettore = idSettore;
    }

    public Biglietto() {
		
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
    
    public int getIdEvento() {
        int idEvento = 0; // Valore predefinito, nel caso in cui non venga trovato alcun settore.
        List<Settore> sectors;
        
        try {
            // Recupero la lista di tutti i settori utilizzando il metodo getAllSectors.
            sectors = SectorsDatabase.getAllSectors();

            // Itero sulla lista per trovare il settore corrispondente all'idSettore dell'oggetto corrente.
            for (Settore settore : sectors) {
                if (settore.getIdSettore() == this.getIdSettore()) {
                    idEvento = settore.getIdEvento();
                    break; // Esco dal ciclo appena trovato il settore corrispondente.
                }
            }
        } catch (ParseException e) {
            logger.error("Errore durante il recupero dei settori: " + e.getMessage(), e);
        }

        return idEvento;
    }


    
    
}
