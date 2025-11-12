import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Pannello che rappresenta un singolo sensore con stato, valore e controlli.
 * Include uno sfondo con effetto gradiente e glossy.
 */
public class SensorPanel extends JPanel {
    private Sensore sensore;
    private JLabel statoLabel;
    private JLabel valoreLabel;
    private JLabel nomeLabel;
    private JButton powerBtn;

    public SensorPanel(Sensore s) {
        this.sensore = s;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // 🔹 Bordo con titolo (ID sensore)
        setBorder(BorderFactory.createTitledBorder(
            s.getClass().getSimpleName() + " (" + s.getIdSensore() + ")"
        ));

        // 🔹 Etichetta tipo sensore
        JLabel tipoLabel = new JLabel("Tipo: " + s.getTipo().getDescrizione());
        tipoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        // Colore uniforme per leggibilità su sfondo scuro
        tipoLabel.setForeground(Color.WHITE);

        // 🔹 Nome personalizzato
        nomeLabel = new JLabel("Nome: " +
            (s.getNomePersonalizzato() != null ? s.getNomePersonalizzato() : "N/D"));
        nomeLabel.setForeground(Color.WHITE);

        // 🔹 Stato e valore
        statoLabel = new JLabel("Stato: " + (s.isAcceso() ? "ON" : "OFF"));
        statoLabel.setForeground(Color.WHITE);
        valoreLabel = new JLabel("Valore: N/D");
        valoreLabel.setForeground(Color.WHITE);

        // 🔹 Pulsante accensione
        powerBtn = new JButton(s.isAcceso() ? "Spegni" : "Accendi");
        powerBtn.addActionListener(e -> togglePower());
        powerBtn.setFocusPainted(false);
        powerBtn.setBackground(Color.LIGHT_GRAY.brighter());
        powerBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        powerBtn.setOpaque(true);
        powerBtn.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        // 🔹 Aggiunta componenti
        add(tipoLabel);
        add(nomeLabel);
        add(statoLabel);
        add(valoreLabel);
        add(powerBtn);
    }

    /**
     * Aggiorna il valore mostrato nel pannello.
     */
    public void updateData(DatoSensore dato) {
        if (dato != null) {
            valoreLabel.setText("Valore: " + dato.getValoreFormattato());
        }
    }

    public void turnOnSensor() {
        if (!sensore.isAcceso()) {
            sensore.accendi();
            statoLabel.setText("Stato: ON");
            powerBtn.setText("Spegni");
        }
    }

    public void turnOffSensor() {
        if (sensore.isAcceso()) {
            sensore.spegni();
            statoLabel.setText("Stato: OFF");
            powerBtn.setText("Accendi");
        }
    }

    private void togglePower() {
        if (sensore.isAcceso()) {
            turnOffSensor();
        } else {
            turnOnSensor();
        }
    }

    /**
     * 🎨 Effetto grafico: gradiente + glossy.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 🌈 Gradiente base (verticale)
        Color startColor = new Color(Costanti.COLOR1_HEX).brighter();
        Color endColor = new Color(Costanti.COLOR2_HEX).darker();
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, h, endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, w, h);

        // ✨ Effetto glossy (riflesso traslucido nella parte alta)
        GradientPaint gloss = new GradientPaint(
            0, 0, new Color(255, 255, 255, 80), // bianco semi-trasparente
            0, h * 0.4f, new Color(255, 255, 255, 0)
        );
        g2d.setPaint(gloss);
        g2d.fillRect(0, 0, w, (int)(h * 0.4));

        g2d.dispose();
    }
}
