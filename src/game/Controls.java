package game;

import java.awt.event.*;


public class Controls implements KeyListener {
		private Direction directionQueue = Direction.Left;
		
		
		/**
		 * Irányitást megvalósító függvény
		 * 
		 * @param e KeyEvent
		 */
		@Override
        public void keyPressed(KeyEvent e) {
			
            switch(e.getKeyChar()) {
            case 'a':
                directionQueue = Direction.Left;
                break;
            case 'd':
                directionQueue = Direction.Right;
                break;
            case 'w':
                directionQueue = Direction.Up;
                break;
            case 's':
                directionQueue = Direction.Down;
                break;
            }
        }
		
		/**
		 * Nincs használatban!
		 */
		@Override
        public void keyReleased(KeyEvent e) {
        	
        }
		
		/**
		 * Nincs használatban!
		 */
		@Override
        public void keyTyped(KeyEvent e) {
        	
        }
		
		/**
		 * A directionQueue gettere
		 * 
		 * Visszadja a billentyûzetrõl kapott irányt
		 * 
		 * @return directionQueue
		 */
		public Direction getDirectionQueue() {
			return directionQueue;
		}
}

