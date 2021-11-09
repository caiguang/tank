package cc.caiguang.tank.util;

import javafx.scene.media.AudioClip;

public class SoundEffect {

    public static void play(String src) {
        AudioClip audioClip = new AudioClip(SoundEffect.class.getResource(src).toString());
        audioClip.setVolume(0.05);
        audioClip.play();
    }
}
