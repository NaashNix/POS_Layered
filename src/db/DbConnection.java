package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection = null;
    private Connection connection;

    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Shop",
                "naashnix",
                "Naash@1234"
        );
    }

    public static DbConnection getInstance() throws ClassNotFoundException, SQLException {
        return (dbConnection==null)?(dbConnection = new DbConnection()) : dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }

}
