// ProductListPage.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductListPage extends JFrame {

     private List<Product> products;
    private JPanel productListPanel;
    private CartPage cartPage;

    public ProductListPage() {
        setSize(800, 600); // Set the size of the frame to 800x600 pixels
        setTitle("Product List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.products = new ArrayList<>();
        fetchProductsFromDatabase();

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5; // Set the weight to make the search bar half of the screen width

        JTextField searchBar = new JTextField("Search for products");
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for products")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText("Search for products");
                }
            }
        });

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterProducts(searchBar.getText().toLowerCase());
            }
        });
        contentPanel.add(searchBar, gbc);

        JLabel cartLabel = new JLabel("My Cart");
        cartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1;
        gbc.weightx = 0.5; // Set the weight to make the cart label half of the screen width
        contentPanel.add(cartLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0; // Allow the search bar and cart label to take full height
        gbc.fill = GridBagConstraints.BOTH;

        productListPanel = new JPanel(); // Initialize productListPanel
        productListPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Display three products in a row
        productListPanel.setBackground(Color.WHITE);
        displayProducts(products);

        contentPanel.add(new JScrollPane(productListPanel), gbc);

        add(contentPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        cartPage = new CartPage(); // Initialize the cart page
    }

    private void fetchProductsFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mkhan_pharmacy", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT `Medicine Name`, Composition, `Image URL` FROM medicines");

            while (rs.next()) {
                String name = rs.getString("Medicine Name");
                String composition = rs.getString("Composition");
                String imageUrl = rs.getString("Image URL");

                products.add(new Product(name, composition, imageUrl));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayProducts(List<Product> productList) {
        productListPanel.removeAll();
        int numProductsPerRow = 3; // Number of products to display per row
        int panelWidth = getContentPane().getWidth() / numProductsPerRow - 20; // Calculate the preferred width for each panel

        for (Product product : productList) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            productPanel.setPreferredSize(new Dimension(panelWidth, panelWidth + 100)); // Set preferred size for each panel

            JLabel nameLabel = new JLabel(product.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel compositionLabel = new JLabel("Composition: " + product.getComposition());
            compositionLabel.setHorizontalAlignment(SwingConstants.CENTER);

            ImageIcon imageIcon = new ImageIcon(product.getImageUrl());
            Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledImageIcon, JLabel.CENTER);

            JButton addButton = new JButton("Add to Cart");
            addButton.setBackground(new Color(0, 100, 0)); // Set button background color to green
            addButton.addActionListener(e -> {
                cartPage.addItem(product); // Add the product to the cart
            });

            productPanel.add(imageLabel, BorderLayout.CENTER);
            productPanel.add(nameLabel, BorderLayout.NORTH);
            productPanel.add(compositionLabel, BorderLayout.CENTER);
            productPanel.add(addButton, BorderLayout.SOUTH);

            productListPanel.add(productPanel);
        }
        productListPanel.revalidate();
        productListPanel.repaint();
    }

    private void filterProducts(String query) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(query)) {
                filteredProducts.add(product);
            }
        }
        displayProducts(filteredProducts);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductListPage::new);
    }

    private static class Product {
        private String name;
        private String composition;
        private String imageUrl;

        public Product(String name, String composition, String imageUrl) {
            this.name = name;
            this.composition = composition;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getComposition() {
            return composition;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
