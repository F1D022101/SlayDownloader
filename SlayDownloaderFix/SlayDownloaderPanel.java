import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public class SlayDownloaderPanel extends JPanel {

    private final JTextField urlField = new JTextField();
    private final JTextField savePathField = new JTextField();
    private final JButton browseButton = new JButton("Browse");
    private final JButton downloadButton = new JButton("Download");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel statusLabel = new JLabel("Status: Siap Mendownload File");
    private final JLabel imagePreview = new JLabel();

    public SlayDownloaderPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(249, 250, 251));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font bold = new Font("Arial", Font.BOLD, 16);
        Font regular = new Font("Arial", Font.PLAIN, 14);
        Color accent = new Color(245, 158, 11);

        add(centeredLabel("Welcome to", regular, new Color(107, 114, 128)));
        add(centeredLabel("SlayDownloader", new Font("Arial", Font.BOLD, 32), new Color(17, 24, 39)));
        add(centeredLabel("Download files everytime you want", regular, new Color(107, 114, 128)));
        add(Box.createVerticalStrut(20));

        JPanel card = createCardPanel();
        addField(card, "URL File:", urlField, bold, regular);
        addBrowseSection(card, "Simpan Ke:", savePathField, browseButton, bold, regular, accent);
        card.add(Box.createVerticalStrut(10));

        downloadButton.setFont(bold);
        downloadButton.setBackground(accent);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setAlignmentX(CENTER_ALIGNMENT);
        card.add(downloadButton);
        card.add(Box.createVerticalStrut(10));

        progressBar.setStringPainted(true);
        card.add(progressBar);

        statusLabel.setFont(regular);
        statusLabel.setForeground(new Color(107, 114, 128));
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        card.add(statusLabel);

        imagePreview.setAlignmentX(CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(imagePreview);

        add(card);

        browseButton.addActionListener(e -> chooseSaveLocation());
        downloadButton.addActionListener(e -> startDownload());

        urlField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { showPreview(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { showPreview(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { showPreview(); }
        });
    }

    private JLabel centeredLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panel.setAlignmentX(CENTER_ALIGNMENT);
        return panel;
    }

    private void addField(JPanel panel, String labelText, JTextField field, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(labelFont);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        field.setFont(fieldFont);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    private void addBrowseSection(JPanel panel, String labelText, JTextField field, JButton button,
                                  Font labelFont, Font fieldFont, Color buttonColor) {
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(labelFont);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        innerPanel.setBackground(Color.WHITE);

        field.setFont(fieldFont);
        field.setEditable(false);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        innerPanel.add(field);
        innerPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        button.setFont(labelFont);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        innerPanel.add(button);

        panel.add(innerPanel);
    }

    private void chooseSaveLocation() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Lokasi Penyimpanan");

        try {
            URL url = new URL(urlField.getText());
            String fileName = new File(url.getPath()).getName();
            fileChooser.setSelectedFile(new File(fileName));
        } catch (Exception ignored) {}

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            savePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void showPreview() {
        String url = urlField.getText().trim();
        if (url.matches(".*\\.(png|jpg|jpeg)$")) {
            try {
                ImageIcon icon = new ImageIcon(new URL(url));
                Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                imagePreview.setIcon(new ImageIcon(img));
                imagePreview.setText(null);
            } catch (Exception e) {
                imagePreview.setIcon(null);
                imagePreview.setText("⚠ Gagal memuat gambar");
            }
        } else {
            imagePreview.setIcon(null);
            imagePreview.setText(null);
        }
    }

    private void startDownload() {
        String url = urlField.getText().trim();
        String savePath = savePathField.getText().trim();

        if (url.isEmpty() || savePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "URL dan path tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        progressBar.setValue(0);
        progressBar.repaint();
        statusLabel.setText("Status: Mengunduh...");
        downloadButton.setEnabled(false);

        new FileDownloadTask(url, savePath).execute();
    }

    private class FileDownloadTask extends SwingWorker<Void, Integer> {
        private final String url, path;
        private int lastPublished = 0;

        FileDownloadTask(String url, String path) {
            this.url = url;
            this.path = path;
        }

        protected Void doInBackground() throws Exception {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            long fileSize = conn.getContentLengthLong();

            try (BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                 FileOutputStream out = new FileOutputStream(path)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                long total = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    total += bytesRead;
                    int progress = (int) ((total * 100) / fileSize);
                    if (progress >= lastPublished + 10) {
                        lastPublished = progress;
                        publish(progress);
                    }
                }
            }
            return null;
        }

        protected void process(List<Integer> chunks) {
            progressBar.setValue(chunks.get(chunks.size() - 1));
        }

        protected void done() {
            progressBar.setValue(100);
            statusLabel.setText("Status: Download selesai!");
            downloadButton.setEnabled(true);
            saveToHistory(url, "File Biasa", path);
            showFinishedPopup(path);
        }
    }

    private void showFinishedPopup(String path) {
        JOptionPane.showMessageDialog(this,
                "✅ File berhasil disimpan di:\n" + path,
                "Download Selesai", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveToHistory(String url, String format, String path) {
        try (PrintWriter out = new PrintWriter(new FileWriter("history.txt", true))) {
            out.println("URL: " + url);
            out.println("Format: File Biasa");
            out.println("Saved To: " + path);
            out.println("Waktu: " + LocalDateTime.now());
            out.println("====================================");
        } catch (IOException e) {
            System.err.println("❌ Gagal menyimpan ke history: " + e.getMessage());
        }
    }
}