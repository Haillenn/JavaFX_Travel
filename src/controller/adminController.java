package controller;

import DAO.AdminDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Tour;
import model.Booking;

import java.io.File;

public class adminController {
    @FXML private TextField nameField, locationField, priceField, durationField, imageField;
    @FXML private ImageView previewImage;
    @FXML private TableView<Tour> tourTable;
    @FXML private TableColumn<Tour, Integer> idCol;
    @FXML private TableColumn<Tour, String> nameCol, locationCol, priceCol, durationCol, imageCol;
    @FXML private Label usernameLabel;
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, Integer> bookingIdCol;
    @FXML private TableColumn<Booking, String> userEmailCol, tourNameCol, bookingDateCol, noteCol, statusCol;
    @FXML private TableColumn<Booking, Integer> peopleCol;

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();
    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    // khởi tạo
    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cell -> cell.getValue().tourIDProperty().asObject());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        locationCol.setCellValueFactory(cell -> cell.getValue().timeProperty());
        priceCol.setCellValueFactory(cell -> cell.getValue().priceProperty());
        durationCol.setCellValueFactory(cell -> cell.getValue().descriptionProperty());
        imageCol.setCellValueFactory(cell -> cell.getValue().imageProperty());

        tourTable.setItems(tourList);
        loadTours();

        tourTable.setOnMouseClicked(event -> fillTourForm());

        bookingIdCol.setCellValueFactory(cell -> cell.getValue().bookingIDProperty().asObject());
        userEmailCol.setCellValueFactory(cell -> cell.getValue().userEmailProperty());
        tourNameCol.setCellValueFactory(cell -> cell.getValue().tourNameProperty());
        bookingDateCol.setCellValueFactory(cell -> cell.getValue().bookingDateProperty());
        peopleCol.setCellValueFactory(cell -> cell.getValue().peopleProperty().asObject());
        noteCol.setCellValueFactory(cell -> cell.getValue().noteProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());

        bookingTable.setItems(bookingList);
        loadBookings();
    }

    //load thông tin các tour
    private void loadTours() {
        tourList.setAll(AdminDAO.getAllTours());
        clearTourForm();
    }

    //load thông tin đặt tour
    private void loadBookings() {
        bookingList.setAll(AdminDAO.getAllBookings());
    }

    //điền thông tin vào form
    private void fillTourForm() {
        Tour selected = tourTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nameField.setText(selected.getName());
            locationField.setText(selected.getTime());
            priceField.setText(selected.getPrice());
            durationField.setText(selected.getDescription());
            imageField.setText(selected.getImage());
            previewImage.setImage(new Image("/img/" + selected.getImage()));
        }
    }
    
    //reload form
    private void clearTourForm() {
        nameField.clear();
        locationField.clear();
        priceField.clear();
        durationField.clear();
        imageField.clear();
        previewImage.setImage(null);
    }

    // thêm tour
    @FXML
    public void addTour() {
        AdminDAO.addTour(
            nameField.getText(),
            locationField.getText(),
            priceField.getText(),
            durationField.getText(),
            imageField.getText()
        );
        loadTours();
    }

    // sửa tour
    @FXML
    public void updateTour() {
        Tour selected = tourTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AdminDAO.updateTour(
                selected.getTourID(),
                nameField.getText(),
                locationField.getText(),
                priceField.getText(),
                durationField.getText(),
                imageField.getText()
            );
            loadTours();
        }
    }
    
    //xóa tour
    @FXML
    public void deleteTour() {
        Tour selected = tourTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AdminDAO.deleteTour(selected.getTourID());
            loadTours();
        }
    }
    
    //hàm chọn ảnh
    @FXML
    public void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh tour");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String fileName = file.getName();
            imageField.setText(fileName);
            previewImage.setImage(new Image("/img/" + fileName));
        }
    }

    // hàm xem trước ảnh
    @FXML
    public void previewImage() {
        String fileName = imageField.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                Image img = new Image("/img/" + fileName);
                previewImage.setImage(img);
            } catch (Exception e) {
                showAlert("Lỗi ảnh", "Không thể hiển thị ảnh từ đường dẫn: /img/" + fileName);
            }
        }
    }

    // xác nhận tour
    @FXML
    public void confirmBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AdminDAO.updateBookingStatus(selected.getBookingID(), "Đã xác nhận");
            loadBookings();
        }
    }

    // hủy tour
    @FXML
    public void rejectBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AdminDAO.updateBookingStatus(selected.getBookingID(), "Đã huỷ");
            loadBookings();
        }
    }

    // đăng xuất
    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng nhập");
        } catch (Exception e) {
            e.printStackTrace();
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
