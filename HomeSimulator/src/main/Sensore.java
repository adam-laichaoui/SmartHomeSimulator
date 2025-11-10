package main;

import java.util.Random;

/*
 * Superclasse di tutti i Sensori: genera il valore casuale
 * e lo invia alla Centralina.
 */
public abstract class Sensore extends Thread {

    private static final String MSG_ERR = "Errore: ";
	private final String id;
    private boolean attivo = true;
    
    private Centralina centralina;
    
    public Sensore(String id, Centralina centralina) {
    	this.id = id;
    	this.centralina = centralina;
    }

	public abstract double generaValore();
    
    public abstract DatoSensore generaDato();
    
    private void inviaDati() {
    	centralina.aggiornaDato(id, generaDato());
    }
    
    @Override
    public synchronized void run() {
        while (true) {
            if (!isAcceso()) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println(MSG_ERR + e.getMessage());
                    break;
                }
            }
            
            inviaDati();
            
            Random random = new Random();

            int randomSleep = 800 + random.nextInt(1001);
            try {
                Thread.sleep(randomSleep);
            } catch (InterruptedException e) {
                System.out.println(MSG_ERR + e.getMessage());
                break;
            }
        }
    }
    
	public void accendi() {
    	attivo = true;
    }

    public void spegni() {
    	attivo = false;
    }
    
    public boolean isAcceso() {
    	return attivo;
    }

    //serve per la gui
public String getIdSensore() {
    return id;
}

// idea per personalizzare da tastiera il nome del sensore 
private String nomePersonalizzato;

public void setNomePersonalizzato(String nome) {
    this.nomePersonalizzato = nome;
}

public String getNomePersonalizzato() {
    return nomePersonalizzato;
}

}