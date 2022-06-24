package cn.gaein.java.video.tool.models;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;

public class InputVideo {
    private final File file;
    private final FFmpegBuilder builder = new FFmpegBuilder();

    public InputVideo(String path) {
        file = new File(path);
        builder.setInput(file.getPath());
    }

    public boolean getIsExists() {
        return file.exists();
    }

    public String getDisplayName() {
        var fullName = file.getName();
        var extIndex = fullName.lastIndexOf('.');
        var pureName = fullName.substring(0, extIndex);
        var extName = fullName.substring(extIndex);

        var builder = new StringBuilder(fullName.length());

        if (pureName.length() <= 9) {
            builder.append(fullName);
        } else {
            builder.append(pureName, 0, 2);
            builder.append("...");
            builder.append(pureName, pureName.length() - 3, pureName.length() - 1);
            builder.append(extName);
        }

        return builder.toString();
    }

    public FFmpegBuilder getBuilder() {
        return builder;
    }
}
