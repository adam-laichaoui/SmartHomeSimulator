package main;

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
    public void showForSeconds(int seconds) {
        setVisible(true);
        // Timer Swing: dopo tot secondi chiude lo splash e apre MyFrame
        new Timer(seconds * 1000, e -> {
            dispose(); // chiude lo splash
            new MyFrame(); // apre la finestra principale
        }).start();
    }
}
