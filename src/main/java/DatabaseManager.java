import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:h2:./data/recipes-db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initDatabase() throws Exception {
        try (Connection connection = getConnection();
        Statement statement = connection.createStatement()) {

            statement.execute("""
CREATE TABLE IF NOT EXISTS recipes ( 
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    cooking_time INT,
    instructions VARCHAR(20000)
)""");
            statement.execute("""
CREATE TABLE IF NOT EXISTS ingredients ( 
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT,
    name VARCHAR(255),
    quantity DOUBLE,
    measurement VARCHAR(50)
)""");
        }
    }
}
