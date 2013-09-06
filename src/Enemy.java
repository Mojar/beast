import java.awt.Image;
//import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Enemy extends Actor    //http://zetcode.com/tutorials/javagamestutorial/movingsprites/
{
    private int minMoveTimer = 50;
    private int maxMoveTimer = 100;
    private int coolDownTimer = randomWithinRange(minMoveTimer, maxMoveTimer);
    private int coolDownRate = 30; //milliseconds * the gameplay rate

	 private int directionX = 0;
    private int directionY = 0;

    public int getDirectionX()
	 {
       return this.directionX;
	 }
	 
	 public int getDirectionY()
	 {
       return this.directionY;
	 }

	 public void setDirectionX(int dX)
	 {
       this.directionX = dX;
	 }
	 
	 public void setDirectionY(int dY)
	 {
       this.directionY = dY;
	 }
	 
	 public Player searchClosestPlayer(ArrayList<Player> allPlayers)
	 {
       Player closestPlayer = allPlayers.get(0);

		 int distanceX = closestPlayer.getX() - this.getX();
       int distanceY = closestPlayer.getY() - this.getY();
       int shortestDistance = (int)Math.sqrt((distanceX*distanceX) + (distanceY*distanceY));

       int temp_distanceX;
		 int temp_distanceY;
		 int temp_shortestDistance;

		 for (int i = 1; i < allPlayers.size(); i++){
	       temp_distanceX = allPlayers.get(i).getX() - this.getX();
	       temp_distanceY = allPlayers.get(i).getY() - this.getY();
	       temp_shortestDistance = (int)Math.sqrt((distanceX*distanceX) + (distanceY*distanceY));
	       if (temp_shortestDistance < shortestDistance){
			    closestPlayer = allPlayers.get(i);
			 }
		 }
		 return closestPlayer;
	 }

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("enemyImage.png")); //as opposed to '= new ImageIcon(loc);'
        Image image = ii.getImage();
        this.setImage(image);
    }

    public void move(ArrayList<Player> allPlayers, boolean didCollide)
	 {
		  Player closestPlayer = searchClosestPlayer(allPlayers);

		  if ((coolDownTimer <= 0) && (!didCollide)){
		       if (closestPlayer.getX() > this.getX()){
				    this.directionX = 1;
				 }
				 else if (closestPlayer.getX() < this.getX()){
		          this.directionX = -1;
				 }
				 else{
		          this.directionX = 0;
				 }
		       if (closestPlayer.getY() > this.getY()){
				    this.directionY = 1;
				 }
				 else if (closestPlayer.getY() < this.getY()){
		          this.directionY = -1;
				 }
				 else{
		          this.directionY = 0;
				 }

		     this.setX(getX() + (directionX * getWidth()));
           this.setY(getY() + (directionY * getHeight()));
           coolDownTimer = randomWithinRange(minMoveTimer, maxMoveTimer);
		  }
        else if(didCollide){
   	     this.setX(getX() + (directionX * getWidth()));
           this.setY(getY() + (directionY * getHeight()));;
		  }
		  else{
		     coolDownTimer -= coolDownRate;
		  }
    }

    public int randomWithinRange(int min, int max) //http://stackoverflow.com/questions/363681/generating-random-number-in-a-range-with-java
	{
      return (min + (int)(Math.random() * ((max - min) + 1)));
	}
}