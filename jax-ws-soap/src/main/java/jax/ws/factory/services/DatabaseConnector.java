package jax.ws.factory.services;

import java.sql.*;

public class DatabaseConnector {

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    private static String jdbc_driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost/wsfactory";
    private static String username = "willywangky";
    private static String password = "willywangky";

    public DatabaseConnector() {
        try {
            System.out.println("Connecting to database...");
            Class.forName(jdbc_driver);
            this.connection = DriverManager.getConnection(url, username, password);
            this.statement = connection.createStatement();
            System.out.println("Connected to database");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to database");
        }
    }

    public ResultSet getResult() {
        return this.result;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Statement getStatement() {
        return this.statement;
    }

    public void extractData(String query) {
        System.out.println("EXECUTING QUERY: " + query);
        try {
            this.result = this.statement.executeQuery(query);
            System.out.println("SUCCESSFULLY EXECUTING QUERY: " + query);
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateDatabase(String query) {
        System.out.println("EXECUTING QUERY: " + query);
        try {
            int x = this.statement.executeUpdate(query);
            System.out.println("SUCCESSFULLY EXECUTE UPDATE ON DATABASE");
            System.out.println("Numbers of line affected: " + x);
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
    }
}