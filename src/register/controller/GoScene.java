package register.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import register.entity.Product;
import register.entity.User;

public class GoScene {
	public static void goAccountSence(User user){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GoScene.class.getResource("../view/AccountScene.fxml"));
			Parent root = (Parent)fxmlLoader.load();
			AdminController controller = fxmlLoader.getController();
			controller.setLoginUser(user);
			Stage adminStage = new Stage();
			adminStage.setTitle("Vietnam National University of Agriculture");
			adminStage.setScene(new Scene(root));
			adminStage.show();
			} catch (Exception e) {
				System.err.println("Không tìm thấy view" + e.getMessage());
		}
	}
	
	public static void goLoginScreen() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GoScene.class.getResource("../view/RegisterScene.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage registerStage = new Stage();
			registerStage.setTitle("Vietnam National University of Agriculture");
			registerStage.setScene(new Scene(root));
			registerStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void goUpdateDeleteUserScene(User user) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GoScene.class.getResource("../view/FormUpdateDeleteUser.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			UpdateDeleteUserController controller = fxmlLoader.getController();
			controller.setUser(user);
			Stage adminStage = new Stage();
			adminStage.setTitle("Vietnam National University of Agriculture");
			adminStage.setScene(new Scene(root));
			adminStage.show();
		} catch (Exception e) {
			System.err.println("Không tìm thấy view" + e.getMessage());
		}
	}

	public static void goProductSence(User user) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GoScene.class.getResource("../view/ProductScene.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			ProductController controller = fxmlLoader.getController();
			controller.setLoginUser(user);
			Stage adminStage = new Stage();
			adminStage.setTitle("Vietnam National University of Agriculture");
			adminStage.setScene(new Scene(root));
			adminStage.show();
		} catch (Exception e) {
			System.err.println("Không tìm thấy view" + e.getMessage());
		}
	}

	public static void goUpdateDeleteProductScene(Product product) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GoScene.class.getResource("../view/FormUpdateDeleteProduct.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			UpdateDeleteProductController controller = fxmlLoader.getController();
			controller.setProduct(product);
			Stage adminStage = new Stage();
			adminStage.setTitle("Vietnam National University of Agriculture");
			adminStage.setScene(new Scene(root));
			adminStage.show();
		} catch (Exception e) {
			System.err.println("Không tìm thấy view" + e.getMessage());
		}
	}

	public static void goUserSence(User user) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GoScene.class.getResource("../view/UserScene.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			UserController controller = fxmlLoader.getController();
			controller.setLoginUser(user);
			Stage adminStage = new Stage();
			adminStage.setTitle("Vietnam National University of Agriculture");
			adminStage.setScene(new Scene(root));
			adminStage.show();
		} catch (Exception e) {
			System.err.println("Không tìm thấy view" + e.getMessage());
		}
	}

}
