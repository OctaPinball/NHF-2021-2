import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;

public class Game extends JPanel{
	
	enum Direction {
		  Up,
		  Down,
		  Left,
		  Right
		}
	
	private int highScore;
	private int score = 0;
	private int remainedDots;
	
	private List<Tile> tiles;
	private int tileHelper[][] = 
			{
		{1,1,1,1,1,1,1,1,1,1,1,1,0},
		{1,0,0,0,0,1,0,0,0,0,0,1,0},
		{3,0,0,0,0,1,0,0,0,0,0,1,0},
		{1,0,0,0,0,1,0,0,0,0,0,1,0},
		{1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,0,0,1,0,0,1,0,0,0,0},
		{1,0,0,0,0,1,0,0,1,0,0,0,0},
		{1,1,1,1,1,1,0,0,1,1,1,1,0},
		{0,0,0,0,0,1,0,0,0,0,0,2,0},
		{0,0,0,0,0,1,0,0,0,0,0,2,0},
		{0,0,0,0,0,1,0,0,2,2,2,5,2},
		{0,0,0,0,0,1,0,0,2,0,0,0,0},
		{0,0,0,0,0,1,0,0,2,0,0,0,0},
		{6,6,6,6,6,1,2,2,2,0,0,0,0},
		{0,0,0,0,0,1,0,0,2,0,0,0,0},
		{0,0,0,0,0,1,0,0,2,0,0,0,0},
		{0,0,0,0,0,1,0,0,2,2,2,2,2},
		{0,0,0,0,0,1,0,0,2,0,0,0,0},
		{0,0,0,0,0,1,0,0,2,0,0,0,0},
		{1,1,1,1,1,1,1,1,1,1,1,1,0},
		{1,0,0,0,0,1,0,0,0,0,0,1,0},
		{1,0,0,0,0,1,0,0,0,0,0,1,0},
		{3,1,1,0,0,1,1,1,1,1,1,4,2},
		{0,0,1,0,0,1,0,0,1,0,0,0,0},
		{0,0,1,0,0,1,0,0,1,0,0,0,0},
		{1,1,1,1,1,1,0,0,1,1,1,1,0},
		{1,0,0,0,0,0,0,0,0,0,0,1,0},
		{1,0,0,0,0,0,0,0,0,0,0,1,0},
		{1,1,1,1,1,1,1,1,1,1,1,1,1},
	};
	//0 = no tile
	//1 = tile with dot
	//2 = tile without dot
	//3 = tile with big dot
	//4 = tile with dot without upway
	//5 = tile without dot without upway
	//6 = cant turn
	
	private Controls controls = new Controls();
	
	private PacMan pacMan;
	private Blinky blinky;
	private Inky inky;
	private Pinky pinky;
	private Clyde clyde;
	
	private int level = 1;
	private Ghost.GhostState currentState;
	private int currentTime;
	private int currentStateNumber;
	private int scatterTime[][] = {
			{7000,7000,5000,5000},
			{7000,7000,5000,1000},
			{5000,5000,5000,1000}
	};
	private int chaseTime[][] = {
			{20000,20000,20000},
			{20000,20000,353000},
			{20000,20000,357000}
	};
	
	private Timer timer;
	private boolean timerEnabled;
	
	Game(Controls controls_input){
		this.setPreferredSize(new Dimension(560,720));
		this.addKeyListener(controls);
		controls = controls_input;
		tiles = new ArrayList<Tile>();
		buildMaze();
		timerEnabled = false;
		simpleTimer();
		timer.start();
	}
	
	public void buildMaze() {
		for (int j = 0; j < 29; j++)
		{
			for(int i = 0; i < 13; i++)
			{
				if (tileHelper[j][i] >= 1) {
					Tile newTile = new Tile(i*20+20,j*20+80,tileHelper[j][i]);
					tiles.add(newTile);					
				}

			}
			for(int i = 13; i < 26; i++)
			{
				if (tileHelper[j][25 - i] >= 1) {
					Tile newTile = new Tile(i*20+20,j*20+80,tileHelper[j][25 - i]);
					tiles.add(newTile);					
				}

			}
		}
		Tile t3 = new Tile(0,340,6);
		Tile t4 = new Tile(540,340,6);
		Tile t1 = new Tile(-20,340, 6);
		Tile t2 = new Tile(560,340, 6);
		t1.setTeleportTile(t4);
		t2.setTeleportTile(t3);
		tiles.add(t1);
		tiles.add(t2);
		tiles.add(t3);
		tiles.add(t4);
		
		for (Tile i : tiles)
        {
			for (Tile j : tiles)
	        {
				if(i.getPosition().equals(j.getPosition(), 0, -20))
					i.setNext(Game.Direction.Up, j);
				if(i.getPosition().equals(j.getPosition(), 0, 20))
					i.setNext(Game.Direction.Down, j);
				if(i.getPosition().equals(j.getPosition(), -20, 0))
					i.setNext(Game.Direction.Left, j);
				if(i.getPosition().equals(j.getPosition(), 20, 0))
					i.setNext(Game.Direction.Right, j);
	        }
        }
	}
	
	public void initLevel() {
		remainedDots = 244;
		currentTime = 0;
		currentStateNumber = 0;
		currentState = Ghost.GhostState.Scatter;
		pacMan = new PacMan(280,640);
		blinky = new Blinky(240, 160);
		pinky = new Pinky(320, 160);
		inky = new Inky(280, 160);
		clyde = new Clyde(360, 160);
		blinky.setState(Ghost.GhostState.Scatter);
		pinky.setState(Ghost.GhostState.Scatter);
		inky.setState(Ghost.GhostState.Scatter);
		clyde.setState(Ghost.GhostState.Scatter);
		timerEnabled = true;
	}
	
	public void simpleTimer() {
		timer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
	}
	
	public void levelCompleted() {
		level += 1;
		initLevel();
	}
	
	public void addDotScore(int i) {
		score += i;
		remainedDots -= 1;
		if (remainedDots == 0)
		{
			levelCompleted();
		}
	}
	
	public void enterFrightened() {
		blinky.setState(Ghost.GhostState.Frightened);
		pinky.setState(Ghost.GhostState.Frightened);
		inky.setState(Ghost.GhostState.Frightened);
		clyde.setState(Ghost.GhostState.Frightened);
	}
	
	public void pacmanTick() {
		
	}
	
	public void ghostTick() {
		
	}
	
	public void tick() {
		if (timerEnabled) {
		currentTime += 15;
		
		if(pacMan.getPosition().measureDistance(blinky.getPosition()) < 20)
		{
			timerEnabled = false;
		}
		if(pacMan.getPosition().measureDistance(inky.getPosition()) < 20)
		{
			timerEnabled = false;
		}
		if(pacMan.getPosition().measureDistance(clyde.getPosition()) < 20)
		{
			timerEnabled = false;
		}
		if(pacMan.getPosition().measureDistance(pinky.getPosition()) < 20)
		{
			timerEnabled = false;
		}
		
		if(!(currentState.equals(Ghost.GhostState.Chase) && currentStateNumber == 3))
		{
			if (currentState.equals(Ghost.GhostState.Scatter) && currentTime > scatterTime[level][currentStateNumber])
			{
				currentState = Ghost.GhostState.Chase;
				currentTime = 0;
				blinky.setState(Ghost.GhostState.Chase);
				pinky.setState(Ghost.GhostState.Chase);
				inky.setState(Ghost.GhostState.Chase);
				clyde.setState(Ghost.GhostState.Chase);
			}
			else if (currentState.equals(Ghost.GhostState.Chase) && currentTime > chaseTime[level][currentStateNumber])
			{
				currentState = Ghost.GhostState.Scatter;
				currentTime = 0;
				currentStateNumber += 1;
				blinky.setState(Ghost.GhostState.Scatter);
				pinky.setState(Ghost.GhostState.Scatter);
				inky.setState(Ghost.GhostState.Scatter);
				clyde.setState(Ghost.GhostState.Scatter);
			}
		}
		
		blinky.detTarget(pacMan);
		pinky.detTarget(pacMan);
		inky.detTarget(pacMan, blinky);
		clyde.detTarget(pacMan);
		
		boolean pacmanInTile = false;
		for (Tile i : tiles)
        {
			if(pacMan.getPosition().equals(i.getPosition()))
			{
				pacMan.turn(i);
				pacMan.movePacMan(i);
				if (i.isHasDot())
				{
					i.setHasDot(false);
					addDotScore(10);
				}
				if (i.isHasBigDot())
				{
					i.setHasBigDot(false);
					addDotScore(50);
					enterFrightened();
				}
				if(i.getTeleportLink() != null)
				{
					pacMan.setPosition(i.getTeleportLink().getPosition());
				}
				pacmanInTile = true;
			}
			if (blinky.getPosition().equals(i.getPosition()))
			{
				blinky.setLastTile(i);
				if(i.getTeleportLink() != null)
				{
					blinky.setPosition(i.getTeleportLink().getPosition());
				}
				if(i.isIntersection() == true)
					blinky.choseDirection(i);
			}
			if (pinky.getPosition().equals(i.getPosition()))
			{
				pinky.setLastTile(i);
				if(i.getTeleportLink() != null)
				{
					pinky.setPosition(i.getTeleportLink().getPosition());
				}
				if(i.isIntersection() == true)
					pinky.choseDirection(i);
			}
			if (inky.getPosition().equals(i.getPosition()))
			{
				inky.setLastTile(i);
				if(i.getTeleportLink() != null)
				{
					inky.setPosition(i.getTeleportLink().getPosition());
				}
				if(i.isIntersection() == true)
					inky.choseDirection(i);
			}
			if (clyde.getPosition().equals(i.getPosition()))
			{
				clyde.setLastTile(i);
				if(i.getTeleportLink() != null)
				{
					clyde.setPosition(i.getTeleportLink().getPosition());
				}
				if(i.isIntersection() == true)
					clyde.choseDirection(i);
			}
        }
		if (!pacmanInTile)
			pacMan.move(2);
		blinky.move(2);
		pinky.move(2);
		inky.move(2);
		clyde.move(2);
		pacMan.setDirection(controls.getDirectionQueue());
		repaint();
		}
	}

	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Image maze = new ImageIcon("C:\\Users\\Dell\\Downloads\\PacMan_background.png").getImage();
        g2d.drawImage(maze, 0, 0, null);
        
        for (Tile i : tiles)
        {
        	if (i.isHasDot())
        	{
        		g2d.setPaint(Color.white);
        		g2d.fillOval(i.getPosition().getX()+10, i.getPosition().getY()+10, 5, 5);
        	}
        	if (i.isHasBigDot())
        	{
        		g2d.setPaint(Color.yellow);
        		g2d.fillOval(i.getPosition().getX()+4, i.getPosition().getY()+5, 16, 16);
        	}
        }
        
        Image pacManTexture_down = new ImageIcon("C:\\Users\\Dell\\Downloads\\down.gif").getImage();
        Image pacManTexture_up = new ImageIcon("C:\\Users\\Dell\\Downloads\\up.gif").getImage();
        Image pacManTexture_right = new ImageIcon("C:\\Users\\Dell\\Downloads\\right.gif").getImage();
        Image pacManTexture_left = new ImageIcon("C:\\Users\\Dell\\Downloads\\left.gif").getImage();
        Image ghost_texture = new ImageIcon("C:\\Users\\Dell\\Downloads\\ghost.gif").getImage();
        Image b_up = new ImageIcon("C:\\Users\\Dell\\Downloads\\b_up.gif").getImage();
        Image b_down = new ImageIcon("C:\\Users\\Dell\\Downloads\\b_down.gif").getImage();
        Image b_left = new ImageIcon("C:\\Users\\Dell\\Downloads\\b_left.gif").getImage();
        Image b_right = new ImageIcon("C:\\Users\\Dell\\Downloads\\b_right.gif").getImage();
        Image c_up = new ImageIcon("C:\\Users\\Dell\\Downloads\\c_up.gif").getImage();
        Image c_down = new ImageIcon("C:\\Users\\Dell\\Downloads\\c_down.gif").getImage();
        Image c_left = new ImageIcon("C:\\Users\\Dell\\Downloads\\c_left.gif").getImage();
        Image c_right = new ImageIcon("C:\\Users\\Dell\\Downloads\\c_right.gif").getImage();
        Image i_up = new ImageIcon("C:\\Users\\Dell\\Downloads\\i_up.gif").getImage();
        Image i_down = new ImageIcon("C:\\Users\\Dell\\Downloads\\i_down.gif").getImage();
        Image i_left = new ImageIcon("C:\\Users\\Dell\\Downloads\\i_left.gif").getImage();
        Image i_right = new ImageIcon("C:\\Users\\Dell\\Downloads\\i_right.gif").getImage();
        Image p_up = new ImageIcon("C:\\Users\\Dell\\Downloads\\p_up.gif").getImage();
        Image p_down = new ImageIcon("C:\\Users\\Dell\\Downloads\\p_down.gif").getImage();
        Image p_left = new ImageIcon("C:\\Users\\Dell\\Downloads\\p_left.gif").getImage();
        Image p_right = new ImageIcon("C:\\Users\\Dell\\Downloads\\p_right.gif").getImage();
        Image frightened = new ImageIcon("C:\\Users\\Dell\\Downloads\\f.gif").getImage();
        Image frightenedend = new ImageIcon("C:\\Users\\Dell\\Downloads\\fe.gif").getImage();
        if(pacMan.getDirection().equals(Game.Direction.Down))
        	g2d.drawImage(pacManTexture_down, pacMan.getPosition().getX(), pacMan.getPosition().getY(), null);
        if(pacMan.getDirection().equals(Game.Direction.Up))
        	g2d.drawImage(pacManTexture_up, pacMan.getPosition().getX(), pacMan.getPosition().getY(), null);
        if(pacMan.getDirection().equals(Game.Direction.Left))
        	g2d.drawImage(pacManTexture_left, pacMan.getPosition().getX(), pacMan.getPosition().getY(), null);
        if(pacMan.getDirection().equals(Game.Direction.Right))
        	g2d.drawImage(pacManTexture_right, pacMan.getPosition().getX(), pacMan.getPosition().getY(), null);

        
        if(blinky.getState().equals(Ghost.GhostState.Frightened))
        	g2d.drawImage(frightened, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        else 
        {
        	if(blinky.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(b_down, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        	if(blinky.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(b_up, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        	if(blinky.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(b_left, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        	if(blinky.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(b_right, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        }
        
        if(clyde.getState().equals(Ghost.GhostState.Frightened))
        	g2d.drawImage(frightened, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        else
        {
        	if(clyde.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(c_down, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        	if(clyde.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(c_up, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        	if(clyde.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(c_left, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        	if(clyde.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(c_right, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        }
        
        if(inky.getState().equals(Ghost.GhostState.Frightened))
        	g2d.drawImage(frightened, inky.getPosition().getX(), inky.getPosition().getY(), null);
        else
        {
        	if(inky.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(i_down, inky.getPosition().getX(), inky.getPosition().getY(), null);
        	if(inky.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(i_up, inky.getPosition().getX(), inky.getPosition().getY(), null);
        	if(inky.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(i_left, inky.getPosition().getX(), inky.getPosition().getY(), null);
        	if(inky.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(i_right, inky.getPosition().getX(), inky.getPosition().getY(), null);
        }
        
        if(pinky.getState().equals(Ghost.GhostState.Frightened))
        	g2d.drawImage(frightened, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        else
        {
        	if(pinky.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(p_down, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        	if(pinky.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(p_up, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        	if(pinky.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(p_left, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        	if(pinky.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(p_right, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        }

        /*
        g2d.setPaint(Color.red);
		g2d.fillOval(blinky.getTarget().getX()+10, blinky.getTarget().getY()+10, 7, 7);
		g2d.setPaint(Color.pink);
		g2d.fillOval(pinky.getTarget().getX()+10, pinky.getTarget().getY()+10, 7, 7);
		g2d.setPaint(Color.cyan);
		g2d.fillOval(inky.getTarget().getX()+10, inky.getTarget().getY()+10, 7, 7);
		g2d.setPaint(Color.orange);
		g2d.fillOval(clyde.getTarget().getX()+10, clyde.getTarget().getY()+10, 7, 7);
        */
        g2d.dispose();
    }
	
}
