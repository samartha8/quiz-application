package coursework_final;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizManager {
    private List<Question> questions = new ArrayList<>();
    private int score = 0;

    // Method to load questions based on the level
    public void loadQuestions(String level) {
        questions.clear(); // Clear any existing questions

        // Query to fetch questions based on the difficulty level
        String query = "SELECT * FROM Questions WHERE difficulty_level = ? ORDER BY RAND() LIMIT 5";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Setting the level parameter
            statement.setString(1, level);

            ResultSet resultSet = statement.executeQuery();

            // Loop through the result set and create Question objects
            while (resultSet.next()) {
                int id = resultSet.getInt("question_id");  // 'question_id' as per your schema
                String text = resultSet.getString("question_text");
                String option1 = resultSet.getString("option1");
                String option2 = resultSet.getString("option2");
                String option3 = resultSet.getString("option3");
                String option4 = resultSet.getString("option4");
                String correctAnswer = resultSet.getString("correct_answer");

                // Add question to list
                questions.add(new Question(id, text, option1, option2, option3, option4, correctAnswer));
            }

            System.out.println("Loaded Questions: " + questions.size());

            // Shuffle questions to randomize the order
            Collections.shuffle(questions);

        } catch (SQLException e) {
            System.err.println("Error loading questions from the database.");
            e.printStackTrace();
        }
    }

    // Getter for the list of questions
    public List<Question> getQuestions() {
        return questions;
    }

    // Method to handle answering questions and updating score
    public void answerQuestion(Question question, String answer) {
        if (question.getCorrectAnswer().equals(answer)) {
            score++;
        }
    }

    // Method to get the current score
    public int getScore() {
        return score;
    }

    // Reset the score (useful if a new quiz session is started)
    public void resetScore() {
        score = 0;
    }
}
