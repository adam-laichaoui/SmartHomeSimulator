//package main;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * Immagazzina i dati relativi ad un Sensore. Verra creata un'istanza
 *  dei dati ogni volta che si aggiornerà la Centralina
 */
public class DatoSensore {
    private final String tipo;           
    private final double valore;
    private final LocalTime timestamp;

    public DatoSensore(double valore, String tipo) {
        this.tipo = tipo;
        this.valore = valore;
        this.timestamp = LocalTime.now();
    }

    public String getTipo() {
        return tipo;
    }

    public double getValore() {
        return valore;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public String getOraFormattata() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }

    //mostrare meglio il valore double 

    
    // Restituisce il valore arrotondato a 2 cifre decimali
    public String getValoreFormattato() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(valore);
    }

    // perchè funzioni il pannellodello  storico 
       @Override
    public String toString() {
        return String.format("Valore: %.2f  (timestamp:)", valore,  timestamp);
    }
}