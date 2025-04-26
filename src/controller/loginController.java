package controller;

import DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class loginController {

    @FXML
    private TextField mail;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink goToRegister;

    @FXML
    private void initialize() {
        
    }

    // đăng nhập
    @FXML
    private void login() {
        String email = mail.getText().trim();
        String pass = password.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        String role = UserDAO.getRoleByEmailAndPassword(email, pass);

        if (role == null) {
            showAlert("Lỗi", "Email hoặc mật khẩu không đúng");
            return;
        }

        try {
            FXMLLoader loader;
            if (role.equalsIgnoreCase("admin")) {
                loader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
            }

            Parent root = loader.load();

            if (role.equalsIgnoreCase("user")) {
                controller.userController userController = loader.getController();
                userController.setCurrentUserEmail(email); 
            }

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hệ thống quản lý tour");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở giao diện phù hợp");
        }

    }

    //sang trang register
    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) mail.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng ký tài khoản");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở trang đăng ký!");
        }
    }

    // load Dashboard
    private void loadDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            Stage stage = (Stage) mail.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở Dashboard!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
