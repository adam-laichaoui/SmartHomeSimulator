//package main;

import javax.swing.*;
import java.awt.*;

public class SensorPanel extends JPanel {
    private Sensore sensore;
    private JLabel statoLabel;
    private JLabel valoreLabel;
    private JLabel nomeLabel;
    private JButton powerBtn;

    public SensorPanel(Sensore s) {
    this.sensore = s;
    setLayout(new FlowLayout(FlowLayout.LEFT));

    // Usa l'ID testuale del sensore
    setBorder(BorderFactory.createTitledBorder(
        s.getClass().getSimpleName() + " (" + s.getIdSensore() + ")"
    ));

    // Nuova label per il tipo sensore
    JLabel tipoLabel = new JLabel("Tipo: " + s.getTipo().getDescrizione());
    tipoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

    // Colore in base al tipo
    switch (sensore.getTipo()) {
        case LUCE -> tipoLabel.setForeground(Color.YELLOW.darker());//darker() serve per migliorare la leggibilità del colore 
        case TEMPERATURA -> tipoLabel.setForeground(Color.RED);
        case UMIDITA -> tipoLabel.setForeground(Color.CYAN.darker());
        case  FUMO -> tipoLabel.setForeground(Color.GREEN.darker());
        case MOVIMENTO -> tipoLabel.setForeground(Color.MAGENTA.darker());
    }

    // Mostra nome personalizzato
    nomeLabel = new JLabel("Nome: " +
        (s.getNomePersonalizzato() != null ? s.getNomePersonalizzato() : "N/D"));

    statoLabel = new JLabel("Stato: " + (s.isAcceso() ? "ON" : "OFF"));
    valoreLabel = new JLabel("Valore: N/D");
    powerBtn = new JButton(s.isAcceso() ? "Spegni" : "Accendi");

    powerBtn.addActionListener(e -> togglePower());

    //Aggiungi tutti i componenti
    add(tipoLabel);
    add(nomeLabel);
    add(statoLabel);
    add(valoreLabel);
    add(powerBtn);
}


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
}
