import java.awt.Image;
//import java.net.URL;
import javax.swing.ImageIcon;

public class Wall extends Actor    //http://zetcode.com/tutorials/javagamestutorial/movingsprites/
{  
    public Wall(int x, int y, int w, int h) {
        super(x, y, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("wallImage.png")); //as opposed to '= new ImageIcon(loc);'
        Image image = ii.getImage();
        this.setImage(image);
    }
}