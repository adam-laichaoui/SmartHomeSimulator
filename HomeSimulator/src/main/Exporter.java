//package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Exporter {
    public static void esportaStorico(Map<String, List<DatoSensore>> storico) {
        try {
            String desktopPath = System.getProperty("user.home") + File.separator +
                    "Desktop" + File.separator + "CasaIoT_Dati";
            File folder = new File(desktopPath);
            if (!folder.exists()) folder.mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File file = new File(folder, "storico_sensori_" + timestamp + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("=== Storico dati sensori ===");
                writer.newLine();
                writer.write("Generato: " + new Date());
                writer.newLine();
                writer.newLine();

                for (Map.Entry<String, List<DatoSensore>> entry : storico.entrySet()) {
                    writer.write("Sensore: " + entry.getKey());
                    writer.newLine();
                    for (DatoSensore d : entry.getValue()) {
                        writer.write("  " + d.getOraFormattata() + "  →  " + d.getValoreFormattato() );
                        writer.newLine();
                    }
                    writer.newLine();
                }
            }

            System.out.println("File esportato in: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
