import java.awt.Image;
//import java.net.URL;
import javax.swing.ImageIcon;

public class Block extends Actor    //http://zetcode.com/tutorials/javagamestutorial/movingsprites/
{  
    public Block(int x, int y, int w, int h) {
        super(x, y, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("blockImage.png")); //as opposed to '= new ImageIcon(loc);'
        Image image = ii.getImage();
        this.setImage(image);
    }

    public void move(int directionX, int directionY) {
        this.setX(this.getX() + (directionX * this.getWidth()));
        this.setY(this.getY() + (directionY * this.getHeight()));
    }
}