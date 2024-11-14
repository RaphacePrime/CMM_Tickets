package classespackage;

import java.util.Date;

public class EventoStadio extends Evento {
	
public EventoStadio(int idEvento, String nome, Date data, String ora,  Luogo luogo, int numMaxBigliettiAcquistabili) {
		
		super(idEvento, nome, data, ora, luogo, numMaxBigliettiAcquistabili);
		
	}
}
