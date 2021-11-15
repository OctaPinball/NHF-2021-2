
import java.awt.event.*;


public class Controls implements KeyListener {
		private Game.Direction directionQueue = Game.Direction.Left;
		@Override
        public void keyPressed(KeyEvent e) {

            //int key = e.getKeyChar();
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
            /*
                if (key == KeyEvent.VK_A) {
                    directionQueue = Game.Direction.Left;
                    System.out.println("1");
                } else if (key == KeyEvent.VK_RIGHT) {
                	directionQueue = Game.Direction.Right;
                } else if (key == KeyEvent.VK_UP) {
                	directionQueue = Game.Direction.Up;
                } else if (key == KeyEvent.VK_DOWN) {
                	directionQueue = Game.Direction.Down;
                } else if (key == KeyEvent.VK_ESCAPE) {
                    //inGame = false;
                } */
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

