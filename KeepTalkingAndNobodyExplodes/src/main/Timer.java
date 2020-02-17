package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Timer {
	private BufferedImage timerFrame;
	private Font timerFont;
	private boolean depleted;

	long time;
	long millis;
	long sec;
	long min;
	long dsec;

	String minString = "";
	String secString = "";
	String dsecString = "";

	Clip beep;

	public Timer(int sec) {
		depleted = false;
		time = sec * 1000;

		try {
			timerFrame = ImageIO.read(getClass().getResourceAsStream("/timer/timerFrame.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create the Timer Font
		try {
			timerFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/timer/timerFont.ttf")).deriveFont(90f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		try {
			beep = AudioSystem.getClip();
			beep.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/timer/timerBeep.wav")));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		}
	}

	public String getTimerString() {
		long timePassed = new GregorianCalendar().getTimeInMillis() - millis;
		String ret = "";
		if (time - timePassed > 0) {
			min = (time - timePassed) / 60000;
			sec = (time - timePassed) % 60000 / 1000;
			dsec = (Math.round(((time - timePassed) % 1000) / 10));

			minString = "";
			secString = "";
			dsecString = "";

			if (min < 10)
				minString = "0";
			minString += String.valueOf(min);
			if (sec < 10)
				secString = "0";
			secString += String.valueOf(sec);
			if (dsec < 10)
				dsecString = "0";
			dsecString += String.valueOf(dsec);

			if (min > 0)
				ret = minString + ":" + secString;
			else ret = secString + ":" + dsecString;
		} else {
			ret = "00:00";
			depleted = true;
		}
		return ret;
	}

	public void start() {
		millis = new GregorianCalendar().getTimeInMillis();
	}

	public void update(Graphics g) {
		if (!depleted) {
			if(!beep.isRunning()) {
				beep.loop(Clip.LOOP_CONTINUOUSLY);
				beep.start();
			}
			g.drawImage(timerFrame, 1500, 0, null);
			g.setFont(timerFont);
			g.setColor(new Color(0x1e1e1e));
			g.drawString("88:88", 1545, 135);
			g.setColor(new Color(0xFF0000));
			g.drawString(getTimerString(), 1545, 135);
		}
	}

	/**
	 * @return the depleted
	 */
	public boolean isDepleted() {
		return depleted;
	}
}
