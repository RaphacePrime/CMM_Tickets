package classes_package;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import login_package.Login;

public class Settore {

	private int idSettore;
	private String nome;
	private float prezzo;
	private String posizione;
	private int anello;
	private int postiTotali;
	public int postiAcquistati;
	public int idEvento;
	
    private static Logger logger = LogManager.getLogger(Settore.class);
	
	public Settore(int idSettore, String nome, float prezzo, String posizione, int anello, int postiTotali, int postiAcquistati, int idEvento)
	{
		this.idSettore=idSettore;
		this.nome=nome;
		this.prezzo=prezzo;
		this.posizione=posizione;
		this.anello=anello;
		this.postiTotali=postiTotali;
		this.postiAcquistati=postiAcquistati;
		this.idEvento=idEvento;
	}
	
	public Settore(String nome, float prezzo, String posizione, int anello, int postiTotali, int postiAcquistati, int idEvento)
	{
		this.nome=nome;
		this.prezzo=prezzo;
		this.posizione=posizione;
		this.anello=anello;
		this.postiTotali=postiTotali;
		this.postiAcquistati=postiAcquistati;
		this.idEvento=idEvento;
	}
	
	
	 public int getIdSettore() { return idSettore; }
	 public void setIdSettore(int idSettore) { this.idSettore = idSettore; }
	 
	 public String getNome() { return nome; }
	 public void setNome(String nome) { this.nome=nome; }
	 
	 public float getPrezzo() { return prezzo; }
	 public void setPrezzo(float prezzo) { this.prezzo=prezzo; }
	 
	 public String getPosizione() { return posizione; }
	 public void setPosizione(String posizione) { this.posizione=posizione; }
	 
	 public int getAnello() { return anello; }
	 public void setAnello(int anello) { this.anello=anello; }
	 
	 public int getPostiTotali() { return postiTotali; }
	 public void setPostiTotali(int postiTotali) { this.postiTotali=postiTotali; }
	 
	 public int getPostiAcquistati() { return postiAcquistati; }
	 public void addPostiAcquistati() { this.postiAcquistati++; }
	 
	 public int getIdEvento() { return idEvento; }
	 public void setIdEvento(int idEvento) { this.idEvento=idEvento; }
	 
	 public void showLoggerSettore() {
	    	logger.info("Settore: "+this.posizione+ ", "+String.valueOf(this.anello)); 
	    }
	 
	 public String showSettore() {
	    	return this.getNome()+ " "+ this.getPosizione()+ ", anello "+String.valueOf(this.anello); 
	    }
	 
}
