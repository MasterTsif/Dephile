package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound
{
	private Clip musicClip; // Dedicated to background music
	private Clip effectClip; // Dedicated to sound effects
	private FloatControl volumeControl;
	int volumeScale = 3;
	private float volume;
	URL[] soundURL = new URL[30];
	private long pausePosition = 0;

	public Sound()
	{
		soundURL[0] = this.getClass().getResource( "/sound/MainTheme.wav" );
		soundURL[1] = this.getClass().getResource( "/sound/levelup.wav" );
		soundURL[2] = this.getClass().getResource( "/sound/unlock.wav" );
		soundURL[3] = this.getClass().getResource( "/sound/coin.wav" );
		soundURL[4] = this.getClass().getResource( "/sound/vfor.wav" );
		soundURL[5] = this.getClass().getResource( "/sound/speak.wav" );
		soundURL[6] = this.getClass().getResource( "/sound/hitmonster.wav" );
		soundURL[7] = this.getClass().getResource( "/sound/blocked.wav" );
		soundURL[8] = this.getClass().getResource( "/sound/burning.wav" );
		soundURL[9] = this.getClass().getResource( "/sound/receivedamage.wav" );
		soundURL[10] = this.getClass().getResource( "/sound/cursor.wav" );
		soundURL[11] = this.getClass().getResource( "/sound/cuttree.wav" );
		soundURL[12] = this.getClass().getResource( "/sound/gameover.wav" );
	}

	public void setMusicFile(int i)
	{
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream( soundURL[i] );
			musicClip = AudioSystem.getClip();
			musicClip.open( ais );
			volumeControl = (FloatControl) musicClip.getControl( FloatControl.Type.MASTER_GAIN );
			checkVolume();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setEffectFile(int i)
	{
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream( soundURL[i] );
			effectClip = AudioSystem.getClip();
			effectClip.open( ais );
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void playMusic()
	{
		if (musicClip != null)
		{
			musicClip.start();
		}
	}

	public void loopMusic()
	{
		if (musicClip != null)
		{
			musicClip.loop( Clip.LOOP_CONTINUOUSLY );
		}
	}

	public void stopMusic()
	{
		if (musicClip != null)
		{
			musicClip.stop();
		}
	}

	public void pauseMusic()
	{
		if (musicClip != null && musicClip.isRunning())
		{
			pausePosition = musicClip.getMicrosecondPosition();
			musicClip.stop();
		}
	}

	public void resumeMusic()
	{
		if (musicClip != null && !musicClip.isRunning())
		{
			musicClip.setMicrosecondPosition( pausePosition );
			musicClip.start();
		}
	}

	public void playEffect()
	{
		if (effectClip != null)
		{
			effectClip.start();
		}
	}

	public void setVolume(float volume)
	{
		if (volumeControl != null)
		{
			// Volume should be in the range of 0.0 (silent) to 1.0 (full volume)
			volume = Math.max( 0.0f, Math.min( volume, 0.5f ) );
			float db = (float) (Math.log( volume ) / Math.log( 10 ) * 20); // Convert to decibels
			volumeControl.setValue( db ); // Set the volume in decibels
		}
	}

	public void checkVolume()
	{
		switch (volumeScale)
		{
			case 0:
				volume = -80f;
				break;
			case 1:
				volume = -20f;
				break;
			case 2:
				volume = -12f;
				break;
			case 3:
				volume = -5f;
				break;
			case 4:
				volume = 1f;
				break;
			case 5:
				volume = 6f;
				break;
		}
		volumeControl.setValue( volume );
	}
}
