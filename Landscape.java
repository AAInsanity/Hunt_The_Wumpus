//Landscape.java
//CS231
//Project9
//Thomas Deng

import java.util.*;
import java.awt.Graphics;

public class Landscape {
	
	private Comparator<Vertex> comp;
    private int numRows;
    private int numCols;
    private Random gen;
    private Vertex[][] vertices;
    private Graph graph;
    private Hunter hunter;
    private Wumpus wumpus;
    private Supply supply1;
    private Supply supply2;
    private boolean killed;
    private Explosion exp; //depicts the explosion
    private boolean exposed;
	
	//constructs a landscape that can hold a grid of Mushrooms with the given number
	//of rows and columns
	public Landscape( int rows, int cols ) {
 		numRows = rows;
 		numCols = cols;
 		gen = new Random();
 		vertices = new Vertex[rows][cols];
 		comp = new VertexComparator();
 		graph = new Graph(comp);
 		killed = false;
 		exp = new Explosion(0,0);
 		exposed = false;
 		
 		//fill a rows * cols grid with vertices
 		for (int i=0; i<rows; i++) {
 			for (int j=0; j<cols; j++) {
				String str = "row: " + i + "col: " + j;
				vertices[i][j] = new Vertex(i,j, str);
				//vertices[i][j].setVisible(true);//testing purpose
 				} 				
 			}	
 		
 		
 		//randomly connect some of the vertices and add them to the graph
		for (int k=0; k<rows; k++) {
			for (int l=0; l<cols; l++) {
				if (k<rows-1 && gen.nextFloat()<0.8) {
					graph.addEdge(vertices[k][l], Vertex.Direction.NORTH, vertices[k+1][l]);
					}
				if (l<cols-1 && gen.nextFloat()<0.8) {
					graph.addEdge(vertices[k][l], Vertex.Direction.WEST, vertices[k][l+1]);
					}
				}
			}
		
		Vertex h = graph.randVertex(); //starting vertex for hunter
		hunter = new Hunter(h);
		graph.shortestPath(h);
		Vertex w = graph.randVertex(); //starting vertex for wumpus
		//eliminates 2 possibilities
		//1. the hunter and wumpus are born in the same vertex
		//2. the hunter can never reach the wumpus
		while(w.isSame(h)|| w.getCost()==2147483647 ) {
			w = graph.randVertex();
			}
		wumpus = new Wumpus(w);
		Vertex s1 = graph.randVertex(); //initial location for the first supply
		Vertex s2 = graph.randVertex(); //initial location for the first supply
		while(s1.isSame(h) || s1.isSame(w) || s1.getCost()<1){
			s1 = graph.randVertex();
			}
		while(s2.isSame(h)|| s2.isSame(w)|| s2.isSame(s1)|| s2.getCost()<1) {
			s2 = graph.randVertex();
			}
		supply1 = new Supply(s1);
		supply2 = new Supply(s2);
		graph.shortestPath(w);	  				
		}	
	
	//resets the landscape
	public void refresh() {
 		vertices = new Vertex[numRows][numCols];
 		graph = new Graph(comp);
 		killed = false;
 		exp = new Explosion(0,0);
 		exposed = false;
 		
 		//fill a numRows * numCols grid with vertices
 		for (int i=0; i<numRows; i++) {
 			for (int j=0; j<numCols; j++) {
				String str = "row: " + i + "col: " + j;
				vertices[i][j] = new Vertex(i,j, str);
				//vertices[i][j].setVisible(true);//testing purpose
 				} 				
 			}	
 		
 		
 		//randomly connect some of the vertices and add them to the graph
		for (int k=0; k<numRows; k++) {
			for (int l=0; l<numCols; l++) {
				if (k<numRows-1 && gen.nextFloat()<0.8) {
					graph.addEdge(vertices[k][l], Vertex.Direction.NORTH, vertices[k+1][l]);
					}
				if (l<numCols-1 && gen.nextFloat()<0.8) {
					graph.addEdge(vertices[k][l], Vertex.Direction.WEST, vertices[k][l+1]);
					}
				}
			}
		
		Vertex h = graph.randVertex(); //starting vertex for hunter
		hunter = new Hunter(h);
		graph.shortestPath(h);
		Vertex w = graph.randVertex(); //starting vertex for wumpus
		//eliminates 2 possibilities
		//1. the hunter and wumpus are born in the same vertex
		//2. the hunter can never reach the wumpus
		while(w.isSame(h)|| w.getCost()==2147483647 ) {
			w = graph.randVertex();
			}
		wumpus = new Wumpus(w);
		Vertex s1 = graph.randVertex(); //initial location for the first supply
		Vertex s2 = graph.randVertex(); //initial location for the first supply
		while(s1.isSame(h) || s1.isSame(w) || s1.getCost()<1){
			s1 = graph.randVertex();
			}
		while(s2.isSame(h)|| s2.isSame(w)|| s2.isSame(s1)|| s2.getCost()<1) {
			s2 = graph.randVertex();
			}
		supply1 = new Supply(s1);
		supply2 = new Supply(s2);
		graph.shortestPath(w);	  					
		}
	
	//returns the exp
	public Explosion getExp() {
		return exp;
		}
	
	//returns exposed	
	public boolean getExposed() {
		return exposed;
		}	
	
	//actions with hunter
	
	//move hunter to N	
	public void hMoveN() {
		hunter.moveN();
		}
	
	//move hunter to S		
	public void hMoveS() {
		hunter.moveS();
		}
	
	//move hunter to W		
	public void hMoveW() {
		hunter.moveW();
		}
	
	//move hunter to E		
	public void hMoveE() {
		hunter.moveE();
		}
	
	//arm hunter
	public void hArm() {
		hunter.setArmed(true);
		}
	
	//disarm hunter
	public void hDisarm() {
		hunter.setArmed(false);
		}
	
	//possibly moves the wumpus to some random direction	
	public void wMove() {
		wumpus.move();
		graph.shortestPath(wumpus.getVertex());
// 		System.out.println("distance between h and w: " + hunter.getVertex().getCost());
		}
		
	
	//fire at a certain direction	
	public void hFire(Vertex.Direction d) { 
	//the reason I calculated the coordinates instead of doing the neighbor business
	//is that even though there might be no door between two rooms, an arrow/bullet can still
	//kill the wumpus
		if (hunter.getAmmo()<1) {
			return;
			}		
		else {
			hunter.reduceAmmo();
			int aimRow = hunter.getRow();
			int aimCol = hunter.getCol();
			if (d==Vertex.Direction.NORTH) {
				aimRow--;
				}
			else if (d==Vertex.Direction.SOUTH) {
				aimRow++;
				}
			else if (d==Vertex.Direction.WEST) {
				aimCol--;
				}
			else if (d==Vertex.Direction.EAST) {
				aimCol++;
				}
				
			exp.setCol(aimCol);
			exp.setRow(aimRow);
			exp.setVisible(true);
			
			int wRow = wumpus.getRow();
			int wCol = wumpus.getCol();		
												
			if (aimRow >=0 && aimRow<numRows && aimCol>=0 && aimCol<numCols) {	
				if (aimCol==wCol && aimRow==wRow) { //if the aim is exactly the wumpus
					this.killed = true;
					}		
				else if	(Math.abs(wRow-aimRow)<=1 && Math.abs(wCol-aimCol)<=1) { //if the aim is not the wumpus but is close
					this.exposed = true;
					}
				} 
			}		
		}
	
	//draws exp	
	public void drawExp(Graphics g, int scale) {
		exp.draw(g, scale);
		}	
	
	//sets exp to be visible (or not)
	public void setExpVisible(boolean boo) {		
		exp.setVisible(boo);
		}	
	
	//return whether the wumpus has been killed	
	public boolean getKilled() {
		return killed;
		}	
		
	//consequence if player wins
	public void playerWin() {
		wumpus.setVisible(1);
		}
	
	//tells if the hunter and wumpus encounters
	public boolean encounter() {
		if (hunter.getCol()==wumpus.getCol() && hunter.getRow()==wumpus.getRow())
			return true;
		else
			return false;
		}
	
	//supplies the hunter if hunter found the supply	
	public void supply() {
		if (supply1.getVertex().isSame(hunter.getVertex()) && supply1.getUsed()==false) { //if hunter reaches the supply location and the supply is unused
			supply1.setVisible(true);
			supply1.setUsed(true);
			hunter.addAmmo(1);
			}
		else if (supply2.getVertex().isSame(hunter.getVertex()) && supply2.getUsed()==false) {
			supply2.setVisible(true);
			supply2.setUsed(true);
			hunter.addAmmo(1);
			}	
		}	
	
	//shows the Wumpus	
	public void showWumpus() { //reveals the wumpus for one time step
		wumpus.setVisible(2);
		}	
	
	//updates the time for various agents	
	public void updateTime() {
	//updates the time for vertices and supplies 
	//if a visible vertex reaches time 5, it will disappear
	//if a supply reaches time 3, it will disappear
	//if a wumpus reaches time 2, it will disappear
	//if a exp is visible, it will disappear
		for (Vertex v1: graph.getVertices()) {
			if (v1.getVisible() == true) {
				if (v1.getTime()==5) {
					v1.setVisible(false);
					v1.setTime(0);
					}
				else {
					v1.addTime();
					}	
				}
			}
		if (supply1.getVisible()==true) {
			if (supply1.getTime()==3) {
				supply1.setVisible(false);
				}
			else {
				supply1.addTime();
				}	
			}
			
		if (supply2.getVisible()==true) {
			if (supply2.getTime()==3) {
				supply2.setVisible(false);
				}
			else {
				supply2.addTime();
				}				
			}
			
		if (wumpus.getVisible()>0) {
			if (wumpus.getTime()==2) {
				wumpus.setVisible(0);
				}
			else {
				wumpus.addTime();
				}	
			}				
		if (exp.getVisible()==true) {
			if (exp.getTime()==1) {
				exp.setVisible(false);			
				}
			else {
				exp.addTime();
				}
			}				
		}	
		
	//consequence if wumpus wins
	public void wumpusWin() {
		wumpus.setVisible(2);
		}												
		
	//returns the number of rows in the landscape.
	public int getRows() {
		return numRows;
		}
		
	//returns the number of columns in the landscape.
	public int getCols() {
		return numCols;
		}
	
	//return hunter
	public Hunter getHunter() {
		return hunter;
		}
	
	//return wumpus	
	public Wumpus getWumpus() {	
		return wumpus;
		}
	
	//return graph
	public Graph getGraph() {
		return graph;
		}	
	
	//returns the distance between hunter and wumpus	
	public int getDistance() {
		return hunter.getVertex().getCost();
		}			
	
	//returns a String representing the Landscape.
	public String toString() {
		return "# of rows: " + numRows + "; # of cols: " + numCols;
		}
		
	//draw all the stuff in the landscape
	public void draw (Graphics g, int gridScale) {    	
 		for (Vertex v1: graph.getVertices()) {
 			v1.draw(g, gridScale);
 			}
 		hunter.draw(g, gridScale);
 		wumpus.draw(g, gridScale);
 		supply1.draw(g, gridScale);
 		supply2.draw(g, gridScale);		
		}
		
		
	//test method
	public static void main (String[] Args) {
		Landscape l1 = new Landscape(6,6);
		System.out.println(l1.toString());
		Hunter h1 = l1.getHunter();
		System.out.println("hunter position: " + h1.getVertex().getRow() + ", " + h1.getVertex().getCol());
		Wumpus w1 = l1.getWumpus();
		System.out.println("wumpus position: " + w1.getVertex().getRow() + ", " + w1.getVertex().getCol());
		l1.getGraph().shortestPath(h1.getVertex());
		System.out.println("hunter to wumpus: " + w1.getVertex().getCost());
		System.out.println("# of vertices in the graph: " + l1.getGraph().getVertices().size());		
		}
	
	//comparator for vertices
	class VertexComparator implements Comparator<Vertex> {
	    public int compare( Vertex v1, Vertex v2 ) {
	        // returns negative number if v1 has greater cost
	        return -v1.compareTo(v2);
	    	}
		}		
	
	}