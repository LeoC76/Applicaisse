package stage;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Lancement de l'application
        SwingUtilities.invokeLater(() -> {
            ApplicationCaisse app = new ApplicationCaisse();
            app.setVisible(true);
        });
    }
}

