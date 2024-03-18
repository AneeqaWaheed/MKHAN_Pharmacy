import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Signup extends JFrame {

    public Signup() {
        setTitle("Signup Page");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupPanel.add(welcomeLabel);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical space

        JLabel createAccountLabel = new JLabel("Create Your Account");
        createAccountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, createAccountLabel.getPreferredSize().height)); // Set maximum width
        signupPanel.add(createAccountLabel);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical space

        JLabel privacyPolicyLabel = new JLabel("<html><body style='width: 280px;'>All information provided is kept confidential and will not be disclosed except for the purpose of verification</body></html>");
        privacyPolicyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupPanel.add(privacyPolicyLabel);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical space

        JLabel contactInfoLabel = new JLabel("Primary Contact Information");
        contactInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contactInfoLabel.setForeground(Color.BLUE);
        contactInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupPanel.add(contactInfoLabel);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical space

        String[] fieldLabels = {"First Name", "Middle Name", "Last Name", "Date of Birth", "Contact Number", "Email", "Password", "Full Address"};
        JComponent[] fields = new JComponent[fieldLabels.length];
        for (int i = 0; i < fieldLabels.length; i++) {
            final int index = i; // Create final variable inside the loop
            if (fieldLabels[i].equals("Password")) {
                JPasswordField passwordField = new JPasswordField();
                passwordField.setBorder(new LineBorder(new Color(0, 100, 0), 1, true)); // Green border, 1 pixel thickness
                passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));
                passwordField.setForeground(Color.GRAY);
                passwordField.setText(fieldLabels[i]);
                passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        JPasswordField field = (JPasswordField) evt.getSource();
                        if (String.valueOf(field.getPassword()).equals(fieldLabels[index])) {
                            field.setText("");
                            field.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        JPasswordField field = (JPasswordField) evt.getSource();
                        if (String.valueOf(field.getPassword()).isEmpty()) {
                            field.setText(fieldLabels[index]);
                            field.setForeground(Color.GRAY);
                        }
                    }
                });
                fields[i] = passwordField;
            } else {
                JTextField textField = new JTextField();
                textField.setBorder(new LineBorder(new Color(0, 100, 0), 1, true)); // Green border, 1 pixel thickness
                textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
                textField.setForeground(Color.GRAY);
                textField.setText(fieldLabels[i]);
                textField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        JTextField field = (JTextField) evt.getSource();
                        if (field.getText().equals(fieldLabels[index])) {
                            field.setText("");
                            field.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        JTextField field = (JTextField) evt.getSource();
                        if (field.getText().isEmpty()) {
                            field.setText(fieldLabels[index]);
                            field.setForeground(Color.GRAY);
                        }
                    }
                });
                fields[i] = textField;
            }
            signupPanel.add(fields[i]);
            signupPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical space
        }

        JButton signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(0, 100, 0)); // Dark green background
        signupButton.setForeground(Color.WHITE); // White text color
        signupButton.setBorderPainted(false);
        signupButton.setFocusPainted(false);
        signupPanel.add(signupButton);
        signupPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical space

        signupButton.addActionListener(e -> {
            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO `users` (`First Name`, `Middle Name`, `Last Name`, `Date of Birth`, `Contact Number`, `Email`, `Password`, `Full Address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, ((JTextField) fields[0]).getText()); // First Name
                    pstmt.setString(2, ((JTextField) fields[1]).getText()); // Middle Name
                    pstmt.setString(3, ((JTextField) fields[2]).getText()); // Last Name

                    // Parse and set Date of Birth
                    String dobInput = ((JTextField) fields[3]).getText();
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date parsedDate = dateFormat.parse(dobInput);
                        java.sql.Date dob = new java.sql.Date(parsedDate.getTime());
                        pstmt.setDate(4, dob);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Invalid Date of Birth format. Please use dd/MM/yyyy.");
                        return; // Exit the ActionListener
                    }

                    pstmt.setString(5, ((JTextField) fields[4]).getText()); // Contact Number
                    pstmt.setString(6, ((JTextField) fields[5]).getText()); // Email
                    pstmt.setString(7, ((JPasswordField) fields[6]).getText()); // Password
                    pstmt.setString(8, ((JTextField) fields[7]).getText()); // Full Address

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "User registered successfully");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to register user");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        });

        add(signupPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mkhan_pharmacy";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Signup::new);
    }
}
