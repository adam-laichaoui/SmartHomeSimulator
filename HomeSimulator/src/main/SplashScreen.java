import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Splash screen iniziale dell'applicazione.
 * Mostra un logo o titolo per alcuni secondi prima di aprire la finestra principale.
 */
public class SplashScreen extends JWindow {

    public SplashScreen() {
        //  Pannello principale con effetto gradiente + glossy
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JLabel logoLabel;
        logoLabel = new JLabel(Costanti.TITLE, SwingConstants.CENTER);
        logoLabel.setFont(new Font(Costanti.TITLE_FONT, Font.BOLD, Costanti.DIM_TITLE_TEXT));
        logoLabel.setForeground(Color.WHITE);
        panel.add(logoLabel, BorderLayout.CENTER);

        //  Label inferiore ("Caricamento...")
        JLabel loading = new JLabel(Costanti.AVVIO_TXT, SwingConstants.CENTER);
        loading.setFont(new Font(Costanti.SECONDO_FONT, Font.ITALIC, 16));
        loading.setForeground(new Color(Costanti.LIGHT_GREY_HEX));
        panel.add(loading, BorderLayout.SOUTH);

        //  Impostazioni finestra
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
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Pannello interno con gradiente diagonale e effetto "glassy" realistico.
     */
    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Attiva antialiasing per bordi più morbidi
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //  Gradiente base diagonale (↘)
            Color start = new Color(Costanti.COLOR2_HEX).darker();
            Color end = new Color(Costanti.COLOR2_HEX).brighter();
            GradientPaint baseGradient = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
            g2d.setPaint(baseGradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Effetto glossy realistico (solo parte superiore, diagonale morbida)
            GradientPaint gloss = new GradientPaint(
                0, 0, new Color(255, 255, 255, 100),               // bianco semi-trasparente
                getWidth(), (int)(getHeight() * 0.4),              // svanisce diagonalmente verso il basso
                new Color(255, 255, 255, 0)                        // completamente trasparente
            );
            g2d.setPaint(gloss);

            // Disegna il riflesso solo nella parte alta, inclinato
            int[] xPoints = { 0, getWidth(), getWidth(), 0 };
            int[] yPoints = { 0, 0, (int)(getHeight() * 0.4), (int)(getHeight() * 0.2) };
            g2d.fillPolygon(xPoints, yPoints, 4);

            g2d.dispose();
        }
    }
}
