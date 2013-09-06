//Tutorial: http://zetcode.com/tutorials/javagamestutorial/basics/

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener //The Board is a panel, where the game takes place.
{
    private Timer timer;

    private ArrayList<Player> playerList = new ArrayList<Player>();
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<Block> blockList = new ArrayList<Block>();
    private ArrayList<Wall> wallList = new ArrayList<Wall>();
	 private ArrayList<Point2D> visitedLocations = new ArrayList<Point2D>();

    private int currentPlayer;
    private int currentEnemy;
    private int currentBlock;
    private int currentWall;

    private int BLOCK_WIDTH = 15;
    private int BLOCK_HEIGHT = 15;
    private int BOARD_WIDTH = BLOCK_WIDTH * 30;
    private int BOARD_HEIGHT = BLOCK_HEIGHT * 40;
    private int offsetBoard_Bottom = BOARD_HEIGHT - (2 * BLOCK_HEIGHT);

    private int numOfPlayers = 3;
    private int numOfEnemies = 5;
    private int numOfBlocks = (int)(0.55 * ((BOARD_WIDTH / BLOCK_WIDTH) * (BOARD_HEIGHT / BLOCK_HEIGHT))); //length x height x percent  (Ideal % = 0.55)
    private int numOfWalls = (int)(0.01 * ((BOARD_WIDTH / BLOCK_WIDTH) * (BOARD_HEIGHT / BLOCK_HEIGHT)));
    private int difficulty = 1;
    private boolean isPlaying;
    private int timerSpeed = 155; //milliseconds refresh rate     ideal = 55

    private String gameTitle = "BEAST";

	 public int getBoardSizeX()
	 {
       return this.BOARD_WIDTH;
	 }

	 public int getBoardSizeY()
	 {
       return this.BOARD_HEIGHT;
	 }

	 public int getBlockSizeX()
	 {
       return this.BLOCK_WIDTH;
	 }

	 public int getBlockSizeY()
	 {
       return this.BLOCK_HEIGHT;
	 }

	 public String getTitle()
	 {
       return this.gameTitle;
	 }

	 //Initialize the gameboard
	 public GameBoard()
	 {
       addKeyListener(new TAdapter());
       setFocusable(true);
       setBackground(Color.BLACK);
       setDoubleBuffered(true);

		 //---Create the elements on the board---//
		 // Put walls along the boundary
       for (int i = 0; i < BOARD_WIDTH; i += BLOCK_WIDTH){   //Top Row
		    wallList.add(new Wall(i, 0, BLOCK_WIDTH, BLOCK_HEIGHT));
	    }
		 for (int i = 0; i < BOARD_WIDTH; i += BLOCK_WIDTH){   //Bottom Row
		    wallList.add(new Wall(i, offsetBoard_Bottom, BLOCK_WIDTH, BLOCK_HEIGHT));
		 }
		 for (int i = BLOCK_HEIGHT; i < offsetBoard_Bottom; i += BLOCK_HEIGHT){   //Sides
		    wallList.add(new Wall(0, i, BLOCK_WIDTH, BLOCK_HEIGHT));
          wallList.add(new Wall(BOARD_WIDTH - BLOCK_WIDTH, i, BLOCK_WIDTH, BLOCK_HEIGHT));
		 }

		 //Create everything within the boundary
       for (int i = 0; i < numOfPlayers; i++){
		    playerList.add(new Player(randomLocation(BLOCK_WIDTH, BOARD_WIDTH - BLOCK_WIDTH), randomLocation(BLOCK_HEIGHT, offsetBoard_Bottom), BLOCK_WIDTH, BLOCK_HEIGHT));
		 }
		 for (int i = 0; i < numOfEnemies; i++){
		    enemyList.add(new Enemy(randomLocation(BLOCK_WIDTH, BOARD_WIDTH - BLOCK_WIDTH), randomLocation(BLOCK_HEIGHT, offsetBoard_Bottom), BLOCK_WIDTH, BLOCK_HEIGHT));
		 }
		 for (int i = 0; i < numOfBlocks; i++){
		    blockList.add(new Block(randomLocation(BLOCK_WIDTH, BOARD_WIDTH - BLOCK_WIDTH), randomLocation(BLOCK_HEIGHT, offsetBoard_Bottom), BLOCK_WIDTH, BLOCK_HEIGHT));
		 }
		 for (int i = 0; i < numOfWalls; i++){
		    wallList.add(new Wall(randomLocation(BLOCK_WIDTH, BOARD_WIDTH - BLOCK_WIDTH), randomLocation(BLOCK_HEIGHT, offsetBoard_Bottom), BLOCK_WIDTH, BLOCK_HEIGHT));
		 }

		 // Remove overlaps
		 removeOverlap();

		 timer = new Timer(timerSpeed, this);
   	 timer.start();
	 }

   public void removeOverlap()
	{
	   //Remove all blocks overlapped by walls
		for (int i = 0; i < wallList.size(); i++){
		   for (int j = 0; j < blockList.size(); j++){
			   if ((wallList.get(i).getX() == blockList.get(j).getX()) && (wallList.get(i).getY() == blockList.get(j).getY())){
		         blockList.remove(j);
				}
			}
		}

		//Remove all blocks overlapped by enemies
		for (int i = 0; i < enemyList.size(); i++){
		   for (int j = 0; j < blockList.size(); j++){
			   if ((enemyList.get(i).getX() == blockList.get(j).getX()) && (enemyList.get(i).getY() == blockList.get(j).getY())){
		         blockList.remove(j);
				}
			}
		}

		//Remove all blocks overlapped by players
		for (int i = 0; i < playerList.size(); i++){
		   for (int j = 0; j < blockList.size(); j++){
			   if ((playerList.get(i).getX() == blockList.get(j).getX()) && (playerList.get(i).getY() == blockList.get(j).getY())){
		         blockList.remove(j);
				}
			}
		}
		
		//Remove all blocks overlapped by other blocks
		for (int i = 0; i < blockList.size(); i++){
		   for (int j = i + 1; j < blockList.size(); j++){
			   if ((blockList.get(i).getX() == blockList.get(j).getX()) && (blockList.get(i).getY() == blockList.get(j).getY())){
		         blockList.remove(j);
				}
			}
		}
   }

	 //Draw the game
	 public void paint(Graphics g)
	 {
      super.paint(g);

        //Draw the individual elements
	    for (int i = 0; i < playerList.size(); i++){
		    g.drawImage(playerList.get(i).getImage(), playerList.get(i).getX(), playerList.get(i).getY(), this);
	    }
	    for (int i = 0; i < enemyList.size(); i++){
		    g.drawImage(enemyList.get(i).getImage(), enemyList.get(i).getX(), enemyList.get(i).getY(), this);
	    }
	    for (int i = 0; i < blockList.size(); i++){
		    g.drawImage(blockList.get(i).getImage(), blockList.get(i).getX(), blockList.get(i).getY(), this);
	    }
	    for (int i = 0; i < wallList.size(); i++){
		    g.drawImage(wallList.get(i).getImage(), wallList.get(i).getX(), wallList.get(i).getY(), this);
	    }

		  Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    //Do this every time an action has performed
    public void actionPerformed(ActionEvent e) {   //http://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
        //Move players
		  for (int i = 0; i < playerList.size(); i++){
		     playerList.get(i).move();
	    }

      checkCollisions();

		 //Move Enemies
		 for (int i = 0; i < enemyList.size(); i++){
		    enemyList.get(i).move(playerList, false);
	    }

        checkCollisions();
        repaint();
    }

   public void checkCollisions()
	{
		//Check for player/enemy overlap
		for (int i = 0; i < playerList.size(); i++){
		   for (int j = 0; j < enemyList.size(); j++){
			   if ((playerList.get(i).getX() == enemyList.get(j).getX()) && (playerList.get(i).getY() == enemyList.get(j).getY())){
		         if (playerList.size() > 1){
                  playerList.remove(i);
					}
					else{
		            playerList.add(new Player(randomLocation(BLOCK_WIDTH, BOARD_WIDTH - BLOCK_WIDTH), randomLocation(BLOCK_HEIGHT, offsetBoard_Bottom), BLOCK_WIDTH, BLOCK_HEIGHT));
                  playerList.remove(0);
                  removeOverlap();
						//playerList.clear();
					}
				}
			}
		}
      //Check for player/wall overlap
		for (int i = 0; i < playerList.size(); i++){
		   for (int j = 0; j < wallList.size(); j++){
			   if ((playerList.get(i).getX() == wallList.get(j).getX()) && (playerList.get(i).getY() == wallList.get(j).getY())){
               playerList.get(i).setDirectionX(playerList.get(i).getDirectionX() * -1);
               playerList.get(i).setDirectionY(playerList.get(i).getDirectionY() * -1);
					playerList.get(i).move();
					playerList.get(i).setDirectionX(0);
               playerList.get(i).setDirectionY(0);
				}
			}
		}
		//Check for player/player overlap
      for (int i = 0; i < playerList.size(); i++){
		   for (int j = 1; j < playerList.size(); j++){
			   if ((playerList.get(i).getX() == playerList.get(j).getX()) && (playerList.get(i).getY() == playerList.get(j).getY())){
               playerList.get(i).setDirectionX(playerList.get(i).getDirectionX() * -1);
               playerList.get(i).setDirectionY(playerList.get(i).getDirectionY() * -1);
					playerList.get(i).move();
					playerList.get(i).setDirectionX(0);
               playerList.get(i).setDirectionY(0);
				}
			}
		}
		//Check for player/block overlap
		for (int i = 0; i < playerList.size(); i++){
		   for (int j = 0; j < blockList.size(); j++){
			   if ((playerList.get(i).getX() == blockList.get(j).getX()) && (playerList.get(i).getY() == blockList.get(j).getY())){
					//If the blocks cannot be pushed
					if (!canPush(playerList.get(i).getX(),playerList.get(i).getY())){
						//System.out.println("cannot push");
	               playerList.get(i).setDirectionX(playerList.get(i).getDirectionX() * -1);
	               playerList.get(i).setDirectionY(playerList.get(i).getDirectionY() * -1);
						playerList.get(i).move();
						playerList.get(i).setDirectionX(0);
	               playerList.get(i).setDirectionY(0);
					}
					else{
                  blockList.get(j).move(playerList.get(i).getDirectionX(), playerList.get(i).getDirectionY());
					}
				}
			}
		}
	//Check for block/block overlap
		for (int i = 0; i < blockList.size(); i++){
		   for (int j = i + 1; j < blockList.size(); j++){
			   if ((blockList.get(i).getX() == blockList.get(j).getX()) && (blockList.get(i).getY() == blockList.get(j).getY())){
		         blockList.get(j).move(playerList.get(0).getDirectionX(), playerList.get(0).getDirectionY());
		         //recheck collisions in case a block was missed
					checkCollisions();
				}
			}
		}
		
      //Check for enemy/wall overlap
		for (int i = 0; i < enemyList.size(); i++){
		   for (int j = 0; j < wallList.size(); j++){
			   if ((enemyList.get(i).getX() == wallList.get(j).getX()) && (enemyList.get(i).getY() == wallList.get(j).getY())){
               enemyList.get(i).setDirectionX(enemyList.get(i).getDirectionX() * -1);
               enemyList.get(i).setDirectionY(enemyList.get(i).getDirectionY() * -1);
					enemyList.get(i).move(playerList, true);
 					enemyList.get(i).setDirectionX(0);
               enemyList.get(i).setDirectionY(0);
				}
			}
		}
      //Check for enemy/block overlap
		for (int i = 0; i < enemyList.size(); i++){
		   for (int j = 0; j < blockList.size(); j++){
			   if ((enemyList.get(i).getX() == blockList.get(j).getX()) && (enemyList.get(i).getY() == blockList.get(j).getY())){
               enemyList.get(i).setDirectionX(enemyList.get(i).getDirectionX() * -1);
               enemyList.get(i).setDirectionY(enemyList.get(i).getDirectionY() * -1);
					enemyList.get(i).move(playerList, true);
					enemyList.get(i).setDirectionX(0);
               enemyList.get(i).setDirectionY(0);
//               findBestMove(enemyList.get(i));
				}
			}
		}
      //Check for enemy/enemy overlap
		for (int i = 0; i < enemyList.size(); i++){
		   for (int j = i + 1; j < enemyList.size(); j++){
			   if ((enemyList.get(i).getX() == enemyList.get(j).getX()) && (enemyList.get(i).getY() == enemyList.get(j).getY())){
               enemyList.get(i).setDirectionX(enemyList.get(i).getDirectionX() * -1);
               enemyList.get(i).setDirectionY(enemyList.get(i).getDirectionY() * -1);
					enemyList.get(i).move(playerList, true);
 					enemyList.get(i).setDirectionX(0);
               enemyList.get(i).setDirectionY(0);
				}
			}
		}
	}

	public boolean canPush(int loc_X, int loc_Y)
	{
	   int objectType_Next = checkLocation(loc_X, loc_Y);
		int objectType_Following = checkLocation(loc_X + (playerList.get(0).getDirectionX() * BLOCK_WIDTH), loc_Y + (playerList.get(0).getDirectionY() * BLOCK_HEIGHT));

		if (objectType_Next == 0){ //Blank space
	      // System.out.println("Pushing block");
	      return true;
		}
		if (objectType_Next == 1){ //Block
	      return canPush(loc_X + playerList.get(0).getDirectionX() * BLOCK_WIDTH, loc_Y + playerList.get(0).getDirectionY() * BLOCK_HEIGHT);
		}
		if ((objectType_Next == 2 || objectType_Next == 3) && (objectType_Following == 0)){  //Enemy or player followed by a blank space
	      // System.out.println("Blocked by enemy or player");
			return false;
		}
		else if ((objectType_Next == 2 || objectType_Next == 3) && (objectType_Following != 0)){  //Enemy or player followed by a non-blank space
	      // System.out.println("Crushing enemy or player");
			if (objectType_Next == 2){ //Remove the Enemy
		      // System.out.println("Crushed enemy number " + currentEnemy);
			   enemyList.remove(currentEnemy);
         }else{ //Remove the Player
		      // System.out.println("Crushed player number " + currentPlayer);
			   playerList.remove(currentPlayer);
         }
			return true;
		}
		if (objectType_Next == 4){ //Wall
	      // System.out.println("Blocked by wall");
	      return false;
		}
		return false;
	}

	//returned int corresponds to: (0 = blank space, 1 = block, 2 = enemy, 3 = player, 4 = wall)
	public int checkLocation(int loc_X, int loc_Y)
	{
       //check if a block is there
       for (int i = 0; i < blockList.size() - 1; i++){
		    //If the block exists in the space
			 if ((blockList.get(i).getX() == loc_X) && (blockList.get(i).getY() == loc_Y)){
					 currentBlock = i;
					 return 1; //objectType = 1 for block
			 }
		  }
       //check if an enemy is there
       for (int i = 0; i < enemyList.size(); i++){
		    if ((enemyList.get(i).getX() == loc_X) && (enemyList.get(i).getY() == loc_Y)){
                currentEnemy = i;
				 return 2; //objectType = 2 for enemy
			 }
       }
       //check if a player is there
       for (int i = 0; i < playerList.size(); i++){
		    if ((playerList.get(i).getX() == loc_X) && (playerList.get(i).getY() == loc_Y)){
                currentPlayer = i;
				 return 3; //objectType = 3 for player
          }
        }
       //check if a wall is there
       for (int i = 0; i < wallList.size(); i++){
		    if ((wallList.get(i).getX() == loc_X) && (wallList.get(i).getY() == loc_Y)){
                currentWall = i;
				 return 4; //objectType = 4 for wall
		    }
		 }
       currentBlock = -1;
       currentEnemy = -1;
       currentPlayer = -1;
       currentWall = -1;
       return 0; //objectType = 0 for blank
	}

	public boolean moveRandomly(Enemy myEnemy)
	{
      int min = 1;
      int max = 3;
		int moveDirectionX = 3 - (min + (int)(Math.random() * ((max - min) + 1)));
		int moveDirectionY = 3 - (min + (int)(Math.random() * ((max - min) + 1)));

		myEnemy.setDirectionX(moveDirectionX);
		myEnemy.setDirectionX(moveDirectionY);
		myEnemy.move(playerList, true);

		return true;
	}

   public void findBestMove(Enemy myEnemy)
	{
      //int locationsChecked = 5;
      //Look for blank spaces
		//If moving up
		if (myEnemy.getDirectionX() == 0 && myEnemy.getDirectionY() == -1){
         //check upper-right
			if (checkLocation(myEnemy.getX() + BLOCK_WIDTH, myEnemy.getY() - BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionX(1);
			   myEnemy.move(playerList, false);
			}
         //check upper-left
			else if (checkLocation(myEnemy.getX() - BLOCK_WIDTH, myEnemy.getY() - BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionX(-1);
			   myEnemy.move(playerList, false);
			}
		}
      //If moving down
		else if (myEnemy.getDirectionX() == 0 && myEnemy.getDirectionY() == 1){
         //check lower-right
			if (checkLocation(myEnemy.getX() + BLOCK_WIDTH, myEnemy.getY() + BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionX(1);
			   myEnemy.move(playerList, false);
			}
         //check lower-left
			if (checkLocation(myEnemy.getX() - BLOCK_WIDTH, myEnemy.getY() + BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionX(-1);
			   myEnemy.move(playerList, false);
			}
		}
      //If moving left
		else if (myEnemy.getDirectionX() == -1 && myEnemy.getDirectionY() == 0){
         //check upper-left
			if (checkLocation(myEnemy.getX() - BLOCK_WIDTH, myEnemy.getY() - BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionY(-1);
			   myEnemy.move(playerList, false);
			}
         //check lower-left
			if (checkLocation(myEnemy.getX() - BLOCK_WIDTH, myEnemy.getY() + BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionY(1);
			   myEnemy.move(playerList, false);
			}
		}
      //If moving right
		else if (myEnemy.getDirectionX() == 1 && myEnemy.getDirectionY() == 0){
         //check upper-right
			if (checkLocation(myEnemy.getX() + BLOCK_WIDTH, myEnemy.getY() - BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionY(-1);
			   myEnemy.move(playerList, false);
			}
         //check lower-right
			if (checkLocation(myEnemy.getX() + BLOCK_WIDTH, myEnemy.getY() + BLOCK_HEIGHT) == 0){
			   myEnemy.setDirectionY(1);
			   myEnemy.move(playerList, false);
			}
		}
   }

	public int randomLocation(int min, int max) //http://stackoverflow.com/questions/363681/generating-random-number-in-a-range-with-java
	{
      int locationExact = (min + (int)(Math.random() * ((max - min) + 1)));
		int locationGrid = locationExact - (locationExact % BLOCK_WIDTH);
		return locationGrid;
	}

    //Do this every time a key is pressed
    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            playerList.get(0).keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            playerList.get(0).keyReleased(e);
        }
    }
}