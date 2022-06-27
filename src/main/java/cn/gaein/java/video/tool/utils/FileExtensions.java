package cn.gaein.java.video.tool.utils;

import javafx.stage.FileChooser;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class FileExtensions {
    public static final FileChooser.ExtensionFilter allVideo =
            new FileChooser.ExtensionFilter("所有支持的视频格式",
                    "*.avi;*.f4v;*.flv;*.m2ts;*.mkv;*.mov;*.mp4;*.mp4v;*.mpeg4;*.rm;*.rmvb;*.ts;*.webm");
    public static final FileChooser.ExtensionFilter avi =
            new FileChooser.ExtensionFilter("音频视频交错格式", "*.avi");
    public static final FileChooser.ExtensionFilter f4v =
            new FileChooser.ExtensionFilter("Flash MP4 格式", "*.f4v");
    public static final FileChooser.ExtensionFilter flv =
            new FileChooser.ExtensionFilter("Flash 视频格式", "*.flv");
    public static final FileChooser.ExtensionFilter m2ts =
            new FileChooser.ExtensionFilter("MPEG影音流格式", "*.m2ts");
    public static final FileChooser.ExtensionFilter mkv =
            new FileChooser.ExtensionFilter("Matroska多媒体容器", "*.mkv");
    public static final FileChooser.ExtensionFilter mov =
            new FileChooser.ExtensionFilter("QuickTime封装格式", "*.mov");
    public static final FileChooser.ExtensionFilter mp4 =
            new FileChooser.ExtensionFilter("MPEG-4 14格式", "*.mp4");
    public static final FileChooser.ExtensionFilter mp4v =
            new FileChooser.ExtensionFilter("MPEG-4 14视频格式", "*.mp4v");
    public static final FileChooser.ExtensionFilter mpeg4 =
            new FileChooser.ExtensionFilter("MPEG-4 编码格式", "*.mpeg4");
    public static final FileChooser.ExtensionFilter rm =
            new FileChooser.ExtensionFilter("RealMedia 容器", "*.rm");
    public static final FileChooser.ExtensionFilter rmvb =
            new FileChooser.ExtensionFilter("RealMedia 可变码率容器", "*.rmvb");
    public static final FileChooser.ExtensionFilter ts =
            new FileChooser.ExtensionFilter("MPEG2 传输流容器", "*.ts");
    public static final FileChooser.ExtensionFilter webm =
            new FileChooser.ExtensionFilter("开放网络媒体格式", "*.webm");

    public static Collection<FileChooser.ExtensionFilter> getVideoExtensions() {
        return List.of(allVideo, avi, f4v, flv, m2ts, mkv, mov, mp4, mp4v, mpeg4, rm, rmvb, ts, webm);
    }
}
