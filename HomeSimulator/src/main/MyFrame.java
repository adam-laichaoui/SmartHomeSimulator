package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MyFrame extends JFrame implements Observer {
    private Centralina centralina;
    private JPanel sensorsPanel;
    private Map<String, SensorPanel> panels;

    public MyFrame() {
        super("Casa IoT Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        panels = new HashMap<>();
        centralina = new Centralina(this);

        // MENU
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Esporta");
        exportItem.addActionListener(e -> esportaDati());
        fileMenu.add(exportItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // PANNELLO PULSANTI
        JPanel controlPanel = new JPanel();
        JButton generaBtn = new JButton("Genera valori");
        JButton freezeBtn = new JButton("Freeze");
        JButton nuovoSensoreBtn = new JButton("Nuovo sensore");

        generaBtn.addActionListener(e -> accendiSensori());
        freezeBtn.addActionListener(e -> spegniSensori());
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
        String[] options = {"Sensore Luce", "Sensore Temperatura"};
        JComboBox<String> combo = new JComboBox<>(options);
        JTextField nomeField = new JTextField();

        panel.add(labelTipo);
        panel.add(combo);
        panel.add(labelNome);
        panel.add(nomeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crea Nuovo Sensore",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String scelta = (String) combo.getSelectedItem();
            String nome = nomeField.getText().trim();

            Sensore nuovo = null;
            if (scelta.equals("Sensore Luce")) {
                nuovo = new SensoreLuce(centralina);
            } else if (scelta.equals("Sensore Temperatura")) {
                nuovo = new SensoreTemperatura(centralina);
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

    private void esportaDati() {
        Exporter.esporta(centralina.getDati());
        JOptionPane.showMessageDialog(this, "Dati esportati sul Desktop.");
    }

    @Override
    public void update(String id, Map<String, DatoSensore> dati) {
        if (id != null && panels.containsKey(id)) {
            panels.get(id).updateData(dati.get(id));
        }
    }
}
