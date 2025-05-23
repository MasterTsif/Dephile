package main;

import entity.NPC_Ice;
import entity.NPC_Sand;
import entity.NPC_Green;
import entity.NPC_Skeleton;
import monster.Blue_Slime;
import monster.Rock_Slime;
import object.*;
import tile_interactive.IT_Crystal;
import tile_interactive.IT_DryTree;

public class AssetSetter
{
	GamePanel gp;

	public AssetSetter(GamePanel gp)
	{
		this.gp = gp;
	}

	public void setObject()
	{
		int io = 0;
		gp.obj[io] = new OBJ_Key( gp );             //key green
		gp.obj[io].worldX = gp.tileSize * 14;
		gp.obj[io].worldY = gp.tileSize * 24;
		io++;
		io++;
		gp.obj[io] = new OBJ_Axe( gp );             //axe
		gp.obj[io].worldX = gp.tileSize * 33;
		gp.obj[io].worldY = gp.tileSize * 12;
		io++;
		gp.obj[io] = new OBJ_Shield_Blue( gp );     //blue shield
		gp.obj[io].worldX = gp.tileSize * 16;
		gp.obj[io].worldY = gp.tileSize * 12;
		io++;
		gp.obj[io] = new OBJ_PotionBlue( gp );      //blue potion
		gp.obj[io].worldX = gp.tileSize * 16;
		gp.obj[io].worldY = gp.tileSize * 37;
		io++;
		gp.obj[io] = new OBJ_PotionRed( gp );       //red potion
		gp.obj[io].worldX = gp.tileSize * 33;
		gp.obj[io].worldY = gp.tileSize * 37;
		io++;
		gp.obj[io] = new OBJ_Door( gp );            //door to sand
		gp.obj[io].worldX = gp.tileSize * 39;
		gp.obj[io].worldY = gp.tileSize * 22;
		io++;
		gp.obj[io] = new OBJ_Key( gp );             //key sand
		gp.obj[io].worldX = gp.tileSize * 43;
		gp.obj[io].worldY = gp.tileSize * 37;
		io++;
		gp.obj[io] = new OBJ_Door( gp );            //door to ice
		gp.obj[io].worldX = gp.tileSize * 46;
		gp.obj[io].worldY = gp.tileSize * 9;
		io++;
		gp.obj[io] = new OBJ_Key( gp );             //key ice
		gp.obj[io].worldX = gp.tileSize * 2;
		gp.obj[io].worldY = gp.tileSize * 5;
		io++;
		gp.obj[io] = new OBJ_Door( gp );        //door to skeletons
		gp.obj[io].worldX = gp.tileSize * 3;
		gp.obj[io].worldY = gp.tileSize * 9;
		io++;
		gp.obj[io] = new OBJ_Key( gp );             //final key
		gp.obj[io].worldX = gp.tileSize * 6;
		gp.obj[io].worldY = gp.tileSize * 14;
		io++;
		gp.obj[io] = new OBJ_Door( gp );        //door to finish
		gp.obj[io].worldX = gp.tileSize * 5;
		gp.obj[io].worldY = gp.tileSize * 34;
		io++;
		gp.obj[io] = new OBJ_Chest( gp );       //finish chest
		gp.obj[io].worldX = gp.tileSize * 5;
		gp.obj[io].worldY = gp.tileSize * 39;
	}

	public void setNPC()
	{
		int in = 0;
		gp.npc[in] = new NPC_Green( gp );
		gp.npc[in].worldX = gp.tileSize * 23;
		gp.npc[in].worldY = gp.tileSize * 20;
		in++;
		gp.npc[in] = new NPC_Sand( gp );
		gp.npc[in].worldX = gp.tileSize * 45;
		gp.npc[in].worldY = gp.tileSize * 23;
		in++;
		gp.npc[in] = new NPC_Ice( gp );
		gp.npc[in].worldX = gp.tileSize * 47;
		gp.npc[in].worldY = gp.tileSize * 2;
		in++;
		gp.npc[in] = new NPC_Skeleton( gp );
		gp.npc[in].worldX = gp.tileSize * 9;
		gp.npc[in].worldY = gp.tileSize * 10;
	}

	public void setMonster()
	{
		int im = 0;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 18;
		gp.monster[im].worldY = gp.tileSize * 12;
		im++;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 31;
		gp.monster[im].worldY = gp.tileSize * 12;
		im++;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 31;
		gp.monster[im].worldY = gp.tileSize * 37;
		im++;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 18;
		gp.monster[im].worldY = gp.tileSize * 37;
		im++;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 40;
		gp.monster[im].worldY = gp.tileSize * 16;
		im++;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 46;
		gp.monster[im].worldY = gp.tileSize * 13;
		im++;
		gp.monster[im] = new Blue_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 41;
		gp.monster[im].worldY = gp.tileSize * 11;
		im++;
		gp.monster[im] = new Rock_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 40;
		gp.monster[im].worldY = gp.tileSize * 2;
		im++;
		gp.monster[im] = new Rock_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 23;
		gp.monster[im].worldY = gp.tileSize * 4;
		im++;
		gp.monster[im] = new Rock_Slime( gp );
		gp.monster[im].worldX = gp.tileSize * 9;
		gp.monster[im].worldY = gp.tileSize * 5;
	}

	public void setInteractiveTile()
	{
		int it = 0;
		gp.iTile[it] = new IT_DryTree( gp, 38, 20 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 37, 20 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 36, 20 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 35, 20 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 35, 21 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 35, 22 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 35, 23 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 35, 24 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 36, 24 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 37, 24 );
		it++;
		gp.iTile[it] = new IT_DryTree( gp, 38, 24 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 2, 3 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 3, 3 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 4, 3 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 4, 4 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 4, 5 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 4, 6 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 4, 7 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 3, 7 );
		it++;
		gp.iTile[it] = new IT_Crystal( gp, 2, 7 );
	}
}
