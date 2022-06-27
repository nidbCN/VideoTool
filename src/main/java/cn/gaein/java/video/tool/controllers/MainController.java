package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.compontents.PositionBar;
import cn.gaein.java.video.tool.compontents.VideoCell;
import cn.gaein.java.video.tool.models.Video;
import cn.gaein.java.video.tool.models.VideoFragment;
import cn.gaein.java.video.tool.utils.FileExtensions;
import cn.gaein.java.video.tool.videosurface.PixelBufferVideoSurface;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Gaein
 */
public class MainController implements Initializable {
    private final EmbeddedMediaPlayer mediaPlayer;

    @FXML
    private MFXListView<Video> inputFileList;
    @FXML
    private BorderPane displayViewPane;
    @FXML
    private final ImageView displayView = new ImageView();
    @FXML
    private MFXButton flagStartBtn;
    @FXML
    private MFXButton flagCompleteBtn;
    @FXML
    private MFXButton flagEditBtn;
    @FXML
    private MFXButton flagCancelBtn;
    @FXML
    private MFXListView outputFileList;
    @FXML
    private MFXButton exportOutputBtn;
    @FXML
    private MFXButton inputAddBtn;
    @FXML
    private MFXButton inputRemoveBtn;
    @FXML
    private FontIcon displayCtrlIcon;
    @FXML
    private MFXButton displayCtrlBtn;
    @FXML
    private MFXButton displayStopBtn;
    @FXML
    private StackPane displayCtrlPane;
    private PositionBar displayPositionBar;
    @FXML
    private MFXButton outputMoveUpBtn;
    @FXML
    private MFXButton outputMoveDownBtn;

    public MainController() {
        var mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        mediaPlayer.events().addMediaPlayerEventListener(
                new MediaPlayerEventAdapter() {
                    @Override
                    public void playing(MediaPlayer mediaPlayer) {
                        // set icon to pause
                        displayCtrlIcon.setIconLiteral("mdi2p-pause");
                    }

                    @Override
                    public void paused(MediaPlayer mediaPlayer) {
                        // set icon to play
                        displayCtrlIcon.setIconLiteral("mdi2p-play");
                    }

                    @Override
                    public void opening(MediaPlayer mediaPlayer) {
                        displayStopBtn.setDisable(false);
                    }

                    @Override
                    public void stopped(MediaPlayer mediaPlayer) {
                        displayCtrlIcon.setIconLiteral("mdi2p-play");
                        setDisplayView();
                    }
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // center player
        displayView.fitWidthProperty().bind(displayViewPane.widthProperty());
        displayView.fitHeightProperty().bind(displayViewPane.heightProperty());
        displayView.setPreserveRatio(true);
        displayViewPane.setCenter(displayView);
        displayViewPane.setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL2));
        mediaPlayer.videoSurface().set(
                PixelBufferVideoSurface.pixelBufferVideoSurfaceForImageView(displayView));
        displayPositionBar = new PositionBar(mediaPlayer);
        displayCtrlPane.getChildren().add(displayPositionBar);
        setDisplayView();

        // input file list
        var converter = FunctionalStringConverter.to(Video::getDisplayName);
        inputFileList.setConverter(converter);
        inputFileList.setCellFactory(v -> new VideoCell(inputFileList, v));
    }

    @FXML
    protected void onAddInputClick() {
        var inputFileListArr = inputFileList.getItems();

        var chooser = new FileChooser();

        chooser.setTitle("导入视频文件");
        chooser.getExtensionFilters().addAll(FileExtensions.getVideoExtensions());

        var fileList = chooser.showOpenMultipleDialog(new Stage());
        if (fileList == null) {
            return;
        }

        fileList.forEach(f -> {
            var video = new Video(f);
            var index = inputFileListArr.size();

            inputFileListArr.add(index, video);

            var item = inputFileList.getCell(index);
            item.setOnMouseClicked(e -> {
                // video item clicked
                mediaPlayer.media().play(
                        video.getFile().getPath(), "play-and-pause");  // pause before finished
                displayCtrlIcon.setIconLiteral("mdi2p-pause");
            });
        });
    }

    @FXML
    protected void onRemoveInputClick() {
        var inputFileListArr = inputFileList.getItems();
        var selectFileListArr = inputFileList.getSelectionModel().getSelectedValues();

        inputFileListArr.removeAll(selectFileListArr);

        // Only select one, must be the video witch are playing
        onStopDisplayClick();
    }

    @FXML
    protected void onCtrlDisplayClick() {
        if (mediaPlayer.status().isPlaying()) {
            if (mediaPlayer.status().canPause()) {
                // pause
                mediaPlayer.controls().pause();
            }
        } else {
            // play
            mediaPlayer.controls().play();
        }
    }

    @FXML
    protected void onStopDisplayClick() {
        mediaPlayer.controls().stop();
    }


    private VideoFragment fragmentInEdit;

    @FXML
    protected void onFlagStartClick() {
        var selectedList = inputFileList.getSelectionModel().getSelectedValues();
        if (selectedList.size() < 1) {
            // no item selected
            return;
        }

        var video = selectedList.get(0);
        video.startFragment(displayPositionBar.getVideoTime());
    }

    @FXML
    protected void onFlagCompleteClicked() {
        var selectedList = inputFileList.getSelectionModel().getSelectedValues();
        if (selectedList.size() < 1) {
            // no item selected
            return;
        }

        var video = selectedList.get(0);
        fragmentInEdit = video.completeFragment(displayPositionBar.getVideoTime());
    }

    private void setDisplayView() {
        displayView.setImage(new Image(Objects.requireNonNull(
                getClass().getResource("images/player_background.png")).toExternalForm()));
        displayStopBtn.setDisable(true);
    }
}