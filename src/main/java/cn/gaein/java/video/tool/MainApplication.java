package cn.gaein.java.video.tool;

import cn.gaein.java.video.tool.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Gaein
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(
                MainApplication.class.getResource("main-view.fxml"));

        loader.setControllerFactory(c -> new MainController(stage));

        var scene = new Scene(loader.load(), 1280, 600);
        scene.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("styles/Global.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("styles/Button.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("styles/Fonts.css")).toExternalForm()
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