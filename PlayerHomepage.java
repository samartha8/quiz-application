package coursework_final;

import javax.swing.*;

public class PlayerHomepage {
    private JFrame frame;

    public PlayerHomepage() {
        frame = new JFrame("Player Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new java.awt.GridLayout(5, 1));

        JLabel titleLabel = new JLabel("Welcome Player!", JLabel.CENTER);
        frame.add(titleLabel);

        JButton beginnerButton = new JButton("Beginner");
        JButton intermediateButton = new JButton("Intermediate");
        JButton advancedButton = new JButton("Advanced");
        JButton backButton = new JButton("Back");

        frame.add(beginnerButton);
        frame.add(intermediateButton);
        frame.add(advancedButton);
        frame.add(backButton);

        beginnerButton.addActionListener(e -> startQuiz("Beginner"));
        intermediateButton.addActionListener(e -> startQuiz("Intermediate"));
        advancedButton.addActionListener(e -> startQuiz("Advanced"));
        backButton.addActionListener(e -> {
            new Main();  // Back to main application (instead of WelcomePage)
            frame.dispose();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startQuiz(String level) {
        new QuizPage(level);
        frame.dispose();
    }
}
