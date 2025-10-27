package main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class SensoreTemperatura extends Sensore{

	private static final double TEMPERATURA_MIN = 18;
	private static final double TEMPERATURA_MAX = 27;
	
	private static int contaDispositivi = 1;
	
	public SensoreTemperatura() {
		synchronized(this) {
		this.id = "T" + contaDispositivi;
		contaDispositivi+=1;
		}
	}
	

	@Override
	public double leggiDato() {
		return ThreadLocalRandom.current().nextDouble(TEMPERATURA_MIN, TEMPERATURA_MAX);
	}
	
	@Override
	public String toString() {
		return String.format("[%s] %s: TEMPERATURA = %.2f \t\t\t Val precedente: %.2f\n", oraFormattata, id, valoreLetto, ultimoValoreLetto);
	}

}
