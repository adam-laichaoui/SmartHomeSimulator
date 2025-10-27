package main;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class Sensore implements Runnable{
	
	
	protected boolean stato = true;
	protected double valoreLetto;
	protected String id;
	protected double ultimoValoreLetto;
	protected LocalTime timestamp;
	protected String oraFormattata;
	
	@Override
    public void run() {
		if(isAcceso()) {
        for(int i = 0; i < 5; ++i) {
        	ultimoValoreLetto = valoreLetto;
            valoreLetto = leggiDato();
            timestamp = LocalTime.now();
            DateTimeFormatter formattatore = DateTimeFormatter.ofPattern("HH:mm:ss");
            oraFormattata = timestamp.format(formattatore);
            System.out.printf(toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        }
    }
	
	
	public void accendi() {
		this.stato = true;
	}
	
	public void spegni() {
		this.stato = false;
	}
	
	public boolean isAcceso() {
		return stato == true;
	}
	
	public abstract double leggiDato();
	
}
