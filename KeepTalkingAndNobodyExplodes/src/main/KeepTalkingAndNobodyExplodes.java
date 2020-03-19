package main;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class KeepTalkingAndNobodyExplodes {

	public static void main(String[] args) {

		// Audio clips
		Clip menuMusic = null;
		Clip defusedSound = null;
		Clip explosionSound = null;

		Timing timing = new Timing(4000);
		GameWindow window = new GameWindow();

		// Open the Audio clips
		try {
			menuMusic = AudioSystem.getClip();
			menuMusic.open(AudioSystem.getAudioInputStream(KeepTalkingAndNobodyExplodes.class.getResourceAsStream("/menuLoop.wav")));
			defusedSound = AudioSystem.getClip();
			defusedSound.open(AudioSystem.getAudioInputStream(KeepTalkingAndNobodyExplodes.class.getResourceAsStream("/defused.wav")));
			explosionSound = AudioSystem.getClip();
			explosionSound.open(AudioSystem.getAudioInputStream(KeepTalkingAndNobodyExplodes.class.getResourceAsStream("/timer/explosion.wav")));

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		}

		// Game loop
		while (true) {

			defusedSound.setMicrosecondPosition(0);
			explosionSound.setMicrosecondPosition(0);
			menuMusic.setMicrosecondPosition(0);
			// Start menu music
			menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
			menuMusic.start();

			// Start the menu
			window.startMenu();

			// Wait for user to click the play button
			while (!window.getMenu().getPlayButton().isPressed())
				if (window.getMenu().getQuitButton().isClick())
					System.exit(0);
				else
					window.pause(1);

			// Start the game
			window.startGame();

			// Wait for the bomb to explode or to be solved
			while (!window.getBomb().isExploded() && !window.getBomb().isSolved())
				window.pause(1);

			// Play the explosion or defuse sound
			if (window.getBomb().isExploded()) {
				explosionSound.start();
			} else if (window.getBomb().isSolved()) {
				defusedSound.start();
			}

			// Stop the menu music and wait for repeat
			menuMusic.stop();
			
			timing.start();
			while (timing.countDown())
				;
			explosionSound.stop();
			defusedSound.stop();
		}
	}
}
