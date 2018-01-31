//Vertex.java
//CS231
//Project9
//ThomasDeng

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Vertex extends Agent implements Comparable<Vertex> {
	
	public enum Direction{NORTH, SOUTH, EAST, WEST, NONE};
	private BSTMap<Direction, Vertex> edges;
	private int cost; //for Dijkstra
	private boolean marked; //for Dijkstra
	private String label; //for printing purpose
	private boolean visible;
	public int time; //time since it has been visible
	
	public static Direction opposite(Direction d) {
		if (d == Direction.NORTH) {
			return Direction.SOUTH;
			}
		else if (d == Direction.SOUTH) {
			return Direction.NORTH;
			}
		else if (d == Direction.EAST) {
			return Direction.WEST;
			}
		else if (d == Direction.WEST) {
			return Direction.EAST;
			}
		else {
			return Direction.NONE;
			}	 			
		}
	
	//constructor method for Vertex
	public Vertex(int r, int c, String label) {
		super(r,c);
		Comparator<Direction> comp = new DirectionComparator();
		edges = new BSTMap<Direction, Vertex>(comp);
		cost = 0;
		marked = false;
		this.label = label;
		visible = false;
		time = 0;
		}
	
	//compares the location of two vertices	
	public boolean isSame(Vertex v1) {
		if (this.getCol()==v1.getCol() && this.getRow()==v1.getRow()) {
			return true; 
			}
		else {
			return false;
			}	
		}
	
	//return time	
	public int getTime() {
		return time;
		}		
	
	//increments time by 1
	public void addTime() {
		time++;
		}
	
	//sets time to t
	public void	setTime(int t) {
		time = t;
		}	
	
	//return whether vertex is visible	
	public boolean getVisible() {
		return visible;
		}	
	
	//set whether the vertex is visible	
	public void setVisible(boolean visible) {
		this.visible = visible;
		}	
	
	//connect the current vertex to other
	//to current vertex, other is to the direction dir	
	public void connect(Vertex other, Direction dir) {
		edges.put(dir, other);
		}
	
	//returns the neighbor at the direction dir	
	public Vertex getNeighbor(Direction dir) {
		if (!edges.containsKey(dir))
			return null;
		else
			return edges.get(dir);
		}
	
	//return a collection of neighbors 	
	public Collection<Vertex> getNeighbors() {
		return edges.getValues();
		}
	
	//returns positive number if other is more costly
	public int compareTo(Vertex other) {
		return other.cost - this.cost;
		}
	
	//returns the cost	
	public int getCost() {
		return cost;
		}
	
	//get if the vertex is marked
	public boolean getMarked() {
		return marked;
		}
	
	//sets the cost	
	public void setCost(int cost) {
		this.cost = cost;
		} 							
	
	//sets whether marked is true
	public void setMarked(boolean marked) {
		this.marked = marked;
		}
	
	//to a String representation
	public String toString() {
		String str = "row: "+row+" col: "+col +"\nlabel: " + label + "\n# of neighbors: " + edges.size() + "\ncost: " + cost + "\nmarked flag: " + marked;
		return str;
		}
	
	//draws the vertex with g	
	public void draw(Graphics g, int scale) {
			if (!this.visible){ 
				return;
				}
			int xpos = this.col*scale;
			int ypos = this.row*scale;
			int border = 2;
			int half = scale / 2;
			int eighth = scale / 8;
			int sixteenth = scale / 16;
		
			// draw rectangle for the walls of the cave
			if (this.cost == 3) {	
				// wumpus is nearby
				g.setColor(new Color(255,255,0));
				}					
			else if (this.cost == 2) {	
				// wumpus is really nearby
				g.setColor(new Color(255,127,80));
				}
			else if (this.cost <= 1) {	
				// wumpus is really really nearby
				g.setColor(new Color(220,20,60));
				}
			else {
				// wumpus is not nearby
				g.setColor(Color.black);
				}
			
			g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
		
			// draw rectangle for the walls of the cave
			if (this.cost == 3) {	
				// wumpus is nearby
				g.setColor(new Color(255,255,0));
				}					
			else if (this.cost == 2) {	
				// wumpus is really nearby
				g.setColor(new Color(255,127,80));
				}
			else if (this.cost <= 1) {	
				// wumpus is really really nearby
				g.setColor(new Color(220,20,60));
				}
			else {
				// wumpus is not nearby
				g.setColor(Color.black);
				}
				
			if (this.edges.containsKey(Direction.NORTH))
				g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
			if (this.edges.containsKey(Direction.SOUTH))
				g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
					  eighth, eighth + sixteenth);
			if (this.edges.containsKey(Direction.WEST))
				g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
			if (this.edges.containsKey(Direction.EAST))
				g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
					  eighth + sixteenth, eighth);
			}		
	
	//test method	
	public static void main(String Args[]) {
		Vertex v1 = new Vertex(10, 20, "cool");
		System.out.println(v1.opposite(Direction.NORTH));
		System.out.println(v1.opposite(Direction.SOUTH));
		System.out.println(v1.opposite(Direction.EAST));
		System.out.println(v1.opposite(Direction.WEST));
		System.out.println(v1.opposite(Direction.NONE));
		v1.setCost(3);
		Vertex v2 = new Vertex(3,4,"warm");
		v2.setCost(5);
		Vertex v3 = new Vertex(50, 3, "hot");
		v1.connect(v2, Direction.NORTH);
		v1.connect(v3, Direction.SOUTH);
		System.out.println(v1.toString());
		System.out.println("v1's south neighbor: " + v1.getNeighbor(Direction.SOUTH));
		System.out.println("v1's east neighbor: " + v1.getNeighbor(Direction.EAST));
		}	
		
	//comparator for Direction, used to construct BSTMap
	class DirectionComparator implements Comparator<Direction> {
	    public int compare( Direction d1, Direction d2 ) {
	        // returns the ordinal correlation of the two enums
	        return d1.compareTo(d2);
	    	}
		}
	
	}		
	
