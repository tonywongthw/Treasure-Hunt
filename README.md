# Treasure Hunt
 An interesting Treasure Hunt simulation built in Java!

# Inspiration

 To simulate a situation where the main character need to eat sandwiches to obtain enough energy for defeating all zombies, and then obtain the treasure

# Demo

![Demo](https://github.com/tonywongthw/projects/blob/main/screenshots/Treasure%20Hunt%20Demo.gif)

# Objects

![Object](https://github.com/tonywongthw/projects/blob/main/screenshots/Screenshot%202022-07-13%20123828.jpg)

a. Zombie : Stationary Entity - Need 3 units of energy to kill

b. Sandwich : Stationary Entity - Increase 5 units of energy when obtained

c. Player : Movable Entity - Aim to obtain enough energy to defeat all zombies and then obtain the treasure

d. Treasure : Stationary Entity - Complete the simulation when obtained

e. Bullet : Movable Entity - Cost 3 units of energy to fire within a specific range for killing zombie

# Loading Environment

Environemnt is loaded via a .csv file inside the "test" folder. It contans coordiates for zombie, sandwich and player, as well as the initial energy level of player.

# Strategy

1. Maintain sufficient energy to defeat zombies. If energy is low, move to sandwich and eat that. Else, move to cloest zombie to defeat it.
2. If all zombies are defeated, move to treasure to obtain that.
3. If all sandwiches are exhausted but there are still zombies, terminate the simulation.

# Cloning and Testing

Clone the repository

Run the main class "ShadowTreasureComplete"