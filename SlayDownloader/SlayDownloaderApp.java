import javax.swing.*;
import java.awt.*;

public class SlayDownloaderApp extends JFrame {

    public SlayDownloaderApp() {
        setTitle("SlayDownloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);

        // Warna tema
        Color background = new Color(249, 250, 251);
        Color text = new Color(17, 24, 39);
        Color textSecondary = new Color(107, 114, 128);
        // Color primary = new Color(59, 130, 246);
        // Color secondary = new Color(16, 185, 129);
        Color accent = new Color(245, 158, 11);
        Color card = Color.WHITE;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(background);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int frameWidth = 400;
        mainPanel.setMaximumSize(new Dimension(frameWidth, Integer.MAX_VALUE));
        mainPanel.setPreferredSize(new Dimension(frameWidth, 700));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Header
        JLabel welcomeText = new JLabel("Welcome to");
        welcomeText.setForeground(textSecondary);
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appTitle = new JLabel("SlayDownloader");
        appTitle.setForeground(text);
        appTitle.setFont(new Font("Arial", Font.BOLD, 32));
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Download files and create ZIP archives");
        subtitle.setForeground(textSecondary);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(welcomeText);
        mainPanel.add(appTitle);
        mainPanel.add(subtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Section title
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(background);
        sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // agar center di mainPanel

        // Section title
        JLabel quickActions = new JLabel("Quick Actions");
        quickActions.setForeground(text);
        quickActions.setFont(new Font("Arial", Font.BOLD, 18));
        quickActions.setAlignmentX(Component.LEFT_ALIGNMENT); // teks di tengah
        sectionPanel.add(quickActions);
        sectionPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // ==== Vertical cards container ====
        JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setBackground(background);

        // Tambahkan dua card secara vertikal
        cardsContainer.add(createDashboardCard(
                "Download File",
                "Download files from URL with various formats",
                "asset/down1.jpg", text, textSecondary));
        cardsContainer.add(Box.createRigidArea(new Dimension(10, 10))); // jarak antar card
        cardsContainer.add(createDashboardCard(
                "Compress to ZIP",
                "Upload and compress files into ZIP archive",
                "asset/down2.jpg", text, textSecondary));

        // Tambahkan container ini ke section
        sectionPanel.add(cardsContainer);

        // Tambahkan section ke main panel
        mainPanel.add(sectionPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        // Info section
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setBackground(card);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, accent),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel infoTitle = new JLabel("How to use");
        infoTitle.setFont(new Font("Arial", Font.BOLD, 16));
        infoTitle.setForeground(text);
        infoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoText = new JLabel("<html>• Use Download to save files from URLs<br>" +
                "• Use Compress to create ZIP archives from multiple files<br>" +
                "• All downloads are saved to your device storage</html>");
        infoText.setFont(new Font("Arial", Font.PLAIN, 15));
        infoText.setForeground(textSecondary);
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoText.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));

        infoCard.add(infoTitle);
        infoCard.add(infoText);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(infoCard);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createDashboardCard(String title, String description, String pathGambar, Color text, Color textSecondary) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setAlignmentX(Component.CENTER_ALIGNMENT); 
        // Icon panel
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(Color.WHITE); // biar tidak tumpang warna
        iconPanel.setPreferredSize(new Dimension(56, 56));
        iconPanel.setMaximumSize(new Dimension(56, 56));
        iconPanel.setLayout(new BorderLayout());

        // Gambar icon
        ImageIcon downIcon = null;
        try {
                downIcon = new ImageIcon(getClass().getResource(pathGambar));
        } catch (Exception e) {
                System.out.println("⚠️ Gambar tidak ditemukan di classpath.");
        }
        // Resize gambar biar pas 32x32
        Image scaled = downIcon.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaled));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        iconPanel.add(iconLabel, BorderLayout.CENTER);

        // iconPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding 10px


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(text);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(textSecondary);
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        JButton chevron = new JButton(">");
        chevron.setBackground(new Color(242,157,188,100));
        chevron.setForeground(Color.WHITE);
        chevron.setFocusPainted(false);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(chevron, BorderLayout.EAST);

        card.add(iconPanel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SlayDownloaderApp::new);
    }
}
