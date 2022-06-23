package cn.gaein.java.video.tool.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXListView;

public class MainController {

    @FXML
    private JFXListView<Label> inputFileList;

    @FXML
    private JFXButton testButton;

    @FXML
    protected void onTestButtonClick() {
        System.out.println("Test");

        var inputFileListArr = inputFileList.getItems();
        inputFileListArr.add(new Label("abc.mp4"));
        inputFileListArr.add(new Label("test...eo.mkv"));
        inputFileListArr.add(new Label("data...cc.avi"));
    }
}