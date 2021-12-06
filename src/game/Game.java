package game;
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

	

	private int highScore;
	private int score = 0;
	private int remainedDots;
	
	private List<Tile> tiles;

	
	private Controls controls;
	
	private PacMan pacMan;
	private Blinky blinky;
	private Inky inky;
	private Pinky pinky;
	private Clyde clyde;
	
	private int level = 1;
	private int life;
	private GhostState currentState;
	private int currentTime;
	private int currentStateNumber;
	private int ghostEaten;

	
	private Timer timer;
	private boolean timerEnabled;
	private boolean timerFrozen;
	private Position ghostDeathPosition = new Position(0,0);
	private boolean ghostDied = false;
	private int frozenFor = 0;

	private MainFrame f;
	
	public Game(Controls controls_input, MainFrame frame) {
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
	
	/**
	 * Felépíti a pályát
	 * 
	 * Létrehozza az összes mezõt és beállítja az egyes mezõk tulajdonságait
	 */
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
					i.setNext(Direction.Up, j);
				if(i.getPosition().equals(j.getPosition(), 0, 10))
					i.setNext(Direction.Down, j);
				if(i.getPosition().equals(j.getPosition(), -20, 0))
					i.setNext(Direction.Left, j);
				if(i.getPosition().equals(j.getPosition(), 20, 0))
					i.setNext(Direction.Right, j);
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
					i.setNext(Direction.Up, j);
				if(i.getPosition().equals(j.getPosition(), 0, 20))
					i.setNext(Direction.Down, j);
			}
		}
		g12.setNext(Direction.Down, g8);
		g8.setNext(Direction.Up, g12);
		
		
		
		for (Tile i : tiles)
        {
			for (Tile j : tiles)
	        {
				if(i.getPosition().equals(j.getPosition(), 0, -20))
					i.setNext(Direction.Up, j);
				if(i.getPosition().equals(j.getPosition(), 0, 20))
					i.setNext(Direction.Down, j);
				if(i.getPosition().equals(j.getPosition(), -20, 0))
					i.setNext(Direction.Left, j);
				if(i.getPosition().equals(j.getPosition(), 20, 0))
					i.setNext(Direction.Right, j);
	        }
			if(g14.getPosition().equals(i.getPosition(), -10, 0))
			{
				g14.setNext(Direction.Left, i);
				i.setNext(Direction.Right, g14);
			}
			if(g14.getPosition().equals(i.getPosition(), 10, 0))
			{
				g14.setNext(Direction.Right, i);
				i.setNext(Direction.Left, g14);
			}
			
        }
		tiles.addAll(ghostHouse);
		tiles.addAll(ghostHouse2);
	}
	
	/**
	 * Beállíta a level kezdeti értékeit
	 * 
	 * Pacman és a szellemek pozíciója, maradék pontok szá,a szellemek fázisa, stb.
	 */
	public void initLevel() {
		buildMaze();
		remainedDots = 244;
		currentTime = 0;
		currentStateNumber = 0;
		ghostEaten = 0;
		currentState = GhostState.Scatter;
		pacMan = new PacMan(280,640);
		blinky = new Blinky();
		pinky = new Pinky();
		inky = new Inky();
		clyde = new Clyde();
		blinky.setState(GhostState.Scatter);
		pinky.setState(GhostState.Scatter);
		inky.setState(GhostState.Scatter);
		clyde.setState(GhostState.Scatter);
		timerEnabled = true;
	}
	
	/**
	 * Létrehozza a timert
	 */
	public void simpleTimer() {
		timer = new Timer(TIMER_MS, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
	}
	
	/**
	 * Megállítja a timert
	 */
	public void stopTimer() {
		timer.stop();
	}
	
	/**
	 * Meghívódik ha a szintet teljesítette a játékos
	 */
	public void levelCompleted() {
		level += 1;
		initLevel();
	}
	
	/**
	 * A megevett pont értékét a score-hoz adja
	 * 
	 * Ellenõrzi, hogy a szintet teljesítette a játékos
	 * 
	 * @param i - pont értéke
	 */
	public void addDotScore(int i) {
		score += i;
		remainedDots -= 1;
		if (remainedDots == 0)
		{
			levelCompleted();
		}
	}
	
	/**
	 * Az összes szellem belép a "Frightened" modeba
	 */
	public void enterFrightened() {
		blinky.setState(GhostState.Frightened);
		pinky.setState(GhostState.Frightened);
		inky.setState(GhostState.Frightened);
		clyde.setState(GhostState.Frightened);
	}
	
	/**
	 * Ellenõrzi, hogy a játékos megszerezett-e elég pontot ahhoz, hogy a meghatározott szellem kijöjjön a szellemházból.
	 */
	public void dotEaten() {
		addDotScore(10);
		if (!pinky.getState().equals(GhostState.ExitGhostHouse) && !pinky.getState().equals(GhostState.InGhostHouse))
		{
			inky.setPelletToExit(inky.getPelletToExit() - 1);
			if(inky.getPelletToExit() == 0)
				inky.setState(GhostState.ExitGhostHouse);
		}
		if (!inky.getState().equals(GhostState.ExitGhostHouse) && !inky.getState().equals(GhostState.InGhostHouse))
		{
			clyde.setPelletToExit(clyde.getPelletToExit() - 1);
			if(clyde.getPelletToExit() == 0)
				clyde.setState(GhostState.ExitGhostHouse);
		}
	}
	
	/**
	 * Pacman viselkedését írja le minden egyes Timer "tikkre"
	 */
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
	
	/**
	 * A szellemek viselkedését írja le minden egyes Timer "tikkre"
	 */
	public void ghostTick() {
		for (Tile i : tiles)
        {
			if (blinky.getPosition().equals(i.getPosition()))
			{
				if(i.isGhostBounce() || (i.isPinkyBounce() && !(blinky.getState().equals(GhostState.ExitGhostHouse) || blinky.getState().equals(GhostState.Eaten))))
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
				if(i.isGhostBounce() || (i.isPinkyBounce() && !(pinky.getState().equals(GhostState.ExitGhostHouse) || pinky.getState().equals(GhostState.Eaten))))
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
		
		if (blinky.getPosition().equals(GHOSTHOUSE,0,-20) && blinky.getState().equals(GhostState.ExitGhostHouse))
			blinky.dropState();
		if (inky.getPosition().equals(GHOSTHOUSE,0,-20) && inky.getState().equals(GhostState.ExitGhostHouse))
			inky.dropState();
		if (pinky.getPosition().equals(GHOSTHOUSE,0,-20) && pinky.getState().equals(GhostState.ExitGhostHouse))
			pinky.dropState();
		if (clyde.getPosition().equals(GHOSTHOUSE,0,-20) && clyde.getState().equals(GhostState.ExitGhostHouse))
			clyde.dropState();
		
		if (blinky.getPosition().equals(PINKY_GHOSTHOUSE) && blinky.getState().equals(GhostState.Eaten))
		{
			System.out.println("out");
			blinky.setState(GhostState.ExitGhostHouse);
		}
		if (inky.getPosition().equals(INKY_GHOSTHOUSE) && inky.getState().equals(GhostState.Eaten))
			inky.setState(GhostState.ExitGhostHouse);
		if (pinky.getPosition().equals(PINKY_GHOSTHOUSE) && pinky.getState().equals(GhostState.Eaten))
		{
			System.out.println("out");
			pinky.setState(GhostState.ExitGhostHouse);
		}
		if (clyde.getPosition().equals(CLYDE_GHOSTHOUSE) && clyde.getState().equals(GhostState.Eaten))
			clyde.setState(GhostState.ExitGhostHouse);
		
		ghostDetTarget();
		
		blinky.move(MOVE_STEPS);
		pinky.move(MOVE_STEPS);
		inky.move(MOVE_STEPS);
		clyde.move(MOVE_STEPS);
		
	}
	
	/**
	 * Megvizsgálja a szellemek fázisát és ha kell új értéket ad nekik
	 */
	public void checkGhostState() {
		if(!(currentState.equals(GhostState.Chase) && currentStateNumber == 3))
		{
			if (currentState.equals(GhostState.Scatter) && currentTime > SCATTER_TIME[level-1][currentStateNumber])
			{
				currentState = GhostState.Chase;
				currentTime = 0;
				blinky.setState(GhostState.Chase);
				pinky.setState(GhostState.Chase);
				inky.setState(GhostState.Chase);
				clyde.setState(GhostState.Chase);
			}
			else if (currentState.equals(GhostState.Chase) && currentTime > CHASE_TIME[level-1][currentStateNumber])
			{
				currentState = GhostState.Scatter;
				currentTime = 0;
				currentStateNumber += 1;
				blinky.setState(GhostState.Scatter);
				pinky.setState(GhostState.Scatter);
				inky.setState(GhostState.Scatter);
				clyde.setState(GhostState.Scatter);
			}
		}
	}
	
	/**
	 * Visszaállítja minden egyes entitás helyét és fázisát a kedõértékre
	 */
	public void respawn() {
		pacMan = new PacMan(280,640);
		blinky = new Blinky();
		pinky = new Pinky();
		clyde = new Clyde();
		inky = new Inky();
		blinky.setState(GhostState.Scatter);
		pinky.setState(GhostState.Scatter);
		inky.setState(GhostState.Scatter);
		clyde.setState(GhostState.Scatter);
		currentTime = 0;
		currentStateNumber = 0;
		ghostEaten = 0;
	}
	
	
	/**
	 * Pacman halálát leíró függvény
	 * 
	 * Amennyiben lehetséges újraéled, de ha már nincs több élete akkor a játék véget ér
	 */
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
	}
	
	/**
	 * Leírja ha egy szellem és pacman találkozik.
	 * 
	 * @param p - Pacman
	 * @param g - szellem amelyel Pacman ütközött
	 */
	public void collision(PacMan p, Ghost g) {
		if(g.getState().equals(GhostState.Frightened))
		{
			ghostDeathPosition.setPosition(g.getPosition());
			score += GHOST_EAT_SCORE[ghostEaten];
			g.setState(GhostState.Eaten);
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
	
	/**
	 * Ellenõrzi, hogy volt-e ütközés Pacman és valamely szellem között
	 * 
	 * @return bollean - Igaz ha volt ütközés, hamis ha nem volt
	 */
	public boolean checkCollison() {
		if(pacMan.getPosition().measureDistance(blinky.getPosition()) < 20 && (!blinky.getState().equals(GhostState.Eaten)))
		{
			collision(pacMan, blinky);
			return true;
		}
		if(pacMan.getPosition().measureDistance(inky.getPosition()) < 20 && (!inky.getState().equals(GhostState.Eaten)))
		{
			collision(pacMan, inky);
			return true;
		}
		if(pacMan.getPosition().measureDistance(clyde.getPosition()) < 20 && (!clyde.getState().equals(GhostState.Eaten)))
		{
			collision(pacMan, clyde);
			return true;
		}
		if(pacMan.getPosition().measureDistance(pinky.getPosition()) < 20 && (!pinky.getState().equals(GhostState.Eaten)))
		{
			collision(pacMan, pinky);
			return true;
		}
		return false;
	}

	/**
	 * Meghatározást kér az összes szellem céljára
	 */
	public void ghostDetTarget() {
		blinky.detTarget(pacMan);
		pinky.detTarget(pacMan);
		inky.detTarget(pacMan, blinky);
		clyde.detTarget(pacMan);
	}
	
	/**
	 * Idõ eltelését hívja meg minden szellemen
	 * 
	 * @param i - eltelt idõ (ms-ben)
	 */
	public void timePassed(int i) {
		blinky.timePassed(i);
		inky.timePassed(i);
		pinky.timePassed(i);
		clyde.timePassed(i);
	}
	
	/**
	 * Timer egységnyi idejének eltelésének következményét leíró függvény
	 */
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
		pacmanTick();
		if(checkCollison())
			return;
		
		ghostTick();
		if(checkCollison())
			return;
		
		timePassed(TIMER_MS);
		checkGhostState();

		repaint();
		}
	}

	/**
	 * Függvény amely kirajzolja a megfelelõ képeket
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Image maze = new ImageIcon("res\\PacMan_background.png").getImage();
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
        
        if(blinky != null)
        	g2d.drawImage(blinky.getImage(), blinky.getPosition().getX(), blinky.getPosition().getY(), null);
        if(pinky != null)
        	g2d.drawImage(pinky.getImage(), pinky.getPosition().getX(), pinky.getPosition().getY(), null);
        if(clyde != null)
        	g2d.drawImage(clyde.getImage(), clyde.getPosition().getX(), clyde.getPosition().getY(), null);
        if(inky != null)
        	g2d.drawImage(inky.getImage(), inky.getPosition().getX(), inky.getPosition().getY(), null);
        if(pacMan != null)
        	g2d.drawImage(pacMan.getImage(), pacMan.getPosition().getX(), pacMan.getPosition().getY(), null);
        
    
        
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
