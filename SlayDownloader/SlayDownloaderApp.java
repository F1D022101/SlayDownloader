import javax.swing.*;
import java.awt.*;

public class SlayDownloaderApp extends JFrame {

    public SlayDownloaderApp() {
        setTitle("File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);

        // Warna tema
        Color background = new Color(249, 250, 251);
        Color text = new Color(17, 24, 39);
        Color textSecondary = new Color(107, 114, 128);
        Color primary = new Color(59, 130, 246);
        Color secondary = new Color(16, 185, 129);
        Color accent = new Color(245, 158, 11);
        Color card = Color.WHITE;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(background);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Header
        JLabel welcomeText = new JLabel("Welcome to");
        welcomeText.setForeground(textSecondary);
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appTitle = new JLabel("File Manager");
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
                primary, text, textSecondary));
        cardsContainer.add(Box.createRigidArea(new Dimension(0, 10))); // jarak antar card
        cardsContainer.add(createDashboardCard(
                "Compress to ZIP",
                "Upload and compress files into ZIP archive",
                secondary, text, textSecondary));

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

        JLabel infoText = new JLabel("<html>• Use Download to save files from URLs<br>" +
                "• Use Compress to create ZIP archives from multiple files<br>" +
                "• All downloads are saved to your device storage</html>");
        infoText.setFont(new Font("Arial", Font.PLAIN, 14));
        infoText.setForeground(textSecondary);

        infoCard.add(infoTitle);
        infoCard.add(infoText);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(infoCard);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createDashboardCard(String title, String description, Color color, Color text, Color textSecondary) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Icon panel
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(color);
        iconPanel.setPreferredSize(new Dimension(56, 56));
        iconPanel.setMaximumSize(new Dimension(56, 56));

        // Texts
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(text);

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(textSecondary);

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        JButton chevron = new JButton(">");
        chevron.setBackground(color);
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
