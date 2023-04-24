import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {
    private static PostgreSQLConnection instance;
    private Connection connection;
    private final String url = "jdbc:postgresql://babar.db.elephantsql.com:5432/jwovwopv";
    private final String username = "jwovwopv";
    private final String password = "SgL55s3Cvm9TAhQibdo7QxDG-b4EUvXB";

    private PostgreSQLConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static synchronized PostgreSQLConnection getInstance() {
        if (instance == null) {
            instance = new PostgreSQLConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
