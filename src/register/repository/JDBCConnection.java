package register.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    // Thông tin kết nối cơ sở dữ liệu
    private static final String jdbcURL = "jdbc:ucanaccess://lib/exam.accdb";
    private static final String dbUser = "";  // Username cho DB (nếu cần)
    private static final String dbPassword = "";  // Password cho DB (nếu cần)

    // Phương thức kết nối cơ sở dữ liệu
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); // Tải driver JDBC
        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    // Phương thức đóng kết nối cơ sở dữ liệu
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
