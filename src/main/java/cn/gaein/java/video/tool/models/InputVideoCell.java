package cn.gaein.java.video.tool.models;

import com.jfoenix.controls.JFXListCell;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;

public class InputVideoCell extends JFXListCell<BorderPane> {
    public InputVideoCell(InputVideo video) {
        setInputVideo(video);
    }

    private InputVideo inputVideo;

    private EventHandler<? super MouseEvent> onDeleteClicked;

    public final InputVideo getInputVideo() {
        return inputVideo;
    }

    public final void setInputVideo(InputVideo video) {
        inputVideo = video;

        var innerDeleteBtn = new FontIcon("mdi2d-delete");
        innerDeleteBtn.setOnMouseClicked(e -> onDeleteClicked.handle(e));

        var node = new BorderPane();

        node.setLeft(new Label(inputVideo.getDisplayName()));
        node.setRight(innerDeleteBtn);
        node.setMinWidth(136);

        setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
        setGraphic(node);
    }

    public final void setOnDeleteClicked(
            EventHandler<? super MouseEvent> value) {
        onDeleteClicked = value;
    }

    public final EventHandler<? super MouseEvent> getOnDeleteClicked() {
        return onDeleteClicked;
    }
}
