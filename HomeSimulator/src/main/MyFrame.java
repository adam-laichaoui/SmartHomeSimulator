//package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class MyFrame extends JFrame implements Observer {
    private Centralina centralina;
    private JPanel sensorsPanel;
    private Map<String, SensorPanel> panels;
    //private boolean freezeAttivo = false; // indica se i sensori sono attualmente in pausa
    private PannelloStorico pannelloStorico;


    public MyFrame() {
        super(Costanti.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Costanti.X_DIM, Costanti.Y_DIM);
        setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon (Costanti.IMM_PATH + "\\logo.jpg"); 
    	setIconImage(logo.getImage());//cambia l'icona del frame
    	getContentPane().setBackground(Color.WHITE);
        panels = new HashMap<>();
        centralina = new Centralina(this);

        //MENU
         setJMenuBar( new MyMenu( this)); // cosi posso collegare mymenu a centralina 

        // PANNELLO PULSANTI 
        add(new ControlPanel(this), BorderLayout.NORTH);


        // AREA SENSORS
        sensorsPanel = new JPanel();
        sensorsPanel.setLayout(new BoxLayout(sensorsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sensorsPanel);

        add(scrollPane, BorderLayout.CENTER);

        // SENSORS INIZIALI
        aggiungiSensore(new SensoreLuce(centralina));
        aggiungiSensore(new SensoreTemperatura(centralina));
        aggiungiSensore(new SensoreUmidita(centralina));
        aggiungiSensore(new SensoreFumo(centralina));
        aggiungiSensore(new SensoreMovimento(centralina));
       
        pannelloStorico = new PannelloStorico();
        add(pannelloStorico, BorderLayout.EAST);

        setVisible(true);
    }

    private void aggiungiSensore(Sensore s) {
        panels.put(s.getIdSensore(), new SensorPanel(s));
        sensorsPanel.add(panels.get(s.getIdSensore()));
        sensorsPanel.revalidate();
        sensorsPanel.repaint();
        s.start();
    }

    protected void creaNuovoSensore() {
    JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
    JLabel labelTipo = new JLabel("Tipo di sensore:");
    JLabel labelNome = new JLabel("Nome personalizzato:");

    // ComboBox con enum interno
    JComboBox<Sensore.TipoSensore> combo = new JComboBox<>(Sensore.TipoSensore.values());
    JTextField nomeField = new JTextField();

    panel.add(labelTipo);
    panel.add(combo);
    panel.add(labelNome);
    panel.add(nomeField);

    int result = JOptionPane.showConfirmDialog(
            this, panel, "Crea Nuovo Sensore",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );

    if (result == JOptionPane.OK_OPTION) {
        Sensore.TipoSensore tipo = (Sensore.TipoSensore) combo.getSelectedItem();
        String nome = nomeField.getText().trim();
        Sensore nuovo = null;

        if (tipo != null) {
            switch (tipo) {
                case LUCE -> nuovo = new SensoreLuce(centralina);
                case TEMPERATURA -> nuovo = new SensoreTemperatura(centralina);
                case UMIDITA -> nuovo = new SensoreUmidita(centralina);
                case FUMO -> nuovo = new SensoreFumo(centralina);
                case MOVIMENTO -> nuovo = new SensoreMovimento(centralina);
            }
        }

        if (nuovo != null) {
            if (!nome.isEmpty()) {
                nuovo.setNomePersonalizzato(nome);
            }
            aggiungiSensore(nuovo);
        }
    }
}      //uso protected perchè possa accedervi anche la calsse control pannel 


    protected void accendiSensori() {
        for (SensorPanel p : panels.values()) {
            p.turnOnSensor();
        }
    }     //uso protected perchè possa accedervi anche la calsse control pannel 


    protected void spegniSensori() {
        for (SensorPanel p : panels.values()) {
            p.turnOffSensor();
        }
    } //uso protected perchè possa accedervi anche la calsse control pannel 

    public void esportaDati() {
    // ora esporta l’intero storico dei valori
    Exporter.esportaStorico(centralina.getStorico());
    JOptionPane.showMessageDialog(this, "Storico completo esportato sul Desktop.");
    }


    
    @Override
public void update(String id, Map<String, DatoSensore> dati) {
    SwingUtilities.invokeLater(() -> {// se no non genera valori
        if (id != null && panels.containsKey(id)) {
            panels.get(id).updateData(dati.get(id));
        }
        pannelloStorico.aggiornaStorico(centralina.getStorico());
    });
}

}
