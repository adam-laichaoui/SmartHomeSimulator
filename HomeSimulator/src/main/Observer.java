package main;

import java.util.Map;

/*
 * Observer: Interfaccia necessaria per notificare alla GUI
 * gli aggiornamenti avvenuti nella Centralina
 */
@FunctionalInterface
public interface Observer {
    public void update(String id, Map<String, DatoSensore > dati);
}