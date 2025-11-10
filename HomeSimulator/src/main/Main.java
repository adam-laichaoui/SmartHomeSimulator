package main;

/**
 * Classe di avvio principale dell'applicazione Casa IoT Simulator.
 * Avvia la GUI Swing e quindi i thread dei sensori.
 */
public class Main {

    public static void main(String[] args) {
        // Imposta lo stile grafico del sistema operativo (facoltativo, ma più gradevole)
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Impossibile impostare il look and feel di sistema: " + e.getMessage());
        }

        // Avvio della GUI Swing nel thread dedicato all'interfaccia grafica (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MyFrame(); // crea e mostra la finestra principale
        });
    }
}
