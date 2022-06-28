module cn.gaein.video.videotool {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;
    requires MaterialFX;
    requires ffmpeg;
    requires uk.co.caprica.vlcj;
    requires uk.co.caprica.vlcj.javafx;

    exports cn.gaein.java.video.tool;
    exports cn.gaein.java.video.tool.compontents;
    exports cn.gaein.java.video.tool.compontents.cell;
    exports cn.gaein.java.video.tool.controllers;
    exports cn.gaein.java.video.tool.models;

    opens cn.gaein.java.video.tool to javafx.fxml;
    opens cn.gaein.java.video.tool.controllers to javafx.fxml;
}