/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mkhanpharmacy;

/**
 *
 * @author Aneeqa
 */
// Product.java
public class Product {
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
