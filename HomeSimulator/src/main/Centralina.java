//package main;

import java.util.*;

/*
 * Riceve ed immagazzina i valori generati dai vari sensori, risolvendo la concorrenza.
 * Notifica la GUI tramite Observer Pattern il fatto che ci siano dei dati aggiornati.
 */
public class Centralina {

    // Mantiene lo storico completo dei valori di ogni sensore
    private final Map<String, List<DatoSensore>> storicoSensori = new HashMap<>();

    private Observer osservatore;

    public Centralina(Observer osservatore) {
        this.osservatore = osservatore;
    }

    //  Aggiunge un nuovo dato allo storico e notifica la GUI
    public synchronized void aggiornaDato(String id, DatoSensore dato) {
        // aggiungi il dato alla lista del sensore, creando la lista se non esiste
        storicoSensori.computeIfAbsent(id, k -> new ArrayList<>()).add(dato);

        // costruisci una mappa temporanea con SOLO l'ultimo valore di ogni sensore
        Map<String, DatoSensore> ultimiValori = new HashMap<>();
        for (Map.Entry<String, List<DatoSensore>> e : storicoSensori.entrySet()) {
            List<DatoSensore> lista = e.getValue();
            if (!lista.isEmpty()) {
                ultimiValori.put(e.getKey(), lista.get(lista.size() - 1));
            }
        }

        // notifica la GUI con gli ultimi valori
        osservatore.update(id, ultimiValori);
    }

    // Restituisce una copia di tutto lo storico dei sensori
    public synchronized Map<String, List<DatoSensore>> getStorico() {
        Map<String, List<DatoSensore>> copia = new HashMap<>();
        for (var entry : storicoSensori.entrySet()) {
            copia.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copia;
    }

    // Restituisce solo gli ultimi valori (per compatibilità)
    public synchronized Map<String, DatoSensore> getUltimiValori() {
        Map<String, DatoSensore> mappa = new HashMap<>();
        for (var entry : storicoSensori.entrySet()) {
            List<DatoSensore> lista = entry.getValue();
            if (!lista.isEmpty()) {
                mappa.put(entry.getKey(), lista.get(lista.size() - 1));
            }
        }
        return mappa;
    }
}
