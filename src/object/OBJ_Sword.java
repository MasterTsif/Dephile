package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity
{
	public OBJ_Sword(GamePanel gp)
	{
		super( gp );
		name = "Shield";
		type = type_sword;
		down1 = setup( "/objects/sword", gp.getTileSize(), gp.getTileSize() );
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nA basic sword.";
	}
}
