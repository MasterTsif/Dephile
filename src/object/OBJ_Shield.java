package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity
{
	public OBJ_Shield(GamePanel gp)
	{
		super( gp );
		name = "Shield";
		type = type_shield;
		down1 = setup( "/objects/shield", gp.getTileSize(), gp.getTileSize() );
		defenseValue = 1;
		description = "[" + name + "]\nA basic shield.";
	}
}
