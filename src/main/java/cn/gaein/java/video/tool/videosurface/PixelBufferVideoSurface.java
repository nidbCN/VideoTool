package cn.gaein.java.video.tool.videosurface;

import cn.gaein.java.video.tool.helper.BoxHelper;
import cn.gaein.java.video.tool.videosurface.callback.VideoBufferFormatCallback;
import cn.gaein.java.video.tool.videosurface.callback.VideoRenderCallback;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters;

import java.nio.ByteBuffer;

public class PixelBufferVideoSurface extends CallbackVideoSurface {
    public PixelBufferVideoSurface(ImageView view, BoxHelper<PixelBuffer<ByteBuffer>> bufferBox) {
        super(new VideoBufferFormatCallback(view, bufferBox),
                new VideoRenderCallback(bufferBox),
                true,
                VideoSurfaceAdapters.getVideoSurfaceAdapter());
    }

    public static PixelBufferVideoSurface pixelBufferVideoSurfaceForImageView(ImageView view) {
        var bufferBox = new BoxHelper<PixelBuffer<ByteBuffer>>();
        return new PixelBufferVideoSurface(view, bufferBox);
    }
}

