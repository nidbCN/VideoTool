package cn.gaein.java.video.tool.videosurface;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;

import java.nio.ByteBuffer;

public class VideoRenderCallback implements RenderCallback {
    public VideoRenderCallback() {
    }

    @Override
    public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
    }
}
