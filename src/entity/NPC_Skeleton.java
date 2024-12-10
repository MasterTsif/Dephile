package entity;

import main.GamePanel;

public class NPC_Skeleton extends Entity
{

	private static final int SOLID_AREA_X = 10;
	private static final int SOLID_AREA_Y = 0;
	private static final int SOLID_AREA_WIDTH = 48;
	private static final int SOLID_AREA_HEIGHT = 48;

	public NPC_Skeleton(GamePanel gp)
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
		up1 = setup( "/npc/skeleton/up", tileSize, tileSize );
		down1 = setup( "/npc/skeleton/down", tileSize, tileSize );
		left1 = setup( "/npc/skeleton/left", tileSize, tileSize );
		right1 = setup( "/npc/skeleton/right", tileSize, tileSize );
	}

	private void loadDialogues()
	{
		dialogues[0] = "0/2.This is the end, my friend.";
		dialogues[1] = "1/1.No puzzles remain, no traps to spring,\nno foes to face.";
		dialogues[2] = "2/2.May our paths cross again, in light\nor in shadow.";
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
