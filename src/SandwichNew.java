import bagel.Image;

/**
 * class of Sandwich
 */
public class SandwichNew extends StaticEntity{

    // image
    private final Image imageSandwich = new Image("res/images/sandwich.png");

    /**
     * constructor for Sandwich
     * @param x position of x
     * @param y position of y
     */
    public SandwichNew (double x, double y){
        super(x,y);
        super.image = imageSandwich;
    }
}
