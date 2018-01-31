//Supply.java
//CS231
//Project9
//ThomasDeng

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class Supply extends Agent {
	
	private Vertex location;
	private boolean visible;
	private boolean used; 
	public int time; //time since it has been visible
	
	//constructor
	public Supply(Vertex v) {
		super(0,0);
		location = v;
		visible = false;
		used = false;
		time = 0;
		}
	
	//return if is used
	public boolean getUsed() {
		return used;
		}
	
	//set if is used
	public void setUsed(boolean used) {
		this.used = used;
		}
	
	//return location	
	public Vertex getVertex() {
		return location;
		}		
	
	//return col
	public int getCol() {
		return location.getCol();
		}
	
	//return row	
	public int getRow() {
		return location.getRow();
		}		
	
	//set visible to visible
	public void setVisible(boolean visible) {
		this.visible = visible;
		}
	
	//return if is visible
	public boolean getVisible() {
		return visible;
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
	
	//draws a supply	
	public void draw(Graphics g, int scale) {
			if (this.visible == false){ 
				return;
				}
			int xpos = location.col*scale;
			int ypos = location.row*scale;

			int eighth = scale / 8;
		
			// draw Supply
			g.setColor(Color.red);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g.drawString("+1", xpos + 2*eighth, ypos + 5*eighth);
			}	
	
	}