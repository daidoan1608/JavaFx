package register.controller;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import register.entity.Product;
import register.entity.User;
import register.repository.ProductDAO;

public class UserController implements Initializable {
	@FXML
	private TableView<Product> productListTV;

	@FXML
	private TableColumn<Product, Number> sttCol;

	@FXML
	private TableColumn<Product, String> brandCol;

	@FXML
	private TableColumn<Product, String> modelCol;

	@FXML
	private TableColumn<Product, String> osCol;

	@FXML
	private TableColumn<Product, String> priceCol;

	@FXML
	private TableColumn<Product, String> storageCol;

	@FXML
	private TableColumn<Product, String> ramCol;

	@FXML
	private TextField brandTf;

	@FXML
	private TextField modelTf;

	@FXML
	private TextField osTf;

	@FXML
	private TextField priceTf;

	@FXML
	private TextField storageTf;

	@FXML
	private TextField ramTf;

	@FXML
	private ImageView imageProduct;


	@FXML
	private Hyperlink logout;

	@FXML
	private Label loginUser;

	private User user;

	private ProductDAO productDAO = new ProductDAO();

	public User getLoginUser() {
		return user;
	}

	public void setLoginUser(User user) {
		this.user = user;
	}

	@FXML
	private void handleLogout(ActionEvent event) {
		user = null;
		brandTf.getScene().getWindow().hide();
		GoScene.goLoginScreen();
	}

	@FXML
	private void onClickGoToAM() {
		brandTf.getScene().getWindow().hide();
		GoScene.goAccountSence(user);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			loginUser.setText("Xin chào " + user.getFullname());
		});
		sttCol.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<>(productListTV.getItems().indexOf(column.getValue()) + 1));
		brandCol.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
		modelCol.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
		osCol.setCellValueFactory(new PropertyValueFactory<Product, String>("os"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
		storageCol.setCellValueFactory(new PropertyValueFactory<Product, String>("storage"));
		ramCol.setCellValueFactory(new PropertyValueFactory<Product, String>("ram"));

		List<Product> productList = productDAO.getAllProducts();
		ObservableList<Product> obsList = FXCollections.observableArrayList(productList);
		productListTV.setItems(obsList);
	}

	@FXML
	public void onClickRow() {
		Product selectedItem = (Product) productListTV.getSelectionModel().getSelectedItem();
		brandTf.setText(selectedItem.getBrand());
		modelTf.setText(selectedItem.getModel());
		osTf.setText(selectedItem.getOs());
		priceTf.setText(String.valueOf(selectedItem.getPrice()));
		storageTf.setText(String.valueOf(selectedItem.getStorage()));
		ramTf.setText(String.valueOf(selectedItem.getRam()));
		try {
			Image image = new Image(selectedItem.getPath());
			imageProduct.setImage(image);
		} catch (Exception e) {
			Image image = new Image("image/vnua.png");
			imageProduct.setImage(image);
		}
		
		loadList();
	}


	public void loadList() {
		List<Product> userList = productDAO.getAllProducts();
		ObservableList<Product> obsList = FXCollections.observableArrayList(userList);
		productListTV.setItems(obsList);
	}
}
