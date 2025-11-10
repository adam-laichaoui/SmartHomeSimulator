package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Exporter {
    public static void esporta(Map<String, DatoSensore> dati) {
        try {
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "CasaIoT_Dati";
            File folder = new File(desktopPath);
            if (!folder.exists()) folder.mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File file = new File(folder, "sensori_" + timestamp + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<String, DatoSensore> entry : dati.entrySet()) {
                    DatoSensore d = entry.getValue();
                    writer.write(entry.getKey() + ": " + d.getValore() + " " );
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
