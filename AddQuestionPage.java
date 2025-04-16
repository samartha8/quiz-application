package coursework_final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * AddQuestionPage allows the admin to add new quiz questions to the database.
 */
public class AddQuestionPage {
    private JFrame frame;
    private JTextField questionTextField;
    private JTextField option1TextField;
    private JTextField option2TextField;
    private JTextField option3TextField;
    private JTextField option4TextField;
    private JTextField correctAnswerTextField;
    private JComboBox<String> difficultyLevelComboBox;

    /**
     * Constructor initializes the Add Question Page UI.
     */
    public AddQuestionPage() {
        frame = new JFrame("Add Question");
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding for better alignment

        // Title Label
        JLabel titleLabel = new JLabel("Add Question", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Question Text Field
        JLabel questionLabel = new JLabel("Question Text:");
        questionTextField = new JTextField(20);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(questionLabel, gbc);
        gbc.gridx = 1;
        frame.add(questionTextField, gbc);

        // Option 1 Field
        JLabel option1Label = new JLabel("Option 1:");
        option1TextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(option1Label, gbc);
        gbc.gridx = 1;
        frame.add(option1TextField, gbc);

        // Option 2 Field
        JLabel option2Label = new JLabel("Option 2:");
        option2TextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(option2Label, gbc);
        gbc.gridx = 1;
        frame.add(option2TextField, gbc);

        // Option 3 Field
        JLabel option3Label = new JLabel("Option 3:");
        option3TextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(option3Label, gbc);
        gbc.gridx = 1;
        frame.add(option3TextField, gbc);

        // Option 4 Field
        JLabel option4Label = new JLabel("Option 4:");
        option4TextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(option4Label, gbc);
        gbc.gridx = 1;
        frame.add(option4TextField, gbc);

        // Correct Answer Field
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerTextField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(correctAnswerLabel, gbc);
        gbc.gridx = 1;
        frame.add(correctAnswerTextField, gbc);

        // Difficulty Level Dropdown (Removed "Difficulty:" text)
        difficultyLevelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        gbc.gridx = 1;
        gbc.gridy = 7;
        frame.add(difficultyLevelComboBox, gbc);

        // Buttons
        JButton addButton = new JButton("Add Question");
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 30)); // Keep button sizes consistent

        gbc.gridx = 0;
        gbc.gridy = 8;
        frame.add(addButton, gbc);
        gbc.gridx = 1;
        frame.add(backButton, gbc);

        // Action Listeners
        addButton.addActionListener(e -> addQuestion());
        backButton.addActionListener(e -> {
            new AdminPanel(); // Return to Admin Panel
            frame.dispose();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Adds the entered question and its details to the database.
     */
    private void addQuestion() {
        String questionText = questionTextField.getText();
        String option1 = option1TextField.getText();
        String option2 = option2TextField.getText();
        String option3 = option3TextField.getText();
        String option4 = option4TextField.getText();
        String correctAnswer = correctAnswerTextField.getText();
        String difficultyLevel = (String) difficultyLevelComboBox.getSelectedItem();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Questions (question_text, option1, option2, option3, option4, correct_answer, difficulty_level) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, questionText);
            statement.setString(2, option1);
            statement.setString(3, option2);
            statement.setString(4, option3);
            statement.setString(5, option4);
            statement.setString(6, correctAnswer);
            statement.setString(7, difficultyLevel);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Question added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add question.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears all input fields after adding a question.
     */
    private void clearFields() {
        questionTextField.setText("");
        option1TextField.setText("");
        option2TextField.setText("");
        option3TextField.setText("");
        option4TextField.setText("");
        correctAnswerTextField.setText("");
        difficultyLevelComboBox.setSelectedIndex(0);
    }
}
