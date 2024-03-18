// CartPage.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends JFrame {

     private List<Product> cartItems;
    private JPanel cartPanel;
    private JLabel totalItemsLabel;

    public CartPage() {
        setTitle("Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        cartItems = new ArrayList<>();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        totalItemsLabel = new JLabel("Total Items: 0");
        contentPanel.add(totalItemsLabel, BorderLayout.NORTH);

        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(cartPanel);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle checkout action
            }
        });
        contentPanel.add(checkoutButton, BorderLayout.SOUTH);

        add(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addItem(Product product) {
        cartItems.add(product);
        updateCart();
    }

    private void updateCart() {
        cartPanel.removeAll();

        int totalItems = 0;
        for (Product product : cartItems) {
            JPanel itemPanel = new JPanel(new BorderLayout());

            JLabel nameLabel = new JLabel(product.getName());
            itemPanel.add(nameLabel, BorderLayout.NORTH);

            JLabel compositionLabel = new JLabel("Composition: " + product.getComposition());
            itemPanel.add(compositionLabel, BorderLayout.CENTER);

            ImageIcon imageIcon = new ImageIcon(product.getImageUrl());
            Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledImageIcon, JLabel.CENTER);
            itemPanel.add(imageLabel, BorderLayout.WEST);

            JPanel quantityPanel = new JPanel();
            JLabel quantityLabel = new JLabel("Quantity:");
            quantityPanel.add(quantityLabel);
            SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1); // initial, min, max, step
            JSpinner quantitySpinner = new JSpinner(spinnerModel);
            quantityPanel.add(quantitySpinner);
            itemPanel.add(quantityPanel, BorderLayout.EAST);

            cartPanel.add(itemPanel);

            totalItems++;
        }

        totalItemsLabel.setText("Total Items: " + totalItems);
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CartPage::new);
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
