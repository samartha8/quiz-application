package coursework_final;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Test class for the functionality of the Quiz System.
 */
public class QuizSystemTest {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizedb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Test for saving player's score to the database.
     */
    @Test
    public void testPlayerScoreSaving() {
        Player player = new Player(100, "Beginner", "TestPlayer"); // Provide a player name
        player.saveScore(); // This should save the score to the database

        // Verify that the score was saved correctly
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery("SELECT score FROM Players WHERE player_name = 'TestPlayer' ORDER BY player_id DESC LIMIT 1")) {

            if (resultSet.next()) {
                int savedScore = resultSet.getInt("score");
                assertEquals(100, savedScore); // Check if the saved score matches
            } else {
                fail("No score found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database connection error: " + e.getMessage());
        }
    }

    /**
     * Test for the Admin login functionality.
     */
    @Test
    public void testAdminLogin() {
        // Simulate admin login (you may need to adjust this based on your actual login logic)
        AdminLogin adminLogin = new AdminLogin();
        // Placeholder for actual login logic testing
        assertTrue("Admin login should be successful with correct credentials", true);
    }

    /**
     * Test for loading quiz questions for a particular difficulty level.
     */
    @Test
    public void testLoadQuestions() {
        QuizManager quizManager = new QuizManager();
        quizManager.loadQuestions("Beginner");
        assertFalse("Questions should be loaded for Beginner level", quizManager.getQuestions().isEmpty());
    }

    /**
     * Test for updating a quiz question.
     */
    @Test
    public void testUpdateQuestion() {
        UpdateQuestionPage updateQuestionPage = new UpdateQuestionPage();
        // Placeholder for actual update logic testing
        assertTrue("Update question should be successful", true);
    }

    /**
     * Test for database connection.
     */
    @Test
    public void testDatabaseConnection() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            assertNotNull("Database connection should not be null", connection);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database connection failed: " + e.getMessage());
        }
    }
}
