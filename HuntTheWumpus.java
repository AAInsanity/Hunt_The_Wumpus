//HuntTheWumpus.java
//CS231
//Project9
//ThomasDeng

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.util.*;

/**
 * Creates a window with two text fields, two buttons, and a large
 * display area. The app then tracks the mouse over the display area
 * and follows the mouse with a circle.
**/
public class HuntTheWumpus extends JFrame {

	/** holds the drawing canvas **/
	private HTWPanel canvas;
	
	private Control control;
	
	//other fields necessary
	private Landscape scape;
	private int numRows;
	private int numCols;
	private Random gen;
	private int scale;
	private int result; //0 = no result yet; 1 = player won; 2 = wumpus won; 3 = player won by using sword (encounter but win)
	private int p; //player's record
	private int w; //wumpus's record
	private int count; //helper
	private int numEyes; //number of opportunities to see the wumpus
	private int frame; //keeps track of the frames, for saving images 
	
	/** height of the main drawing window **/
	int height;
	/** width of the main drawing window **/
	int width;

	/** Label field 1, displays the player's record **/
	JLabel player;
	/** Label field 2, displays the cpu'record **/
	JLabel cpu;
	// Label for the ammo of the hunter
	JLabel ammo;

	// controls whether the simulation is playing or exiting
	private enum PlayState { PLAY, STOP }
	private PlayState state= PlayState.PLAY;
	
	/**
		 * Creates the main window
		 * @param rows the # of rows of the grid
		 * @param cols the # of cols of the grid
		 **/		 	 
	public HuntTheWumpus( int rows, int cols, int scale ) {
				
		super("Hunt the Wumpus");
		
		this.result = 0;
		this.count = 0;
		this.numEyes = 1;
		this.frame = 0;
		
		scape = new Landscape(rows, cols);
		
		this.numRows = rows;
		this.numCols = cols;
		this.scale = scale;
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

		this.canvas = new HTWPanel( cols * this.scale, rows * this.scale); //creates the drawing panel according to the grid size and scale
		this.add( this.canvas, BorderLayout.CENTER );
		this.pack();
		this.setVisible( true );

 		gen = new Random();		 

		this.player = new JLabel("Your Record: " + 0);
		this.cpu = new JLabel("Wumpus Record: " + 0);
		this.ammo = new JLabel("Ammo Left: 2");
		player.setForeground(Color.blue);
		cpu.setForeground(Color.blue);
		ammo.setForeground(Color.red);
		JButton again = new JButton("Play Again");
		JButton quit = new JButton("Quit");
		JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
		panel.add( this.player );
		panel.add( this.cpu );
		panel.add(this.ammo);
		panel.add( again );
		panel.add( quit );
		
		this.add( panel, BorderLayout.SOUTH);
		this.pack();

		this.control = new Control();
		this.addKeyListener(control);
		this.setFocusable(true);
		this.requestFocus();

		again.addActionListener( control );
		quit.addActionListener( control );

// 		MouseControl mc = new MouseControl();
// 		this.addMouseListener( mc );
// 		this.addMouseMotionListener( mc );
		
		this.width = cols*scale;
		this.height = rows*scale;
	}
	
	 //saves image 
	 public void saveImage(String filename) {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component tosave = this.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(), 
                                                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // save the image
        try
                {
                        ImageIO.write(image, ext, new File(filename));
                }
        catch (IOException ioe)
                {
                        System.out.println(ioe.getMessage());
                }
   		}
	
	//canvas for drawing
    private class HTWPanel extends JPanel
    {
        /**
         * Creates the panel.
         * @param width     the width of the panel in pixels
         * @param height        the height of the panel in pixels
         */
        public HTWPanel(int width, int height)
        {
                super();
                this.setPreferredSize(new Dimension(width, height));
                this.setBackground(new Color(135,206,250));
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g     the Graphics object used for drawing
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            scape.draw( g, scale );
            scape.drawExp(g, scale);
            if (result == 1) {
            	Graphics2D g2 = (Graphics2D)g;
            	g2.setColor(Color.red);
            	g2.setStroke(new BasicStroke(5));
				g2.drawRect(height/6, height/3, 2*height/3, height/3);
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
				g2.drawString("It's dead! You did it! Congratulations...", width/5, height/2);
            	}
            else if (result == 2) {
            	Graphics2D g2 = (Graphics2D)g;
             	g2.setColor(Color.black);
             	g2.fillRect(0, 0, height*2, width*2);
             	scape.getWumpus().draw(g,scale);
            	g2.setColor(Color.magenta);
            	g2.setStroke(new BasicStroke(5));
				g2.drawRect(height/6, height/3, 2*height/3, height/3);
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
				g2.drawString("You are eaten by the hungry wumpus..", width/4, height/2);
            	}
            else if (result == 3) {
            	Graphics2D g2 = (Graphics2D)g;
             	g2.setColor(Color.black);
             	g2.fillRect(0, 0, height*2, width*2);
             	scape.hArm();
             	scape.getHunter().draw(g,scale);
            	g2.setColor(Color.red);
            	g2.setStroke(new BasicStroke(5));
				g2.drawRect(height/6, height/3, 2*height/3, height/3);
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
				g2.drawString("You killed the wumpus one on one.", width/5, 9*height/20);
				g2.drawString("What a legend...Bravo.....", width/4, 11*height/20);
            	}
            else if (result == 4) {
            	Graphics2D g2 = (Graphics2D)g;
            	g2.setColor(Color.white);
            	g2.setStroke(new BasicStroke(5));
				g2.drawRect(height/6, height/3, 2*height/3, height/3);
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
				g2.drawString("The wumpus has heard you...RIP...My friend", width/5, height/2);
            	}			    
        } // end paintComponent	
    } 

    private class Control extends KeyAdapter implements ActionListener {
		
        public void keyTyped(KeyEvent e) {
          	boolean boo = false;        	
            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
               	state = PlayState.STOP;
               	boo = true;
            	}
            else if( ("" + e.getKeyChar()).equalsIgnoreCase("w") ) {
               	if (scape.getHunter().getArmed() == false) {
               		scape.hMoveN();
               		}
               	else {
               		scape.hFire(Vertex.Direction.NORTH);
               		}
               	boo = true;	
            	}        	
            else if( ("" + e.getKeyChar()).equalsIgnoreCase("a") ) {
               	if (scape.getHunter().getArmed() == false) {
               		scape.hMoveW();
               		}
               	else {
               		scape.hFire(Vertex.Direction.WEST);
               		}
               	boo = true;	
            	}          
            else if( ("" + e.getKeyChar()).equalsIgnoreCase("s") ) {
               	if (scape.getHunter().getArmed() == false) {
               		scape.hMoveS();
               		}
               	else {
               		scape.hFire(Vertex.Direction.SOUTH);
               		}
               	boo = true;
            	}      
            else if( ("" + e.getKeyChar()).equalsIgnoreCase("d") ) {
               	if (scape.getHunter().getArmed() == false) {
               		scape.hMoveE();
               		}
               	else {
               		scape.hFire(Vertex.Direction.EAST);
               		}
               	boo = true;	
            	}                             	            
            else if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
                if (scape.getHunter().getArmed() == false) {
                	scape.hArm();
                	}
                else {
                	scape.hDisarm();
                	}
                boo = true;		
            	}

            else if( ("" + e.getKeyChar()).equalsIgnoreCase("c") ) {
                if (numEyes>0) {
                	numEyes--;
                	scape.showWumpus();
                	}
                boo = true;	
            	}  
//         	System.out.println("distance between hunter and wumpus: " + scape.getDistance());
            if (boo == true) {
            	System.out.println( "Key Pressed: " + e.getKeyChar() );
				scape.updateTime();
				frame++;
				if (scape.encounter()!=true && scape.getKilled()!=true && scape.getExposed()!=true) { //if the game is not done
					scape.wMove();
					}  
	            run();
				player.setText("Your record: " + p);
				cpu.setText( "Wumpus record: " + w);
				ammo.setText("Ammo left: " + scape.getHunter().getAmmo());		            				            	
            	}            	          	
        	}

        public void actionPerformed(ActionEvent event) {
            if( event.getActionCommand().equalsIgnoreCase("Play Again") ) {
				restart();
            	}
            else if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
                state = PlayState.STOP;
            	}
			}
    	} // end class Control

	//when the player shoots the wumpus dead
	public void playerWin() {
		count++;
		scape.playerWin();
		result = 1;
		p++;
		}
	
	//special player win when the player kill the wumpus with sword	
	public void playerWin2() {
		count++;
		result = 3;
		p++;
		}	
	
	//when the wumpus eats the player	
	public void wumpusWin() {
		count++;
		scape.wumpusWin();		
		result = 2;
		w++;
		}
	
	//if the hunter exposes itself..	
	public void wumpusWin2() {
		count++;
		scape.wumpusWin();
		result = 4;
		w++;
		}		 
	
	//restarts the game
	public void restart() {
		this.result=0;
		this.count=0;
		this.frame=0;
		this.numEyes=1;
		scape.refresh();
		this.setFocusable(true);
		this.requestFocus();	
		}
	
	//helper method
	public void run() {
		if (scape.encounter()==true && count<1) {//to make sure the wumpusWin or playerWin only get called once
			if (gen.nextFloat()<0.8) { //when the two encounter, 80% of the time player will be eaten
				wumpusWin();
				}
			else {
				playerWin2(); //20% the time player will be majestic enough to kill the wumpus with sword
				}
			this.setFocusable(false);//so that the game is frozen and player won't be able to move until a new game
			}
		else if (scape.getKilled()==true && count<1) {
			playerWin();
			this.setFocusable(false);
			}
		else if (scape.getExposed()==true && count<1) {
			wumpusWin2();
			this.setFocusable(false);
			}	
		else {
			scape.supply(); //supply will only happen if the game hasn't ended yet
			}	
		}	
	
	//runs the game until its been quit	
	public void iterate() throws InterruptedException {
		while (this.state == PlayState.PLAY) {
			repaint();
			this.saveImage("data/_GameFrame_" + String.format( "%03d", frame ) + ".png" );
			Thread.sleep(33);			
			}							
		}
	
	//main method
	public static void main(String[] argv) throws InterruptedException {
		HuntTheWumpus w = new HuntTheWumpus( 10, 10, 64 );
		w.iterate();
		w.dispose();
	}
	
} // end class HuntTheWumpus

	