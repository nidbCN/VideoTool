package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.InputVideo;
import cn.gaein.java.video.tool.models.InputVideoCell;
import cn.gaein.java.video.tool.utils.FileExtensions;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
        chooser.getExtensionFilters().addAll(FileExtensions.getVideoExtensions());
        var fileList = chooser.showOpenMultipleDialog(new Stage());

        fileList.forEach(file -> {
            var item = new InputVideoCell(new InputVideo(file));
            item.setOnDeleteClicked(e -> {
                System.out.println("Remove");
                inputFileListArr.remove(item);
            });
            inputFileListArr.add(item);
        });
    }
}