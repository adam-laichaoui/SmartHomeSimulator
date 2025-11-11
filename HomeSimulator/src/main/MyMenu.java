package main;

import javax.swing.*;

/**
 * Classe che rappresenta la barra dei menu principale dell'applicazione.
 * Non possiede direttamente la centralina, ma interagisce con il frame principale (MyFrame),
 * che contiene tutta la logica e i dati dell'applicazione.
 */
public class MyMenu extends JMenuBar {

    public MyMenu(MyFrame frame) {
        super();

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

        add(helpMenu);
    }
}
