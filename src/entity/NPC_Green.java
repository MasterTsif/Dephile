package entity;

import main.GamePanel;

public class NPC_Green extends Entity
{

	private static final int SOLID_AREA_X = 10;
	private static final int SOLID_AREA_Y = 0;
	private static final int SOLID_AREA_WIDTH = 48;
	private static final int SOLID_AREA_HEIGHT = 48;

	public NPC_Green(GamePanel gp)
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
		up1 = setup( "/npc/green/up", tileSize, tileSize );
		down1 = setup( "/npc/green/down", tileSize, tileSize );
		left1 = setup( "/npc/green/left", tileSize, tileSize );
		right1 = setup( "/npc/green/right", tileSize, tileSize );
	}

	private void loadDialogues()
	{
		dialogues[0] = "0/7. Hail, wanderer of fated paths,\nthe dark has chosen you...";
		dialogues[1] = "1/7. Ask not how we came to this desolation,\nfor none here remember the light of before.";
		dialogues[2] = "2/7. Only whispers remain. The chest holds our\nescape,a fragile hope amidst the shadow.";
		dialogues[3] = "3/7. To the left it lies, yet the cruel winds of\ndestiny bid you journey first to the right.";
		dialogues[4] = "4/7. Riches may glimmer in the gloom, yet\ntread lightly—traps yawn open\nand beasts hunger for the unwary.";
		dialogues[5] = "5/7. The waters heal, but heed this warning:\n\"They grant reprieve to your foes as well.\"";
		dialogues[6] = "6/7. In the heart of the castle, a key awaits.\nSeize it, swift and bold,\nor be lost to the creeping dusk.";
		dialogues[7] = "7/7. Take up your axe to cleave the pale wood and\nvibrant crystal,and ready your blade and\nshield against what stirs in the dark.";

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
