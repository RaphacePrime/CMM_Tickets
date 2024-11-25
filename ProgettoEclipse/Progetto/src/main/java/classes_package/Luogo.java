package classes_package;

public class Luogo {
	private int idLuogo;
	private String nome;
	private String indirizzo;
	private String città;
	private String nomeFile;
	
	
	public Luogo(int idLuogo, String nome, String indirizzo, String città, String nomeFile)
	{
		this.setIdLuogo(idLuogo);
		this.setNome(nome);
		this.setIndirizzo(indirizzo);
		this.setCittà(città);
		this.setNomeFile(nomeFile);

	}
	
	public Luogo(String nome, String indirizzo, String città, String nomeFile)
	{
		this.setNome(nome);
		this.setIndirizzo(indirizzo);
		this.setCittà(città);
		this.setNomeFile(nomeFile);
	}

	public Luogo() {
		// TODO Auto-generated constructor stub
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

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
}
