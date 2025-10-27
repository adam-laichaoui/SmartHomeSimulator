package main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class SensoreLuce extends Sensore{

	private static final double LUMINOSITA_MIN = 300;
	private static final double LUMINOSITA_MAX = 600;
	
	private static int contaDispositivi = 1;
	
	public SensoreLuce() {
		synchronized(this) {
		this.id = "L" + contaDispositivi;
		contaDispositivi+=1;
		}
	}
	
	@Override
	public double leggiDato() {
		return ThreadLocalRandom.current().nextDouble(LUMINOSITA_MIN, LUMINOSITA_MAX);
	}
	
	
	@Override
	public String toString() {
		return String.format("[%s] %s: LUCE = %.2f \t\t\t\t Val precedente: %.2f\n", oraFormattata, id, valoreLetto, ultimoValoreLetto);
	}

}
