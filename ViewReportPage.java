package coursework_final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * ViewReportPage class displays the quiz results in a table format.
 * It connects to the database and loads player report data into the table.
 */
public class ViewReportPage {
    private JFrame frame;
    private JTable reportTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor initializes the JFrame, table, and loads the report data.
     */
    public ViewReportPage() {
        // Create a new JFrame for the report page
        frame = new JFrame("Quiz Reports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Table setup with a DefaultTableModel to manage the table's data
        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true); // Ensure the table fills the viewport area
        reportTable.setAutoCreateRowSorter(true);  // Allow sorting by clicking column headers

        // Add table to a scroll pane for better UI handling of large data
        JScrollPane scrollPane = new JScrollPane(reportTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a Back button to navigate to the Admin Panel
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40)); // Set a preferred size for consistency
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Make the button text more readable

        // Back button action: dispose of the current frame and open the Admin Panel
        backButton.addActionListener(e -> {
            new AdminPanel();  // Open the Admin Panel
            frame.dispose();   // Close the current report page frame
        });

        // Add the back button at the bottom of the frame
        frame.add(backButton, BorderLayout.SOUTH);

        // Load the report data from the database
        loadReportData();

        // Make the frame appear at the center of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Loads the player report data from the database and populates the JTable.
     */
    private void loadReportData() {
        // Database connection
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "")) {
            String query = "SELECT player_id, player_name, score, difficulty_level FROM Players ORDER BY score DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Set column headers for the table
            tableModel.setColumnIdentifiers(new String[]{"ID", "Player Name", "Score", "Difficulty"});

            // Loop through the results from the database and add rows to the table
            while (resultSet.next()) {
                int id = resultSet.getInt("player_id");
                String name = resultSet.getString("player_name");
                int score = resultSet.getInt("score");
                String difficulty = resultSet.getString("difficulty_level");

                // Add each row to the table model
                tableModel.addRow(new Object[]{id, name, score, difficulty});
            }

        } catch (SQLException e) {
            // Display an error message if there's an issue with the database connection or query
            JOptionPane.showMessageDialog(frame, "Error loading report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
