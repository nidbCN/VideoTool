package cn.gaein.java.video.tool.videosurface;

import cn.gaein.java.video.tool.videosurface.callback.VideoBufferFormatCallback;
import cn.gaein.java.video.tool.videosurface.callback.VideoRenderCallback;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

import java.nio.ByteBuffer;

public class PixelBufferVideoSurface extends CallbackVideoSurface {

    private PixelBuffer<ByteBuffer> buffer;

    public PixelBufferVideoSurface(ImageView view) {
        super(new VideoBufferFormatCallback(), new VideoBufferFormatCallback(), true, VideoSurfaceAdapters.getVideoSurfaceAdapter());
    }

    public static PixelBufferVideoSurface pixelBufferVideoSurfaceForImageView(ImageView view) {
        return new PixelBufferVideoSurface(view);
    }





}

