import bagel.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * An example Bagel game.
 * file built with reference from suggested solution
 */
public class ShadowTreasureComplete extends AbstractGame {

    // image source file
    private final Image BACKGROUND = new Image("res/images/background.png");
    // defining closeness, shoot range and dead range of the game.
    public static final int CLOSENESS = 50;
    public static final int SHOOT_RANGE = 150;
    public static final int DEAD_RANGE = 25;

    // for rounding double number
    private static DecimalFormat df = new DecimalFormat("0.00");

    // defining tick cycle and variable tick
    private final int TICK_CYCLE = 10;
    private int tick;

    // list of characters
    private Player player;
    private ArrayList<StaticEntity> listStatic = new ArrayList<>();
    private TreasureNew treasure;

    // among listStatic, the sandwich and zombie that are closest to player
    private SandwichNew sandwich;
    private ZombieNew zombie;

    // end of game indicator
    private boolean endOfGame;

    /**
     * getter to get the tick of the game
     * @return tick
     */
    public int getTick() {
        return tick;
    }

    /**
     * getter to get the tick cycle of the game
     * @return tick cycle
     */
    public int getTICK_CYCLE(){
        return TICK_CYCLE;
    }

    /**
     * method to set the path for loading environment file
     */
    public ShadowTreasureComplete() throws IOException {
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        this.endOfGame = false;
    }

    /**
     * getter to get the sandwich that is visible and closest to player
     * @return sandwich
     */
    public SandwichNew getSandwich() {
        for (StaticEntity staticEntity: listStatic){
            //check if the static entity is sandwich and visible
            if (staticEntity instanceof SandwichNew && staticEntity.isVisible()){
                //assign it if sandwich is null
                if (sandwich==null){
                    sandwich = (SandwichNew) staticEntity;
                } //assign it if the entity is visible
                else if (!sandwich.isVisible()){
                    sandwich = (SandwichNew) staticEntity;
                } //assign it if the entity is closer to player
                else if (staticEntity.getDistanceToPlayer(player) < sandwich.getDistanceToPlayer(player)){
                    sandwich = (SandwichNew) staticEntity;
                }
            }

        }
        return sandwich;
    }

    /**
     * getter to get the zombie that is visible and closest to player
     * @return zombie
     */
    public ZombieNew getZombie() {
        for (StaticEntity staticEntity: listStatic){
            //check if the static entity is sandwich and visible
            if (staticEntity instanceof ZombieNew && staticEntity.isVisible()){
                //assign it if zombie is null
                if (zombie == null){
                    zombie = (ZombieNew) staticEntity;
                } //assign it if the entity is visible
                else if (!zombie.isVisible()){
                    zombie = (ZombieNew) staticEntity;
                } //assign it if the entity is closer to player
                else if (staticEntity.getDistanceToPlayer(player) < zombie.getDistanceToPlayer(player)){
                    zombie = (ZombieNew) staticEntity;
                }
            }
        }
        return zombie;
    }

    /**
     * getter to get the treasure that is visible
     * @return treasure
     */
    public TreasureNew getTreasure() {
        return treasure;
    }

    /**
     * setter to set the sate of the game
     * @param endOfGame set it to be true or false
     */
    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    /**
     * method to check if there are zombie visible, or all have been killed
     * @return true if there are no more visible zombie
     */
    public boolean checkNoZombie(){
        boolean result = true;
        for (StaticEntity staticEntity: listStatic){
            if (staticEntity instanceof ZombieNew && staticEntity.isVisible()){
                result = false;
            }
        }
        return result;
    }

    /**
     * method to check if there are sandwiches visible, or all have been eater
     * @return true if there are no more visible sandwiches
     */
    public boolean checkNoSandwich(){
        boolean result = true;
        for (StaticEntity staticEntity: listStatic){
            if (staticEntity instanceof SandwichNew && staticEntity.isVisible()){
                result = false;
            }
        }
        return result;
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                type = type.replaceAll("[^a-zA-Z0-9]", ""); // remove special characters
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                switch (type) {
                    case "Player" -> this.player = new Player(x, y, Integer.parseInt(parts[3]));
                    case "Zombie"  -> listStatic.add(new ZombieNew(x,y));
                    case "Sandwich"  -> listStatic.add(new SandwichNew(x,y));
                    case "Treasure"  -> this.treasure = new TreasureNew(x, y);
                    default    -> throw new BagelError("Unknown type: " + type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)){
            Window.close();
        } else{
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                // update player status
                player.update(this);
                tick = 1;
            }
            tick++;
            for (StaticEntity staticEntity: listStatic){
                staticEntity.draw();
            }
            treasure.draw();
            player.render();
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasureComplete game = new ShadowTreasureComplete();
        game.run();
    }
}
