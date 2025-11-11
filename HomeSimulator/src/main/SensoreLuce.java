//package main;

import java.util.concurrent.ThreadLocalRandom;

/*
 * Specializzazione di Sensore
 */
public class SensoreLuce extends Sensore {

    public static final String NOME_SENSORE = "Luce";
    public static final String PREFIX_SENSORE = "L";
    private static int contaDispositivi = 1;

    private static final double LUMINOSITA_MIN = 300;
    private static final double LUMINOSITA_MAX = 600;
    
    public SensoreLuce(Centralina centralina) {
        super(PREFIX_SENSORE + contaDispositivi, centralina);
        synchronized (this) {
            contaDispositivi++;
        }
    }

    @Override
    public double generaValore() {
        return ThreadLocalRandom.current().nextDouble(LUMINOSITA_MIN, LUMINOSITA_MAX);
    }

	@Override
	public DatoSensore generaDato() {
		return new DatoSensore(generaValore(), NOME_SENSORE);
	}
}