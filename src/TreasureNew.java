import bagel.Image;

/**
 * class of Treasure
 */
public class TreasureNew extends StaticEntity{

    // image
    private final Image imageTreasure = new Image("res/images/treasure.png");

    /**
     * constructor for Sandwich
     * @param x position of x
     * @param y position of y
     */
    public TreasureNew (double x, double y){
        super(x,y);
        super.image = imageTreasure;
    }
}
