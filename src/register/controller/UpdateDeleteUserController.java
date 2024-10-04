package register.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import register.entity.User;
import register.repository.UserDAO;
import register.upload.FileUploader;

public class UpdateDeleteUserController implements Initializable {
	@FXML
	private Label usernameLb;
	
	@FXML
	private TextField fullnameTf;
	
	@FXML
	private TextField emailTf;
	
	@FXML
	private TextField passwordTf;
	
	@FXML
	private RadioButton roleAdmin;
	
	@FXML
	private RadioButton roleUser;
	
	@FXML
	private ToggleGroup role;
	
	@FXML
	private ImageView avatar;
	
	@FXML
	private Button uploadButton;
	
	private UserDAO userDAO = new UserDAO();
	
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			fullnameTf.setText(user.getFullname());
			usernameLb.setText(user.getUsername());
			emailTf.setText(user.getEmail());
			passwordTf.setText(user.getPassword());
			if ("Admin".equalsIgnoreCase(user.getRole())) {
			roleAdmin.setSelected(true);
		} else if ("User".equalsIgnoreCase(user.getRole())) {
			roleUser.setSelected(true);
		}
		try {
			Image image = new Image(user.getPath());
			avatar.setImage(image);
		} catch (Exception e) {
			Image image = new Image("image/vnua.png");
			avatar.setImage(image);
		}
		});
	}
	
	@FXML
	public void onClickUpdate() {
		User user = new User();
		user.setFullname(fullnameTf.getText());
		user.setUsername(usernameLb.getText());
		user.setEmail(emailTf.getText());
		Toggle selectedToggle = role.getSelectedToggle();
		if (selectedToggle != null) {
			RadioButton selectedRadioButton = (RadioButton) selectedToggle;
			user.setRole(selectedRadioButton.getText());
		} else {
			user.setRole("USER");
		}

		// Lấy đường dẫn của ảnh từ ImageView
		String imagePath = (avatar.getImage() != null) ? avatar.getImage().getUrl() : null;

		// Nếu ảnh tồn tại, thiết lập đường dẫn cho thuộc tính path của User
		if (imagePath != null) {
			File imageFile = new File(imagePath); // Chuyển đổi URL của ảnh về file
			String fileName = imageFile.getName(); // Lấy tên file ảnh (bao gồm cả phần mở rộng)
			user.setPath("image/" + usernameLb.getText() + "/" + fileName); // Đường dẫn đầy đủ
		} else {
			user.setPath(null); // Nếu không có ảnh thì set null
		}
		
		user.setPassword("123456");
		if (userDAO.updateUser(user)) {
			MessageBox.showInfo("Sửa thành công User " + user.getFullname());
			Stage stage = (Stage) fullnameTf.getScene().getWindow();
	        stage.close(); 
		} else
			MessageBox.showError("Lỗi");
	}

	@FXML
	public void onClickDelete() {
		String path = userDAO.findByUsername(usernameLb.getText()).getPath();
		if (userDAO.deleteUser(usernameLb.getText())) {
			MessageBox.showInfo("Xóa thành công User " + fullnameTf.getText());
			if (path != null && !path.isEmpty()) {
	            File imageFile = new File("src/" + path);
	            if (imageFile.exists()) {
	                if (imageFile.delete()) {
	                    System.out.println("Ảnh đã được xóa: " + path);
	                    File userDirectory = imageFile.getParentFile();
	                    
	                    // Kiểm tra và xóa thư mục nếu nó trống
	                    if (userDirectory.exists() && userDirectory.isDirectory() && userDirectory.list().length == 0) {
	                        if (userDirectory.delete()) {
	                            System.out.println("Thư mục người dùng đã được xóa: " + userDirectory.getPath());
	                        } else {
	                            System.out.println("Không thể xóa thư mục người dùng: " + userDirectory.getPath());
	                        }
	                    }
	                } else {
	                    System.out.println("Không thể xóa ảnh: " + path);
	                }
	            } else {
	                System.out.println("Không tìm thấy tệp ảnh: " + path);
	            }
	        }
			Stage stage = (Stage) fullnameTf.getScene().getWindow();
	        stage.close(); 
		} else
			MessageBox.showError("Lỗi");
	}
	
	@FXML
	public void setupUploadButton() {
		 uploadButton.setOnAction(event -> {
		        Image image = FileUploader.uploadImage(user.getUsername());
		        if (image != null) {
		            avatar.setImage(image);
		        }
		    });
	}
}
