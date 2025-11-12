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
 * Viene aggiornato automaticamente dalla Centralina tramite l'Observer.
 */
public class PannelloStorico extends JPanel {

    private JTextArea areaTesto;

    public PannelloStorico() {
        setLayout(new BorderLayout());
       setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(Costanti.LIGHT_GREY_HEX)), 
                                           " Storico Dati Sensori",
                                           TitledBorder.LEFT, 
                                           TitledBorder.TOP, 
                                           new Font(Costanti.SECONDO_FONT, Font.BOLD, 14), 
                                           Color.WHITE));

        setPreferredSize(new Dimension(Costanti.DIM_W, 0)); // Larghezza fissa laterale
        setBackground(new Color(Costanti.COLOR1_HEX));

        areaTesto = new JTextArea() {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Attiva antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Colori del gradiente
        Color startColor = new Color(Costanti.COLOR1_HEX).brighter();
        Color endColor = new Color(Costanti.LIGHT_GREY_HEX).darker();

        // Gradiente verticale (dall'alto verso il basso)
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 100, getHeight(), endColor);

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();

        // Poi disegna normalmente il testo sopra
        super.paintComponent(g);
    }
};
       areaTesto.setOpaque(false); // molto importante: disabilita il riempimento di sfondo "pieno"
        areaTesto.setEditable(false);
        areaTesto.setFont(new Font(Costanti.TITLE_FONT, Font.BOLD, 12));
        areaTesto.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(areaTesto);
        add(scroll, BorderLayout.CENTER);
    }

   
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
        areaTesto.setCaretPosition(areaTesto.getDocument().getLength()); // scorre sempre in fondo
    }

    // crea effetto gradiente in barra menu 
    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    // Attiva l'antialiasing (per bordi più morbidi)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    //  Definisci i due colori del gradiente
    Color startColor = new Color(Costanti.COLOR2_HEX).darker();
    Color endColor = new Color(Costanti.COLOR2_HEX).brighter();

    // Crea un gradiente verticale (da alto → basso)
    GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);

    //  Applica il gradiente
    g2d.setPaint(gradient);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    g2d.dispose(); // libera le risorse grafiche
}
}
