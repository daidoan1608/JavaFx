package register.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import register.entity.Product;
import register.repository.ProductDAO;
import register.upload.FileUploader;

public class UpdateDeleteProductController implements Initializable {
	@FXML
	private Label modelLb;
	
	@FXML
	private TextField brandTf;
	
	@FXML
	private TextField priceTf;
	
	@FXML
	private TextField osTf;

	@FXML
	private TextField storageTf;

	@FXML
	private TextField ramTf;

	@FXML
	private ImageView imageProduct;
	
	@FXML
	private Button uploadButton;
	
	private ProductDAO productDAO = new ProductDAO();
	
	private Product product;

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getrpoduct() {
		return product;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			brandTf.setText(product.getBrand());
			modelLb.setText(product.getModel());
			priceTf.setText(String.valueOf(product.getPrice()));
			osTf.setText(product.getOs());
			storageTf.setText(String.valueOf(product.getStorage()));
			ramTf.setText(String.valueOf(product.getRam()));

		try {
			Image image = new Image(product.getPath());
			imageProduct.setImage(image);
		} catch (Exception e) {
			Image image = new Image("image/vnua.png");
			imageProduct.setImage(image);
		}
		});
	}
	
	@FXML
	public void onClickUpdate() {
		Product product = new Product();
		product.setBrand(brandTf.getText());
		product.setModel(modelLb.getText());
		product.setOs(osTf.getText());
		product.setPrice(Double.parseDouble(priceTf.getText()));
		product.setStorage(Integer.parseInt(storageTf.getText()));
		product.setRam(Integer.parseInt(ramTf.getText()));

		// Lấy đường dẫn của ảnh từ ImageView
		String imagePath = (imageProduct.getImage() != null) ? imageProduct.getImage().getUrl() : null;

		// Nếu ảnh tồn tại, thiết lập đường dẫn cho thuộc tính path của User
		if (imagePath != null) {
			File imageFile = new File(imagePath); // Chuyển đổi URL của ảnh về file
			String fileName = imageFile.getName(); // Lấy tên file ảnh (bao gồm cả phần mở rộng)
			product.setPath("image/" + modelLb.getText() + "/" + fileName); // Đường dẫn đầy đủ
		} else {
			product.setPath(null); // Nếu không có ảnh thì set null
		}
		if (productDAO.updateProduct(product)) {
			MessageBox.showInfo("Sửa thành công sản phẩm " + product.getModel());
			Stage stage = (Stage) brandTf.getScene().getWindow();
	        stage.close(); 
		} else
			MessageBox.showError("Lỗi");
	}

	@FXML
	public void onClickDelete() {
		String path = productDAO.findProductByModel(modelLb.getText()).getPath();
		if (productDAO.deleteProductByModel(modelLb.getText())) {
			MessageBox.showInfo("Xóa thành công User " + modelLb.getText());
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
			Stage stage = (Stage) brandTf.getScene().getWindow();
	        stage.close(); 
		} else
			MessageBox.showError("Lỗi");
	}
	
	@FXML
	public void setupUploadButton() {
		 uploadButton.setOnAction(event -> {
		        Image image = FileUploader.uploadImage(product.getModel());
		        if (image != null) {
		            imageProduct.setImage(image);
		        }
		    });
	}
}
