//package main;

import javax.swing.*;
import java.awt.*;

/**
 * Splash screen iniziale dell'applicazione.
 * Mostra un logo o titolo per alcuni secondi prima di aprire la finestra principale.
 */
public class SplashScreen extends JWindow {

    public SplashScreen() {
        // Pannello principale
        JPanel panel = new JPanel();
        panel.setBackground(new Color(Costanti.COLOR2_HEX));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setLayout(new BorderLayout());

        // Logo o titolo (puoi anche caricare un'immagine)
        JLabel logoLabel = new JLabel(Costanti.TITLE, SwingConstants.CENTER);
        logoLabel.setFont(new Font(Costanti.TITLE_FONT, Font.BOLD, Costanti.DIM_TITLE_TEXT));
        logoLabel.setForeground(new Color(Costanti.COLOR1_HEX));
          
        System.out.println("DEBUG PATH -> " + getClass().getResource(Costanti.IMM_PATH + "/logopiccolo.jpg"));

        // Carica il logo (assicurati che logo.png sia in /resources o nel classpath)
        ImageIcon logoIcon = null;
        try {
            logoIcon = new ImageIcon(getClass().getResource(Costanti.IMM_PATH + "/logopiccolo.jpg")); //  percorso relativo alle risorse
        } catch (Exception e) {
            System.out.println("Impossibile caricare il logo: " + e.getMessage());
        }

        //JLabel logoLabel;
        if (logoIcon != null) {
            logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        } else {
            // testo se non trova l’immagine
            logoLabel = new JLabel(Costanti.TITLE, SwingConstants.CENTER);
            logoLabel.setFont(new Font(Costanti.SECONDO_FONT, Font.BOLD, 28));
        }

        panel.add(logoLabel, BorderLayout.CENTER);

        // Testo in basso
        JLabel loading = new JLabel(Costanti.AVVIO_TXT, SwingConstants.CENTER);
        loading.setFont(new Font(Costanti.SECONDO_FONT, Font.PLAIN, 14));
        loading.setForeground(Color.DARK_GRAY);
        panel.add(loading, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    /**
     * Mostra lo splash screen per alcuni secondi, poi apre la GUI principale.
     */
  public void showForSeconds(int seconds, MyFrame frame) {
    setVisible(true);

    Timer timer = new Timer(seconds * 1000, e -> {
        dispose();                // chiude lo splash
        frame.setVisible(true);   // mostra la GUI principale già creata
    });
    timer.setRepeats(false);      // ✅ esegui solo una volta
    timer.start();
}

}
