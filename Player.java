package coursework_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Player {
    private int score;
    private String difficultyLevel;
    private String playerName; // Added playerName attribute

    public Player(int score, String difficultyLevel, String playerName) { // Updated constructor
        this.score = score;
        this.difficultyLevel = difficultyLevel;
        this.playerName = playerName; // Initialize playerName
    }

    public void saveScore() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizedb", "root", "");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Players (player_name, score, difficulty_level) VALUES (?, ?, ?)")) {

            statement.setString(1, playerName); // Set playerName in the query
            statement.setInt(2, score);
            statement.setString(3, difficultyLevel);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}