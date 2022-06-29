package cn.gaein.java.video.tool;

import cn.gaein.java.video.tool.controllers.MainController;
import cn.gaein.java.video.tool.helper.ResourceHelper;
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
        var resources = new ResourceHelper();

        resources.setResourceLocation("source-font",
                Objects.requireNonNull(getClass().getResource("fonts/SourceHanSansCN-Medium.otf")).toExternalForm()
        );

        var loader = new FXMLLoader(
                MainApplication.class.getResource("main-view.fxml"));

        loader.setControllerFactory(c -> new MainController(stage));

        var scene = new Scene(loader.load(), 1280, 560);
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