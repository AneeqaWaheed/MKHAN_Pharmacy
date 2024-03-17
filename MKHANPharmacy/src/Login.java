import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

    public Login() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical space

        JTextField usernameField = new JTextField(20);
        usernameField.setBorder(new LineBorder(new Color(0, 100, 0), 2, true)); // Dark green border
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, usernameField.getPreferredSize().height));
        usernameField.setText("Username");
        JLabel usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical space

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(new LineBorder(new Color(0, 100, 0), 2, true)); // Dark green border
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));
        passwordField.setText("Password");
        JLabel passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical space

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 100, 0)); // Dark green background
        loginButton.setForeground(Color.WHITE); // White text color
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical space

        JLabel signupLabel = new JLabel("Don't have an account?");
        JTextArea signupLink = new JTextArea("Signup Now");
        signupLink.setForeground(Color.BLUE);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLink.setBorder(BorderFactory.createEmptyBorder());
        signupLink.setOpaque(false);
        signupLink.setEditable(false);
        signupLink.setFocusable(false);
        signupLink.setFont(signupLink.getFont().deriveFont(Font.BOLD));
        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openSignupPage();
            }
        });
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.add(signupLabel);
        signupPanel.add(signupLink);
        loginPanel.add(signupPanel);

        add(loginPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void openSignupPage() {
        Signup Signup = new Signup(); // Assuming your signup page class is named Signup
        Signup.setVisible(true);
        dispose(); // Close the login page if needed
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
