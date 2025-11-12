import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Classe che rappresenta la barra dei menu principale dell'applicazione.
 * Non possiede direttamente la centralina, ma interagisce con il frame principale (MyFrame),
 * che contiene tutta la logica e i dati dell'applicazione.
 */
public class MyMenu extends JMenuBar {

    public MyMenu(MyFrame frame) {
        super();

        setPreferredSize(new Dimension(Costanti.DIM_W, 35));
        Font menuFont = new Font(Costanti.SECONDO_FONT, Font.BOLD, 14); 


        // Menu "File"
        JMenu fileMenu = new JMenu("File");

        // Voce "Esporta"
        JMenuItem exportItem = new JMenuItem("Esporta");
        exportItem.addActionListener(e -> frame.esportaDati());
        fileMenu.add(exportItem);

        // Voce "Esci" 
        JMenuItem exitItem = new JMenuItem("Esci");
        exitItem.addActionListener(e -> {
            int conferma = JOptionPane.showConfirmDialog(
                    frame,
                    "Sei sicuro di voler uscire?",
                    "Conferma uscita",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (conferma == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        // Aggiunge il menu "File" alla barra
        add(fileMenu);

        // Menu "Aiuto"
        JMenu helpMenu = new JMenu("Aiuto");

        JMenuItem infoItem = new JMenuItem("Informazioni");
        infoItem.addActionListener(e -> JOptionPane.showMessageDialog(
                frame,
                Costanti.TITLE+"\n" + Costanti.VERSIONE+"\nSimulatore di sensori IoT per la casa intelligente.",
                "Informazioni",
                JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(infoItem);
        // Applica il font alla barra e ai menu
            setFont(menuFont);
            fileMenu.setFont(menuFont);
            helpMenu.setFont(menuFont);

            // Applica anche alle singole voci
            exportItem.setFont(menuFont);
            exitItem.setFont(menuFont);
            infoItem.setFont(menuFont);

        add(helpMenu);
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
