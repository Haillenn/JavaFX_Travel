package controller;

import DAO.TourDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Tour;
import model.Booking;
import DAO.BookingDAO;
import DAO.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class userController {

    @FXML private VBox tourListVBox;
    @FXML private TextField selectedTourField;
    @FXML private DatePicker bookingDatePicker;
    @FXML private TextField peopleField;
    @FXML private TextField noteField;
    @FXML private Label usernameLabel;

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> tourNameCol;
    @FXML private TableColumn<Booking, String> bookingDateCol;
    @FXML private TableColumn<Booking, Integer> peopleCol;
    @FXML private TableColumn<Booking, String> noteCol;
    @FXML private TableColumn<Booking, String> statusCol;

    private String currentUserEmail;

    // khởi tạo
    @FXML
    public void initialize() {
        tourNameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTourName()));
        bookingDateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBookingDate()));
        peopleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getPeople()).asObject());
        noteCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNote()));
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        loadTours();
    }

    // load các tour
    private void loadTours() {
        tourListVBox.getChildren().clear();
        List<Tour> tours = TourDAO.getAllTours();
        for (Tour tour : tours) {
            VBox card = new VBox(10);
            card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10;");

            ImageView imageView = new ImageView();
            try {
                Image image = new Image(getClass().getResourceAsStream("/img/" + tour.getImage()));
                imageView.setImage(image);
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
            } catch (Exception e) {
                System.out.println("Không tìm thấy ảnh: " + tour.getImage());
            }

            Label nameLabel = new Label("📍 " + tour.getName());
            Label timeLabel = new Label("⏳ Thời gian: " + tour.getTime());
            Label priceLabel = new Label("💵 Giá: " + tour.getPrice());
            Label descLabel = new Label("📝 " + tour.getDescription());
            descLabel.setWrapText(true);

            VBox info = new VBox(nameLabel, timeLabel, priceLabel, descLabel);
            info.setSpacing(5);

            card.getChildren().addAll(imageView, info);
            card.setOnMouseClicked(e -> selectedTourField.setText(tour.getName()));
            tourListVBox.getChildren().add(card);
        }
    }

    // lấy thông tin mail người dùng
    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
        usernameLabel.setText("Xin chào, " + email);
        loadBookings();
    }

    //hàm đặt tour
    @FXML
    private void bookTour() {
        String tourName = selectedTourField.getText();
        LocalDate date = bookingDatePicker.getValue();
        String peopleText = peopleField.getText();
        String note = noteField.getText();

        if (tourName.isEmpty() || date == null || peopleText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin để đặt tour.");
            return;
        }

        int people;
        try {
            people = Integer.parseInt(peopleText);
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số người phải là số nguyên.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO Booking (userEmail, tourName, bookingDate, people, note, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, currentUserEmail);
            stmt.setString(2, tourName);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setInt(4, people);
            stmt.setString(5, note);
            stmt.setString(6, "Chờ xác nhận");
            stmt.executeUpdate();

            stmt.close();
            conn.close();

            loadBookings();
            showAlert("Thành công", "Đặt tour thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể đặt tour: " + e.getMessage());
        }
    }

    //hàm load thông tin trang đặt tour
    private void loadBookings() {
        bookingTable.getItems().clear();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Booking WHERE userEmail = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, currentUserEmail);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("userEmail"),
                    rs.getString("tourName"),
                    rs.getDate("bookingDate").toString(),
                    rs.getInt("people"),
                    rs.getString("note"),
                    rs.getString("status")
                );
                bookingTable.getItems().add(b);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải lịch sử đặt tour: " + e.getMessage());
        }
    }

    // hàm hủy tour đã đặt
    @FXML
    private void cancelBooking() {
        Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert("Thông báo", "Vui lòng chọn một booking để huỷ.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận huỷ tour");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn chắc chắn muốn huỷ tour này?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            boolean result = BookingDAO.cancelBooking(selectedBooking.getBookingID());
            if (result) {
                showAlert("Thành công", "Huỷ tour thành công!");
                loadBookings();
            } else {
                showAlert("Lỗi", "Không thể huỷ tour. Vui lòng thử lại.");
            }
        }
    }
    
    // hàm hiển thị cảnh báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // đăng xuất
    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng nhập");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}