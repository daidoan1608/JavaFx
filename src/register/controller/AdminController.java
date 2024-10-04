package register.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import register.entity.User;
import register.repository.UserDAO;
import register.upload.FileUploader;

public class AdminController implements Initializable {
	@FXML
	private TableView<User> userListTV;

	@FXML
	private TableColumn<User, Number> sttCol;

	@FXML
	private TableColumn<User, String> fullnameCol;

	@FXML
	private TableColumn<User, String> usernameCol;

	@FXML
	private TableColumn<User, String> roleCol;

	@FXML
	private TextField fullnameTf;

	@FXML
	private TextField usernameTf;

	@FXML
	private TextField emailTf;

	@FXML
	private Label loginUser;

	@FXML
	private ToggleGroup role;

	@FXML
	private RadioButton roleAdmin;

	@FXML
	private RadioButton roleUser;

	@FXML
	private Button uploadButton;

	@FXML
	private ImageView avatar;

	@FXML
	private Button btnAM;

	private User user;

	private UserDAO userDAO = new UserDAO();
	
	@FXML
	private Hyperlink logout;
	
	public void setLoginUser(User user) {
		this.user = user;
	}
	
	public User getLoginUser() {
		return user;
	}

	@FXML
	private void handleLogout(ActionEvent event) {
		user = null;
		roleAdmin.getScene().getWindow().hide();
		GoScene.goLoginScreen();
	}

	@FXML
	private void onClickGoToPM() {
		roleAdmin.getScene().getWindow().hide();
		GoScene.goProductSence(user);
	}

	@FXML
	public void setupUploadButton() {
		uploadButton.setOnAction(event -> {
	        Image image = FileUploader.uploadImage(usernameTf.getText());
	        if (image != null) {
	            avatar.setImage(image);
	        }
	    });
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			loginUser.setText("Xin chào " + user.getFullname());
			btnAM.setStyle("-fx-border-color: #1E90FF; " +
					"-fx-border-width: 2px; " + 
					"-fx-background-color: #ADD8E6; ");

		});
		sttCol.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<>(userListTV.getItems().indexOf(column.getValue()) + 1));
		fullnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

		List<User> userList = userDAO.getAllUsers();
		ObservableList<User> obsList = FXCollections.observableArrayList(userList);
		userListTV.setItems(obsList);
	}

	@FXML
	public void onClickRow() {
		User selectedItem = (User) userListTV.getSelectionModel().getSelectedItem();
		GoScene.goUpdateDeleteUserScene(selectedItem);
		loadUserList();
	}


	@FXML
	public void onClickCreate() {
		User user = new User();
		user.setFullname(fullnameTf.getText());
		user.setUsername(usernameTf.getText());
		user.setEmail(emailTf.getText());
		Toggle selectedToggle = role.getSelectedToggle();
		if (selectedToggle != null) {
			RadioButton selectedRadioButton = (RadioButton) selectedToggle;
			user.setRole(selectedRadioButton.getText());
		} else {
			user.setRole("USER");
		}
		user.setPassword("123456");

		String imagePath = (avatar.getImage() != null) ? avatar.getImage().getUrl() : null;

		if (imagePath != null) {
			File imageFile = new File(imagePath);
			String fileName = imageFile.getName();
			user.setPath("image/" + usernameTf.getText() + "/" + fileName);
		} else {
			user.setPath(null);
		}

		if (userDAO.checkUsername(user.getUsername())) {
			MessageBox.showWarning("Username " + user.getUsername() + " đã tồn tại");
		} else if (userDAO.checkEmail(user.getEmail())) {
			MessageBox.showWarning("Email " + user.getEmail() + " đã tồn tại");
		} else {
			userDAO.addUser(user);
			MessageBox.showInfo("Thêm thành công User " + user.getFullname());
			fullnameTf.setText("");
			usernameTf.setText("");
			emailTf.setText("");
			role.selectToggle(null);
			avatar.setImage(null);
			loadUserList();
		}
	}

	public void loadUserList() {
		List<User> userList = userDAO.getAllUsers();
		ObservableList<User> obsList = FXCollections.observableArrayList(userList);
		userListTV.setItems(obsList);
	}
}
