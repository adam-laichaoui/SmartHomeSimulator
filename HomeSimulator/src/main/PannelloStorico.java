import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Pannello laterale che mostra tutti i dati storici dei sensori.
 * Include un effetto grafico con gradiente e riflesso glossy.
 */
public class PannelloStorico extends JPanel {

    private JTextArea areaTesto;

    public PannelloStorico() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Costanti.DIM_W, 0));

        // 🔹 Bordo con titolo personalizzato
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(Costanti.LIGHT_GREY_HEX)),
            " Storico Dati Sensori",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font(Costanti.SECONDO_FONT, Font.BOLD, 14),
            Color.WHITE
        ));

        // 🔹 JTextArea con gradiente interno e testo trasparente
        areaTesto = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // 🌈 Gradiente di base interno
                Color startColor = new Color(Costanti.COLOR1_HEX).brighter();
                Color endColor = new Color(Costanti.COLOR2_HEX).darker();
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, h, endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, w, h);

                // ✨ Effetto glossy (parte alta semi-trasparente)
                GradientPaint gloss = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 90),
                    0, h * 0.4f, new Color(255, 255, 255, 0)
                );
                g2d.setPaint(gloss);
                g2d.fillRect(0, 0, w, (int)(h * 0.4));

                g2d.dispose();
                super.paintComponent(g); // poi disegna il testo
            }
        };
        areaTesto.setOpaque(false);
        areaTesto.setEditable(false);
        areaTesto.setFont(new Font(Costanti.TITLE_FONT, Font.BOLD, 12));
        areaTesto.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(areaTesto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Aggiorna il contenuto della JTextArea con lo storico dei sensori.
     */
    public void aggiornaStorico(Map<String, List<DatoSensore>> storico) {
        StringBuilder sb = new StringBuilder();

        for (String id : storico.keySet()) {
            sb.append("Sensore ").append(id).append("\n");
            List<DatoSensore> lista = storico.get(id);
            for (DatoSensore dato : lista) {
                sb.append("══════════════════════════════════════\n");
                sb.append("  • ").append(dato.toString()).append("\n");
                sb.append("══════════════════════════════════════\n");
            }
            sb.append("\n");
        }

        areaTesto.setText(sb.toString());
        areaTesto.setCaretPosition(areaTesto.getDocument().getLength());
    }

    /**
     * Effetto gradiente + glossy sul pannello principale.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 🌈 Gradiente di base
        Color startColor = new Color(Costanti.COLOR1_HEX).brighter();
        Color endColor = new Color(Costanti.COLOR2_HEX).darker();
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, h, endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, w, h);

        // ✨ Effetto glossy superiore
        GradientPaint gloss = new GradientPaint(
            0, 0, new Color(255, 255, 255, 80),
            0, h * 0.35f, new Color(255, 255, 255, 0)
        );
        g2d.setPaint(gloss);
        g2d.fillRect(0, 0, w, (int)(h * 0.35));

        g2d.dispose();
    }
}
