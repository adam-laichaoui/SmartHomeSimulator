package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe di avvio principale dell'applicazione Casa IoT Simulator.
 * Mostra uno splash screen iniziale con il logo, poi avvia la GUI principale.
 */
public class Main {

    public static void main(String[] args) {
        // I(facoltativo, ma migliora l'aspetto)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Impossibile impostare il look and feel di sistema: " + e.getMessage());
        }

        // Avvio dell'interfaccia grafica nel thread dedicato (EDT)
        SwingUtilities.invokeLater(() -> {
            // Mostra lo splash screen per alcuni secondi, poi apre MyFrame
            SplashScreen splash = new SplashScreen();
            splash.showForSeconds(Costanti.SPLESHSCREEN_TIME); // imposta qui i secondi di durata
        });
    }
}
