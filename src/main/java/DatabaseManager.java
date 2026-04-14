import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:h2:./data/recipes-db";

    //TODO exception, what exception are you expecting?
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    //TODO exception, what exception are you expecting?
    public static void initDatabase() throws Exception {
        //GOOD this is a good example of using try-with resources, even if there is a SQL issue, the connection will get closed properly.
        try (Connection connection = getConnection();
        Statement statement = connection.createStatement()) {

            //GOOD in general this is a good SQL table setup, good use of IFNOTEXISTS, AUTOINCREMENT and appropriate restrictions on the varchars!
            statement.execute("""
CREATE TABLE IF NOT EXISTS recipes ( 
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    cooking_time INT,
    instructions VARCHAR(20000)
)""");

//TODO in the Ingredient.class you define quantity as a INT, but here you are saving as a DOUBLE, double is probably the better choice but be consistent
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
