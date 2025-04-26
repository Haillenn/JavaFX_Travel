package controller;

import java.time.LocalDate;

import DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class registerController {

    @FXML private TextField name, email, phone, address;
    @FXML private PasswordField password, repassword;
    @FXML private ChoiceBox<String> gender;
    @FXML private DatePicker date;
    @FXML private Hyperlink goToLogin;

    @FXML
    private void initialize() {
    	//khởi tạo
        gender.getItems().addAll("Nam", "Nữ", "Khác");
        gender.setValue("Nam");
    }

    //hàm đăng ký
    @FXML
    private void register() {
        String ten = name.getText().trim();
        String mail = email.getText().trim();
        String sdt = phone.getText().trim();
        String pass = password.getText();
        String repass = repassword.getText();
        String gioitinh = gender.getValue();
        LocalDate ngaysinh = date.getValue();

        if (ten.isEmpty() || mail.isEmpty() || sdt.isEmpty() || pass.isEmpty() || repass.isEmpty()
            || gioitinh == null || ngaysinh == null) {
            showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!pass.equals(repass)) {
            showAlert("Thông báo", "Mật khẩu không khớp!");
            return;
        }

        if (UserDAO.isEmailExists(mail)) {
            showAlert("Thông báo", "Email đã tồn tại!");
            return;
        }

        boolean success = UserDAO.registerUser(ten, mail, sdt, pass, gioitinh, ngaysinh);

        if (success) {
            showAlert("Thành công", "Đăng ký thành công!");
            clearFields();
        } else {
            showAlert("Lỗi", "Không thể đăng ký. Vui lòng thử lại.");
        }
    }

    private void clearFields() {
        name.clear(); email.clear(); phone.clear(); address.clear();
        password.clear(); repassword.clear(); date.setValue(null);
        gender.setValue(null);
    }

    // quay về login
    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) goToLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng nhập hệ thống");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể chuyển về trang đăng nhập");
        }
    }

    // hàm cảnh báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
