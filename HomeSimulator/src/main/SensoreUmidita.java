
import java.util.concurrent.ThreadLocalRandom;

/**
 * Sensore di Umidità relativa (0–100%)
 */
public class SensoreUmidita extends Sensore {

    public static final String NOME_SENSORE = "Umidità";
    private static int contaDispositivi = 1;

    // Limiti realistici di umidità
    private static final double UMID_MIN = 0.0;
    private static final double UMID_MAX = 100.0;

    /**
     * Costruttore
     * @param centralina la centralina che riceverà i dati del sensore
     */
    public SensoreUmidita(Centralina centralina) {
        super("UMID-" + contaDispositivi, centralina, TipoSensore.UMIDITA);
        synchronized (SensoreUmidita.class) {
            contaDispositivi++;
        }
    }

    /**
     * Genera un valore casuale di umidità relativa tra 0% e 100%
     */
    @Override
    public double generaValore() {
        return ThreadLocalRandom.current().nextDouble(UMID_MIN, UMID_MAX);
    }

    /**
     * Crea un oggetto DatoSensore contenente il valore corrente
     */
    @Override
    public DatoSensore generaDato() {
        return new DatoSensore(generaValore(), NOME_SENSORE);
    }
}
