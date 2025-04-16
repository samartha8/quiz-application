package coursework_final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateQuestionPage {
    private JFrame frame;
    private JTextField questionTextField;
    private JTextField option1TextField;
    private JTextField option2TextField;
    private JTextField option3TextField;
    private JTextField option4TextField;
    private JTextField correctAnswerTextField;
    private JComboBox<String> difficultyLevelComboBox;
    private JComboBox<String> questionTextComboBox;

    public UpdateQuestionPage() {
        frame = new JFrame("Update Question");
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Update Question", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Question Text Combo Box
        JLabel questionLabel = new JLabel("Select Question:");
        questionTextComboBox = new JComboBox<>();
        loadQuestionTexts();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(questionLabel, gbc);

        gbc.gridx = 1;
        frame.add(questionTextComboBox, gbc);

        // Question Text Field
        JLabel questionTextLabel = new JLabel("Question Text:");
        questionTextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(questionTextLabel, gbc);

        gbc.gridx = 1;
        frame.add(questionTextField, gbc);

        // Option 1 Text Field
        JLabel option1Label = new JLabel("Option 1:");
        option1TextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(option1Label, gbc);

        gbc.gridx = 1;
        frame.add(option1TextField, gbc);

        // Option 2 Text Field
        JLabel option2Label = new JLabel("Option 2:");
        option2TextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(option2Label, gbc);

        gbc.gridx = 1;
        frame.add(option2TextField, gbc);

        // Option 3 Text Field
        JLabel option3Label = new JLabel("Option 3:");
        option3TextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(option3Label, gbc);

        gbc.gridx = 1;
        frame.add(option3TextField, gbc);

        // Option 4 Text Field
        JLabel option4Label = new JLabel("Option 4:");
        option4TextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(option4Label, gbc);

        gbc.gridx = 1;
        frame.add(option4TextField, gbc);

        // Correct Answer Text Field
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerTextField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 7;
        frame.add(correctAnswerLabel, gbc);

        gbc.gridx = 1;
        frame.add(correctAnswerTextField, gbc);

        // Difficulty Level Combo Box
        JLabel difficultyLabel = new JLabel("Difficulty Level:");
        difficultyLevelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});

        gbc.gridx = 0;
        gbc.gridy = 8;
        frame.add(difficultyLabel, gbc);

        gbc.gridx = 1;
        frame.add(difficultyLevelComboBox, gbc);

        // Update and Back Buttons
        JButton updateButton = new JButton("Update Question");
        JButton backButton = new JButton("Back");

        // Set preferred size for the Back button to make it more consistent
        backButton.setPreferredSize(new Dimension(120, 30));

        gbc.gridx = 0;
        gbc.gridy = 9;
        frame.add(updateButton, gbc);

        gbc.gridx = 1;
        frame.add(backButton, gbc);

        // Load question data based on the selected question text
        questionTextComboBox.addActionListener(e -> loadQuestionData((String) questionTextComboBox.getSelectedItem()));

        // Update button action
        updateButton.addActionListener(e -> updateQuestion());

        // Back button action
        backButton.addActionListener(e -> {
            new AdminPanel(); // Go back to Admin Panel
            frame.dispose();  // Dispose of the current frame
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadQuestionTexts() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT question_text FROM Questions")) {

            while (resultSet.next()) {
                questionTextComboBox.addItem(resultSet.getString("question_text"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestionData(String questionText) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Questions WHERE question_text = ?")) {

            statement.setString(1, questionText);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                questionTextField.setText(resultSet.getString("question_text"));
                option1TextField.setText(resultSet.getString("option1"));
                option2TextField.setText(resultSet.getString("option2"));
                option3TextField.setText(resultSet.getString("option3"));
                option4TextField.setText(resultSet.getString("option4"));
                correctAnswerTextField.setText(resultSet.getString("correct_answer"));
                difficultyLevelComboBox.setSelectedItem(resultSet.getString("difficulty_level"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateQuestion() {
        String questionText = questionTextField.getText();
        String option1 = option1TextField.getText();
        String option2 = option2TextField.getText();
        String option3 = option3TextField.getText();
        String option4 = option4TextField.getText();
        String correctAnswer = correctAnswerTextField.getText();
        String difficultyLevel = (String) difficultyLevelComboBox.getSelectedItem();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Questions SET question_text = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, " +
                             "correct_answer = ?, difficulty_level = ? WHERE question_text = ?")) {

            statement.setString(1, questionText);
            statement.setString(2, option1);
            statement.setString(3, option2);
            statement.setString(4, option3);
            statement.setString(5, option4);
            statement.setString(6, correctAnswer);
            statement.setString(7, difficultyLevel);
            statement.setString(8, questionText);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Question updated successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update the question.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
