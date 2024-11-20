package classes_package;

public class Evento {
    private int idEvento;
    private String nome;
    private String data; // Data in formato String (ad esempio, "2024-11-19")
    private String ora;
    private int numMaxBigliettiAcquistabili;
    private boolean postoNumerato;
    private String dataInizioVendita;
    private int idLuogo; // Riferimento a un luogo

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

    public String getLuogo() {
        // Restituisce solo l'idLuogo, l'interpretazione completa avverrà nel pannello
        return "Luogo ID: " + idLuogo;
    }

    @Override
    public String toString() {
        return nome + " - " + data + " " + ora;
    }
}
