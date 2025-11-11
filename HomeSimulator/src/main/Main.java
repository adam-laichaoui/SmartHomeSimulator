
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Impossibile impostare il look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            // 1Crea la GUI principale, ma non la mostra ancora
            MyFrame frame = new MyFrame();
            frame.setVisible(false);

            // 2️ Crea e mostra lo splash screen
            SplashScreen splash = new SplashScreen();
            splash.showForSeconds(3, frame); // mostra per 3 secondi, poi mostra il frame
        });
    }
}
