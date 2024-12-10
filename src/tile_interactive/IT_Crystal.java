package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_Crystal extends InteractiveTile
{
	GamePanel gp;

	public IT_Crystal(GamePanel gp, int col, int row)
	{
		super( gp, col, row );
		this.gp = gp;
		this.worldX = gp.getTileSize() * col;
		this.worldY = gp.getTileSize() * row;

		name = "Crystal";
		down1 = setup( "/tiles_interactive/crystal", gp.getTileSize(), gp.getTileSize() );
		destructible = true;
		life = 3;
	}

	public boolean isCorrectItem(Entity entity)
	{
		return entity.currentWeapon.type == type_axe;
	}

	public void playSE()
	{
		gp.playSE( 11 );
	}
}
