import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    private boolean freezeAttivo = false; // stato locale del freeze

    public ControlPanel(MyFrame frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        TitledBorder border = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.WHITE.brighter(), 5),  // bordo bianco scurito 
        " CONTROLLI SENSORI ",                       // testo del titolo
        TitledBorder.LEFT,                             // allineamento a sinistra
        TitledBorder.TOP,                              // posizione del titolo
        new Font(Costanti.SECONDO_FONT, Font.BOLD, 13),          // font personalizzato
        Color.WHITE.brighter()                          // colore bianco
        );
        setBorder(border);

        JButton generaBtn = new JButton("Genera valori");
        JButton freezeBtn = new JButton("Freeze");
        JButton nuovoSensoreBtn = new JButton("Nuovo sensore");

        
        // modificare colore e font dei bottoni 

        Font btnFont = new Font(Costanti.SECONDO_FONT, Font.BOLD, 13);

        for (JButton b : List.of(generaBtn, freezeBtn, nuovoSensoreBtn)) {
        b.setFocusPainted(false);
        b.setFont(btnFont);
        b.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // padding
        b.setOpaque(true);  // necessario per far vedere il colore su alcuni Look and Feel
        b.setBackground(Color.LIGHT_GRAY.brighter());
        b.setBorderPainted(false);
        b.setContentAreaFilled(true);

        }

        //  Pulsante "Genera valori"
        generaBtn.addActionListener(e -> frame.accendiSensori());

        // Pulsante "Freeze / Riprendi"
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

        // Pulsante "Nuovo sensore"
        nuovoSensoreBtn.addActionListener(e -> frame.creaNuovoSensore());

        // Aggiunta dei pulsanti al pannello
        add(generaBtn);
        add(freezeBtn);
        add(nuovoSensoreBtn);
    }

     @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    // Attiva l'antialiasing (per bordi più morbidi)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    //  Definisci i due colori del gradiente
    Color startColor = new Color(Costanti.COLOR1_HEX).brighter();
    Color endColor = new Color(Costanti.COLOR2_HEX).darker();

    // Crea un gradiente verticale (da alto → basso)
    GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);

    //  Applica il gradiente
    g2d.setPaint(gradient);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    g2d.dispose(); // libera le risorse grafiche
}

}
