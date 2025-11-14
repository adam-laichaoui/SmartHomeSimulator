//package main;

import java.util.concurrent.ThreadLocalRandom;

/*
 * Specializzazione di Sensore
 */
public class SensoreMovimento extends Sensore {
    
    public static final String NOME_SENSORE = "Movimento";
    public static final String PREFIX_SENSORE = "M";
    private static int contaDispositivi = 1;

    private static final int MOVIMENTO_MIN = 0;
    private static final int MOVIMENTO_MAX = 100;
    private static final int SOGLIA_MOVIMENTO = 85;
    private static final long DURATA_SEGNALAZIONE_MS = 10000;

    private int abilitazione = 0;
    private long tempoInizioMovimento = 0;
    private boolean movimentoAttivo = false;

   public SensoreMovimento(Centralina centralina) {
        super("MOV-" + contaDispositivi, centralina, TipoSensore.MOVIMENTO);
        synchronized (SensoreMovimento.class) {
            contaDispositivi++;
        }
    }

    @Override
    public double generaValore() {
        return ThreadLocalRandom.current().nextDouble(MOVIMENTO_MIN, MOVIMENTO_MAX);
    }

    public boolean isMovimento() {
        return generaValore() >= SOGLIA_MOVIMENTO;
    }

    @Override
    public DatoSensore generaDato() {
        long oraCorrente = System.currentTimeMillis();

        if (!movimentoAttivo && isMovimento()) {
            movimentoAttivo = true;
            tempoInizioMovimento = oraCorrente;
            abilitazione = 1;
        }

        if (movimentoAttivo) {
            if (oraCorrente - tempoInizioMovimento < DURATA_SEGNALAZIONE_MS) {
                return new DatoSensore(abilitazione, NOME_SENSORE);
            } else {
            	abilitazione = 0;
                movimentoAttivo = false;
                return new DatoSensore(abilitazione, NOME_SENSORE);
            }
        }

        return new DatoSensore(abilitazione, NOME_SENSORE);
    }
}
