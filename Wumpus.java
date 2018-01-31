//Wumpus.java
//CS231
//Project9
//ThomasDeng

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Wumpus extends Agent {
	
	private Vertex location;
	private int visible; //0 = not visible, 1 = Wumpus lose, 2 = Wumpus win
	private int time; //time since it has been visible
	
	//constructor for Wumpus
	public Wumpus(Vertex v) {
		super(0,0);
		location = v;
		visible = 0;
		time=0;
		}
	
	//returns the location Vertex	
	public Vertex getVertex() {
		return location;
		}		
	
	//returns the col of location
	public int getCol() {
		return location.getCol();
		}
	
	//returns the row of location	
	public int getRow() {
		return location.getRow();
		}		
	
	//returns time	
	public int getTime() {
		return time;
		}		
	
	//increment time by 1
	public void addTime() {
		time++;
		}
	
	//sets time to t
	public void	setTime(int t) {
		time = t;
		}	
	
	//returns whether Wumpus is visible	
	public int getVisible() {
		return visible;
		}		
	
	//sets whether Wumpus is visible	
	public void setVisible(int visible) {
		this.visible = visible;
		}
	
	//possibly moves the wumpus to some random direction
	public void move() {
		Random gen = new Random();
		int dir = gen.nextInt(4); //this int chooses a random direction
		if (gen.nextFloat()<0.5) { //for 50% of the time
			if (dir == 0 && location.getNeighbor(Vertex.Direction.NORTH)!=null) {
				location = location.getNeighbor(Vertex.Direction.NORTH);
				}
			if (dir == 1 && location.getNeighbor(Vertex.Direction.SOUTH)!=null) {
				location = location.getNeighbor(Vertex.Direction.SOUTH);
				}
			if (dir == 2 && location.getNeighbor(Vertex.Direction.EAST)!=null) {
				location = location.getNeighbor(Vertex.Direction.EAST);
				}
			if (dir == 3 && location.getNeighbor(Vertex.Direction.WEST)!=null) {
				location = location.getNeighbor(Vertex.Direction.WEST);															
				}
			}	
		}				
	
	//draws the Wumpus at its location
	public void draw(Graphics g, int scale) {
			if (this.visible == 0){ 
				return;
				}
			int xpos = location.col*scale;
			int ypos = location.row*scale;
			int half = scale / 2;
			int quartar = scale / 4;
			int eighth = scale / 8;
			int sixteenth = scale / 16;
		
			// draw wumpus
			if (visible == 1)
				// wumpus is dead
				g.setColor(Color.black);
			else
				// wumpus ate the hunter
				g.setColor(Color.magenta);
			
			g.fillRect(xpos + quartar, ypos + quartar, half, half);
			}	
	
	}