package cn.gaein.java.video.tool.videosurface.callback;

import cn.gaein.java.video.tool.helper.BoxHelper;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

import java.nio.ByteBuffer;

/**
 * @author Gaein
 */
public class VideoBufferFormatCallback implements BufferFormatCallback {
    private final ImageView view;
    private final BoxHelper<PixelBuffer<ByteBuffer>> bufferBox;
    private int sourceWidth;
    private int sourceHeight;

    public VideoBufferFormatCallback(ImageView view, BoxHelper<PixelBuffer<ByteBuffer>> bufferBox) {
        this.view = view;
        this.bufferBox = bufferBox;
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
        bufferBox.setValue(buffer);
        view.setImage(new WritableImage(buffer));
    }
}
