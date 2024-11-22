package classes_package;

public class Luogo {
	private int idLuogo;
	private String nome;
	private String indirizzo;
	private String città;
	
	
	public Luogo(int idLuogo, String nome, String indirizzo, String città)
	{
		this.setIdLuogo(idLuogo);
		this.setNome(nome);
		this.setIndirizzo(indirizzo);
		this.setCittà(città);

	}
	
	public Luogo(String nome, String indirizzo, String città)
	{
		this.setNome(nome);
		this.setIndirizzo(indirizzo);
		this.setCittà(città);
	}

	public int getIdLuogo() {
		return idLuogo;
	}

	public void setIdLuogo(int idLuogo) {
		this.idLuogo = idLuogo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCittà() {
		return città;
	}

	public void setCittà(String città) {
		this.città = città;
	}
}
