package cn.gaein.java.video.tool.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MainController {
    @FXML
    public JFXListView inputFileList;

    @FXML
    public ImageView displayView;

    @FXML
    public JFXButton flagStartBtn;
    @FXML
    public JFXButton flagEndBtn;
    @FXML
    public JFXButton flagEditBtn;
    @FXML
    public JFXButton flagCancelBtn;
    @FXML
    public JFXListView outputFileList;
    @FXML
    public JFXButton exportOutputBtn;

    @FXML
    protected void onTestButtonClick() {
        System.out.println("Test");

        var inputFileListArr = inputFileList.getItems();

        inputFileListArr.add(new HBox(new Label("test.mkv"), new FontIcon("mdi2a-account")));
    }
}