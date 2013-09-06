import java.awt.Image;
//import java.net.URL;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Player extends Actor  //http://zetcode.com/tutorials/javagamestutorial/movingsprites/
{  
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

	 public Player(int x, int y, int w, int h) 
	 {
        super(x, y, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("playerImage.png")); //as opposed to '= new ImageIcon(loc);'
        Image image = ii.getImage();
        this.setImage(image);
    }

    public void move() 
	 {
        this.setX(getX() + (directionX * getWidth()));
        this.setY(getY() + (directionY * getHeight()));
    }
    
    public void die()
	 {
       //Play sound
       //Reduce life count
	 }

   public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            directionX = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            directionX = 1;
        }

        if (key == KeyEvent.VK_UP) {
            directionY = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            directionY = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            directionX = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            directionX = 0;
        }

        if (key == KeyEvent.VK_UP) {
            directionY = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            directionY = 0;
        }
    }
}