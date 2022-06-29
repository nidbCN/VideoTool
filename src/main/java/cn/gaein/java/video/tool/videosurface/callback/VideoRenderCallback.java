package cn.gaein.java.video.tool.videosurface.callback;

import cn.gaein.java.video.tool.helper.BoxHelper;
import javafx.application.Platform;
import javafx.scene.image.PixelBuffer;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;

import java.nio.ByteBuffer;

/**
 * @author Gaein
 */
public class VideoRenderCallback implements RenderCallback {
    private final BoxHelper<PixelBuffer<ByteBuffer>> bufferBox;

    public VideoRenderCallback(BoxHelper<PixelBuffer<ByteBuffer>> bufferBox) {
        this.bufferBox = bufferBox;
    }

    @Override
    public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
        Platform.runLater(() -> bufferBox.getValue().updateBuffer(pb -> null));
    }
}
