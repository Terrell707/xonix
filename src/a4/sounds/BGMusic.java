package a4.sounds;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class BGMusic {

private AudioClip clip;		// Holds the sound clip to be played.
	
	/**
	 * No-arg constructor. Creates the audio clip to be played.
	 */
	public BGMusic()
	{
		// Creates the directory in which the sound is located.
		String soundDir = "." + File.separator + "sounds" + File.separator + "bgMusic.wav";
		
		// Tries to create the sound.
		try 
		{
			// Creates a file holding the directory of the sound. Will then turn that file name into a URL
			//	so that the sound can be accessed.
			File file = new File(soundDir);
			if (file.exists()){
				clip = Applet.newAudioClip(file.toURI().toURL());
			}
			else {
				throw new RuntimeException("Sound: file not found: " + soundDir);
			}
		}
		// If the sound cannot be created, this will catch the error.
		catch (MalformedURLException e)
		{
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
	}
	
	/**
	 * Plays the sound clip.
	 */
	public void play()
	{
		clip.play();
	}
	
	/**
	 * Loops the sound clip.
	 */
	public void loop()
	{
		clip.loop();
	}
	
	/**
	 * Stops the sound clip if its playing.
	 */
	public void stop()
	{
		clip.stop();
	}
}

