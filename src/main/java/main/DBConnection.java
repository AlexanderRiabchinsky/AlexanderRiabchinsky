package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "blogDriver";
    private static String dbUser = "root";
    private static String dbPass = "testtest";

 //   private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS users");
                connection.createStatement().execute("DROP TABLE IF EXISTS posts");
                connection.createStatement().execute("DROP TABLE IF EXISTS post_votes");
                connection.createStatement().execute("DROP TABLE IF EXISTS tags");
                connection.createStatement().execute("DROP TABLE IF EXISTS tag2post");
                connection.createStatement().execute("DROP TABLE IF EXISTS post_comments");
                connection.createStatement().execute("DROP TABLE IF EXISTS captcha_codes");
                connection.createStatement().execute("DROP TABLE IF EXISTS global_settings");

                connection.createStatement().execute("CREATE TABLE users(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "is_moderator TINYINT NOT NULL, " +
                        "reg_time DATETIME NOT NULL, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "email VARCHAR(255) NOT NULL, " +
                        "password VARCHAR(255) NOT NULL, " +
                        "code VARCHAR(255), " +
                        "photo TEXT, " +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE posts(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "is_active TINYINT NOT NULL, " +
                        "moderation_status ENUM('NEW','ACCEPTED','DECLINED') NOT NULL, " +
                        "moderator_id INT, " +
                        "user_id INT NOT NULL," +
                        "time DATETIME NOT NULL, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "text TEXT NOT NULL, " +
                        "view_count INT NOT NULL, " +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE post_votes(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "user_id INT NOT NULL, " +
                        "post_id INT NOT NULL, " +
                        "time DATETIME NOT NULL, " +
                        "value TINYINT NOT NULL," +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE tags(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE tag2post(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "post_id INT NOT NULL, " +
                        "tag_id INT NOT NULL, " +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE post_comments(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "parent_id INT, " +
                        "post_id INT NOT NULL, " +
                        "user_id INT NOT NULL, " +
                        "time DATETIME NOT NULL," +
                        "text TEXT NOT NULL, " +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE captcha_codes(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "time DATETIME NOT NULL , " +
                        "code TINYTEXT NOT NULL, " +
                        "secret_code TINYTEXT NOT NULL , " +
                        "PRIMARY KEY(id))");
                connection.createStatement().execute("CREATE TABLE global_settings(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "code VARCHAR(255) NOT NULL , " +
                        "name VARCHAR(255) NOT NULL , " +
                        "value VARCHAR(255) NOT NULL , " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

//    public static void executeMultiInsert() throws SQLException {
//        String sql = "INSERT INTO voter_count(name, birthDate, `count`) VALUES " + insertQuery.toString();
//        DBConnection.getConnection().createStatement().execute(sql);
//    }

//    public static void countVoter(String name, String birthDay) throws SQLException {
//        birthDay = birthDay.replace('.', '-');
//        insertQuery.append((insertQuery.length() > 0 ? "," : "") + "('" + name + "', '" + birthDay + "',1)");
//        if (insertQuery.length() > 100000) {
//            executeMultiInsert();
//            insertQuery = new StringBuilder();
//        }
//    }

//    public static void printVoterCounts() throws SQLException {
//        DBConnection.executeMultiInsert();
//        String sql = "SELECT name, birthDate, COUNT(*) AS `count` FROM voter_count GROUP BY name, birthDate  HAVING  `count` > 1";
//        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
//        while (rs.next()) {
//            System.out.println("\t" + rs.getString("name") + " (" +
//                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
//        }
//    }
}
