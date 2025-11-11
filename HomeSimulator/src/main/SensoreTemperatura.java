//package main;

import java.util.concurrent.ThreadLocalRandom;

/*
 * Specializzazione di Sensore
 */
public class SensoreTemperatura extends Sensore {

    public static final String NOME_SENSORE = "Temperatura";
    public static final String PREFIX_SENSORE = "T";
    private static int contaDispositivi = 1;

    private static final double TEMPERATURA_MIN = 18;
    private static final double TEMPERATURA_MAX = 27;
    
    public SensoreTemperatura(Centralina centralina) {
        super(PREFIX_SENSORE + contaDispositivi, centralina);
        synchronized (this) {
            contaDispositivi++;
        }
    }

    @Override
    public double generaValore() {
        return ThreadLocalRandom.current().nextDouble(TEMPERATURA_MIN, TEMPERATURA_MAX);
    }
    
	@Override
	public DatoSensore generaDato() {
		return new DatoSensore(generaValore(), NOME_SENSORE);
	}
}