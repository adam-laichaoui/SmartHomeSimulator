//package main;

import javax.swing.*;


import java.awt.*;
// import java.awt.event.*; -------> non ulilizzato perchè uso lamda expression
import java.util.*;

public class MyFrame extends JFrame implements Observer {
    private Centralina centralina;
    private JPanel sensorsPanel;
    private Map<String, SensorPanel> panels;
    private boolean freezeAttivo = false; // indica se i sensori sono attualmente in pausa


    public MyFrame() {
        super(Costanti.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Costanti.X_DIM, Costanti.Y_DIM);
        setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon (Costanti.IMM_PATH + "/logo.jpg");
    	setIconImage(logo.getImage());//cambia l'icona del frame
    	getContentPane().setBackground(new Color(Costanti.WHITE_HEX));
        panels = new HashMap<>();
        centralina = new Centralina(this);

        //MENU
         setJMenuBar( new MyMenu( this)); // cosi posso collegare mymenu a centralina 

        // PANNELLO PULSANTI
        JPanel controlPanel = new JPanel();
        JButton generaBtn = new JButton("Genera valori");
        JButton freezeBtn = new JButton("Freeze");
        JButton nuovoSensoreBtn = new JButton("Nuovo sensore");

        generaBtn.addActionListener(e -> accendiSensori());
        freezeBtn.addActionListener(e -> {
            if (!freezeAttivo) {
                // Se il freeze non è attivo → spegne tutti i sensori
                spegniSensori();
                freezeAttivo = true;
                freezeBtn.setText("Riprendi");
                JOptionPane.showMessageDialog(this, "Tutti i sensori sono stati messi in pausa.", 
                        "Freeze", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Se il freeze è attivo → riaccende tutti i sensori
                accendiSensori();
                freezeAttivo = false;
                freezeBtn.setText("Freeze");
                JOptionPane.showMessageDialog(this, "Tutti i sensori sono stati riattivati.", 
                        "Freeze disattivato", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        nuovoSensoreBtn.addActionListener(e -> creaNuovoSensore());

        controlPanel.add(generaBtn);
        controlPanel.add(freezeBtn);
        controlPanel.add(nuovoSensoreBtn);

        // AREA SENSORS
        sensorsPanel = new JPanel();
        sensorsPanel.setLayout(new BoxLayout(sensorsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sensorsPanel);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // SENSORS INIZIALI
        aggiungiSensore(new SensoreLuce(centralina));
        aggiungiSensore(new SensoreTemperatura(centralina));
        aggiungiSensore(new SensoreUmidita(centralina));
        aggiungiSensore(new SensoreFumo(centralina));
        aggiungiSensore(new SensoreMovimento(centralina));

        setVisible(true);
    }

    private void aggiungiSensore(Sensore s) {
        panels.put(s.getIdSensore(), new SensorPanel(s));
        sensorsPanel.add(panels.get(s.getIdSensore()));
        sensorsPanel.revalidate();
        sensorsPanel.repaint();
        s.start();
    }

    private void creaNuovoSensore() {
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
}

    private void accendiSensori() {
        for (SensorPanel p : panels.values()) {
            p.turnOnSensor();
        }
    }

    private void spegniSensori() {
        for (SensorPanel p : panels.values()) {
            p.turnOffSensor();
        }
    }

    public void esportaDati() {
    // ora esporta l’intero storico dei valori
    Exporter.esportaStorico(centralina.getStorico());
    JOptionPane.showMessageDialog(this, "Storico completo esportato sul Desktop.");
    }


    @Override
    public void update(String id, Map<String, DatoSensore> dati) {
        if (id != null && panels.containsKey(id)) {
            panels.get(id).updateData(dati.get(id));
        }
    }
}
