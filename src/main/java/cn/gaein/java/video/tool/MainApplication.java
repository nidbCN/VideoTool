package cn.gaein.java.video.tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(
                MainApplication.class.getResource("main-view.fxml"));

        var scene = new Scene(loader.load(), 1280, 600);
        scene.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("styles/Button.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("styles/GlowSansSC.css")).toExternalForm()
        );

        stage.setTitle("Video Tool!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}