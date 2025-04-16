package coursework_final;

import javax.swing.*;
import java.awt.*;

/**
 * The AdminPanel class provides an interface for administrators
 * to manage quiz questions and view reports. It allows adding,
 * updating, deleting questions, and viewing reports.
 */
public class AdminPanel {
    private JFrame frame;

    /**
     * Constructor initializes the Admin Panel UI.
     */
    public AdminPanel() {
        // Create the main frame
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        // Title Panel with a centered label
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Admin Panel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Button Panel (Options)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Stack buttons vertically
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding for spacing

        // Create action buttons
        JButton addButton = createStyledButton("Add Question");
        JButton updateButton = createStyledButton("Update Question");
        JButton deleteButton = createStyledButton("Delete Question");
        JButton viewReportButton = createStyledButton("View Report");

        // Add buttons to the panel with spacing
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Adds spacing
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(viewReportButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Back Button Panel
        JPanel backPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 30)); // Keep button size consistent
        backPanel.add(backButton);
        frame.add(backPanel, BorderLayout.SOUTH);

        // Button Actions

        /**
         * Opens the AddQuestionPage when the "Add Question" button is clicked.
         */
        addButton.addActionListener(e -> {
            new AddQuestionPage();
            frame.dispose(); // Close current frame
        });

        /**
         * Opens the UpdateQuestionPage when the "Update Question" button is clicked.
         */
        updateButton.addActionListener(e -> {
            new UpdateQuestionPage();
            frame.dispose();
        });

        /**
         * Opens the DeleteQuestionPage when the "Delete Question" button is clicked.
         */
        deleteButton.addActionListener(e -> {
            new DeleteQuestionPage();
            frame.dispose();
        });

        /**
         * Opens the ViewReportPage when the "View Report" button is clicked.
         */
        viewReportButton.addActionListener(e -> {
            new ViewReportPage();
            frame.dispose();
        });

        /**
         * Handles the "Back" button click to return to Player Homepage or stay in Admin Panel.
         */
        backButton.addActionListener(e -> {
            int choice = JOptionPane.showOptionDialog(frame,
                    "Do you want to go back to the Player Homepage or stay in the Admin Panel?",
                    "Choose Destination",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"Player Homepage", "Stay Here"}, "Player Homepage");

            if (choice == JOptionPane.YES_OPTION) {
                new PlayerHomepage(); // Navigate to Player Homepage
                frame.dispose();
            }
        });

        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true); // Make frame visible
    }

    /**
     * Creates a styled button with a uniform appearance.
     *
     * @param text The text to display on the button.
     * @return A JButton with consistent styling.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(200, 40)); // Bigger buttons for better UI
        return button;
    }
}
