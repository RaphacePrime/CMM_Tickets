package classes_package;

public class Settore {

	private int idSettore;
	private String nome;
	private float prezzo;
	private int postiTotali;
	public int postiAcquistati;
	public Evento evento;
	
	public Settore(int idSettore, String nome, float prezzo, int postiTotali, int postiAcquistati, Evento evento)
	{
		this.idSettore=idSettore;
		this.nome=nome;
		this.prezzo=prezzo;
		this.postiTotali=postiTotali;
		this.postiAcquistati=postiAcquistati;
		this.evento=evento;
	}
	
	public Settore(String nome, float prezzo, int postiTotali, int postiAcquistati, Evento evento)
	{
		this.nome=nome;
		this.prezzo=prezzo;
		this.postiTotali=postiTotali;
		this.postiAcquistati=postiAcquistati;
		this.evento=evento;
	}
	
	
	 public int getIdSettore() { return idSettore; }
	 public void setIdSettore(int idSettore) { this.idSettore = idSettore; }
	 
	 public String getNome() { return nome; }
	 public void setNome(String nome) { this.nome=nome; }
	 
	 public float getPrezzo() { return prezzo; }
	 public void setPrezzo(float prezzo) { this.prezzo=prezzo; }
	 
	 public int getPostiTotali() { return postiTotali; }
	 public void setPostiTotali(int postiTotali) { this.postiTotali=postiTotali; }
	 
	 public int getPostiAcquistati() { return postiAcquistati; }
	 public void addPostiAcquistati() { this.postiAcquistati++; }
	 
}
