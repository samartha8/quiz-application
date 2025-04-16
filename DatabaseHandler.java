package coursework_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/quizedb"; // Ensure this matches your DB name
    private static final String USER = "root"; // Update if necessary
    private static final String PASSWORD = ""; // Update password if required

    // Static block to load MySQL JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        }
    }

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to create tables if they don't exist
    public static void initializeDatabase() {
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            System.out.println("Connection Established!");

            String createQuestionsTable = "CREATE TABLE IF NOT EXISTS Questions ("
                    + "question_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "question_text VARCHAR(255) NOT NULL, "
                    + "option1 VARCHAR(100) NOT NULL, "
                    + "option2 VARCHAR(100) NOT NULL, "
                    + "option3 VARCHAR(100) NOT NULL, "
                    + "option4 VARCHAR(100) NOT NULL, "
                    + "correct_answer VARCHAR(100) NOT NULL, "
                    + "difficulty_level ENUM('Beginner', 'Intermediate', 'Advanced') NOT NULL"
                    + ")";

            String createPlayersTable = "CREATE TABLE IF NOT EXISTS Players ("
                    + "player_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "player_name VARCHAR(100) NOT NULL, "
                    + "score INT DEFAULT 0, "
                    + "difficulty_level ENUM('Beginner', 'Intermediate', 'Advanced') NOT NULL"
                    + ")";

            String createReportsTable = "CREATE TABLE IF NOT EXISTS Reports ("
                    + "report_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "player_id INT, "
                    + "score INT, "
                    + "difficulty_level ENUM('Beginner', 'Intermediate', 'Advanced'), "
                    + "FOREIGN KEY (player_id) REFERENCES Players(player_id)"
                    + ")";

            // Execute table creation statements
            stmt.executeUpdate(createQuestionsTable);
            stmt.executeUpdate(createPlayersTable);
            stmt.executeUpdate(createReportsTable);

            System.out.println("Tables checked/created successfully.");
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
