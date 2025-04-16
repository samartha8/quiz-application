package coursework_final;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;

    public Main() {
        frame = new JFrame("Quiz System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 1));

        JLabel welcomeLabel = new JLabel("Welcome to the PUBG Quiz System!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(welcomeLabel);

        JButton playerButton = new JButton("Player");
        JButton adminButton = new JButton("Admin");

        frame.add(playerButton);
        frame.add(adminButton);

        playerButton.addActionListener(e -> {
            new PlayerHomepage(); // Open Player Homepage
            frame.dispose();
        });

        adminButton.addActionListener(e -> {
            new AdminLogin(); // Open Admin Login Page
            frame.dispose();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
