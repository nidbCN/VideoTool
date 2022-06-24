module cn.gaein.video.videotool {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires com.jfoenix;
    requires ffmpeg;
    requires uk.co.caprica.vlcj;
    requires uk.co.caprica.vlcj.javafx;

    opens cn.gaein.java.video.tool to javafx.fxml;
    exports cn.gaein.java.video.tool;
    exports cn.gaein.java.video.tool.controllers;
    opens cn.gaein.java.video.tool.controllers to javafx.fxml;
}