package register.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import register.entity.Product;

public class ProductDAO {
	public void addProduct(Product phone) {
        String sql = "INSERT INTO tblproducts (brand, model, price, operating_system, storage, ram, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phone.getBrand());
            statement.setString(2, phone.getModel());
            statement.setDouble(3, phone.getPrice());
            statement.setString(4, phone.getOs());
            statement.setInt(5, phone.getStorage());
            statement.setInt(6, phone.getRam());
            statement.setString(7, phone.getPath());
            statement.executeUpdate();
        }  catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn");
		}
    }

    public List<Product> getAllProducts() {
        List<Product> phoneList = new ArrayList<>();
        String sql = "SELECT * FROM tblproducts";

        try (Connection connection = JDBCConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
            	Product phone = new Product();
            	phone.setBrand(rs.getString("brand"));
            	phone.setModel(rs.getString("model"));
            	phone.setPrice(rs.getDouble("price"));
            	phone.setOs(rs.getString("os"));
            	phone.setStorage(rs.getInt("storage"));
            	phone.setRam(rs.getInt("ram"));
            	phone.setPath(rs.getString("path"));
                phoneList.add(phone);
            }
        } catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn" + e.getMessage());
		}
        return phoneList;
    }

    public Product findProductByModel(String model) {
        String sql = "SELECT * FROM tblproducts WHERE model = ?";
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, model);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            	Product phone = new Product();
            	phone.setBrand(rs.getString("brand"));
            	phone.setModel(rs.getString("model"));
            	phone.setPrice(rs.getDouble("price"));
            	phone.setOs(rs.getString("os"));
            	phone.setStorage(rs.getInt("storage"));
            	phone.setRam(rs.getInt("ram"));
            	phone.setPath(rs.getString("path"));
                return phone;
            }
        } catch (ClassNotFoundException e) {
			System.err.println("Không tìm thấy file DB");
		} catch (SQLException e) {
			System.err.println("Sai câu lệnh truy vấn");
		}
        return null;
    }
    
    public boolean updateProduct(Product phone) {
        String sql = "UPDATE tblproducts SET brand = ?, price = ?, os = ?, storage = ?, ram = ?, path = ? WHERE model = ?";
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Thiết lập các giá trị cho câu lệnh SQL
            statement.setString(1, phone.getBrand());
            statement.setDouble(2, phone.getPrice());
            statement.setString(3, phone.getOs());
            statement.setInt(4, phone.getStorage());
            statement.setInt(5, phone.getRam());
            statement.setString(6, phone.getPath());
            statement.setString(7, phone.getModel());

            // Thực hiện lệnh cập nhật
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;  // Trả về true nếu có bản ghi được cập nhật
            
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy file DB");
        } catch (SQLException e) {
            System.err.println("Sai câu lệnh truy vấn");
        }
        return false;
    }
    
    public boolean deleteProductByModel(String model) {
        String sql = "DELETE FROM tblproducts WHERE model = ?";
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Thiết lập giá trị cho câu lệnh SQL
            statement.setString(1, model);
            
            // Thực hiện lệnh xóa
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;  // Trả về true nếu có bản ghi được xóa
            
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy file DB");
        } catch (SQLException e) {
            System.err.println("Sai câu lệnh truy vấn");
        }
        return false;
    }
}