package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity
{
	public OBJ_Axe(GamePanel gp)
	{
		super( gp );
		name = "Axe";
		type = type_axe;
		down1 = setup( "/objects/axe", gp.getTileSize(), gp.getTileSize() );
		attackValue = 2;
		attackArea.width = 28;
		attackArea.height = 28;
		description = "[" + name + "]\nA basic axe.";
	}
}
