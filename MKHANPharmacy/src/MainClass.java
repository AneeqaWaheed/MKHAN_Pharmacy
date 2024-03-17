
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aneeqa
 */
public class MainClass {
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        LandingPage landingPage = new LandingPage();
        landingPage.setVisible(true);
    });
}

    
}
