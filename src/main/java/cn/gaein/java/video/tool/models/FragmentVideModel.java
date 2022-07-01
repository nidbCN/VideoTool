package cn.gaein.java.video.tool.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Gaein
 */
public class FragmentVideModel {
    private final BooleanProperty disableAudio
            = new SimpleBooleanProperty();
    private final BooleanProperty enableCrop
            = new SimpleBooleanProperty();
    private final IntegerProperty cropFrom
            = new SimpleIntegerProperty();
    private final IntegerProperty cropTo
            = new SimpleIntegerProperty();
    private final IntegerProperty cropWidth
            = new SimpleIntegerProperty();
    private final IntegerProperty cropHeight
            = new SimpleIntegerProperty();

    public BooleanProperty disableAudioProperty() {
        return disableAudio;
    }

    public BooleanProperty enableCropProperty() {
        return enableCrop;
    }

    public IntegerProperty cropFromProperty() {
        return cropFrom;
    }

    public IntegerProperty cropToProperty() {
        return cropTo;
    }

    public IntegerProperty cropWidthProperty() {
        return cropWidth;
    }

    public IntegerProperty cropHeightProperty() {
        return cropHeight;
    }
}
