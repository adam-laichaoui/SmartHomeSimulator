import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MyMenu extends JMenuBar {

    public MyMenu(MyFrame frame) {
        super();

        setPreferredSize(new Dimension(Costanti.DIM_W, 35));
        Font menuFont = new Font(Costanti.SECONDO_FONT, Font.BOLD, 14);

        // MENU FILE
        JMenu fileMenu = new JMenu("File");

        //  ESPORTA DAL PANNELLO STORICO
        JMenuItem exportStoricoVisibile = new JMenuItem("Esporta storico visibile");
        exportStoricoVisibile.addActionListener(e -> {

            if (frame.getPannelloStorico() == null) {
                JOptionPane.showMessageDialog(frame,
                        "Il pannello storico non è disponibile!",
                        "ERRORE",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Exporter.esportaDaPannello(frame.getPannelloStorico());
                JOptionPane.showMessageDialog(frame,
                        "Esportazione del pannello completata!",
                        "ESPORTAZIONE OK",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Errore durante l'esportazione:\n" + ex.getMessage(),
                        "ERRORE",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        fileMenu.add(exportStoricoVisibile);

        //  ESPORTA SOLO UN SENSORE
JMenuItem exportSingoloItem = new JMenuItem("Esporta un sensore...");
exportSingoloItem.addActionListener(e -> {

    // recupera tutto lo storico dalla centralina
    Map<String, List<DatoSensore>> storico = frame.getCentralina().getStorico();

    if (storico.isEmpty()) {
        JOptionPane.showMessageDialog(frame,
                "Non ci sono sensori con dati da esportare!",
                "Errore",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // crea lista degli ID disponibili
    Object[] ids = storico.keySet().toArray();

    // finestra di scelta sensore
    String scelto = (String) JOptionPane.showInputDialog(
            frame,
            "Seleziona il sensore da esportare:",
            "Esporta storico sensore",
            JOptionPane.QUESTION_MESSAGE,
            null,
            ids,
            ids[0]
    );

    if (scelto != null) {
        // esporta il sensore selezionato
        Exporter.esportaSingoloSensore(scelto, storico.get(scelto));

        JOptionPane.showMessageDialog(frame,
                "Lo storico del sensore:\n" + scelto + "\nè stato esportato correttamente!",
                "Esportazione completata",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                fileMenu.add(exportSingoloItem);
                exportSingoloItem.setFont(menuFont);


        // 3️ — USCITA
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

        add(fileMenu);

        // MENU AIUTO
        JMenu helpMenu = new JMenu("Aiuto");
        JMenuItem infoItem = new JMenuItem("Informazioni");

        infoItem.addActionListener(e -> JOptionPane.showMessageDialog(
                frame,
                Costanti.TITLE + "\n" +
                        Costanti.VERSIONE + "\n" +
                        "Autori: " + Costanti.AUTORI + "\n\n" +
                        "Simulatore di sensori IoT per casa intelligente.",
                "Informazioni",
                JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(infoItem);

        add(helpMenu);

        // FONT UNIFORME
        fileMenu.setFont(menuFont);
        helpMenu.setFont(menuFont);
        exportStoricoVisibile.setFont(menuFont);
        exitItem.setFont(menuFont);
        infoItem.setFont(menuFont);

        fileMenu.setForeground(Color.WHITE);
        helpMenu.setForeground(Color.WHITE);
    }

    // Effetto grafico glossy
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Color startColor = new Color(Costanti.COLOR2_HEX).darker();
        Color midColor = new Color(Costanti.COLOR2_HEX);
        Color endColor = new Color(Costanti.COLOR2_HEX).brighter();

        GradientPaint topGradient =
                new GradientPaint(0, 0, startColor, 0, h / 2f, midColor);
        g2d.setPaint(topGradient);
        g2d.fillRect(0, 0, w, h / 2);

        GradientPaint bottomGradient =
                new GradientPaint(0, h / 2f, midColor, 0, h, endColor);
        g2d.setPaint(bottomGradient);
        g2d.fillRect(0, h / 2, w, h / 2);

        GradientPaint gloss = new GradientPaint(
                0, 0, new Color(255, 255, 255, 100),
                0, h * 0.6f, new Color(255, 255, 255, 0)
        );
        g2d.setPaint(gloss);
        g2d.fillRect(0, 0, w, (int) (h * 0.6));

        g2d.dispose();
    }
}
