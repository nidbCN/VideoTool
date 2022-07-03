package cn.gaein.java.video.tool.controllers;

import cn.gaein.java.video.tool.models.FragmentViewModel;
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

    private final FragmentViewModel viewModel;

    public FragmentController(Stage stage, FragmentViewModel viewModel) {
        this.stage = stage;
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init bind property
        viewModel.disableAudioProperty().bind(disableAudioBtn.selectedProperty());
        viewModel.enableCropProperty().bind(enableCropBtn.selectedProperty());
        viewModel.cropFromProperty().bind(cropFromText.textProperty());
        viewModel.cropToProperty().bind(cropToText.textProperty());
        viewModel.cropWidthProperty().bind(cropWidthText.textProperty());
        viewModel.cropHeightProperty().bind(cropHeightText.textProperty());
        viewModel.enableEncodeProperty().bind(enableEncodeBtn.selectedProperty());

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


        stage.close();
    }
}
