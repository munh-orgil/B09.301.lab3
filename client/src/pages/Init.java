package pages;

import java.awt.Color;

import javax.swing.JFrame;

public class Init {

    public Init() {
        JFrame frame = new JFrame("Products");
        Products products = new Products();

        frame.add(products);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBackground(Color.white);
        frame.setVisible(true);
        products.frame = frame;
    }
}
