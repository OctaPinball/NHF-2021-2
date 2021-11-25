
import java.awt.event.*;


public class Controls implements KeyListener {
		private Game.Direction directionQueue = Game.Direction.Left;
		@Override
        public void keyPressed(KeyEvent e) {
			
            switch(e.getKeyChar()) {
            case 'a':
                directionQueue = Game.Direction.Left;
                break;
            case 'd':
                directionQueue = Game.Direction.Right;
                break;
            case 'w':
                directionQueue = Game.Direction.Up;
                break;
            case 's':
                directionQueue = Game.Direction.Down;
                break;
            }
        }
		@Override
        public void keyReleased(KeyEvent e) {
        	
        }
		@Override
        public void keyTyped(KeyEvent e) {
        	
        }
		public Game.Direction getDirectionQueue() {
			return directionQueue;
		}
}

