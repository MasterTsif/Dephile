package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity
{
	GamePanel gp;

	public OBJ_Key(GamePanel gp)
	{
		super( gp );
		this.gp = gp;

		type = type_consumable;
		name = "Key";
		down1 = setup( "/objects/key", gp.getTileSize(), gp.getTileSize() );
		description = "[" + name + "]\nA golden key.";
	}

	public boolean use(Entity entity)
	{
		gp.gameState = gp.dialogueState;

		int objIndex = getDetected( entity, gp.obj, "Door" );
		if (objIndex != 999)
		{
			gp.ui.currentDialogue = "You used a " + name + "!";
			gp.playSE( 3 );
			gp.obj[objIndex] = null;
			return true;
		}
		else
		{
			gp.ui.currentDialogue = "There is no door here!";
			return false;
		}
	}
}