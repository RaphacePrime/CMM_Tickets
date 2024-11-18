package classes_package;


public class Biglietto {

	private int idBiglietto;
	public String nomeUtilizzatore;
	public String cognomeUtilizzatore;
	public Posto posto;
	public Utente utente;
	
	
	public Biglietto(int idBiglietto, String nomeUtilizzatore, String cognomeUtilizzatore, Posto posto, Utente utente )
	{
		this.idBiglietto=idBiglietto;
		this.nomeUtilizzatore=nomeUtilizzatore;
		this.cognomeUtilizzatore=cognomeUtilizzatore;
		this.posto=posto;
		this.utente=utente;
	}
	
	public int getIdBiglietto() { return idBiglietto; }
    public void setIdBiglietto(int idBiglietto) { this.idBiglietto = idBiglietto; }
    
}
