package cn.gaein.java.video.tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        var scene = new Scene(loader.load(), 1280, 720);
        stage.setTitle("Video Tool!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}