package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PotionRed extends Entity
{
	GamePanel gp;

	public OBJ_PotionRed(GamePanel gp)
	{
		super( gp );
		this.gp = gp;

		name = "Red Potion";
		type = type_consumable;
		value = 5;
		down1 = setup( "/objects/potion_red", gp.getTileSize(), gp.getTileSize() );
		description = "[" + name + "]\nRestores " + value + "  HP.";
	}

	@Override
	public boolean use(Entity entity)
	{
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " + name + ".\nYou restore " + value + " HP!";
		entity.life += value;
		gp.playSE( 3 );
		return true;
	}
}
