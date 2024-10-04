package register.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import register.entity.User;

public class UserDAO {

	public boolean checkEmail(String email) {
		String sql = "SELECT * FROM tbluser WHERE email = ?";
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, email);

			ResultSet rs = stmt.executeQuery();

			return rs.next();
		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn");
		}
		return false;
	}

	public boolean checkUsername(String username) {
		String sql = "SELECT * FROM tbluser WHERE username = ?";
		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			return rs.next();

		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Lỗi khi kiểm tra username: " + e.getMessage());
		}
		return false;
	}

	public User login(String username, String password) {
		String sql = "SELECT * FROM tbluser WHERE username = ? AND password = ?";
		User user = null;

		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setFullname(rs.getString("fullname"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setPath(rs.getString("path"));
			}

		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy driver JDBC: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
		}
		return user;
	}

	public boolean addUser(User user) {
		String sql = "INSERT INTO tbluser (fullname,username,password,email,role,path) VALUES (?,?,?,?,?,?)";
		boolean rowInserted = false;

		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, user.getFullname());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getRole());
			stmt.setString(6, user.getPath());

			rowInserted = stmt.executeUpdate() > 0;

		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn thêm");
		}
		return rowInserted;
	}

	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		String sql = "SELECT * FROM tbluser";

		try (Connection connection = JDBCConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				User user = new User();
				user.setFullname(rs.getString("fullname"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setPath(rs.getString("path"));
				userList.add(user);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn lấy tất cả người dùng");
		}
		return userList;
	}

	public boolean updateUser(User user) {
		String sql = "UPDATE tbluser SET fullname = ?, password = ?, email = ?, role = ?, path = ? WHERE username = ?";
		boolean rowUpdated = false;

		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, user.getFullname());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getRole());
			stmt.setString(5, user.getPath());
			stmt.setString(6, user.getUsername());

			rowUpdated = stmt.executeUpdate() > 0;
		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn sửa");
		}
		return rowUpdated;
	}
	public User findByUsername(String username) {
		String sql = "SELECT * FROM tbluser WHERE username = ?";
		User user = null;

		try (Connection connection = JDBCConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setFullname(rs.getString("fullname"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setPath(rs.getString("path"));
			}

		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy driver JDBC: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
		}
		return user;
		
	}

	public boolean deleteUser(String username) {
		String sql = "DELETE FROM tbluser WHERE username = ?";
		boolean rowDeleted = false;

		try (Connection connection = JDBCConnection.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, username);
			rowDeleted = stmt.executeUpdate() > 0;
		} catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn xóa");
		}
		return rowDeleted;
	}

}
