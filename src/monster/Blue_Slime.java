package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class Blue_Slime extends Entity
{
	GamePanel gp;

	public Blue_Slime(GamePanel gp)
	{
		super( gp );
		this.gp = gp;
		type = type_monster;
		name = "Blue Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 2;
		defence = 0;
		exp = 2;

		solidArea.x = 3;
		solidArea.y = 9;
		solidArea.width = 42;
		solidArea.height = 42;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage()
	{
		up1 = setup( "/monster/slime_up", gp.getTileSize(), gp.getTileSize() );
		up2 = setup( "/monster/slime_down", gp.getTileSize(), gp.getTileSize() );
		down1 = setup( "/monster/slime_up", gp.getTileSize(), gp.getTileSize() );
		down2 = setup( "/monster/slime_down", gp.getTileSize(), gp.getTileSize() );
		left1 = setup( "/monster/slime_up", gp.getTileSize(), gp.getTileSize() );
		left2 = setup( "/monster/slime_down", gp.getTileSize(), gp.getTileSize() );
		right1 = setup( "/monster/slime_up", gp.getTileSize(), gp.getTileSize() );
		right2 = setup( "/monster/slime_down", gp.getTileSize(), gp.getTileSize() );
	}

	public void setAction()
	{
		actionLockCounter++;

		if (actionLockCounter == 120)
		{
			Random random = new Random();
			int i = random.nextInt( 100 ) + 1;
			if (i <= 25)
			{
				direction = "up";
			}
			if (i > 25 && i <= 50)
			{
				direction = "down";
			}
			if (i > 50 && i <= 75)
			{
				direction = "left";
			}
			if (i > 75 && i <= 100)
			{
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}

	public void damageReaction()
	{
		actionLockCounter = 0;
		direction = gp.player.direction; //make the slime face the opposite of player
	}

	public void checkDrop()
	{
		// cast a die
		int i = new Random().nextInt( 100 ) + 1;
		// set the monster drop
		if (i < 50)
		{
			dropItem( new OBJ_Coin_Bronze( gp ) );
		}
		if (i >= 50 && i < 75)
		{
			dropItem( new OBJ_Heart( gp ) );
		}
		if (i >= 75 && i < 100)
		{
			dropItem( new OBJ_ManaCrystal( gp ) );
		}
	}

}
