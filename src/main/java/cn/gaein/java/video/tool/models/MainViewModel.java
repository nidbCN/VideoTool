package cn.gaein.java.video.tool.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Gaein
 */
public class MainViewModel {
    private final StringProperty status
            = new SimpleStringProperty("空闲");
    private final String tempPath
            = Files.createTempDirectory(Path.of("D:\\Temp"), "VideoToolsExport_").toString();
    private final StringProperty cacheSpace
            = new SimpleStringProperty("0GB");

    public MainViewModel() throws IOException {
        cacheSpace.set(Path.of(tempPath)
                .getRoot().toFile().getFreeSpace() / (1024 * 1024 * 1024) + "GB"
        );
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty cacheSpaceProperty() {
        return cacheSpace;
    }

    public String getTempPath() {
        return tempPath;
    }
}
