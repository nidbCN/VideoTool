package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.MainApplication;
import cn.gaein.java.video.tool.compontents.PlayerView;
import cn.gaein.java.video.tool.compontents.cell.FragmentCell;
import cn.gaein.java.video.tool.compontents.cell.VideoCell;
import cn.gaein.java.video.tool.ffmpeg.ExtFfmpegBuilder;
import cn.gaein.java.video.tool.helper.DialogHelper;
import cn.gaein.java.video.tool.models.Video;
import cn.gaein.java.video.tool.models.VideoFragment;
import cn.gaein.java.video.tool.utils.FileExtensions;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * @author Gaein
 */
public class MainController implements Initializable {
    @FXML
    private MFXButton showMediaInfoBtn;
    @FXML
    private MFXButton exportSettingBtn;
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
    private FFmpegExecutor executor;
    private final ExecutorService executorService
            = new ThreadPoolExecutor(4, 8, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public MainController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listInit();
        dialogInit();

        try {
            executor = new FFmpegExecutor(new FFmpeg("ffmpeg.exe"), new FFprobe("ffprobe.exe"));
        } catch (IOException e) {
            dialogHelper.getErrorDialog("未找到依赖程序：ffmpeg，请安装ffmpeg并添加环境变量\nhttps://ffmpeg.org/");
            throw new RuntimeException(e);
        }
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

        var fileList = chooser.showOpenMultipleDialog(stage);
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

    @FXML
    protected void onShowMediaInfoClicked() {
        var info = playerView.getMediaInfo();
        var dialog = dialogHelper.getInfoDialog(info);
        dialog.setWidth(600);
        dialog.setHeight(400);
        dialog.show();
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
    protected void onFlagEditClicked() throws IOException {
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

        playerView.pause();

        var editStage = new Stage();
        var loader = new FXMLLoader(
                MainApplication.class.getResource("fragment-view.fxml"));
        loader.setControllerFactory(c -> new FragmentController(editStage, fragmentInEdit));
        var scene = new Scene(loader.load(), 600, 400);
        scene.getStylesheets().addAll(
                Objects.requireNonNull(MainApplication.class.getResource("styles/Global.css")).toExternalForm(),
                Objects.requireNonNull(MainApplication.class.getResource("styles/Button.css")).toExternalForm(),
                Objects.requireNonNull(MainApplication.class.getResource("styles/Fonts.css")).toExternalForm()
        );

        editStage.setScene(scene);
        editStage.setTitle("编辑片段" + fragmentInEdit.getDisplayName());
        editStage.setResizable(false);
        editStage.showAndWait();

        // just for test
        var outputFileListArr = outputFileList.getItems();
        outputFileListArr.add(fragmentInEdit);
        fragmentInEdit = null;
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
    protected void onExportSettingClicked() {

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

    @FXML
    protected void onExportOutputClicked() throws InterruptedException {
        var fragmentList = outputFileList.getItems();

        if (fragmentList.size() == 0) {
            // no file to export
            return;
        }

        var chooser = new FileChooser();
        chooser.setTitle("导出序列为");
        chooser.getExtensionFilters().addAll(FileExtensions.getVideoExtensions());
        var file = chooser.showSaveDialog(stage);

        playerView.pause();

        if (fragmentList.size() == 1) {
            // direct export
            var fragment = fragmentList.get(0);
            fragment.edit(builder -> builder
                    .setStopTime(fragment.getEndTime().getTime(), TimeUnit.MILLISECONDS)
                    .setStartOffset(fragment.getStartTime().getTime(), TimeUnit.MILLISECONDS)
                    .addOutput(file.getPath())
                    .done());

            executorService.submit(()
                    -> executor.createJob(fragment.getBuilder()).run()
            );
        } else {
            // export
            Path tempPath;
            try {
                tempPath = Files.createTempDirectory("VideoToolsExport_");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            var taskList = fragmentList.stream().map(f ->
                    (Callable<Void>) () -> {
                        f.edit(builder -> builder
                                .setStopTime(f.getEndTime().getTime(), TimeUnit.MILLISECONDS)
                                .setStartOffset(f.getStartTime().getTime(), TimeUnit.MILLISECONDS)
                                .addOutput(Paths.get(tempPath.toString(), f.getDisplayName() + ".mkv").toString())
                                .done());
                        executor.createJob(f.getBuilder()).run();
                        return null;
                    }).toList();

            executorService.submit(() -> {
                        try {
                            executorService.invokeAll(taskList);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }

                        var builder = new ExtFfmpegBuilder();

                        // build concat input string
                        builder.addInput("concat:\"" + String.join("|",
                                fragmentList.stream().map(f ->
                                        Paths.get(tempPath.toString(), f.getDisplayName() + ".mkv").toString()
                                ).toList()) + "\""
                        );

                        // TODO: apply export setting

                        builder.addOutput(file.getPath())
                                .setVideoCodec("h264")
                                .setAudioCodec("aac")
                                .done();

                        executor.createJob(builder).run();
                    }
            );
        }

        stage.getScene().getWindow().setOnCloseRequest(e -> {
            executorService.shutdown();
            playerView.dispose();
        });
    }
}