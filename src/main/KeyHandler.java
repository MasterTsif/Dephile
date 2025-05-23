package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	private final GamePanel gp;

	// Movement flags
	public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, vfor, shotKeyPressed, enterPressed;

	// Debug
	public boolean DebugConsole = false;

	public KeyHandler(GamePanel gp)
	{
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Not used
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode(); // Key code of the pressed key

		// Title State
		if (gp.gameState == gp.titleState)
		{
			handleTitleState( code );
		}
		// About State
		else if (gp.gameState == gp.aboutState)
		{
			handleAboutState( code );
		}
		// Play State
		else if (gp.gameState == gp.playState)
		{
			handlePlayState( code );
		}
		// Pause State
		else if (gp.gameState == gp.pauseState)
		{
			handlePauseState( code );
		}
		// Dialogue State
		else if (gp.gameState == gp.dialogueState)
		{
			handleDialogueState( code );
		}
		// Character State
		else if (gp.gameState == gp.characterState)
		{
			handleCharacterState( code );
		}
		else if (gp.gameState == gp.optionsState)
		{
			handleOptionsState( code );
		}
		else if (gp.gameState == gp.gameOverState)
		{
			handleGameOverState( code );
		}

		// Debug mode toggle
		if (code == KeyEvent.VK_F1)
		{
			DebugConsole = !DebugConsole;
		}
		if (code == KeyEvent.VK_F12)
		{
			vfor = true;
		}
	}

	private void handleGameOverState(int code)
	{
		if (code == KeyEvent.VK_W)
		{
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0)
			{
				gp.ui.commandNum = 1;
			}
			gp.playSE( 10 );
		}
		if (code == KeyEvent.VK_S)
		{
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1)
			{
				gp.ui.commandNum = 0;
			}
			gp.playSE( 10 );
		}
		if (code == KeyEvent.VK_ENTER)
		{
			if (gp.ui.commandNum == 0)
			{
				gp.playSE( 10 );
				gp.gameState = gp.playState;
				gp.retry();
			}
			else if (gp.ui.commandNum == 1)
			{
				gp.playSE( 10 );
				gp.gameState = gp.titleState;
				gp.restart();
			}
		}
	}

	private void handleTitleState(int code)
	{
		if (code == KeyEvent.VK_W)
		{
			gp.ui.commandNum = (gp.ui.commandNum - 1 + 3) % 3; // Wraps between 0 and 2
			gp.playSE( 10 );
		}
		else if (code == KeyEvent.VK_S)
		{
			gp.ui.commandNum = (gp.ui.commandNum + 1) % 3; // Wraps between 0 and 2
			gp.playSE( 10 );
		}
		else if (code == KeyEvent.VK_ENTER)
		{
			switch (gp.ui.commandNum)
			{
				case 0 ->
				{
					gp.playSE( 10 );
					gp.gameState = gp.playState;
				}
				case 1 ->
				{
					gp.playSE( 10 );
					gp.gameState = gp.aboutState;
				}
				case 2 ->
				{
					gp.playSE( 10 );
					System.exit( 0 );

				}
			}
		}
	}

	private void handleAboutState(int code)
	{
		if (code == KeyEvent.VK_BACK_SPACE)
		{
			gp.gameState = gp.titleState;
			gp.playSE( 10 );
		}
	}

	private void handlePlayState(int code)
	{
		switch (code)
		{
			case KeyEvent.VK_W -> upPressed = true;
			case KeyEvent.VK_S -> downPressed = true;
			case KeyEvent.VK_A -> leftPressed = true;
			case KeyEvent.VK_D -> rightPressed = true;
			case KeyEvent.VK_SPACE -> gp.gameState = gp.pauseState;
			case KeyEvent.VK_E -> ePressed = true;
			case KeyEvent.VK_C -> gp.gameState = gp.characterState;
			case KeyEvent.VK_F -> shotKeyPressed = true;
			case KeyEvent.VK_ESCAPE -> gp.gameState = gp.optionsState;
		}
	}

	private void handlePauseState(int code)
	{
		if (code == KeyEvent.VK_SPACE)
		{
			gp.gameState = gp.playState;
		}
	}

	private void handleDialogueState(int code)
	{
		if (code == KeyEvent.VK_E)
		{
			gp.gameState = gp.playState;
		}
	}

	private void handleCharacterState(int code)
	{
		if (code == KeyEvent.VK_C)
		{
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_W)
		{
			if (gp.ui.slotRow != 0)
			{
				gp.ui.slotRow--;
				gp.playSE( 10 );
			}
		}
		if (code == KeyEvent.VK_S)
		{
			if (gp.ui.slotRow != 3)
			{
				gp.ui.slotRow++;
				gp.playSE( 10 );
			}
		}
		if (code == KeyEvent.VK_A)
		{
			if (gp.ui.slotCol != 0)
			{
				gp.ui.slotCol--;
				gp.playSE( 10 );
			}
		}
		if (code == KeyEvent.VK_D)
		{
			if (gp.ui.slotCol != 4)
			{
				gp.ui.slotCol++;
				gp.playSE( 10 );
			}
		}
		if (code == KeyEvent.VK_ENTER)
		{
			gp.player.selectItem();
		}
	}

	private void handleOptionsState(int code)
	{
		if (code == KeyEvent.VK_ESCAPE)
		{
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER)
		{
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState)
		{
			case 0 -> maxCommandNum = 5;
			case 3 -> maxCommandNum = 1;
		}
		if (code == KeyEvent.VK_W)
		{
			gp.ui.commandNum--;
			gp.playSE( 10 );
			if (gp.ui.commandNum < 0)
			{
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if (code == KeyEvent.VK_S)
		{
			gp.ui.commandNum++;
			gp.playSE( 10 );
			if (gp.ui.commandNum > maxCommandNum)
			{
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_A)
		{
			if (gp.ui.subState == 0)
			{
				if (gp.ui.commandNum == 1 && gp.sound.volumeScale > 0)
				{
					gp.sound.volumeScale--;
					gp.sound.checkVolume();
					gp.playSE( 10 );
				}
			}
		}
		if (code == KeyEvent.VK_D)
		{
			if (gp.ui.subState == 0)
			{
				if (gp.ui.commandNum == 1 && gp.sound.volumeScale < 5)
				{
					gp.sound.volumeScale++;
					gp.sound.checkVolume();
					gp.playSE( 10 );
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();
		switch (code)
		{
			case KeyEvent.VK_W -> upPressed = false;
			case KeyEvent.VK_S -> downPressed = false;
			case KeyEvent.VK_A -> leftPressed = false;
			case KeyEvent.VK_D -> rightPressed = false;
			case KeyEvent.VK_F -> shotKeyPressed = false;
		}
	}
}
