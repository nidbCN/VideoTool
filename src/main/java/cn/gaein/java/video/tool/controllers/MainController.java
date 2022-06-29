package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.compontents.PlayerView;
import cn.gaein.java.video.tool.compontents.cell.FragmentCell;
import cn.gaein.java.video.tool.compontents.cell.VideoCell;
import cn.gaein.java.video.tool.helper.DialogHelper;
import cn.gaein.java.video.tool.models.Video;
import cn.gaein.java.video.tool.models.VideoFragment;
import cn.gaein.java.video.tool.utils.FileExtensions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * @author Gaein
 */
public class MainController implements Initializable {
    @FXML
    private MFXButton outputDeleteBtn;
    @FXML
    private StackPane mainPane;
    @FXML
    private PlayerView playerView;
    @FXML
    private MFXListView<Video> inputFileList;
    @FXML
    private MFXButton flagStartBtn;
    @FXML
    private MFXButton flagCompleteBtn;
    @FXML
    private MFXButton flagEditBtn;
    @FXML
    private MFXButton flagCancelBtn;
    @FXML
    private MFXListView<VideoFragment> outputFileList;
    @FXML
    private MFXButton exportOutputBtn;
    @FXML
    private MFXButton inputAddBtn;
    @FXML
    private MFXButton inputRemoveBtn;
    @FXML
    private MFXButton outputMoveUpBtn;
    @FXML
    private MFXButton outputMoveDownBtn;

    private final Stage stage;
    private DialogHelper dialogHelper;

    public MainController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listInit();
        dialogInit();
    }

    private void listInit() {
        // input file list
        inputFileList.setConverter(FunctionalStringConverter.to(Video::getDisplayName));
        inputFileList.setCellFactory(v -> new VideoCell(inputFileList, v));

        // output file list
        outputFileList.setConverter(FunctionalStringConverter.to(VideoFragment::getDisplayName));
        outputFileList.setCellFactory(f -> new FragmentCell(outputFileList, f));
    }

    private void dialogInit() {
        dialogHelper = new DialogHelper(stage);
    }

    @FXML
    protected void onAddInputClicked() {
        var videoList = inputFileList.getItems();

        var chooser = new FileChooser();
        chooser.setTitle("导入视频文件");
        chooser.getExtensionFilters().addAll(FileExtensions.getVideoExtensions());

        var fileList = chooser.showOpenMultipleDialog(new Stage());
        if (fileList == null) {
            return;
        }

        fileList.forEach(f -> {
            var video = new Video(f);
            var index = videoList.size();

            videoList.add(index, video);

            var item = inputFileList.getCell(index);
            item.setOnMouseClicked(e -> {
                // video item clicked
                playerView.setVideo(video);
                playerView.open();
            });
        });
    }

    @FXML
    protected void onRemoveInputClicked() {
        var videoList = inputFileList.getItems();
        var selectedList = inputFileList.getSelectionModel().getSelectedValues();

        // no video selected
        if (selectedList.size() < 1) {
            dialogHelper.getErrorDialog("未选中视频，无法移除").show();
            return;
        }

        videoList.removeAll(selectedList);

        // Only select one, must be the video witch are playing
        playerView.stop();
    }

    private VideoFragment fragmentInEdit;

    @FXML
    protected void onFlagStartClicked() {
        var video = playerView.getVideo();
        if (video == null) {
            dialogHelper.getErrorDialog("未选中视频，无法开始片段").show();
            return;
        }

        video.startFragment(playerView.getTime());
    }

    @FXML
    protected void onFlagCompleteClicked() {
        var video = playerView.getVideo();
        if (video == null) {
            dialogHelper.getErrorDialog("未选中视频，无法完成片段").show();
            return;
        }

        fragmentInEdit = video.completeFragment(playerView.getTime());
    }

    @FXML
    protected void onFlagEditClicked() {
        var video = playerView.getVideo();
        if (video == null) {
            dialogHelper.getErrorDialog("未选中视频，无法编辑片段").show();
            return;
        }

        if (fragmentInEdit == null) {
            dialogHelper.getErrorDialog("当前视频没有片段，无法编辑片段", config ->
                    config.setOwnerNode(mainPane)).show();
            return;
        }

        // just for test
        var outputFileListArr = outputFileList.getItems();
        outputFileListArr.add(fragmentInEdit);
        fragmentInEdit = null;

        // TODO: change to edit window before add to output list
    }

    @FXML
    protected void onFlagCancelClicked() {
        var video = playerView.getVideo();
        if (video == null) {
            dialogHelper.getErrorDialog("未选中视频，无法取消片段").show();
            return;
        }
        video.deprecateFragment();
    }

    @FXML
    protected void onRemoveOutputClicked() {
        var fragmentList = outputFileList.getItems();
        var selection = outputFileList.getSelectionModel();

        if (fragmentList.size() < 1) {
            return;
        }

        fragmentList.removeAll(selection.getSelectedValues());
    }

    @FXML
    protected void onMoveUpOutputClicked() {
        var fragmentList = outputFileList.getItems();
        var selection = outputFileList.getSelectionModel();

        // only one item, no need to move
        if (fragmentList.size() <= 1) {
            return;
        }

        var fragment = selection.getSelectedValues().get(0);
        var index = fragmentList.indexOf(fragment);

        if (index == 0) {

            return;
        }

        Collections.swap(fragmentList, index, index - 1);
        selection.deselectIndex(index);
        selection.selectIndex(index - 1);
    }

    @FXML
    protected void onMoveDownOutputClicked() {
        var fragmentList = outputFileList.getItems();
        var selection = outputFileList.getSelectionModel();

        // only one item, no need to move
        if (fragmentList.size() <= 1) {
            return;
        }

        var fragment = selection.getSelectedValues().get(0);
        var index = fragmentList.indexOf(fragment);

        // this item is already at last
        if (index == fragmentList.size()) {
            return;
        }

        Collections.swap(fragmentList, index, index + 1);
        selection.deselectIndex(index);
        selection.selectIndex(index + 1);
    }
}