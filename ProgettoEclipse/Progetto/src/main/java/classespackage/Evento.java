package classespackage;

import java.util.Date;

public abstract class Evento {

	private int idEvento;
	private String nome;
	private Date data;
	private Date dataInizioVendita;
	private String ora;
	public Luogo luogo;
	private int numMaxBigliettiAcquistabili;
	private boolean postoNumerato;
	
	
	public Evento(int idEvento, String nome, Date data, Date dataInizioVendita, String ora,  Luogo luogo, int numMaxBigliettiAcquistabili, boolean postoNumerato)
	{
		this.setIdEvento(idEvento);
		this.setNome(nome);
		this.setData(data);
		this.dataInizioVendita=dataInizioVendita;
		this.ora=ora;
		this.luogo=luogo;
		this.setNumMaxBigliettiAcquistabili(numMaxBigliettiAcquistabili);
		this.postoNumerato=postoNumerato;
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
	
	public Date getDataInizioVendita() {
		return dataInizioVendita;
	}


	public void setDataInizioVendita(Date dataInizioVendita) {
		this.dataInizioVendita = dataInizioVendita;
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
	
	public boolean getPostoNumerato() {
		return postoNumerato;
	}


	public void setPostoNumerato(boolean postoNumerato) {
		this.postoNumerato=postoNumerato;
	}
	
	
}
