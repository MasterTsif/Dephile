package entity;

import main.GamePanel;

public class NPC_Ice extends Entity
{

	private static final int SOLID_AREA_X = 10;
	private static final int SOLID_AREA_Y = 0;
	private static final int SOLID_AREA_WIDTH = 48;
	private static final int SOLID_AREA_HEIGHT = 48;

	public NPC_Ice(GamePanel gp)
	{
		super( gp );
		setupSolidArea();
		loadImages();
		loadDialogues();
	}

	private void setupSolidArea()
	{
		solidArea.x = SOLID_AREA_X;
		solidArea.y = SOLID_AREA_Y;
		solidArea.width = SOLID_AREA_WIDTH;
		solidArea.height = SOLID_AREA_HEIGHT;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	private void loadImages()
	{
		int tileSize = gp.getTileSize();
		up1 = setup( "/npc/ice/up", tileSize, tileSize );
		down1 = setup( "/npc/ice/down", tileSize, tileSize );
		left1 = setup( "/npc/ice/left", tileSize, tileSize );
		right1 = setup( "/npc/ice/right", tileSize, tileSize );
	}

	private void loadDialogues()
	{
		dialogues[0] = "0/6.You have walked the bitter path and\nstand now at the edge of fate.";
		dialogues[1] = "1/6.This is the Ice Kingdom, where frost\ntests both flesh and spirit.";
		dialogues[2] = "2/6.Endure the cold, and the golden chest\nshall reveal itself.";
		dialogues[3] = "3/6.Behind the crystals lies the final key.\nTake it, but tread lightly.";
		dialogues[4] = "4/6.Beware the shadows that haunt the\nice—ancient and merciless.";
		dialogues[5] = "5/6.The chest holds freedom, but truth\nhere cuts sharper than the cold.";
		dialogues[6] = "6/6.Do not falter, for the end nears,\nand the world waits for your choice.";

	}

	@Override
	public void update()
	{
		// Custom update logic if needed
	}

	@Override
	public void speak()
	{
		super.speak();
	}
}
