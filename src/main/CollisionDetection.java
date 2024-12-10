package main;

import entity.Entity;
import entity.NPC_Sand;

public class CollisionDetection
{

	GamePanel gp;

	public CollisionDetection(GamePanel gp)
	{
		this.gp = gp;
	}

	public void checkTile(Entity entity)
	{
		int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.x);
		int entityRightWorldX = (int) (entity.worldX + entity.solidArea.x + entity.solidArea.width);
		int entityTopWorldY = (int) (entity.worldY + entity.solidArea.y);
		int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.y + entity.solidArea.height);

		int entityLeftCol = entityLeftWorldX / gp.getTileSize();
		int entityRightCol = entityRightWorldX / gp.getTileSize();
		int entityTopRow = entityTopWorldY / gp.getTileSize();
		int entityBottomRow = entityBottomWorldY / gp.getTileSize();

		int tileNum1, tileNum2;


		if (entity instanceof NPC_Sand) {
			// Check if the NPC intersects with a grass tile (tileNum = 1)
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (tileNum1 == 8 || tileNum2 == 8) { // Tile 8 represents bridge
				entity.collisionOn = true; // Enable collision for NPC_Green on grass
				return; // Exit early to avoid further checks if collision is true
			}
		}

		switch (entity.direction)
		{
			case "up":
				entityTopRow = (entityTopWorldY - entity.speed) / gp.getTileSize(); //predict players next position
				tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
				{
					entity.collisionOn = true;
				}
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.getTileSize();
				tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
				{
					entity.collisionOn = true;
				}
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.getTileSize();
				tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
				{
					entity.collisionOn = true;
				}
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed) / gp.getTileSize();
				tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
				{
					entity.collisionOn = true;
				}
				break;
		}
	}

	public int checkObject(Entity entity, boolean player)
	{
		int index = 999;

		for (int i = 0; i < gp.obj.length; i++)
		{
			if (gp.obj[i] != null)
			{
				//Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//Get object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

				switch (entity.direction)
				{
					case "up":
						entity.solidArea.y -= entity.speed;
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						break;
				}
				if (entity.solidArea.intersects( gp.obj[i].solidArea )) // check if colliding with obj
				{
					if (gp.obj[i].collision)
					{
						entity.collisionOn = true;
					}
					if (player)
					{
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		return index;
	}

	public int checkEntity(Entity entity, Entity[] target)
	{
		int index = 999;

		for (int i = 0; i < target.length; i++)
		{
			if (target[i] != null)
			{
				//Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//Get target's solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

				switch (entity.direction)
				{
					case "up":
						entity.solidArea.y -= entity.speed;
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						break;
				}
				if (entity.solidArea.intersects( target[i].solidArea ))
				{
					if (target[i] != entity)
					{
						entity.collisionOn = true;
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
	}

	public boolean checkPlayer(Entity entity)
	{
		boolean contactPlayer = false;

		//Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		//Get target's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch (entity.direction)
		{
			case "up":
				entity.solidArea.y -= entity.speed;
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				break;
		}
		if (entity.solidArea.intersects( gp.player.solidArea ))
		{
			entity.collisionOn = true;
			contactPlayer = true;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;

		return contactPlayer;
	}
}