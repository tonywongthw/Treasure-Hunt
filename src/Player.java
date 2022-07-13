import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * class of Player
 * file built with reference from suggested solution
 */
public class Player{

    // image source file
    public static final String FILENAME = "res/images/player.png";
    // speed
    public static final double STEP_SIZE = 10;
    // energy level threshold
    private static final int LOW_ENERGY = 3;

    // health bar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();

    // image and type
    private final Image image;
    private final Image bulletImage;
    // render position
    private Point pos;
    private Bullet bullet;

    // direction
    private double directionX;
    private double directionY;

    // health bar parameters
    private int energy;

    /**
     * constructor for Bullet
     * @param x position of x
     * @param y position of y
     * @param energy energy of player
     */
    public Player(double x, double y, int energy) {
        this.image = new Image(FILENAME);
        this.pos = new Point(x,y);
        this.bulletImage = new Image(bullet.FILENAME);
        this.energy = energy;
    }

    /**
     * getter for position of player
     * @return Point pos
     */
    public Point getPos(){
        return this.pos;
    }

    /**
     * method to set the direction of player
     * @param dest the Point that the player is directed to
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
     * method to update the moving direction of player and bullet
     * @param tomb the game environment
     */
    public void update(ShadowTreasureComplete tomb){
        // Check if the treasure is met by player, or the player's energy is (strictly)
        // less than 3 when there are zombies but no sandwich. If so, terminate the game.

        if (bullet==null && (tomb.getTreasure().meets(this) || energy<LOW_ENERGY &&
                !tomb.checkNoZombie() && tomb.checkNoSandwich())) {
            System.out.print(energy);
            if (tomb.getTreasure().meets(this)){
                System.out.print(",success!" );
            }
            tomb.setEndOfGame(true);
        }
        //Otherwise if the player meets the Sandwich increase the energy and set it invisible.
        //If the player meets the Zombie in shooting range, shoot bullet to it.
        else {
            if (tomb.getSandwich() != null){
                if (tomb.getSandwich().meets(this)) {
                    eatSandwich();
                    tomb.getSandwich().setVisible(false);
                }
            }
            if (tomb.getZombie().getDistanceToPlayer(this) < ShadowTreasureComplete.SHOOT_RANGE &&
                    tomb.getZombie().isVisible()) {
                if (bullet == null){
                    shootBullet();
                    bullet = new Bullet(this.pos.x, this.pos.y);
                    bullet.pointTo(tomb.getZombie().getPos());
                    bullet.printpos(tomb);
                }
            }

            //set moving direction of player
            if (tomb.checkNoZombie()){
                pointTo(tomb.getTreasure().getPos());
            } else if (this.energy >= LOW_ENERGY){
                pointTo(tomb.getZombie().getPos());
            } else if (tomb.getSandwich() != null){
                pointTo(tomb.getSandwich().getPos());
            }
            this.pos = new Point(this.pos.x+STEP_SIZE*this.directionX, this.pos.y+STEP_SIZE*this.directionY);

            // if zombie is shot dead, remove. While bullet is shot, record its position.
            if (bullet != null && bullet.getPos() != this.pos){
                bullet.setPos(new Point(bullet.getPos().x+bullet.STEP_SIZE*bullet.getDirectionX(),
                        bullet.getPos().y+bullet.STEP_SIZE*this.bullet.getDirectionY()));
                bullet.printpos(tomb);
                if (bullet.getPos().distanceTo(tomb.getZombie().getPos())<ShadowTreasureComplete.DEAD_RANGE){
                    tomb.getZombie().setVisible(false);
                    bullet = null;
                }
            }
        }
    }

    /**
     * method to render the image and energy of player, and the image of bullet
     */
    public void render() {
        image.drawFromTopLeft(pos.x, pos.y);
        // also show energy level
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
        // show image of bullet
        if (bullet != null){
            bulletImage.drawFromTopLeft(bullet.getPos().x, bullet.getPos().y);
        }
    }
    /**
     * after eating sandwich, increase player energy by 5
     */
    public void eatSandwich(){
        energy += 5;
    }

    /**
     * after shooting bullet, decrease player energy by 3
     */
    public void shootBullet(){
        energy -= 3;
    }

}
