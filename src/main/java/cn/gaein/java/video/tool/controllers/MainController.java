package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.MainApplication;
import cn.gaein.java.video.tool.compontents.PlayerView;
import cn.gaein.java.video.tool.compontents.cell.FragmentCell;
import cn.gaein.java.video.tool.compontents.cell.VideoCell;
import cn.gaein.java.video.tool.ffmpeg.ExtFfmpegBuilder;
import cn.gaein.java.video.tool.helper.DialogHelper;
import cn.gaein.java.video.tool.models.MainViewModel;
import cn.gaein.java.video.tool.models.Video;
import cn.gaein.java.video.tool.models.VideoFragment;
import cn.gaein.java.video.tool.utils.FileExtensions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;

import java.io.File;
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
    private MFXButton aboutBtn;
    @FXML
    private MFXButton cleanBtn;
    @FXML
    private MFXProgressBar statusBar;
    @FXML
    private Label statusLabel;
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
    private Path tempDir;
    private final Stage stage;
    private DialogHelper dialogHelper;
    private FFmpegExecutor executor;
    private final ExecutorService executorService
            = new ThreadPoolExecutor(
            4, 8, 0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("VideoToolPool_").build());

    private final MainViewModel viewModel
            = new MainViewModel();

    public MainController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listInit();
        dialogInit();

        try {
            tempDir = Files.createTempDirectory("VideoToolsExport_");
            executor = new FFmpegExecutor(new FFmpeg("ffmpeg.exe"), new FFprobe("ffprobe.exe"));
        } catch (IOException e) {
            if (tempDir == null) {
                dialogHelper.getErrorDialog("无法创建临时目录，可能会影响序列导出正常使用。");
            } else {
                dialogHelper.getErrorDialog("未找到依赖程序：ffmpeg，请安装ffmpeg并添加环境变量\nhttps://ffmpeg.org/");
                stage.close();
            }
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

    private void BindInit() {
        statusLabel.textProperty().bind(viewModel.statusProperty());
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
        editStage.setTitle("编辑片段: " + fragmentInEdit.getDisplayName());
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
    protected void onExportOutputClicked() {
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

        // export
        var taskList = fragmentList.stream().map(f ->
                (Callable<Void>) () -> {
                    f.edit(builder -> builder
                            .setStartTime(f.getStartTime().getTime(), TimeUnit.MILLISECONDS)
                            .setStopTime(f.getEndTime().getTime(), TimeUnit.MILLISECONDS)
                            .addOutput(Paths.get(tempDir.toString(), f.getDisplayName() + ".ts").toString())
                            .done());
                    executor.createJob(f.getBuilder()).run();
                    return null;
                }).toList();

        executorService.submit(() -> {
                    viewModel.statusProperty().set("处理");

                    try {
                        executorService.invokeAll(taskList);
                        statusBar.progressProperty().set(50);
                    } catch (InterruptedException e) {
                        dialogHelper.getErrorDialog("处理导出队列时发生错误:\n" + e.getMessage()).show();
                        viewModel.statusProperty().set("空闲");
                        e.printStackTrace();
                        return;
                    } finally {
                        statusBar.progressProperty().set(0);
                    }

                    var builder = new ExtFfmpegBuilder();

                    // build concat input string
                    builder.addInput("concat:\"" + String.join("|",
                            fragmentList.stream().map(f ->
                                    Paths.get(tempDir.toString(), f.getDisplayName() + ".ts").toString()
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

    @FXML
    protected void onClearClicked() {
        var count = 0;
        var pathList = new File(tempDir.getParent().toString()).listFiles(
                (dir, name) -> (name.startsWith("VideoToolExport")
                ));

        if (pathList == null) {
            return;
        }

        for (var tempPathItem : pathList) {
            if (tempPathItem == null) {
                continue;
            }

            for (var tempFile : Objects.requireNonNull(tempPathItem.listFiles())
            ) {
                if (tempFile == null) {
                    continue;
                }

                tempFile.delete();
                count += tempFile.length();
            }
        }

        // Unit to MB
        count /= (1024 * 1024);

        dialogHelper.getInfoDialog("清除缓存成功，释放磁盘空间：" +
                (count > 1024 ? (count / 1024 + "GB") : (count + "MB"))
        ).show();
    }

    @FXML
    protected void onAboutClicked() {
        var dialog = dialogHelper.getInfoDialog(
                """
                        \t\t\t\t VideoTool\s
                        开发者: Gaein nidb, https://www.gaein.cn\s
                        Github: https://github.com/nidbCN/VideoTool\s
                        开源协议: The GNU General Public License v3.0\s
                        引用项目: JavaFX, MaterialFX, ffmpeg, ffmpeg-cli-wrapper, vlc, vlcj...\s
                        
                        VideoTool 是自由软件，“‘自由软件’尊重用户的自由，并且尊重整个社区。粗略来讲，一个软件如果是自由软件，这意味着用户可以自由地运行，拷贝，分发，学习，修改并改进该软件。”
                            关于自由软件的哲学与自由软件运动详见：https://www.gnu.org/philosophy/free-sw.html"""
        );

        dialog.setWidth(600);
        dialog.show();
    }
}