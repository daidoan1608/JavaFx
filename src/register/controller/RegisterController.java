package register.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import register.entity.User;
import register.repository.UserDAO;

public class RegisterController {
	@FXML
	private TextField emailTf;

	@FXML
	private TextField fullnameTf;

	@FXML
	private TextField usernameTf;

	@FXML
	private TextField passwordTf;

	@FXML
	private PasswordField passwordTf1;

	@FXML
	private CheckBox viewCb;

	@FXML
	private TextField usernameSignUpTf;

	@FXML
	private TextField passwordSignUpTf;

	@FXML
	private Label errorLabelSignUp;

	@FXML
	private Label errorLabelLogin;

	private UserDAO userDAO = new UserDAO();
	
	
	@FXML
    public void initialize() {
        viewCb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordTf.setText(passwordTf1.getText());
                passwordTf.setVisible(true);
                passwordTf1.setVisible(false);
            } else {
                passwordTf1.setText(passwordTf.getText());
                passwordTf1.setVisible(true);
                passwordTf.setVisible(false);
            }
        });
    }

	@FXML
	public void onClickSignUp() {
		User user = new User();
		user.setFullname(fullnameTf.getText());
		user.setEmail(emailTf.getText());
		user.setUsername(usernameSignUpTf.getText());
		user.setPassword(passwordSignUpTf.getText());
		user.setRole("USER");
		if (userDAO.checkUsername(user.getUsername())) {
			errorLabelSignUp.setText("Đăng ký thất bại, username đã tồn tại!");
			errorLabelSignUp.setTextFill(Color.RED);
		} else if (userDAO.checkEmail(user.getEmail())) {
			errorLabelSignUp.setText("Đăng ký thất bại, email đã tồn tại!");
			errorLabelSignUp.setTextFill(Color.RED);
		} else {
			userDAO.addUser(user);
			errorLabelSignUp.setTextFill(Color.BLUE);
			errorLabelSignUp.setText("Đăng ký tài khoản thành công!");
		}
	}

	@FXML
	public void onClickSignIn() throws IOException {
		passwordTf.setText(passwordTf1.getText());
		String username = usernameTf.getText();
		String password = passwordTf.getText();


		User user = userDAO.login(username, password);

		if (user != null) {
			errorLabelLogin.getScene().getWindow().hide();
			if ("ADMIN".equals(user.getRole())) {
				GoScene.goAccountSence(user);
			} else if ("USER".equals(user.getRole())) {
				GoScene.goUserSence(user);
			}
		} else {
			errorLabelLogin.setTextFill(Color.RED);
			errorLabelLogin.setText("Sai tên đăng nhập hoặc mật khẩu!");
		}
	}
}
