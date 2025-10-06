import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlayDownloaderApp extends JFrame {

    private final JTextField urlField;
    private final JTextField savePathField;
    private final JButton browseButton;
    private final JButton downloadButton;
    private final JProgressBar progressBar;
    private final JLabel statusLabel;

    public SlayDownloaderApp() {
        // ========== FRAME ==========
        setTitle("SlayDownloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);

        // ========== WARNA & FONT ==========
        Color background = new Color(249, 250, 251);
        Color text = new Color(17, 24, 39);
        Color textSecondary = new Color(107, 114, 128);
        Color accent = new Color(245, 158, 11);
        Color card = Color.WHITE;

        Font fontRegular = new Font("Arial", Font.PLAIN, 14);
        Font fontBold = new Font("Arial", Font.BOLD, 16);

        // ========== MAIN PANEL ==========
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(background);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ========== HEADER ==========
        JLabel welcomeText = new JLabel("Welcome to");
        welcomeText.setForeground(textSecondary);
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appTitle = new JLabel("SlayDownloader");
        appTitle.setForeground(text);
        appTitle.setFont(new Font("Arial", Font.BOLD, 32));
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Download files everytime you want");
        subtitle.setForeground(textSecondary);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(welcomeText);
        mainPanel.add(appTitle);
        mainPanel.add(subtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // ========== CARD PANEL UNTUK FORM ==========
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(card);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label URL
        JLabel urlLabel = new JLabel("URL File:");
        urlLabel.setForeground(text);
        urlLabel.setFont(fontBold);
        urlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(urlLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        urlField = new JTextField();
        urlField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        urlField.setFont(fontRegular);
        cardPanel.add(urlField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Label Simpan Ke
        JLabel saveLabel = new JLabel("Simpan Ke:");
        saveLabel.setForeground(text);
        saveLabel.setFont(fontBold);
        saveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(saveLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Form path penyimpanan
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.X_AXIS));
        savePanel.setBackground(card);

        savePathField = new JTextField();
        savePathField.setEditable(false);
        savePathField.setFont(fontRegular);
        savePathField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        savePanel.add(savePathField);
        savePanel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Tombol Browse untuk pilih lokasi simpan
        browseButton = new JButton("Browse");
        browseButton.setBackground(accent);
        browseButton.setForeground(Color.WHITE);
        browseButton.setFocusPainted(false);
        browseButton.setFont(fontBold);
        savePanel.add(browseButton);

        cardPanel.add(savePanel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tombol Download
        downloadButton = new JButton("Download");
        downloadButton.setBackground(accent);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.setFont(new Font("Arial", Font.BOLD, 16));
        downloadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(downloadButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        cardPanel.add(progressBar);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Status Label
        statusLabel = new JLabel("Status: Siap Mendownload File");
        statusLabel.setForeground(textSecondary);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(statusLabel);

        // Tambahkan ke main panel
        mainPanel.add(cardPanel);
        add(mainPanel);

        // ========== EVENT LISTENER ==========
        browseButton.addActionListener(e -> chooseSaveLocation());
        downloadButton.addActionListener(e -> startDownload());

        setVisible(true);
    }

    // ======= METHOD: Pilih lokasi simpan =======
    private void chooseSaveLocation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Lokasi Penyimpanan");
        try {
            URL url = new URL(urlField.getText());
            String fileName = url.getPath().substring(url.getPath().lastIndexOf('/') + 1);
            if (!fileName.isEmpty()) {
                fileChooser.setSelectedFile(new java.io.File(fileName));
            }
        } catch (Exception ignored) {}

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            savePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    // ======= METHOD: Mulai download =======
    private void startDownload() {
        String urlText = urlField.getText();
        String savePath = savePathField.getText();

        if (urlText.isEmpty() || savePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "URL dan Lokasi Simpan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        downloadButton.setEnabled(false);
        statusLabel.setText("Status: Memulai unduhan...");
        progressBar.setValue(0);

        DownloadTask task = new DownloadTask(urlText, savePath);
        task.execute();
    }

    // ======= CLASS: SwingWorker untuk proses download di background =======
    private class DownloadTask extends SwingWorker<Void, Integer> {
        private final String urlString;
        private final String savePath;

        public DownloadTask(String urlString, String savePath) {
            this.urlString = urlString;
            this.savePath = savePath;
        }

        @Override
        protected Void doInBackground() throws Exception {
            try {
                URL url = new URL(urlString);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                long fileSize = httpConn.getContentLengthLong();

                if (fileSize <= 0) {
                    throw new IOException("Tidak bisa mendapatkan ukuran file. URL mungkin tidak valid.");
                }

                try (BufferedInputStream inputStream = new BufferedInputStream(httpConn.getInputStream());
                     FileOutputStream outputStream = new FileOutputStream(savePath)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalBytesRead = 0;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;

                        int progress = (int) ((totalBytesRead * 100) / fileSize);
                        publish(progress);
                    }
                }
                httpConn.disconnect();
            } catch (IOException e) {
                throw new IOException("Gagal mengunduh file: " + e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int latestProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(latestProgress);
            statusLabel.setText(String.format("Status: Downloading... %d%%", latestProgress));
        }

        @Override
        protected void done() {
            try {
                get();
                statusLabel.setText("Status: Download Selesai!");
                progressBar.setValue(100);
                JOptionPane.showMessageDialog(SlayDownloaderApp.this, "File berhasil diunduh!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                statusLabel.setText("Status: Gagal!");
                JOptionPane.showMessageDialog(SlayDownloaderApp.this, "Terjadi kesalahan: " + e.getCause().getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                downloadButton.setEnabled(true);
            }
        }
    }

    // ======= MAIN =======
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SlayDownloaderApp::new);
    }
}
