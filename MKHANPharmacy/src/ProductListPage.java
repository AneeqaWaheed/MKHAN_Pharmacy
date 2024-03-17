import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class ProductListPage extends JFrame {

    private List<Product> products;

    public ProductListPage() {
        this.products = new ArrayList<>();
        products.add(new Product("Product 1", "Description of Product 1", "/Images/landingpage.jpg"));
        products.add(new Product("Product 2", "Description of Product 2", "/Images/landingpage.jpg"));
        products.add(new Product("Product 3", "Description of Product 3", "/Images/landingpage.jpg"));

        setTitle("Product List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

// Search bar with icon and text
JPanel searchPanel = new JPanel(new BorderLayout());
searchPanel.setBackground(Color.WHITE);
JLabel searchIconLabel = new JLabel(new ImageIcon("search-icon.png"));
JTextField searchBar = new JTextField(20);
searchBar.setBorder(BorderFactory.createCompoundBorder(
        searchBar.getBorder(),
        BorderFactory.createEmptyBorder(5, 30, 5, 5))); // Add padding to the text field
searchBar.setFont(new Font("Arial", Font.PLAIN, 14));
searchBar.setForeground(Color.BLACK); // Set the text color to black
searchBar.setMargin(new Insets(2, 5, 2, 5)); // Add margin to the text field
searchBar.setText("Search for products");
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
searchPanel.add(searchIconLabel, BorderLayout.WEST);
searchPanel.add(searchBar, BorderLayout.CENTER);
add(searchPanel, BorderLayout.NORTH);



        // Product list
        JPanel productListPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productListPanel.setBackground(Color.WHITE);
        for (Product product : products) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            JLabel nameLabel = new JLabel("<html><b>" + product.getName() + "</b></html>");
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel descriptionLabel = new JLabel(product.getDescription());
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(product.getImageUrl()));
            Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledImageIcon, JLabel.CENTER);
            JButton addButton = new JButton("Add to Cart");
            addButton.setBackground(Color.LIGHT_GRAY);
            addButton.setForeground(Color.WHITE);

            productPanel.add(imageLabel, BorderLayout.CENTER);
            productPanel.add(nameLabel, BorderLayout.NORTH);
            productPanel.add(descriptionLabel, BorderLayout.CENTER);
            productPanel.add(addButton, BorderLayout.SOUTH);

            productListPanel.add(productPanel);
        }
        add(new JScrollPane(productListPanel), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductListPage());
    }

    private static class Product {
        private String name;
        private String description;
        private String imageUrl;

        public Product(String name, String description, String imageUrl) {
            this.name = name;
            this.description = description;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
