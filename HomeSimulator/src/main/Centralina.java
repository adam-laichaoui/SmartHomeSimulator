package main;

import java.util.HashMap;
import java.util.Map;

/*
 * Riceve ed immagazzina i valori generati dai vari sensori, risolvendo la concorrenza.
 * Notifica la GUI tramite Observer Pattern il fatto che ci siano dei dati aggiornati.
 */
public class Centralina {
	
	private final Map<String, DatoSensore> datiSensori = new HashMap<>();

	private Observer osservatore = new Stampante();
	
	public synchronized void aggiornaDato(String id, DatoSensore dato) {
	        datiSensori.put(id, dato);
	        osservatore.update(id, datiSensori);
	}
}