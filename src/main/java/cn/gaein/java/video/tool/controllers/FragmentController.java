package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.helper.DialogHelper;
import cn.gaein.java.video.tool.models.FragmentViewModel;
import cn.gaein.java.video.tool.models.VideoFragment;
import io.github.palexdev.materialfx.controls.MFXButton;
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
public class FragmentController implements Initializable {
    @FXML
    private MFXToggleButton enableEncodeBtn;
    @FXML
    private MFXButton cancelBtn;
    @FXML
    private MFXButton saveBtn;
    @FXML
    private MFXToggleButton enableCropBtn;
    @FXML
    private MFXTextField cropFromText;
    @FXML
    private MFXTextField cropToText;
    @FXML
    private MFXTextField cropWidthText;
    @FXML
    private MFXTextField cropHeightText;
    @FXML
    private MFXToggleButton disableAudioBtn;

    private final Stage stage;
    private final VideoFragment fragment;
    private final DialogHelper dialogHelper;
    private final FragmentViewModel fragmentModel
            = new FragmentViewModel();

    public FragmentController(Stage stage, VideoFragment fragment) {
        this.stage = stage;
        this.fragment = fragment;

        dialogHelper = new DialogHelper(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init bind property
        fragmentModel.disableAudioProperty().bind(disableAudioBtn.selectedProperty());
        fragmentModel.enableCropProperty().bind(enableCropBtn.selectedProperty());
        fragmentModel.cropFromProperty().bind(cropFromText.textProperty());
        fragmentModel.cropToProperty().bind(cropToText.textProperty());
        fragmentModel.cropWidthProperty().bind(cropWidthText.textProperty());
        fragmentModel.cropHeightProperty().bind(cropHeightText.textProperty());
        fragmentModel.enableEncodeProperty().bind(enableEncodeBtn.selectedProperty());

        cropFromText.disableProperty().bind(enableCropBtn.selectedProperty().not());
        cropToText.disableProperty().bind(enableCropBtn.selectedProperty().not());
        cropWidthText.disableProperty().bind(enableCropBtn.selectedProperty().not());
        cropHeightText.disableProperty().bind(enableCropBtn.selectedProperty().not());
    }

    @FXML
    protected void onCancelBtnClicked() {
        stage.close();
    }

    @FXML
    protected void onSaveBtnClicked() {
        if (fragmentModel.disableAudioProperty().get()) {
            fragment.edit(builder ->
                    builder.addOption("-an"));
        }

        if (fragmentModel.enableCropProperty().get()) {
            var cropFrom = fragmentModel.cropFromProperty().get();
            var cropTo = fragmentModel.cropToProperty().get();
            var cropWidth = fragmentModel.cropWidthProperty().get();
            var cropHeight = fragmentModel.cropHeightProperty().get();

            try {
                Integer.parseInt(cropFrom);
                Integer.parseInt(cropTo);
                Integer.parseInt(cropWidth);
                Integer.parseInt(cropHeight);
            } catch (NumberFormatException e) {
                dialogHelper.getErrorDialog("格式错误，请输入数字").show();
                return;
            }

            fragment.edit(builder ->
                    builder.setVideoFilter("crop=" + cropWidth
                            + ":" + cropHeight
                            + ":" + cropFrom
                            + ":" + cropTo)
            );
        }

        if (fragmentModel.enableEncodeProperty().get()) {
            fragment.edit(builder -> builder
                    .addOption("-vcodec", "h264")
                    .addOption("-acodec", "aac")
            );
        }

        stage.close();
    }
}
