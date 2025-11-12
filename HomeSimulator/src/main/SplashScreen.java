
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.net.URL;


/**
 * Splash screen iniziale dell'applicazione.
 * Mostra un logo o titolo per alcuni secondi prima di aprire la finestra principale.
 */
public class SplashScreen extends JWindow {

    public SplashScreen() {
        // Pannello principale
        JPanel panel = new JPanel();
        panel.setBackground(new Color(Costanti.COLOR2_HEX));
        panel.setLayout(new BorderLayout());

        // Prova a caricare il logo dal classpath
        ImageIcon logoIcon = null;
        try {
            //  Carica l'immagine dal classpath (es: src/resources/logopiccolo.jpg)
            URL logoURL = getClass().getResource("src\\main\\resources\\images\\logopiccolo.jpg");

            if (logoURL != null) {
                logoIcon = new ImageIcon(logoURL);
                System.out.println("Logo caricato da: " + logoURL);
            } else {
                System.err.println("Logo non trovato nel classpath!");
            }
        } catch (Exception e) {
            System.err.println("Errore nel caricamento del logo: " + e.getMessage());
        }

        // Label centrale: immagine o testo alternativo
        JLabel logoLabel;
        if (logoIcon != null) {
            logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        } else {
            logoLabel = new JLabel(Costanti.TITLE, SwingConstants.CENTER);
            logoLabel.setFont(new Font(Costanti.TITLE_FONT, Font.BOLD, Costanti.DIM_TITLE_TEXT));
            logoLabel.setForeground(Color.WHITE);
        }

        panel.add(logoLabel, BorderLayout.CENTER);

        // Testo inferiore ("Caricamento...")
        JLabel loading = new JLabel(Costanti.AVVIO_TXT, SwingConstants.CENTER);
        loading.setFont(new Font(Costanti.SECONDO_FONT, Font.PLAIN, 14));
        loading.setForeground(new Color(Costanti.LIGHT_GREY_HEX));
        panel.add(loading, BorderLayout.SOUTH);

        // Impostazioni finestra
        getContentPane().add(panel);
        setSize(Costanti.DIM_W, Costanti.DIM_H);
        setLocationRelativeTo(null);
    }

    /**
     * Mostra lo splash screen per alcuni secondi, poi apre la GUI principale.
     */
    public void showForSeconds(int seconds, MyFrame frame) {
        setVisible(true);

        Timer timer = new Timer(seconds * 1000, e -> {
            dispose();               // Chiude la finestra splash
            frame.setVisible(true);  // Mostra la GUI principale
        });
        timer.setRepeats(false);     // Esegui solo una volta
        timer.start();
    }
}
