import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class LandingPage extends JFrame {

    public LandingPage() {
        setTitle("MKHAN Pharmacy");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;

        // Left panel for heading and welcome message
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add margin of 20 pixels on all sides

        JLabel welcomeLabel = new JLabel("Welcome to MKHAN");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(0, 100, 0)); // Dark green color
        leftPanel.add(welcomeLabel);

        JLabel pharmacy = new JLabel("Pharmacy  ");
        pharmacy.setFont(new Font("Arial", Font.BOLD, 28));
        pharmacy.setForeground(new Color(3, 23, 252)); // Dark green color
        leftPanel.add(pharmacy);

        // Add vertical margin between pharmacy and tagline
        leftPanel.add(Box.createVerticalStrut(50)); // 10 pixels of vertical space

        JLabel tagLine = new JLabel("Spreading Good Health and Happiness ");
        tagLine.setFont(new Font("Arial", Font.PLAIN, 16));
        tagLine.setForeground(Color.BLACK);
        leftPanel.add(tagLine);

        // Add left panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(leftPanel, gbc);

        // Right panel for image
     JPanel rightPanel = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw gradient-filled rectangle aligned to the right
        int rectWidth = getWidth() / 2; // Set the width of the rectangle to half of the panel width
        int rectHeight = getHeight();
        int rectX = getWidth() - rectWidth; // Align the rectangle to the right
        GradientPaint gradient = new GradientPaint(rectX, 0, new Color(0, 255, 0), rectX, rectHeight, new Color(0, 0, 255));
        g2d.setPaint(gradient);
        g2d.fillRect(rectX, 0, rectWidth, rectHeight);

        // Draw image in a circle
        int diameter = Math.min(getWidth(), getHeight());
        int reducedDiameter = (int) (diameter * 0.8); // Reduce the diameter by 20%
        int xOffset = (diameter - reducedDiameter) / 2;
        int yOffset = (diameter - reducedDiameter) / 2;
        Shape circle = new Ellipse2D.Double(xOffset, yOffset, reducedDiameter, reducedDiameter);
        g2d.clip(circle);
        g2d.drawImage(new ImageIcon(getClass().getResource("/Images/landingpage.jpg")).getImage(), xOffset, yOffset, reducedDiameter, reducedDiameter, null);

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
};
       
        // Add right panel to main panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(rightPanel, gbc);

     
// Oval shaped button for signup/login with border radius
JButton ovalButton = new JButton("Click here to become a part of UK's leading Pharmacy chain") {
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(new Color(0, 100, 0)); // Dark green color when pressed
        } else {
            g.setColor(Color.GREEN); // Green color when not pressed
        }
        int width = getWidth();
        int height = getHeight();
        int borderRadius = 20; // Radius for border
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, borderRadius, borderRadius));
        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 40); // Set larger preferred size for oval button
    }
};
ovalButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Login());
        });

ovalButton.setBackground(new Color(0, 100, 0));
ovalButton.setForeground(Color.WHITE); // Text color
ovalButton.setBorderPainted(false);
ovalButton.setFocusPainted(false);

        // Add oval button to main panel
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(ovalButton, gbc);

        // Add main panel to frame
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LandingPage());
    }
}
