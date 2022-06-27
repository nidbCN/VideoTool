package cn.gaein.java.video.tool.videosurface;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

import java.nio.ByteBuffer;

public class VideoBufferFormatCallback implements BufferFormatCallback {
    private final ImageView view;
    private int sourceWidth;
    private int sourceHeight;

    public VideoBufferFormatCallback(ImageView view) {
        this.view = view;
    }

    @Override
    public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
        return new RV32BufferFormat(sourceWidth, sourceHeight);
    }

    @Override
    public void allocatedBuffers(ByteBuffer[] buffers) {
        assert buffers[0].capacity() == sourceWidth * sourceHeight * 4;
        var pixelFormat = PixelFormat.getByteBgraPreInstance();

        var buffer = new PixelBuffer<>(sourceWidth, sourceHeight, buffers[0], pixelFormat);
        view.setImage(new WritableImage(buffer));
    }
}
