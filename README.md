# Hunt_The_Wumpus
A java implementation of the Hunt The Wumpus game, which was the final project of my CS231 (Data Structures and Algorithms) course at Colby College. It makes use of a couple of self-made data structures such as Hash Map and Graph, and also adopts some interesting algorithms such as the Dijkstra Algorithm. The background story to the game is: The hunter (the user), is trapped in a labyrinth where there is a lurking wumpus in one of the many rooms. The hunter needs to kill the wumpus by getting to the cell nearby and fire an arrow across the wall. If it misfires then the wumpus will hear the sound and kill the hunter. If the hunter wanders into wumpus' cell, it will also be killed by the wumpus.

The details of the games' rules are as follows:
  - The map is a 10 * 10 graph with 100 cells, not all cells are connected, but the wumpus are alwaus reachable for the hunter. For each cell there will be at most one door on each edge. Door is marked with a solid rectangle. If there is no rectangle then its a solid wall. 
  - Hunter will have 2 arrows at the beginning. There will be 2 random cells in the map in which hunter can pick up another arrow.
  - A cell is black if its far away from wumpus. It is becomes yellow when the wumpus is 3 steps away, orange when 2 steps away, red when less than 2 steps away. Each cell that the hunter steps on will remain illuminated for 5 time steps.
  - The wumpus will be move around slowly in the map.
  - You will kill the wumpus if the arrow you've fired hits the cell of the wumpus. If the arrow doesn't hit the wumpus, but hits one of the cells in the 3 * 3 grid centered on the cell of the wumpus, it will hear you and you will be dead.
  - Even if you've used up all your arrows, you still have a dagger, with which you can try to use it to kill the wumpus in its room. However you won't stand a good chance by doing that.. 
  - In every round, you will have one opportunity to see where the wumpus is at for 2 steps, press "C". Use that to your advantage fully. 
  
  
To compile and play:
  - Download the java files
  - In terminal
    1. cd Hunt_The_Wumpus
    2. javac *.java
    3. java HuntTheWumpus
  
  
