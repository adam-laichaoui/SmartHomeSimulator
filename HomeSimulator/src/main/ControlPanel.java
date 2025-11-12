import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    private boolean freezeAttivo = false; // stato locale del freeze

    public ControlPanel(MyFrame frame) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton generaBtn = new JButton("Genera valori");
        JButton freezeBtn = new JButton("Freeze");
        JButton nuovoSensoreBtn = new JButton("Nuovo sensore");

        // 🔹 Pulsante "Genera valori"
        generaBtn.addActionListener(e -> frame.accendiSensori());

        // 🔹 Pulsante "Freeze / Riprendi"
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

        // 🔹 Pulsante "Nuovo sensore"
        nuovoSensoreBtn.addActionListener(e -> frame.creaNuovoSensore());

        // Aggiunta dei pulsanti al pannello
        add(generaBtn);
        add(freezeBtn);
        add(nuovoSensoreBtn);
    }
}
