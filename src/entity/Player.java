package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Shield;
import object.OBJ_Sword;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity
{
	// VALUES
	public KeyHandler keyH;
	final int screenX;
	final int screenY;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;

	public int getScreenX() {return screenX;}

	public int getScreenY() {return screenY;}

	// Constructor
	public Player(GamePanel gp, KeyHandler keyH)
	{
		super( gp );
		this.keyH = keyH;

		// Screen position
		screenX = gp.screenWidth / 2 - (gp.getTileSize() / 2);
		screenY = gp.screenHeight / 2 - (gp.getTileSize() / 2);

		// Define collision area
		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 16;
		solidArea.width = 25;
		solidArea.height = 25;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Initialize player attributes and images
		setDefaultValues();
		// Player Image
		getPlayerImage();
		getPlayerAttackImage();
		//Player Default Inventory
		setItems();
	}

	private void getPlayerImage()
	{
		up1 = setup( "/player/up-left", gp.getTileSize(), gp.getTileSize() );
		up2 = setup( "/player/up-right", gp.getTileSize(), gp.getTileSize() );
		down1 = setup( "/player/down-left", gp.getTileSize(), gp.getTileSize() );
		down2 = setup( "/player/down-right", gp.getTileSize(), gp.getTileSize() );
		left1 = setup( "/player/left-left", gp.getTileSize(), gp.getTileSize() );
		left2 = setup( "/player/left-right", gp.getTileSize(), gp.getTileSize() );
		right1 = setup( "/player/right-left", gp.getTileSize(), gp.getTileSize() );
		right2 = setup( "/player/right-right", gp.getTileSize(), gp.getTileSize() );
	}

	private void getPlayerAttackImage()
	{
		attackUp1 = setup( "/player/attack/up_attack_charge", gp.getTileSize(), gp.getTileSize() * 2 );
		attackUp2 = setup( "/player/attack/up_attack", gp.getTileSize(), gp.getTileSize() * 2 );
		attackDown1 = setup( "/player/attack/down_attack_charge", gp.getTileSize(), gp.getTileSize() * 2 );
		attackDown2 = setup( "/player/attack/down_attack", gp.getTileSize(), gp.getTileSize() * 2 );
		attackLeft1 = setup( "/player/attack/left_attack_charge", gp.getTileSize() * 2, gp.getTileSize() );
		attackLeft2 = setup( "/player/attack/left_attack", gp.getTileSize() * 2, gp.getTileSize() );
		attackRight1 = setup( "/player/attack/right_attack_charge", gp.getTileSize() * 2, gp.getTileSize() );
		attackRight2 = setup( "/player/attack/right_attack", gp.getTileSize() * 2, gp.getTileSize() );
	}

	// Helper for constructor
	public void setDefaultValues()
	{
		// Initial world position
		worldX = gp.getTileSize() * 25;
		worldY = gp.getTileSize() * 20;
		// Player movement speed and starting direction
		speed = 5;
		maxSpeed = speed;
		direction = "down";
		// Player health
		maxLife = 6;
		life = maxLife;
		// Player mana
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		//Player status
		level = 1;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 6;
		coin = 0;
		currentShield = new OBJ_Shield( gp );
		currentWeapon = new OBJ_Sword( gp );
		projectile = new OBJ_Fireball( gp );
		//projectile = new OBJ_Rock( gp );
		attack = getAttack();
		defence = getDefense();
	}

	public void setItems()
	{
		inventory.clear();
		inventory.add( currentWeapon );
		inventory.add( currentShield );
	}

	public void setDefaultPositions()
	{
		worldX = gp.getTileSize() * 25;
		worldY = gp.getTileSize() * 20;
		direction = "down";
	}

	public void restoreLifeAndMana()
	{
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}

	// Update
	public void update()
	{
		// Attack action
		if (attacking)
		{
			attack();
			return; // Skip further actions during attack
		}

		// Movement or interaction based on key inputs
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.ePressed)
		{
			updateDirection();
			checkCollisionsAndInteract();

			// Update position if no collision and not interacting
			if (!collisionOn && !keyH.ePressed)
			{
				updatePosition();
			}

			if (keyH.ePressed && !attackCanceled)
			{
				attacking = true;
				spriteCounter = 0;
			}
			attackCanceled = false;

			// Reset interaction key
			keyH.ePressed = false;

			// Update sprite for animation
			updateSpriteAnimation();
		}

		//handle fireball
		handleFireball();

		// Handle invincibility timer
		handleInvincibility();

		if (life > maxLife)
		{
			life = maxLife;
		}
		if (mana > maxMana)
		{
			mana = maxMana;
		}

		if (life <= 0)
		{
			gp.playSE( 12 );
			//gp.setVolume( 0 );
			gp.stopMusic();
			gp.ui.isMusicPlaying = false;
			gp.gameState = gp.gameOverState;
		}
	}

	// Helper for update
	private void attack()
	{
		spriteCounter++;
		if (spriteCounter <= 5)
		{
			spriteNumber = 1; // Charging phase
		}
		else if (spriteCounter <= 25)
		{
			spriteNumber = 2; // Attack phase
			performAttack();
		}
		else
		{
			//reset attack
			spriteNumber = 1; // Reset to initial sprite
			spriteCounter = 0;
			attacking = false; // Attack sequence complete
		}
	}

	private void performAttack()
	{
		// Save current player position and solid area size
		int originalWorldX = worldX;
		int originalWorldY = worldY;
		int originalSolidAreaWidth = solidArea.width;
		int originalSolidAreaHeight = solidArea.height;

		// Adjust player position based on attack direction
		switch (direction)
		{
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
		}

		// Temporarily set solidArea to attackArea for collision checking
		solidArea.width = attackArea.width;
		solidArea.height = attackArea.height;

		// Check for monster collision during attack
		int monsterIndex = gp.CD.checkEntity( this, gp.monster );
		PlayerDamagingMonster( monsterIndex, attack );

		int iTileIndex = gp.CD.checkEntity( this, gp.iTile );
		damageInteractiveTile( iTileIndex );

		// Restore original player position and solid area size
		worldX = originalWorldX;
		worldY = originalWorldY;
		solidArea.width = originalSolidAreaWidth;
		solidArea.height = originalSolidAreaHeight;
	}

	private void updateDirection()
	{
		if (keyH.upPressed) direction = "up";
		if (keyH.downPressed) direction = "down";
		if (keyH.leftPressed) direction = "left";
		if (keyH.rightPressed) direction = "right";
	}

	//IN HERE
	private void checkCollisionsAndInteract()
	{
		collisionOn = false;
		gp.CD.checkTile( this ); //SOS !!!!!!!!!!!!!!!!!!!!!!!!!!
		int objIndex = gp.CD.checkObject( this, true );
		pickUpObject( objIndex );

		int npcIndex = gp.CD.checkEntity( this, gp.npc );
		interactNPC( npcIndex );

		int monsterIndex = gp.CD.checkEntity( this, gp.monster );
		MonsterDamagingPlayer( monsterIndex );

		int iTileIndex = gp.CD.checkEntity( this, gp.iTile );
		gp.eventH.checkEvent();
	}

	private void updatePosition()
	{
		switch (direction)
		{
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
		}
	}

	private void updateSpriteAnimation()
	{
		spriteCounter++;
		if (spriteCounter > 12)
		{
			spriteNumber = (spriteNumber == 1) ? 2 : 1;
			spriteCounter = 0;
		}
	}

	private void handleFireball()
	{
		if (gp.keyH.shotKeyPressed && !projectile.alive && shotAvailabilityCounter == 30 && projectile.haveResource( this )) //can shoot only one at the time
		{
			// set default coordinates, direction and user
			projectile.set( worldX, worldY, direction, true, this );
			//subtract cost of mana
			projectile.subtractResource( this );
			// add it to the list
			gp.projectileList.add( projectile );
			shotAvailabilityCounter = 0;
			gp.playSE( 6 );
		}
		if (shotAvailabilityCounter < 30)
		{
			shotAvailabilityCounter++;
		}
	}

	private void handleInvincibility()
	{
		if (invincible)
		{
			invincibilityCounter++;
			if (invincibilityCounter > 60)
			{
				invincibilityCounter = 0;
				invincible = false;
			}
		}
	}

	// DRAW
	public void draw(Graphics2D g2)
	{
		//g2.setColor( Color.white );
		//Player Character
		//g2.fillRect( x, y, gp.getTileSize(), gp.getTileSize() ); //Draw a rectangle and fill it with the specified color
		BufferedImage image = null;

		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction)
		{
			case "up":
				if (!attacking)
				{
					if (spriteNumber == 1) image = up1;
					if (spriteNumber == 2) image = up2;
				}
				if (attacking)
				{
					tempScreenY = screenY - gp.getTileSize();
					if (spriteNumber == 1) image = attackUp1;
					if (spriteNumber == 2) image = attackUp2;
				}
				break;
			case "down":
				if (!attacking)
				{
					if (spriteNumber == 1) image = down1;
					if (spriteNumber == 2) image = down2;
				}
				if (attacking)
				{
					tempScreenY = screenY - gp.getTileSize();
					if (spriteNumber == 1) image = attackDown1;
					if (spriteNumber == 2) image = attackDown2;
				}
				break;

			case "left":
				if (!attacking)
				{
					if (spriteNumber == 1) image = left1;
					if (spriteNumber == 2) image = left2;
				}
				if (attacking)
				{
					tempScreenX = screenX - gp.getTileSize();
					if (spriteNumber == 1) image = attackLeft1;
					if (spriteNumber == 2) image = attackLeft2;
				}
				break;

			case "right":
				if (!attacking)
				{
					if (spriteNumber == 1) image = right1;
					if (spriteNumber == 2) image = right2;
				}
				if (attacking)
				{
					tempScreenX = screenX - gp.getTileSize();
					if (spriteNumber == 1) image = attackRight1;
					if (spriteNumber == 2) image = attackRight2;
				}
				break;
		}

		if (invincible)
		{
			g2.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.4f ) ); //make player half transparent
		}
		g2.drawImage( image, tempScreenX, tempScreenY, null );

		//reset alpha after hitting player
		g2.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f ) );

		//debug line to see the collision area of player
		//		g2.setColor( Color.red );
		//		g2.drawRect( screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height );
		//

		// DEBUG
		// AttackArea
		//		tempScreenX = screenX + solidArea.x;
		//		tempScreenY = screenY + solidArea.y;
		//		switch (direction)
		//		{
		//			case "up":
		//				tempScreenY = screenY - attackArea.height;
		//				break;
		//			case "down":
		//				tempScreenY = screenY + gp.getTileSize();
		//				break;
		//			case "left":
		//				tempScreenX = screenX - attackArea.width;
		//				break;
		//			case "right":
		//				tempScreenX = screenX + gp.getTileSize();
		//				break;
		//		}
		//		g2.setColor( Color.red );
		//		g2.setStroke( new BasicStroke( 1 ) );
		//		g2.drawRect( tempScreenX, tempScreenY, attackArea.width, attackArea.height );
	}


	// General Helper
	private int getAttack()
	{
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}

	private int getDefense()
	{
		return defence = dexterity * currentShield.defenseValue;
	}

	private void interactNPC(int i)
	{
		if (!gp.keyH.ePressed) return;

		if (i != 999) // player touching NPC
		{
			attackCanceled = true;
			//initiate NPC dialogue
			gp.gameState = gp.dialogueState;
			gp.npc[i].speak();
			gp.playSE( 5 );
		}
	}

	public void PlayerDamagingMonster(int i, int attack)
	{
		if (i == 999) return; // Player not touching monster

		if (!gp.monster[i].invincible) //inflict Damage On Monster if not invincible
		{
			int damage = attack - gp.monster[i].defence;
			if (damage < 0)
			{
				damage = 0;
			}
			gp.monster[i].life -= damage;
			gp.monster[i].invincible = true;
			gp.monster[i].damageReaction();
			gp.ui.addMessage( gp.monster[i].name + " took " + damage + " damage!" );
			gp.playSE( 6 );

			if (gp.monster[i].life <= 0) //Monster is dead
			{
				gp.monster[i].dying = true;
				gp.ui.addMessage( gp.monster[i].name + " died!" );
				exp += gp.monster[i].exp;
				gp.ui.addMessage( "You got " + gp.monster[i].exp + " experience!" );
				checkLevelUp();
			}
		}
	}

	private void MonsterDamagingPlayer(int i)
	{
		if (i != 999) //player touching monster
		{
			if (!invincible && !gp.monster[i].dying)
			{
				int damage = gp.monster[i].attack - defence;
				if (damage < 0)
				{
					damage = 0;
				}
				life -= damage;
				invincible = true;
				gp.ui.addMessage( "You took " + damage + " damage!" );
				gp.playSE( 9 );
			}
		}
	}

	private void damageInteractiveTile(int iTileIndex)
	{
		if (iTileIndex != 999 && gp.iTile[iTileIndex].destructible && gp.iTile[iTileIndex].isCorrectItem( this ) && !gp.iTile[iTileIndex].invincible)
		{
			gp.iTile[iTileIndex].playSE();
			gp.iTile[iTileIndex].life--;
			gp.iTile[iTileIndex].invincible = true;
			if (gp.iTile[iTileIndex].life <= 0)
			{
				gp.iTile[iTileIndex] = gp.iTile[iTileIndex].getDestroyedForm();
			}
		}
	}

	private void pickUpObject(int i)
	{
		if (i == 999) return;

		// FINISH GAME
		if (Objects.equals( gp.obj[i].name, "Chest" ))
		{
			gp.playSE( 1 );
			finishGame();
			return;
		}
		// PICKUP ITEMS
		if (gp.obj[i].type == type_pickupOnly)
		{
			gp.obj[i].use( this );
			gp.obj[i] = null;
		}
		// OBSTACLES
		else if (gp.obj[i].type == type_obstacle)
		{
			if (keyH.ePressed)
			{
				attackCanceled = true;
				gp.obj[i].interact();
			}
		}
		// INVENTORY ITEMS
		else
		{
			String text;
			if (inventory.size() != maxInventorySize)
			{
				inventory.add( gp.obj[i] );
				gp.playSE( 3 );
				text = "You picked up a " + gp.obj[i].name + "!";
			}
			else
			{
				text = "Your inventory is full!";
			}
			gp.ui.addMessage( text );
			gp.obj[i] = null;
		}
	}

	private void finishGame() {gp.ui.gameFinished = true;}

	private void checkLevelUp()
	{
		if (exp >= nextLevelExp)
		{
			level++;
			if (nextLevelExp < 100) {nextLevelExp = nextLevelExp * 2;}
			else {nextLevelExp += 10;}
			exp = 0;
			if (maxLife < 12)
			{
				maxLife += 2;
			}
			if (maxMana < 6)
			{
				maxMana += 1;
			}
			strength++;
			dexterity++;
			attack = getAttack();
			//defence = getDefense();
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "Congratulations!\nYou advanced to level " + level + "!";
			gp.playSE( 1 );
		}
	}

	public void selectItem()
	{
		int itemIndex = gp.ui.getItemIndexOnSlot();
		if (itemIndex < gp.player.inventory.size())
		{
			Entity selectedItem = inventory.get( itemIndex );
			if (selectedItem.type == type_sword || selectedItem.type == type_axe)
			{
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			if (selectedItem.type == type_shield)
			{
				currentShield = selectedItem;
				defence = getDefense();
			}
			if (selectedItem.type == type_consumable)
			{
				if (selectedItem.use( this )) {inventory.remove( itemIndex );}
			}
		}
	}
}

