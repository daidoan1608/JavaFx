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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import register.entity.Product;
import register.entity.User;
import register.repository.ProductDAO;
import register.upload.FileUploader;

public class ProductController implements Initializable {
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
	private Button uploadButton;

	@FXML
	private ImageView imageProduct;

	@FXML
	private Button btnPM;

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

	@FXML
	public void setupUploadButton() {
		 uploadButton.setOnAction(event -> {
		        Image image = FileUploader.uploadImage(modelTf.getText());
		        if (image != null) {
		            imageProduct.setImage(image);
		        }
		    });
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			loginUser.setText("Xin chào " + user.getFullname());
			btnPM.setStyle("-fx-border-color: #1E90FF; " + // Màu xanh nước biển (DodgerBlue)
					"-fx-border-width: 2px; " + 
					"-fx-background-color: #ADD8E6; ");

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
		GoScene.goUpdateDeleteProductScene(selectedItem);
		loadList();
	}

	@FXML
	public void onClickCreate() {
		Product product = new Product();
		product.setBrand(brandTf.getText());
		product.setModel(modelTf.getText());
		product.setOs(osTf.getText());
		product.setPrice(Double.parseDouble(priceTf.getText()));
		product.setStorage(Integer.parseInt(storageTf.getText()));
		product.setRam(Integer.parseInt(ramTf.getText()));
		String imagePath = (imageProduct.getImage() != null) ? imageProduct.getImage().getUrl() : null;

		// Nếu ảnh tồn tại, thiết lập đường dẫn cho thuộc tính path của User
		if (imagePath != null) {
			File imageFile = new File(imagePath); // Chuyển đổi URL của ảnh về file
			String fileName = imageFile.getName(); // Lấy tên file ảnh (bao gồm cả phần mở rộng)
			product.setPath("image/" + modelTf.getText() + "/" + fileName); // Đường dẫn đầy đủ
		} else {
			product.setPath(null); // Nếu không có ảnh thì set null
		}
		productDAO.addProduct(product);
		MessageBox.showInfo("Thêm thành công sản phẩm " + user.getFullname());
		brandTf.setText("");
		modelTf.setText("");
		osTf.setText("");
		storageTf.setText("");
		ramTf.setText("");
		priceTf.setText("");
		imageProduct.setImage(null);
		loadList();

	}

	public void loadList() {
		List<Product> userList = productDAO.getAllProducts();
		ObservableList<Product> obsList = FXCollections.observableArrayList(userList);
		productListTV.setItems(obsList);
	}
}
