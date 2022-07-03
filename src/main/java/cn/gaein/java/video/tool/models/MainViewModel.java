package cn.gaein.java.video.tool.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Gaein
 */
public class MainViewModel {
    private final StringProperty status
            = new SimpleStringProperty("空闲");

    public StringProperty statusProperty() {
        return status;
    }
}
