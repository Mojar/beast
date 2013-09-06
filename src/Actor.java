import java.awt.Image;

public class Actor  // http://zetcode.com/tutorials/javagamestutorial/sokoban/
{
    private int positionX;
    private int positionY;
    private int width;
    private int height;

    private Image image;

    public Actor(int x, int y, int w, int h)
	 {
        this.positionX = x;
        this.positionY = y;
        this.width = w;
        this.height = h;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        this.image = img;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.positionX = x;
    }
    
    public void setY(int y) {
        this.positionY = y;
    }

    public int getWidth(int w) {
        return this.width = w;
    }
    
    public int getHeight(int h) {
        return this.height = h;
    }
}