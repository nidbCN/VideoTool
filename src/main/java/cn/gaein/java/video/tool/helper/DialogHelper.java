package cn.gaein.java.video.tool.helper;

import cn.gaein.java.video.tool.MainApplication;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.dialogs.MFXStageDialogBuilder;
import io.github.palexdev.materialfx.enums.ButtonType;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Gaein
 */
public class DialogHelper {
    private final Stage stage;
    private final MFXFontIcon errorIcon
            = new MFXFontIcon("mfx-exclamation-circle-filled", 16);
    private final MFXFontIcon infoIcon
            = new MFXFontIcon("mfx-info-circle-filled", 16);

    public DialogHelper(Stage stage) {
        this.stage = stage;
    }

    public MFXStageDialog getDialog(String content, Consumer<MFXGenericDialogBuilder> contentConfig, Consumer<MFXStageDialogBuilder> config) {
        var contentBuilder = MFXGenericDialogBuilder.build()
                .setContentText(content)
                .setShowMinimize(false)
                .setShowAlwaysOnTop(false)
                .makeScrollable(true)
                .addStylesheets(Objects.requireNonNull(MainApplication.class.getResource("styles/Fonts.css")).toExternalForm())
                .addStylesheets(Objects.requireNonNull(MainApplication.class.getResource("styles/Global.css")).toExternalForm())
                .addStyleClasses("source-font");

        contentConfig.accept(contentBuilder);
        var dialogContent = contentBuilder.get();

        var builder = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.WINDOW_MODAL)
                .setDraggable(false)
                .setAlwaysOnTop(true);
        config.accept(builder);

        var dialog = builder.get();

        var confirmBtn = new MFXButton("确定");
        confirmBtn.setButtonType(ButtonType.RAISED);
        confirmBtn.setStyle("-fx-background-color: -mfx-green; -fx-text-fill: white;");
        dialogContent.addActions(Map.entry(
                confirmBtn, event -> dialog.close()
        ));

        return dialog;
    }

    public MFXStageDialog getErrorDialog(String content) {
        return getErrorDialog(content, config -> {
        });
    }

    public MFXStageDialog getErrorDialog(String content, Consumer<MFXStageDialogBuilder> config) {
        return getDialog(content, contentBuilder -> contentBuilder
                        .setHeaderText("错误")
                        .setHeaderIcon(errorIcon)
                        .addStyleClasses("mfx-error-dialog"),
                config);
    }

    public MFXStageDialog getInfoDialog(String content) {
        return getInfoDialog(content, config -> {
        });
    }

    public MFXStageDialog getInfoDialog(String content, Consumer<MFXStageDialogBuilder> config) {
        return getDialog(content, contentBuilder -> contentBuilder
                        .setHeaderText("信息")
                        .setHeaderIcon(infoIcon)
                        .addStyleClasses("mfx-info-dialog")
                        .addStyleClasses("source-font"),
                config);
    }
}
