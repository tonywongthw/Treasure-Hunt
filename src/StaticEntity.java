import bagel.Image;
import bagel.util.Point;

/**
 * parent class of Zombie, Sandwich and Treasure
 */
public abstract class StaticEntity {

    // image
    protected Image image;
    // visibility of the static entities
    private boolean visible;

    // render position of entity
    private final Point pos;

    // distance of the entity to player
    private double distanceToPlayer;

    /**
     * constructor for StaticEntity
     * @param x position of x
     * @param y position of y
     */
    public StaticEntity(double x, double y){
        this.pos = new Point(x,y);
        this.visible = true;
    }

    /**
     * getter for position of entity
     * @return Point pos
     */
    public Point getPos() {
        return pos;
    }

    /**
     * getter for distance to player
     * @param player player
     * @return distance to player
     */
    public double getDistanceToPlayer(Player player) {
        distanceToPlayer = player.getPos().distanceTo(pos);
        return distanceToPlayer;
    }

    /**
     * getter for visibility
     * @return visibility
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * setter for visibility
     * @param visibility boolean for the visibility
     */
    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }

    /**
     * method to draw the entity
     * if the entity is visible, draw the entity at its position
     */
    public void draw() {
        if (visible) {
            image.drawFromTopLeft(pos.x, pos.y);
        }
    }

    /**
     * method to check if the entity has met the player
     * @param player
     * @return true or false
     */
    public boolean meets(Player player) {
        boolean hasMet = false;

        //hasMet is true if the entity is visible and its distance to player is within CLOSENESS
        if (isVisible()){
            distanceToPlayer = player.getPos().distanceTo(pos);
            if (distanceToPlayer < ShadowTreasureComplete.CLOSENESS) {
                hasMet = true;
            }
        }
        return hasMet;
    }
}
