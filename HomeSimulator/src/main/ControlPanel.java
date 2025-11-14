import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Pannello dei controlli principali: pulsanti per gestire i sensori.
 * Include un effetto grafico con gradiente verticale e riflesso glossy nella parte superiore.
 */
public class ControlPanel extends JPanel {
    
    private boolean freezeAttivo = false; // stato locale del freeze

    public ControlPanel(MyFrame frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Bordo con titolo personalizzato
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE.brighter(), 5),   // bordo bianco chiaro
            " CONTROLLI SENSORI ",                                      // testo del titolo
            TitledBorder.LEFT,                                          // allineamento titolo
            TitledBorder.TOP,                                           // posizione del titolo
            new Font(Costanti.SECONDO_FONT, Font.BOLD, 13),             // font personalizzato
            Color.WHITE.brighter()                                      // colore del testo
        );
        setBorder(border);

        // Pulsanti principali
        JButton generaBtn = new JButton("Genera valori");
        JButton freezeBtn = new JButton("Freeze");
        JButton nuovoSensoreBtn = new JButton("Nuovo sensore");
                
        // fissare la dimensione dei bottoni per non farla camiare se cambia il testo meglio così che usaree un gridlayout
        Dimension maxSize = new Dimension(300, 30);

        //  Stile dei pulsanti
        Font btnFont = new Font(Costanti.SECONDO_FONT, Font.BOLD, 13);
        for (JButton b : List.of(generaBtn, freezeBtn, nuovoSensoreBtn)) {
            b.setPreferredSize(maxSize);
            b.setFocusPainted(false);
            b.setFont(btnFont);
            b.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // padding interno
            b.setOpaque(true);
            b.setBackground(Color.LIGHT_GRAY.brighter());
            b.setBorderPainted(false);
            b.setContentAreaFilled(true);
            
        }

        //  Azione "Genera valori"
        generaBtn.addActionListener(e -> frame.accendiSensori());

        // Azione "Freeze / Riprendi"
        freezeBtn.addActionListener(e -> {
            if (!freezeAttivo) {
                frame.spegniSensori();
                freezeAttivo = true;
                freezeBtn.setText("Riprendi");
                JOptionPane.showMessageDialog(frame,
                        "Tutti i sensori sono stati messi in pausa.",
                        "Freeze", JOptionPane.INFORMATION_MESSAGE);
            } else {
                frame.accendiSensori();
                freezeAttivo = false;
                freezeBtn.setText("Freeze");
                JOptionPane.showMessageDialog(frame,
                        "Tutti i sensori sono stati riattivati.",
                        "Freeze disattivato", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Azione "Nuovo sensore"
        nuovoSensoreBtn.addActionListener(e -> frame.creaNuovoSensore());

        // Aggiunta pulsanti al pannello
        add(generaBtn);
        add(freezeBtn);
        add(nuovoSensoreBtn);
    }

    /**
     * Disegna lo sfondo con gradiente verticale e riflesso glossy nella parte superiore.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Gradiente base (verticale, dall'alto verso il basso)
        Color startColor = new Color(Costanti.COLOR1_HEX).brighter();
        Color endColor = new Color(Costanti.COLOR2_HEX).darker();
        GradientPaint base = new GradientPaint(0, 0, startColor, 0, h, endColor);
        g2d.setPaint(base);
        g2d.fillRect(0, 0, w, h);

        //  Effetto glossy (riflesso bianco semi-trasparente nella parte superiore)
        GradientPaint gloss = new GradientPaint(
            0, 0, new Color(255, 255, 255, 100),   // bianco semi-trasparente
            0, h * 0.4f, new Color(255, 255, 255, 0) // svanisce a circa 40% dell'altezza
        );
        g2d.setPaint(gloss);
        g2d.fillRect(0, 0, w, (int)(h * 0.4)); // riflesso solo sulla parte alta

        g2d.dispose(); // libera le risorse grafiche
    }
}
