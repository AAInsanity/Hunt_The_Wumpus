//Hunter.java
//CS231
//Project9
//ThomasDeng

import java.awt.Color;
import java.awt.Graphics;

public class Hunter extends Agent {
	
	private Vertex location;
	private boolean armed;
	private int ammo;
	
	//constructor for Hunter
	public Hunter(Vertex v) {
		super(0,0);
		location = v;
		armed = false;
		v.setVisible(true);
		ammo = 2;
		}
	
	//returns the # of ammo left
	public int getAmmo() {
		return ammo;
		}
	
	//decrements the # of ammo left
	public void reduceAmmo() {
		ammo--;
		}
	
	//add n to the # of ammo left	
	public void addAmmo(int n) {
		ammo = ammo + n;
		}	
	
	//returns the location
	public Vertex getVertex() {
		return location;
		}
	
	//returns col 	
	public int getCol() {
		return location.getCol();
		}
	
	//returns row	
	public int getRow() {
		return location.getRow();
		}					
	
	//move to N	
	public void moveN() {
		if (location.getNeighbor(Vertex.Direction.NORTH)!=null) {
			location = location.getNeighbor(Vertex.Direction.NORTH);
			location.setVisible(true);
			location.setTime(0);
			}
		}
	//move to S
	public void moveS() {
		if (location.getNeighbor(Vertex.Direction.SOUTH)!=null) {
			location = location.getNeighbor(Vertex.Direction.SOUTH);
			location.setVisible(true);
			location.setTime(0);
			}
		}	
	
	//move to W	
	public void moveW() {
		if (location.getNeighbor(Vertex.Direction.WEST)!=null) {
			location = location.getNeighbor(Vertex.Direction.WEST);
			location.setVisible(true);
			location.setTime(0);
			}				
		}
	
	//move to E
	public void moveE() {
		if (location.getNeighbor(Vertex.Direction.EAST)!=null) {
			location = location.getNeighbor(Vertex.Direction.EAST);
			location.setVisible(true);
			location.setTime(0);
			}
		}	
	
	//return if is armed	
	public boolean getArmed() {
		return armed;
		}	
	
	//set whether armed	
	public void setArmed(boolean a) {
		armed = a;
		}	
	
	//draws hunter at current location	
	public void draw(Graphics g, int scale) {
			int xpos = location.col*scale;
			int ypos = location.row*scale;
			
			int quartar = scale / 4;
			int half = scale / 2;
					
			if (armed == true) {
				g.setColor(Color.red);
				}
			else {
				g.setColor(Color.blue);
				}		
			
			g.fillOval(xpos + quartar, ypos + quartar, scale/2, scale/2);
			}
	
	//test purpose
	public static void main (String[] Args) {
		Vertex v1 = new Vertex(10, 20, "a");
		Hunter h1 = new Hunter(v1);
		System.out.println(h1.getVertex().toString());
		Vertex v2 = new Vertex(100, 100, "b");
		v1.connect(v2, Vertex.Direction.SOUTH);
		h1.moveS();
		System.out.println(h1.getVertex().toString());
		h1.moveW();
		System.out.println(h1.getVertex().toString());  		
		}
	
	}