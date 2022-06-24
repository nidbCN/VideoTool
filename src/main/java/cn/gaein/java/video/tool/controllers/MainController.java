package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.InputVideo;
import cn.gaein.java.video.tool.models.InputVideoCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MainController {
    @FXML
    public JFXListView<InputVideoCell> inputFileList;

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
    public JFXButton addInputBtn;

    @FXML
    protected void onAddInputClick() {
        var inputFileListArr = inputFileList.getItems();

        var chooser = new FileChooser();

        chooser.setTitle("导入视频文件");
        chooser.getExtensionFilters().addAll();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"), new ExtensionFilter("All Files", "*.*"));

        var v = new InputVideo("C:\\Users\\Gaein\\Videos\\test.mkv");

        var c = new InputVideoCell(v);

        c.setOnDeleteClicked(e -> {
            System.out.println("Clicked!!!!");
        });

        outputFileList.getItems().add(new JFXListCell<>());

        inputFileListArr.add(c);
    }
}