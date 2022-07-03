package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.ExportViewModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gaein
 */
public class ExportController implements Initializable {
    @FXML
    private MFXTextField audioRate;
    @FXML
    private MFXTextField videoRate;
    @FXML
    private MFXComboBox<String> videoCodecList;
    @FXML
    private MFXComboBox<String> audioCodecList;
    @FXML
    private MFXToggleButton enableEncodeBtn;
    @FXML
    private MFXButton closeBtn;
    @FXML
    private MFXToggleButton disableAudioBtn;

    private final Stage stage;
    private final ExportViewModel viewModel;

    public ExportController(Stage stage, ExportViewModel viewModel) {
        this.stage = stage;
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init bind property
        viewModel.disableAudioProperty().bind(disableAudioBtn.selectedProperty());
        viewModel.enableEncodeProperty().bind(enableEncodeBtn.selectedProperty());
        viewModel.audioCodecProperty().bind(audioCodecList.selectedItemProperty());
        viewModel.videoCodecProperty().bind(videoCodecList.selectedItemProperty());
        viewModel.audioRateProperty().bind(audioRate.textProperty());
        viewModel.videoRateProperty().bind(videoRate.textProperty());

        videoCodecList.getItems().addAll(
                "h264", "hevc", "av1", "dvvideo", "flv1", "mpeg4", "rawvideo", "vp9"
        );

        audioCodecList.getItems().addAll(
                "aac", "dvaudio", "flac", "mp3", "pcm_s16le", "sonic", "vorbis"
        );

        videoCodecList.selectFirst();
        audioCodecList.selectFirst();
        videoRate.textProperty().set("2048");
        audioRate.textProperty().set("512");
    }

    @FXML
    protected void onCloseBtnClicked() {
        stage.close();
    }
}
