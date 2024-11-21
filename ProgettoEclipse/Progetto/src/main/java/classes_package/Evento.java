package classes_package;

public class Evento {
    private int idEvento;
    private String nome;
    private String data; 
    private String ora;
    private int numMaxBigliettiAcquistabili;
    private boolean postoNumerato;
    private String dataInizioVendita;
    private int idLuogo; 

    public Evento(int idEvento, String nome, String data, String ora, int numMaxBigliettiAcquistabili, boolean postoNumerato, String dataInizioVendita, int idLuogo) {
        this.idEvento = idEvento;
        this.nome = nome;
        this.data = data;
        this.ora = ora;
        this.numMaxBigliettiAcquistabili = numMaxBigliettiAcquistabili;
        this.postoNumerato = postoNumerato;
        this.dataInizioVendita = dataInizioVendita;
        this.idLuogo = idLuogo;
    }
    
    public int getId()
    {
    	return idLuogo;
    }
    
    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }
    
    public String getOra() {
        return ora;
    }

    public int getIdLuogo() {
        return idLuogo;
    }

    @Override
    public String toString() {
        return nome + " - " + data + " " + ora;
    }
}
