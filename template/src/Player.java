 
public class Player extends Character {
    boolean moveRight, moveLeft, isVisible;
        public Player(int x, int y, int s){
            super(x,y,s);
            moveLeft = false;
            moveRight = false;
            isVisible = true;
        }
}