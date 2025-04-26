package application;

import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
        	//gọi lớp cha
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            //gọi font
            Font.loadFont(getClass().getResourceAsStream("/fonts/GreatVibes-Regular.ttf"), 12);
            Scene scene = new Scene(root);
            //thêm css
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Đăng nhập hệ thống");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 

    public static void main(String[] args) {
        launch(args);
    }
}
