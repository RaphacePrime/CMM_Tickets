package classes_package;

import java.sql.Time;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import login_package.Login;
public class Evento {
    private int idEvento;
    private String nome;
    private Date data; 
    private String ora;
    private int maxBigliettiAPersona;
    private boolean postoNumerato;
    private Date dataInizioVendita;
    private int idLuogo; 

    private static Logger logger = LogManager.getLogger(Evento.class);

    public Evento(int idEvento, String nome, Date data, String ora, int numMaxBigliettiAcquistabili, boolean postoNumerato, Date dataInizioVendita, int idLuogo) {
        this.idEvento = idEvento;
        this.nome = nome;
        this.data = data;
        this.ora = ora;
        this.maxBigliettiAPersona = numMaxBigliettiAcquistabili;
        this.postoNumerato = postoNumerato;
        this.dataInizioVendita = dataInizioVendita;
        this.idLuogo = idLuogo;
    }
    
    public Evento(String nome, Date data, String ora, int numMaxBigliettiAcquistabili, boolean postoNumerato, Date dataInizioVendita, int idLuogo) {
        this.idEvento = idEvento;
        this.nome = nome;
        this.data = data;
        this.ora = ora;
        this.maxBigliettiAPersona = numMaxBigliettiAcquistabili;
        this.postoNumerato = postoNumerato;
        this.dataInizioVendita = dataInizioVendita;
        this.idLuogo = idLuogo;
    }
    
    public int getIdEvento()
    {
    	return idEvento;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getMaxBigliettiAPersona() {
        return maxBigliettiAPersona;
    }
    
    public boolean getPostoNumerato() {
        return postoNumerato;
    }

    public Date getData() {
        return data;
    }
    
    public String getOra() {
        return ora;
    }
    
    public Date getDataInizioVendita() {
        return dataInizioVendita;
    }

    public int getIdLuogo() {
        return idLuogo;
    }

    @Override
    public String toString() {
        return nome + " - " + data + " " + ora;
    }
    
    public void showEvent() {
    	logger.info( idEvento+" "+nome+" "+data+" "+ora+" "+maxBigliettiAPersona+" "+postoNumerato+" "+dataInizioVendita+" "+idLuogo); 
    }
    
}
