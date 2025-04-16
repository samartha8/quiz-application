package coursework_final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteQuestionPage {
    private JFrame frame;
    private JComboBox<String> questionIdComboBox; // ComboBox for selecting question ID
    private JLabel questionTextLabel; // Label to display the question text
    private JButton deleteButton;

    public DeleteQuestionPage() {
        frame = new JFrame("Delete Question");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Title
        JLabel titleLabel = new JLabel("Delete Question", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Question ID Label
        JLabel questionIdLabel = new JLabel("Select Question ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(questionIdLabel, gbc);

        // Question ID ComboBox
        questionIdComboBox = new JComboBox<>();
        loadQuestionIds(); // Load question IDs from the database
        questionIdComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayQuestionText(); // Display the question text when a question ID is selected
            }
        });
        gbc.gridx = 1;
        frame.add(questionIdComboBox, gbc);

        // Question Text Label
        questionTextLabel = new JLabel("Question Text Will Appear Here");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(questionTextLabel, gbc);

        // Delete Button
        deleteButton = new JButton("Delete");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        frame.add(deleteButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        frame.add(backButton, gbc);

        // Delete button functionality
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteQuestionFromDatabase();
            }
        });

        // Back button functionality
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanel();  // Go back to Admin Panel
                frame.dispose();   // Close the current frame
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadQuestionIds() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT question_id FROM Questions")) {

            while (rs.next()) {
                questionIdComboBox.addItem(rs.getString("question_id")); // Add question IDs to the ComboBox
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading Question IDs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayQuestionText() {
        String questionId = (String) questionIdComboBox.getSelectedItem(); // Get selected question ID
        if (questionId != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
                 PreparedStatement stmt = connection.prepareStatement("SELECT question_text FROM Questions WHERE question_id = ?")) {
                stmt.setInt(1, Integer.parseInt(questionId));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String questionText = rs.getString("question_text");
                    questionTextLabel.setText(questionText); // Display the question text
                } else {
                    questionTextLabel.setText("No Question Found.");
 }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error loading Question Text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Question ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteQuestionFromDatabase() {
        String questionId = (String) questionIdComboBox.getSelectedItem(); // Get selected question ID

        if (questionId == null) {
            JOptionPane.showMessageDialog(frame, "Please select a valid Question ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "")) {
            String query = "DELETE FROM Questions WHERE question_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(questionId));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(frame, "Question Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                questionTextLabel.setText("Question Text Will Appear Here"); // Reset the label
                questionIdComboBox.removeItem(questionId); // Remove the deleted question ID from the ComboBox
            } else {
                JOptionPane.showMessageDialog(frame, "No Question Found with this ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error Deleting Question: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid Question ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}