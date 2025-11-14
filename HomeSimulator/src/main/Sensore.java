import java.util.Random;

/**
 * Superclasse di tutti i Sensori: genera valori casuali
 * e li invia periodicamente alla Centralina.
 */
public abstract class Sensore extends Thread {

    private static final String MSG_ERR = "Errore: ";

    private final String id;
    private boolean attivo = true;//i sensori partono accesi di default
    private Centralina centralina;

    // Nome personalizzabile (per la GUI)
    private String nomePersonalizzato;
    private final  TipoSensore tipo;

    // Costruttore
    public Sensore(String id, Centralina centralina, TipoSensore tipo) {
        this.id = id;
        this.centralina = centralina;
        this.tipo =tipo;
    }



    // Metodi astratti da implementare nei sensori specifici
    public abstract double generaValore();

    public abstract DatoSensore generaDato();

    // Invia il dato generato alla centralina
    private void inviaDati() {
        if (centralina != null) {
            centralina.aggiornaDato(id, generaDato());
        }
    }

    /**
     * Thread principale del sensore: genera e invia dati
     * finché è attivo. Se viene "spento", si mette in attesa
     * finché non viene riacceso.
     */
    @Override
    public void run() {
        Random random = new Random();

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                while (!attivo) {
                    try {
                        wait(); //  sospende il thread finché non viene riacceso
                    } catch (InterruptedException e) {
                        System.out.println(MSG_ERR + "interrotto: " + e.getMessage());
                        return; // esce pulitamente
                    }
                }
            }

            // genera e invia il dato
            inviaDati();

            // attesa casuale fra 800 e 1800 ms
            int randomSleep = 800 + random.nextInt(1001);
            try {
                Thread.sleep(randomSleep);
            } catch (InterruptedException e) {
                System.out.println(MSG_ERR + "sleep interrotto: " + e.getMessage());
                return; // esce dal thread in modo sicuro
            }
        }
    }

    // Accende il sensore e risveglia il thread
    public synchronized void accendi() {
        attivo = true;
        notifyAll(); // risveglia il thread se era in attesa
    }

    // Spegne il sensore
    public synchronized void spegni() {
        attivo = false;
    }

    // Ritorna lo stato attuale
    public synchronized boolean isAcceso() {
        return attivo;
    }

    // Getter e setter per GUI

    public String getIdSensore() {
        return id;
    }

    public void setNomePersonalizzato(String nome) {
        this.nomePersonalizzato = nome;
    }

    public String getNomePersonalizzato() {
        return nomePersonalizzato != null ? nomePersonalizzato : id;
    }

    //facilitarne l'utlizzo dei nomi di tipo sensore
    public enum TipoSensore {
    LUCE("Sensore di luce"),
    FUMO("Sensore di rilevamento fumo"),
    MOVIMENTO("Sensore di movimento"),
    UMIDITA("Sensore di umidità"),
    TEMPERATURA("Sensore di temperatura");

    private final String descrizione;

    TipoSensore(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
   public TipoSensore getTipo() {
    return tipo;}
}


