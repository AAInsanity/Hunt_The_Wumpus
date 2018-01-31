//Agent.java
//CS231
//Project 9
//Thomas Deng

import java.awt.Graphics;

public class Agent {
	
	protected int col;
	protected int row;
	
	//a constructor that sets the position.
	public Agent(int r, int c) {
		col = c;
		row = r;
		}
	
	//returns the index of the row of the grid containing the Agent.
	public int getRow() {
		return row;
		}
		
	//returns the index of the column of the grid containing the Agent.
	public int getCol() {
		return col;
		}
				
	//sets the row index.
	public void setRow( int newRow ) {
		row = newRow;
		}
		
	//sets the column index.
	public void setCol( int newCol ) {
		col = newCol;
		}
		
	//returns a String containing the x and y positions
	public String toString() {
		String str = "(" + row + ", " + col + ")";
		return str;		
 		}
		
	//does nothing
// 	public void updateState( Landscape scape ) {
// 		
// 		}
		
	//does nothing
	public void draw(Graphics g) {
		
		}
	
	
	//test method
	public static void main (String[] Args) {
		Agent a1 = new Agent(7, 10);
		System.out.println(a1.toString());
		Agent a2 = new Agent(8,9);
		System.out.println(a2.getRow());
		System.out.println(a2.getCol());
		a2.setRow(3);
		a2.setCol(5);
		System.out.println(a2.toString());
		}
		
	}