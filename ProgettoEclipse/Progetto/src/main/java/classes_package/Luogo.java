package classes_package;

public class Luogo {
	private int idLuogo;
	private String nome;
	private String indirizzo;
	
	
	public Luogo(int idLuogo, String nome, String indirizzo)
	{
		this.setIdLuogo(idLuogo);
		this.setNome(nome);
		this.setIndirizzo(indirizzo);
	}
	
	public Luogo(String nome, String indirizzo)
	{
		this.setNome(nome);
		this.setIndirizzo(indirizzo);
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
}
