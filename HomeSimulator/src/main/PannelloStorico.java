import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Pannello laterale che mostra tutti i dati storici dei sensori.
 * Viene aggiornato automaticamente dalla Centralina tramite l'Observer.
 */
public class PannelloStorico extends JPanel {

    private JTextArea areaTesto;

    public PannelloStorico() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(" Storico Dati Sensori"));
        setPreferredSize(new Dimension(Costanti.DIM_W, 0)); // Larghezza fissa laterale

        areaTesto = new JTextArea();
        areaTesto.setEditable(false);
        areaTesto.setFont(new Font(Costanti.SECONDO_FONT, Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaTesto);
        add(scroll, BorderLayout.CENTER);
    }

   
    public void aggiornaStorico(Map<String, List<DatoSensore>> storico) {
        StringBuilder sb = new StringBuilder();

        for (String id : storico.keySet()) {
            sb.append("Sensore ").append(id).append("\n");
            List<DatoSensore> lista = storico.get(id);
            for (DatoSensore dato : lista) {
                sb.append("  • ").append(dato.toString()).append("\n");
            }
            sb.append("\n");
        }

        areaTesto.setText(sb.toString());
        areaTesto.setCaretPosition(areaTesto.getDocument().getLength()); // scorre sempre in fondo
    }
}
