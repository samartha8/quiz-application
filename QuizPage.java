package coursework_final;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class QuizPage {
    private JFrame frame;
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int[] roundScores = new int[3]; // Array to store scores for each round
    private int roundCount = 0; // Counter to track rounds
    private int questionCount = 0; // Counter to track questions in each round
    private String playerName;
    private String difficultyLevel;

    public QuizPage(String level) {
        this.difficultyLevel = level;
        // Prompt for player name
        playerName = JOptionPane.showInputDialog("Enter your name:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Anonymous";  // Default to Anonymous if no name is entered
        }

        frame = new JFrame("Quiz - " + level);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel("", JLabel.CENTER);
        frame.add(questionLabel);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            frame.add(optionButtons[i]);
            optionButtons[i].addActionListener(e -> checkAnswer(((JButton) e.getSource()).getText()));
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new PlayerHomepage(); // Redirect to PlayerHomepage
            frame.dispose();
        });

        frame.add(backButton);

        QuizManager manager = new QuizManager();
        manager.loadQuestions(level);
        questions = manager.getQuestions();

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No questions available for this level!", "Error", JOptionPane.ERROR_MESSAGE);
            new PlayerHomepage();
            frame.dispose();
        } else {
            loadNextRound();
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadNextRound() {
        if (roundCount < 3) {
            // Shuffle questions for the round
            QuizManager manager = new QuizManager();
            manager.loadQuestions(difficultyLevel); // or based on selected level
            questions = manager.getQuestions();
            java.util.Collections.shuffle(questions);

            // Reset question count for this round
            currentQuestionIndex = 0;
            questionCount = 0;
            score = 0; // Reset score for the new round

            // Load first question of the round
            loadNextQuestion();
        } else {
            // After all rounds, calculate total and average score
            int totalScore = 0;
            for (int roundScore : roundScores) {
                totalScore += roundScore;
            }
            double averageScore = totalScore / 3.0;

            JOptionPane.showMessageDialog(frame,
                "Quiz Finished!\n" +
                "Round 1 Score: " + roundScores[0] + "\n" +
                "Round 2 Score: " + roundScores[1] + "\n" +
                "Round 3 Score: " + roundScores[2] + "\n" +
                "Total Score: " + totalScore + "\n" +
                "Average Score: " + String.format("%.2f", averageScore)
            );

            // Store Player info in the database
            storePlayerInfo(playerName, totalScore, difficultyLevel);

            // Store report in the database
            storeReport(playerName, totalScore, difficultyLevel);

            new PlayerHomepage(); // Return to PlayerHomepage after finishing the quiz
            frame.dispose();
        }
    }

    private void loadNextQuestion() {
        if (questionCount >= 5) {
            // End of the current round
            roundScores[roundCount] = score; // Store score for this round
            roundCount++;
            JOptionPane.showMessageDialog(frame, "Round " + (roundCount) + " Finished!");

            // After 3 rounds, display the total score and average score
            if (roundCount < 3) {
                JOptionPane.showMessageDialog(frame, "Starting Round " + (roundCount + 1));
            }
            loadNextRound();
            return;
        }

        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            if (q != null) {
                questionLabel.setText(q.getText());
                optionButtons[0].setText(q.getOption1());
                optionButtons[1].setText(q.getOption2());
                optionButtons[2].setText(q.getOption3());
                optionButtons[3].setText(q.getOption4());
            }
        }
    }

    private void checkAnswer(String selectedAnswer) {
        Question q = questions.get(currentQuestionIndex);
        if (q.getCorrectAnswer().equals(selectedAnswer)) {
            score++;
        }

        currentQuestionIndex++;
        questionCount++;
        loadNextQuestion();
    }

    // Method to store player info in the database
    private void storePlayerInfo(String playerName, int totalScore, String difficultyLevel) {
        String insertPlayerQuery = "INSERT INTO Players (player_name, score, difficulty_level) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement(insertPlayerQuery)) {

            statement.setString(1, playerName);
            statement.setInt(2, totalScore);
            statement.setString(3, difficultyLevel);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to store the player's report
    private void storeReport(String playerName, int totalScore, String difficultyLevel) {
        String selectPlayerQuery = "SELECT player_id FROM Players WHERE player_name = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement(selectPlayerQuery)) {

            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int playerId = resultSet.getInt("player_id");

                String insertReportQuery = "INSERT INTO Reports (player_id, score, difficulty_level) VALUES (?, ?, ?)";

                try (PreparedStatement reportStatement = connection.prepareStatement(insertReportQuery)) {
                    reportStatement.setInt(1, playerId);
                    reportStatement.setInt(2, totalScore);
                    reportStatement.setString(3, difficultyLevel);
                    reportStatement.executeUpdate();
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
