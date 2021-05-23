public class Invader extends Character {

    boolean moveRight,moveLeft,isVisible;

    public Invader(int x, int y, int s){
        super(x,y,s);

        moveLeft = false;
        moveRight = true;
        isVisible = true;
    }


}
