package classespackage;

import java.util.Date;

public abstract class Evento {

	private int idEvento;
	private String nome;
	private Date data;
	private String ora;
	public Luogo luogo;
	private int numMaxBigliettiAcquistabili;
	
	
	public Evento(int idEvento, String nome, Date data, String ora,  Luogo luogo, int numMaxBigliettiAcquistabili)
	{
		this.setIdEvento(idEvento);
		this.setNome(nome);
		this.setData(data);
		this.ora=ora;
		this.luogo=luogo;
		this.setNumMaxBigliettiAcquistabili(numMaxBigliettiAcquistabili);
	}
	
	public Evento(String nome, Date data, String ora,  Luogo luogo, int numMaxBigliettiAcquistabili)
	{
		this.setNome(nome);
		this.setData(data);
		this.ora=ora;
		this.luogo=luogo;
		this.setNumMaxBigliettiAcquistabili(numMaxBigliettiAcquistabili);
	}
	


	public int getIdEvento() {
		return idEvento;
	}


	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public String getOra() {
		return ora;
	}


	public void setOra(String ora) {
		this.ora = ora;
	}


	public int getNumMaxBigliettiAcquistabili() {
		return numMaxBigliettiAcquistabili;
	}


	public void setNumMaxBigliettiAcquistabili(int numMaxBigliettiAcquistabili) {
		this.numMaxBigliettiAcquistabili = numMaxBigliettiAcquistabili;
	}
	
	
}
