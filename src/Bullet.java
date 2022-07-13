import bagel.Image;
import bagel.util.Point;
import java.io.*;

/**
 * class of bullet
 */
public class Bullet{

    // image source file
    public static final String FILENAME = "res/images/shot.png";
    // speed
    public static final double STEP_SIZE = 25;

    // image
    private final Image image;
    // render position
    private Point pos;
    // direction
    private double directionX;
    private double directionY;

    // string for storing positions of bullet
    private StringBuilder sb = new StringBuilder();

    /**
     * constructor for Bullet
     * @param x position of x
     * @param y position of y
     */
    public Bullet(double x, double y) {
        this.image = new Image(FILENAME);
        this.pos = new Point(x,y);
    }

    /**
     * getter for position of bullet
     * @return Point pos
     */
    public Point getPos(){
        return this.pos;
    }

    /**
     * setter for position of bullet
     * @param newPoint new Point for bullet
     */
    public void setPos (Point newPoint){
        this.pos = newPoint;
    }

    /**
     * getter for horizontal of bullet
     * @return horizontal direction of bullet
     */
    public double getDirectionX(){
        return directionX;
    }

    /**
     * getter for vertical direction of bullet
     * @return vertical direction of bullet
     */
    public double getDirectionY(){
        return directionY;
    }

    /**
     * method to set the direction of bullet
     * @param dest the Point that the bullet is directed to
     */
    public void pointTo(Point dest){
        this.directionX = dest.x-this.pos.x;
        this.directionY = dest.y-this.pos.y;
        normalizeD();
    }

    /**
     * method to normalise direction
     */
    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    /**
     * method to print position of bullet while moving
     * @param tomb to print position to CSV when the tick is 10
     */
    public void printpos (ShadowTreasureComplete tomb){
        if (tomb.getTick()>tomb.getTICK_CYCLE()) {
            // store new position of bullet in a new row of string
            sb.append(String.format("%.2f", pos.x));
            sb.append(',');
            sb.append(String.format("%.2f", pos.y));
            sb.append('\n');

            //print the string stored in csv file
            try (PrintWriter writer = new PrintWriter("res/IO/output.csv")) {
                writer.write(sb.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
