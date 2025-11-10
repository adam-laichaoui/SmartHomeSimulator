package main;

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

        // Usa l'ID testuale del sensore, non quello di Thread
        setBorder(BorderFactory.createTitledBorder(
            s.getClass().getSimpleName() + " (" + s.getIdSensore() + ")"
        ));

        //  Mostra il nome personalizzato se presente
        nomeLabel = new JLabel("Nome: " +
            (s.getNomePersonalizzato() != null ? s.getNomePersonalizzato() : "N/D"));

        statoLabel = new JLabel("Stato: OFF");
        valoreLabel = new JLabel("Valore: N/D");
        powerBtn = new JButton("Accendi");

        powerBtn.addActionListener(e -> togglePower());

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
