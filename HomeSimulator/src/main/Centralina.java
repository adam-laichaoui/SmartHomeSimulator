
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Riceve ed immagazzina i valori generati dai sensori,
 * mantiene lo storico e notifica la GUI tramite l'Observer Pattern.
 */
public class Centralina {

    // Storico completo: id sensore → lista di tutti i dati registrati
    private final Map<String, List<DatoSensore>> storicoDati = new HashMap<>();

    // Ultimi valori aggiornati di ciascun sensore
    private final Map<String, DatoSensore> ultimiValori = new HashMap<>();

    // Osservatore (la GUI MyFrame)
    private Observer osservatore;

    public Centralina(Observer osservatore) {
        this.osservatore = osservatore;
    }

    /**    
     * Aggiunge un nuovo dato allo storico e aggiorna la GUI.
     */
    public synchronized void aggiornaDato(String id, DatoSensore dato) {
        // aggiungi il dato alla lista dello storico
        storicoDati.computeIfAbsent(id, k -> new ArrayList<>()).add(dato);

        // aggiorna anche l'ultimo valore
        ultimiValori.put(id, dato);

        //  notifica la GUI con i dati aggiornati
        if (osservatore != null) {
            osservatore.update(id, new HashMap<>(ultimiValori));
        }
    }

    /**
     * Restituisce lo storico completo dei sensori (copia difensiva).
     */
    public synchronized Map<String, List<DatoSensore>> getStorico() {
        Map<String, List<DatoSensore>> copia = new HashMap<>();
        for (var entry : storicoDati.entrySet()) {
            copia.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copia;
    }

    /**
     * Restituisce gli ultimi valori di ogni sensore.
     */
    public synchronized Map<String, DatoSensore> getUltimiValori() {
        return new HashMap<>(ultimiValori);
    }
}
