package cn.gaein.java.video.tool.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author Gaein
 */
public class ExportViewModel {
    private final BooleanProperty disableAudio
            = new SimpleBooleanProperty();
    private final BooleanProperty enableEncode
            = new SimpleBooleanProperty();
    private final DoubleProperty videoRate
            = new SimpleDoubleProperty(2048);
    private final DoubleProperty audioRate
            = new SimpleDoubleProperty(512);

    public BooleanProperty disableAudioProperty() {
        return disableAudio;
    }

    public BooleanProperty enableEncodeProperty() {
        return enableEncode;
    }

    public DoubleProperty videoRateProperty() {
        return videoRate;
    }

    public DoubleProperty audioRateProperty() {
        return audioRate;
    }
}
