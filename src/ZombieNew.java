import bagel.Image;

/**
 * class of Zombie
 */
public class ZombieNew extends StaticEntity{

    // image
    private final Image imageZombie = new Image("res/images/zombie.png");

    /**
     * constructor for Zombie
     * @param x position of x
     * @param y position of y
     */
    public ZombieNew (double x, double y){
        super(x,y);
        super.image = imageZombie;
    }
}
