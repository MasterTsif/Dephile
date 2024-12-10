package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile
{
	GamePanel gp;

	public OBJ_Rock(GamePanel gp)
	{
		super( gp );
		this.gp = gp;

		name = "Rock";
		speed = 10;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}

	public void getImage()
	{
		up1 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		up2 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		down1 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		down2 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		left1 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		left2 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		right1 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
		right2 = setup( "/objects/rock_down_1", gp.getTileSize(), gp.getTileSize() );
	}

	public boolean haveResource(Entity user)
	{
		return user.ammo >= useCost;
	}

	public void subtractResource(Entity user)
	{
		user.ammo -= useCost;
	}
}
