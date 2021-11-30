import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


import javax.swing.Timer;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.Color;

public class Game extends JPanel implements Constant{
	

	private static final long serialVersionUID = 1L;


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

	
	private Controls controls = new Controls();
	
	private PacMan pacMan;
	private Blinky blinky;
	private Inky inky;
	private Pinky pinky;
	private Clyde clyde;
	
	private int level = 1;
	private int life;
	private Ghost.GhostState currentState;
	private int currentTime;
	private int currentStateNumber;
	private int ghostEaten;

	
	private Timer timer;
	private boolean timerEnabled;
	private boolean timerFrozen;
	private Position ghostDeathPosition = new Position(0,0);
	private boolean ghostDied = false;
	private int frozenFor = 0;
	//private int ghostTime = 1;
	//private int currentGhostTime = 0;
	//private int pacmanTime = 1;
	//private int currentPacmanTime = 0;
	
	private MainFrame f;
	
	Game(Controls controls_input, MainFrame frame) {
		f = frame;
		this.setPreferredSize(new Dimension(PANEL_WIDH,PANEL_HEIGH));
		this.addKeyListener(controls);
		controls = controls_input;
		life = START_LIFE;
		tiles = new ArrayList<Tile>();
		buildMaze();
		timerEnabled = false;
		simpleTimer();
		timer.start();
	}
	
	public void buildMaze() {
		tiles = new ArrayList<Tile>();
		for (int j = 0; j < 29; j++)
		{
			for(int i = 0; i < 13; i++)
			{
				if (TILE_HELPER[j][i] >= 1) {
					Tile newTile = new Tile(i*20+20,j*20+80,TILE_HELPER[j][i]);
					tiles.add(newTile);					
				}

			}
			for(int i = 13; i < 26; i++)
			{
				if (TILE_HELPER[j][25 - i] >= 1) {
					Tile newTile = new Tile(i*20+20,j*20+80,TILE_HELPER[j][25 - i]);
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
		
		List<Tile> ghostHouse = new ArrayList<Tile>();
		
		Tile g1 = new Tile(230,340,2);
		Tile g2 = new Tile(250,340,2);
		Tile g3 = new Tile(270,340,2);
		Tile g4 = new Tile(290,340,2);
		Tile g5 = new Tile(310,340,2);
		
		Tile g6 = new Tile(230,330,8);
		Tile g7 = new Tile(230,350,8);
		Tile g8 = new Tile(270,330,9);
		Tile g9 = new Tile(270,350,8);
		Tile g10 = new Tile(310,330,8);
		Tile g11 = new Tile(310,350,8);
		
		Tile g12 = new Tile(270,320,2);
		Tile g13 = new Tile(270,300,2);
		Tile g14 = new Tile(270,280,7);
		
		ghostHouse.add(g1);
		ghostHouse.add(g2);
		ghostHouse.add(g3);
		ghostHouse.add(g4);
		ghostHouse.add(g5);
		ghostHouse.add(g6);
		ghostHouse.add(g7);
		ghostHouse.add(g8);
		ghostHouse.add(g9);
		ghostHouse.add(g10);
		ghostHouse.add(g11);


		for (Tile i : ghostHouse)
		{
			for (Tile j : ghostHouse)
			{
				if(i.getPosition().equals(j.getPosition(), 0, -10))
					i.setNext(Game.Direction.Up, j);
				if(i.getPosition().equals(j.getPosition(), 0, 10))
					i.setNext(Game.Direction.Down, j);
				if(i.getPosition().equals(j.getPosition(), -20, 0))
					i.setNext(Game.Direction.Left, j);
				if(i.getPosition().equals(j.getPosition(), 20, 0))
					i.setNext(Game.Direction.Right, j);
			}
		}
		
		List<Tile> ghostHouse2 = new ArrayList<Tile>();
		ghostHouse2.add(g12);
		ghostHouse2.add(g13);
		ghostHouse2.add(g14);
		for (Tile i : ghostHouse2)
		{
			for (Tile j : ghostHouse2)
			{
				if(i.getPosition().equals(j.getPosition(), 0, -20))
					i.setNext(Game.Direction.Up, j);
				if(i.getPosition().equals(j.getPosition(), 0, 20))
					i.setNext(Game.Direction.Down, j);
			}
		}
		g12.setNext(Game.Direction.Down, g8);
		g8.setNext(Game.Direction.Up, g12);
		
		
		
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
			if(g14.getPosition().equals(i.getPosition(), -10, 0))
			{
				g14.setNext(Game.Direction.Left, i);
				i.setNext(Game.Direction.Right, g14);
			}
			if(g14.getPosition().equals(i.getPosition(), 10, 0))
			{
				g14.setNext(Game.Direction.Right, i);
				i.setNext(Game.Direction.Left, g14);
			}
			
        }
		tiles.addAll(ghostHouse);
		tiles.addAll(ghostHouse2);
		System.out.println(g14.countN());
	}
	
	public void initLevel() {
		
		
		buildMaze();
		
		remainedDots = 244;
		currentTime = 0;
		currentStateNumber = 0;
		ghostEaten = 0;
		currentState = Ghost.GhostState.Scatter;
		pacMan = new PacMan(280,640);
		blinky = new Blinky();
		pinky = new Pinky();
		inky = new Inky();
		clyde = new Clyde();
		blinky.setState(Ghost.GhostState.Scatter);
		pinky.setState(Ghost.GhostState.Scatter);
		inky.setState(Ghost.GhostState.Scatter);
		clyde.setState(Ghost.GhostState.Scatter);
		timerEnabled = true;
	}
	
	public void simpleTimer() {
		timer = new Timer(TIMER_MS, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
	}
	
	public void stopTimer() {
		timer.stop();
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
	
	public void dotEaten() {
		addDotScore(10);
		if (!pinky.getState().equals(Ghost.GhostState.ExitGhostHouse) && !pinky.getState().equals(Ghost.GhostState.InGhostHouse))
		{
			inky.setPelletToExit(inky.getPelletToExit() - 1);
			if(inky.getPelletToExit() == 0)
				inky.setState(Ghost.GhostState.ExitGhostHouse);
		}
		if (!inky.getState().equals(Ghost.GhostState.ExitGhostHouse) && !inky.getState().equals(Ghost.GhostState.InGhostHouse))
		{
			clyde.setPelletToExit(clyde.getPelletToExit() - 1);
			if(clyde.getPelletToExit() == 0)
				clyde.setState(Ghost.GhostState.ExitGhostHouse);
		}
	}
	
	
	public void pacmanTick() {
		pacMan.setDirection(controls.getDirectionQueue());
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
					dotEaten();					
				}
				if (i.isHasBigDot())
				{
					i.setHasBigDot(false);
					addDotScore(50);
					ghostEaten = 0;
					enterFrightened();
				}
				if(i.getTeleportLink() != null)
				{
					pacMan.setPosition(i.getTeleportLink().getPosition());
				}
				pacmanInTile = true;
			}
        }
		if (!pacmanInTile)
			pacMan.move(MOVE_STEPS);
		ghostDetTarget();
	}
	
	public void ghostTick() {

		
		
		for (Tile i : tiles)
        {
			if (blinky.getPosition().equals(i.getPosition()))
			{
				if(i.isGhostBounce() || (i.isPinkyBounce() && !(blinky.getState().equals(Ghost.GhostState.ExitGhostHouse) || blinky.getState().equals(Ghost.GhostState.Eaten))))
				{
					blinky.turnAround();
				}
				else
				{
					blinky.setLastTile(i);
					if(i.getTeleportLink() != null)
					{
						blinky.setPosition(i.getTeleportLink().getPosition());
					}
					if(i.isIntersection() == true)
						blinky.choseDirection(i);
				}
			}
			if (pinky.getPosition().equals(i.getPosition()))
			{
				if(i.isGhostBounce() || (i.isPinkyBounce() && !(pinky.getState().equals(Ghost.GhostState.ExitGhostHouse) || pinky.getState().equals(Ghost.GhostState.Eaten))))
				{
					pinky.turnAround();
				}
				else
				{
					pinky.setLastTile(i);
					if(i.getTeleportLink() != null)
					{
						pinky.setPosition(i.getTeleportLink().getPosition());
					}
					if(i.isIntersection() == true)
						pinky.choseDirection(i);
				}
			}
			if (inky.getPosition().equals(i.getPosition()))
			{
				if(i.isGhostBounce())
				{
					inky.turnAround();
				}
				else
				{
					inky.setLastTile(i);
					if(i.getTeleportLink() != null)
					{
						inky.setPosition(i.getTeleportLink().getPosition());
					}
					if(i.isIntersection() == true)
						inky.choseDirection(i);
				}
			}
			if (clyde.getPosition().equals(i.getPosition()))
			{
				if(i.isGhostBounce())
				{
					clyde.turnAround();
				}
				else
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
        }
		
		if (blinky.getPosition().equals(GHOSTHOUSE,0,-20) && blinky.getState().equals(Ghost.GhostState.ExitGhostHouse))
			blinky.dropState();
		if (inky.getPosition().equals(GHOSTHOUSE,0,-20) && inky.getState().equals(Ghost.GhostState.ExitGhostHouse))
			inky.dropState();
		if (pinky.getPosition().equals(GHOSTHOUSE,0,-20) && pinky.getState().equals(Ghost.GhostState.ExitGhostHouse))
			pinky.dropState();
		if (clyde.getPosition().equals(GHOSTHOUSE,0,-20) && clyde.getState().equals(Ghost.GhostState.ExitGhostHouse))
			clyde.dropState();
		
		if (blinky.getPosition().equals(PINKY_GHOSTHOUSE) && blinky.getState().equals(Ghost.GhostState.Eaten))
		{
			System.out.println("out");
			blinky.setState(Ghost.GhostState.ExitGhostHouse);
		}
		if (inky.getPosition().equals(INKY_GHOSTHOUSE) && inky.getState().equals(Ghost.GhostState.Eaten))
			inky.setState(Ghost.GhostState.ExitGhostHouse);
		if (pinky.getPosition().equals(PINKY_GHOSTHOUSE) && pinky.getState().equals(Ghost.GhostState.Eaten))
		{
			System.out.println("out");
			pinky.setState(Ghost.GhostState.ExitGhostHouse);
		}
		if (clyde.getPosition().equals(CLYDE_GHOSTHOUSE) && clyde.getState().equals(Ghost.GhostState.Eaten))
			clyde.setState(Ghost.GhostState.ExitGhostHouse);
		
		ghostDetTarget();
		
		blinky.move(MOVE_STEPS);
		pinky.move(MOVE_STEPS);
		inky.move(MOVE_STEPS);
		clyde.move(MOVE_STEPS);
		
	}
	
	public void checkGhostState() {
		if(!(currentState.equals(Ghost.GhostState.Chase) && currentStateNumber == 3))
		{
			if (currentState.equals(Ghost.GhostState.Scatter) && currentTime > SCATTER_TIME[level-1][currentStateNumber])
			{
				currentState = Ghost.GhostState.Chase;
				currentTime = 0;
				blinky.setState(Ghost.GhostState.Chase);
				pinky.setState(Ghost.GhostState.Chase);
				inky.setState(Ghost.GhostState.Chase);
				clyde.setState(Ghost.GhostState.Chase);
			}
			else if (currentState.equals(Ghost.GhostState.Chase) && currentTime > CHASE_TIME[level-1][currentStateNumber])
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
	}
	
	public void respawn() {
		pacMan = new PacMan(280,640);
		blinky = new Blinky();
		pinky = new Pinky();
		clyde = new Clyde();
		inky = new Inky();
		blinky.setState(Ghost.GhostState.Scatter);
		pinky.setState(Ghost.GhostState.Scatter);
		inky.setState(Ghost.GhostState.Scatter);
		clyde.setState(Ghost.GhostState.Scatter);
		currentTime = 0;
		currentStateNumber = 0;
		ghostEaten = 0;
	}
	
	
	
	@SuppressWarnings("unused")
	public void pacmanDie() {
		life -= 1;
		
		timerEnabled = false;
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (life == 0)
		{
			GameEnd gamened = new GameEnd(score);
			stopTimer();
			f.dispose();
		}
		else
		{
			respawn();
		}
		timerEnabled = true;
		System.out.println("dead");
	}
	
	public void collision(PacMan p, Ghost g) {
		if(g.getState().equals(Ghost.GhostState.Frightened))
		{
			ghostDeathPosition.setPosition(g.getPosition());
			score += GHOST_EAT_SCORE[ghostEaten];
			g.setState(Ghost.GhostState.Eaten);
			ghostEaten++;
			frozenFor = 50;
			timerFrozen = true;
			timerEnabled = false;
			ghostDied = true;
			repaint();
		}
		else
		{
			pacmanDie();
		}
	}
	
	public boolean checkCollison() {
		if(pacMan.getPosition().measureDistance(blinky.getPosition()) < 20 && (!blinky.getState().equals(Ghost.GhostState.Eaten)))
		{
			//timerEnabled = false;
			collision(pacMan, blinky);
			return true;
		}
		if(pacMan.getPosition().measureDistance(inky.getPosition()) < 20 && (!inky.getState().equals(Ghost.GhostState.Eaten)))
		{
			//timerEnabled = false;
			collision(pacMan, inky);
			return true;
		}
		if(pacMan.getPosition().measureDistance(clyde.getPosition()) < 20 && (!clyde.getState().equals(Ghost.GhostState.Eaten)))
		{
			//timerEnabled = false;
			collision(pacMan, clyde);
			return true;
		}
		if(pacMan.getPosition().measureDistance(pinky.getPosition()) < 20 && (!pinky.getState().equals(Ghost.GhostState.Eaten)))
		{
			//timerEnabled = false;
			collision(pacMan, pinky);
			return true;
		}
		return false;
	}

	
	public void ghostDetTarget() {
		blinky.detTarget(pacMan);
		pinky.detTarget(pacMan);
		inky.detTarget(pacMan, blinky);
		clyde.detTarget(pacMan);
	}
	
	public void timePassed(int i) {
		blinky.timePassed(i);
		inky.timePassed(i);
		pinky.timePassed(i);
		clyde.timePassed(i);
	}
	
	public void tick() {
		if(timerFrozen)
		{
			frozenFor -= 1;
			if(frozenFor == 0)
			{
				timerFrozen = false;
				ghostDied = false;
				timerEnabled = true;
			}
		}
		
		if (timerEnabled) {
		currentTime += TIMER_MS;
		//currentPacmanTime += 1;
		//currentGhostTime += 1;
		//if (currentPacmanTime == pacmanTime)
		//{
			pacmanTick();
			//currentPacmanTime = 0;
			if(checkCollison())
				return;
			
			
		//}
		//if (currentGhostTime == ghostTime)
		//{
			ghostTick();
			//currentGhostTime = 0;
			if(checkCollison())
				return;
		//}
		
		timePassed(TIMER_MS);
		checkGhostState();

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
        //Image frightenedend = new ImageIcon("C:\\Users\\Dell\\Downloads\\fe.gif").getImage();
        Image e_up = new ImageIcon("C:\\Users\\Dell\\Downloads\\e_Up.png").getImage();
        Image e_down = new ImageIcon("C:\\Users\\Dell\\Downloads\\e_Down.png").getImage();
        Image e_left = new ImageIcon("C:\\Users\\Dell\\Downloads\\e_Left.png").getImage();
        Image e_right = new ImageIcon("C:\\Users\\Dell\\Downloads\\e_Right.png").getImage();
        
        
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
        else if(blinky.getState().equals(Ghost.GhostState.Eaten))
        {
        	if(blinky.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(e_down, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        	if(blinky.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(e_up, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        	if(blinky.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(e_left, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        	if(blinky.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(e_right, blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        }
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
        else if(clyde.getState().equals(Ghost.GhostState.Eaten))
        {
        	if(clyde.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(e_down, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        	if(clyde.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(e_up, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        	if(clyde.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(e_left, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        	if(clyde.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(e_right, clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        }
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
        else if(inky.getState().equals(Ghost.GhostState.Eaten))
        {
        	if(inky.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(e_down, inky.getPosition().getX(), inky.getPosition().getY(), null);
        	if(inky.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(e_up, inky.getPosition().getX(), inky.getPosition().getY(), null);
        	if(inky.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(e_left, inky.getPosition().getX(), inky.getPosition().getY(), null);
        	if(inky.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(e_right, inky.getPosition().getX(), inky.getPosition().getY(), null);
        }
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
        else if(pinky.getState().equals(Ghost.GhostState.Eaten))
        {
        	if(pinky.getDirection().equals(Game.Direction.Down))
        		g2d.drawImage(e_down, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        	if(pinky.getDirection().equals(Game.Direction.Up))
        		g2d.drawImage(e_up, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        	if(pinky.getDirection().equals(Game.Direction.Left))
        		g2d.drawImage(e_left, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        	if(pinky.getDirection().equals(Game.Direction.Right))
        		g2d.drawImage(e_right, pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        }
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
        for (int i = 0; i < life; i++)
        {
    		g2d.setPaint(Color.yellow);
    		g2d.fillOval(i*20, 700, 16, 16);
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
        /*
        for(Tile i : tiles)
        {
        	g2d.setPaint(Color.yellow);
    		g2d.fillOval(i.getPosition().getX(), i.getPosition().getY(), 16, 16);
        }
        */
        g2d.setPaint(Color.white);
        g2d.setFont(new Font("Arial",Font.BOLD,20));
        g2d.drawString("SCORE: " + score, 0, 20);
        g2d.drawString("HIGH SCORE: " + highScore, 0, 50);
        g2d.drawString("LEVEL: " + level, 0, 698);
        
        if(ghostDied)
        {
        	g2d.setPaint(Color.yellow);
        	g2d.setFont(new Font("Arial",Font.BOLD,15));
            g2d.drawString("" + GHOST_EAT_SCORE[ghostEaten - 1], ghostDeathPosition.getX()-5, ghostDeathPosition.getY()+20);
        }
        
        g2d.dispose();
    }
	
}
