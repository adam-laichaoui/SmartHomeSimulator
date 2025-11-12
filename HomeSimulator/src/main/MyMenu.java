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
 * Barra dei menu principale dell'applicazione.
 * Include un effetto grafico glossy con gradiente verticale.
 */
public class MyMenu extends JMenuBar {

    public MyMenu(MyFrame frame) {
        super();

        //  Dimensioni e font
        setPreferredSize(new Dimension(Costanti.DIM_W, 35));
        Font menuFont = new Font(Costanti.SECONDO_FONT, Font.BOLD, 14);
;
        //  Menu "File"
        JMenu fileMenu = new JMenu("File");

        JMenuItem exportItem = new JMenuItem("Esporta");
        exportItem.addActionListener(e -> frame.esportaDati());
        fileMenu.add(exportItem);

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

        //  Aggiunge il menu "File" alla barra
        add(fileMenu);

        //  Menu "Aiuto"
        JMenu helpMenu = new JMenu("Aiuto");

        JMenuItem infoItem = new JMenuItem("Informazioni");
        infoItem.addActionListener(e -> JOptionPane.showMessageDialog(
            frame,
            Costanti.TITLE + "\n" + Costanti.VERSIONE + "\n"+ " AUTORI: \n " +  Costanti.AUTORI +"\nSimulatore di sensori IoT per la casa intelligente.",
            "Informazioni",
            JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(infoItem);

        //  Font uniforme su tutta la barra
        setFont(menuFont);
        fileMenu.setFont(menuFont);
        fileMenu.setForeground(Color.WHITE);
        helpMenu.setFont(menuFont);
        helpMenu.setForeground(Color.WHITE);
        exportItem.setFont(menuFont);
        exitItem.setFont(menuFont);
        infoItem.setFont(menuFont);

        //  Aggiunge il menu "Aiuto"
        add(helpMenu);
    }

    /**
     * Disegna uno sfondo con gradiente verticale e riflesso glossy realistico.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        //  Gradiente base (verticale, più morbido e tridimensionale)
        Color startColor = new Color(Costanti.COLOR2_HEX).darker();
        Color midColor = new Color(Costanti.COLOR2_HEX);
        Color endColor = new Color(Costanti.COLOR2_HEX).brighter();

        // Usa due gradienti per un effetto "profondo"
        GradientPaint topGradient = new GradientPaint(0, 0, startColor, 0, h / 2f, midColor);
        g2d.setPaint(topGradient);
        g2d.fillRect(0, 0, w, h / 2);

        GradientPaint bottomGradient = new GradientPaint(0, h / 2f, midColor, 0, h, endColor);
        g2d.setPaint(bottomGradient);
        g2d.fillRect(0, h / 2, w, h / 2);

        //  Effetto glossy nella parte superiore
        GradientPaint gloss = new GradientPaint(
            0, 0, new Color(255, 255, 255, 100),    // bianco semi-trasparente
            0, h * 0.6f, new Color(255, 255, 255, 0) // svanisce al 60% dell’altezza
        );
        g2d.setPaint(gloss);
        g2d.fillRect(0, 0, w, (int)(h * 0.6));

        g2d.dispose();
    }
}
