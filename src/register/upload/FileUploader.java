package register.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileUploader {
	public static Image uploadImage(String username) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();

        // Chỉ cho phép chọn các file ảnh
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Hiển thị hộp thoại để chọn file
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
                // Tạo thư mục đích nếu chưa tồn tại
                File destinationDirectory = new File("src/image/" + username);
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                
                // Lấy tên file gốc
                String originalFileName = file.getName();
                
                // Tạo đường dẫn đến file đích
                File destinationFile = new File(destinationDirectory, originalFileName);

                // Sao chép file đến thư mục đích
                Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Trả về ảnh đã được sao chép
                return new Image(destinationFile.toURI().toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
