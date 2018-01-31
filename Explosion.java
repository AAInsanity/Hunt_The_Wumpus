//Explosion.java
//CS231
//Project9
//ThomasDeng

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class Explosion extends Agent {
	
	private int row;
	private int col;
	private boolean visible;
    private int time; //time it has been visible
	
	//constructor
	public Explosion(int r, int c) {
		super(r,c);
		visible = false;
		time = 0;
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
	
	//returns col
	public int getCol() {
		return col;
		}
	
	//returns row
	public int getRow() {
		return row;
		}	
	
	//sets col to c	
	public void setCol(int c) {
		col = c;
		}	
	
	//sets row to r	
	public void setRow(int r) {
		row = r;
		}		
	
	//sets whether is visible
	public void setVisible(boolean boo) {
		visible = boo;
		}		
	
	//gets visible	
	public boolean getVisible() {
		return visible;
		}		
	
	//draws itself if is visible
	public void draw(Graphics g, int scale) {
		if (visible == false) {
			return;
			}		
		else {
			
			int xpos = col*scale;
			int ypos = row*scale;
		
			int quartar = scale / 4;
			int half = scale / 2;
			int eighth = scale / 8;
				
			g.setColor(Color.red);
			
			g.setFont(new Font("Arial", Font.BOLD, 40)); 
			g.drawString("X", xpos+quartar, ypos+3*quartar);
			
			}
		}
	
	}