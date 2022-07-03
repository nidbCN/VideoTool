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
    requires com.google.common;
    requires com.google.gson;

    exports cn.gaein.java.video.tool;
    exports cn.gaein.java.video.tool.compontents;
    exports cn.gaein.java.video.tool.compontents.cell;
    exports cn.gaein.java.video.tool.controllers;
    exports cn.gaein.java.video.tool.models;
    exports cn.gaein.java.video.tool.ffmpeg;

    opens cn.gaein.java.video.tool to javafx.fxml;
    opens cn.gaein.java.video.tool.controllers to javafx.fxml;
    exports cn.gaein.java.video.tool.videos;
}