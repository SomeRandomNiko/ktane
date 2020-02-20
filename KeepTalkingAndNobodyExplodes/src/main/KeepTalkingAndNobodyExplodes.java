package main;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class KeepTalkingAndNobodyExplodes {

	public static void main(String[] args) {
		Clip menuMusic = null;
		try {
			menuMusic = AudioSystem.getClip();
			menuMusic.open(AudioSystem.getAudioInputStream(KeepTalkingAndNobodyExplodes.class.getResourceAsStream("/menuLoop.wav")));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		}

		GameWindow window = new GameWindow();
		window.makeWindow();
		menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
		menuMusic.start();
	}
}
