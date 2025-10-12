import javax.swing.*;

public class SlayDownloaderApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SlayDownloader");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 750);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new SlayDownloaderPanel()); // load panel
            frame.setVisible(true);
        });
    }
}
