import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Classe responsabile dell'esportazione dei dati dei sensori.
 * Supporta:
 *  - esportazione completo storico centralina
 *  - esportazione contenuto pannello storico (testo già formattato)
 *  - esportazione singolo sensore
 */
public class Exporter {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static final SimpleDateFormat FILE_FORMAT =
            new SimpleDateFormat("yyyyMMdd_HHmmss");

    // 1) ESPORTAZIONE STORICO COMPLETO DALLA CENTRALINA

    public static void esportaStorico(Map<String, List<DatoSensore>> storico) {

        if (storico == null || storico.isEmpty()) {
            System.err.println("[Exporter] Nessun dato da esportare.");
            return;
        }

        File file = creaFile("storico_sensori_");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            scriviHeader(writer, "STORICO COMPLETO DATI SENSORI");

            for (Map.Entry<String, List<DatoSensore>> entry : storico.entrySet()) {

                String id = entry.getKey();
                List<DatoSensore> lista = entry.getValue();

                writer.write("* Sensore: " + id);
                writer.newLine();

                if (lista == null || lista.isEmpty()) {
                    writer.write("   Nessun dato registrato.");
                    writer.newLine();
                    writer.newLine();
                    continue;
                }

                for (DatoSensore d : lista) {
                    writer.write("   • [" + d.getOraFormattata() + "]");
                    writer.write(" → " + d.getValoreFormattato());
                    writer.newLine();
                }

                writer.newLine();
            }

            scriviFooter(writer);

        } catch (IOException e) {
            System.err.println("[Exporter] Errore durante l'esportazione: " + e.getMessage());
        }
    }

    // 2) ESPORTA CIO' CHE SI VEDE NEL PANNELLO STORICO (TESTO GIÀ FORMATTATO)

    public static void esportaDaPannello(PannelloStorico pannello) {

        if (pannello == null) {
            System.err.println("[Exporter] Pannello storico nullo, impossibile esportare.");
            return;
        }

        String contenuto = pannello.getTestoFormattato();

        if (contenuto == null || contenuto.isBlank()) {
            System.err.println("[Exporter] Il pannello storico è vuoto, nulla da esportare.");
            return;
        }

        File file = creaFile("pannello_storico_");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            scriviHeader(writer, "DATI VISUALIZZATI NEL PANNELLO");
            writer.write(contenuto);
            writer.newLine();
            scriviFooter(writer);

        } catch (IOException e) {
            System.err.println("[Exporter] Errore durante l'esportazione dal pannello: " + e.getMessage());
        }
    }

    // 3) ESPORTA SOLO UNO SPECIFICO SENSORE

    public static void esportaSingoloSensore(String id, List<DatoSensore> lista) {

        if (lista == null || lista.isEmpty()) {
            System.err.println("[Exporter] Nessun dato per il sensore " + id);
            return;
        }

        File file = creaFile("storico_" + id + "_");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            scriviHeader(writer, "STORICO DEL SENSORE " + id);

            for (DatoSensore d : lista) {
                writer.write("   • [" + d.getOraFormattata() + "]");
                writer.write(" → " + d.getValoreFormattato());
                writer.newLine();
            }

            scriviFooter(writer);

        } catch (IOException e) {
            System.err.println("[Exporter] Errore export singolo sensore: " + e.getMessage());
        }
    }

    // METODI DI SUPPORTO

    /**
     * Crea un file con prefisso + timestamp.
     */
    private static File creaFile(String prefix) {

        File folder = new File(
                System.getProperty("user.home") + File.separator +
                "Desktop" + File.separator +
                "CasaIoT_Dati"
        );

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String timestamp = FILE_FORMAT.format(new Date());
        return new File(folder, prefix + timestamp + ".txt");
    }

    private static void scriviHeader(BufferedWriter writer, String titolo) throws IOException {
        writer.write("═══════════════════════════════════════════");
        writer.newLine();
        writer.write( titolo);
        writer.newLine();
        writer.write("   Generato il: " + DATE_FORMAT.format(new Date()));
        writer.newLine();
        writer.write("═══════════════════════════════════════════");
        writer.newLine();
        writer.newLine();
    }

    private static void scriviFooter(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("═══════════════════════════════════════════");
        writer.newLine();
        writer.write("         ESPORTAZIONE COMPLETATA");
        writer.newLine();
        writer.write("═══════════════════════════════════════════");
        writer.newLine();
    }
}
