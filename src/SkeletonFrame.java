import javax.swing.JFrame;

public class SkeletonFrame extends JFrame {

    public SkeletonFrame() {
        GameBoard myGame = new GameBoard();
		  add(myGame); //put the gameboard JPanel at the centre of the JFrame component
        setTitle(myGame.getTitle());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(myGame.getBoardSizeX() + myGame.getBlockSizeX()/2 - 1, myGame.getBoardSizeY() + myGame.getBlockSizeY() - 2);
        setLocationRelativeTo(null); //null centres the window
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) {
        new SkeletonFrame();
    }
}