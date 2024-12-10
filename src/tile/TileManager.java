package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager
{
	GamePanel gp;
	public Tile[] tile;
	public int[][] mapTileNum;

	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		tile = new Tile[11];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap( "/maps/DefaultWorld.txt" );
	}

	public void getTileImage()
	{
		setup( 0, "water", true );
		setup( 1, "grass", false );
		setup( 2, "wall", true );
		setup( 3, "sand", false );
		setup( 4, "tree", true );
		setup( 5, "earth", false );
		setup( 6, "snow", false );
		setup( 7, "skeleton", false );
		setup( 8, "bridge", false );
		setup( 9, "cactus", true );
		setup( 10, "crystal_plain", true );
	}

	public void setup(int index, String imagePath, boolean collision)
	{
		UtilityTool uTool = new UtilityTool();
		try
		{
			tile[index] = new Tile();
			tile[index].image = ImageIO.read( Objects.requireNonNull( getClass().getResourceAsStream( "/tiles/" + imagePath + ".png" ) ) );
			tile[index].image = uTool.scaleImage( tile[index].image, gp.getTileSize(), gp.getTileSize() );
			tile[index].collision = collision;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath)
	{
		try
		{
			InputStream is = getClass().getResourceAsStream( filePath );
			BufferedReader br = new BufferedReader( new InputStreamReader( is ) );

			int col = 0;
			int row = 0;
			while (col < gp.maxWorldCol && row < gp.maxWorldRow)
			{
				String line = br.readLine();
				while (col < gp.maxWorldCol)
				{
					String[] numbers = line.split( " " );
					int num = Integer.parseInt( numbers[col] );
					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol)
				{
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2)
	{
		int WorldCol = 0;
		int WorldRow = 0;

		while (WorldCol < gp.maxWorldCol && WorldRow < gp.maxWorldRow)
		{
			int tileNum = mapTileNum[WorldCol][WorldRow];

			int WorldX = WorldCol * gp.getTileSize();
			int WorldY = WorldRow * gp.getTileSize();

			int screenX = WorldX - gp.player.worldX + gp.player.getScreenX();
			int screenY = WorldY - gp.player.worldY + gp.player.getScreenY();

			//Optimization - render only tiles that are in the screen
			boolean isVisibleX = WorldX + gp.getTileSize() > gp.player.worldX - gp.player.getScreenX() && WorldX - gp.getTileSize() < gp.player.worldX + gp.player.getScreenX();
			boolean isVisibleY = WorldY + gp.getTileSize() > gp.player.worldY - gp.player.getScreenY() && WorldY - gp.getTileSize() < gp.player.worldY + gp.player.getScreenY();

			if (isVisibleX && isVisibleY)
			{
				g2.drawImage( tile[tileNum].image, screenX, screenY, null );
			}


			WorldCol++;

			if (WorldCol == gp.maxWorldCol)
			{
				WorldCol = 0;
				WorldRow++;

			}
		}
	}

	public int getTileNumber(int worldX, int worldY)
	{
		// Convert world coordinates to tile coordinates
		int col = worldX / gp.getTileSize();
		int row = worldY / gp.getTileSize();

		// Check for out-of-bounds coordinates
		if (col < 0 || col >= gp.maxWorldCol || row < 0 || row >= gp.maxWorldRow)
		{
			return -1; // Return -1 or a suitable default value for invalid coordinates
		}

		// Return the tile number from the map
		return mapTileNum[col][row];
	}
}
