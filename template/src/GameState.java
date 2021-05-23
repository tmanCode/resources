import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



public class GameState extends JPanel implements ActionListener, KeyListener, MouseListener
{
    private final int BOARD_WIDTH = 800, BOARD_HEIGHT = 1000;
    Timer tm = new Timer(5, this); //timer refreshes screen 5mS
    private final Player player;
    Invader[] ayy = new Invader[15];
    private int ayyx = 10;
    private int ayyy = 10;
    private int count = 0;
    private final Projectile[] Shot = new Projectile[50]; // make number higher for more shots in game.
    private final Projectile[] AlienShot = new Projectile[5]; // only 5 on screen at a time
    public boolean lose;
    int destroyed = 0;
    private long time, attackcooldown = 1000;
    int score = 0;
    private long delay = 3000;

    public GameState(){
        tm.start();
        JLabel s = new JLabel("Score: " + score);
        s.setVisible(true);
        s.setBounds(0, 0, 50, 50);
        Main.f.add(s);
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //creating player
        player = new Player(BOARD_WIDTH/2, BOARD_HEIGHT - 60, 5);

        //creating Bullets
        for(int k = 0; k < Shot.length; k++){
            Shot[k] = new Projectile(player.x, player.y - 20, 10,10, Color.red, 0,0 );
        }

        //creating ayys
        for(int i = 0; i < ayy.length; i++){
            ayy[i] = new Invader(ayyx, ayyy, 10);
            ayyx += 40;
            if(i == 4 || i == 9 || i == 14){
                ayyx = 10;
                ayyy +=40;
            }
        }
    }
    


    public void moveBullet(){
        for(int i = 0; i < Shot.length; i++) {
            if (Shot[i].isVisible) {
                //create unit vector for angled movement. EZ EZ.
                double Added = Math.pow(Shot[i].dx,2) + Math.pow(Shot[i].dy,2);
                double Magnitude = Math.sqrt(Added); //magnitude of Unit vector
                if(Shot[i].dx > 0 ){//right side of player
                    Shot[i].x +=  3*Shot[i].dx/Magnitude;
                 //   System.out.println(Shot[i].dx/Magnitude);
                }
                if(Shot[i].dx <= 0){
                    Shot[i].x +=  3*Shot[i].dx/Magnitude;
                    //System.out.println(Shot[i].dx/Magnitude);
                }
                if (Shot[i].dy / Magnitude < 0) {//cant shoot behind you
                } else {
                    Shot[i].y -= 3 * Shot[i].dy / Magnitude; // y direction;
                }




            }
        }
                //TOP OF SCREEN DETECTION
            for(int i = 0; i < Shot.length; i++){
                if (Shot[i].y < (20)){
                    Shot[i].isVisible = false;
                    Shot[i].moveUp = false;
                    Shot[i].y = player.y - 20;
                    Shot[i].x = player.x;
                    if( i >= 15){
                        count = 0;
                    }
                }
            }
            // Bullet and Ayy collision
        for(int i = 0; i < ayy.length; i++){
            for(int j = 0; j < Shot.length; j++) {
                 if (Shot[j].y <= (ayy[i].y + 20) && Shot[j].y >= (ayy[i].y - 10) && ayy[i].isVisible && (Shot[j].x >= ayy[i].x - 15) && Shot[j].x <= (ayy[i].x + 20)) { {
                    Shot[j].isVisible = false;
                    Shot[j].moveUp = false;
                    Shot[j].y = player.y - 20;
                    Shot[j].x = player.x;
                    ayy[i].isVisible = false;
                    ayy[i].moveLeft = false;
                    ayy[i].moveRight = false;
                    destroyed += 1;
                    score += 2;
                }
            }
        }
        }
    }
    

    
    
    
    public void moveAyy(){
        for(int i = 0; i < ayy.length; i++) {
            if (ayy[i].moveLeft && ayy[i].isVisible) {
                ayy[i].x -= 4;
            }
            if (ayy[i].moveRight && ayy[i].isVisible) {
                ayy[i].x += 4;
            }
            // Sets gameover = true if the aliens get to the bottom of the screen or if they are touching the player
            if (ayy[i].y >= 970 || (player.y <= (ayy[i].y + 40) && player.y >= (ayy[i].y - 40) && ayy[i].isVisible && (player.x >= ayy[i].x - 40) && player.x <= (ayy[i].x + 40))){
              lose = true;
            }
        }
            for(int k = 0; k < ayy.length; k++){
            if (ayy[k].x > (BOARD_WIDTH - 40) && ayy[k].isVisible){
                for(int j =0; j<ayy.length; j++){
                    ayy[j].moveLeft = true;
                    ayy[j].moveRight = false;
                    ayy[j].y += 10;
                }
            }
            if (ayy[k].x < 0 && ayy[k].isVisible){
                for(int j =0; j<ayy.length; j++){
                    if(ayy[j].isVisible) {
                        ayy[j].moveRight = true;
                        ayy[j].moveLeft = false;
                        ayy[j].y += 10;
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g){ //draws everything.
        super.paint(g);
       super.paintComponent(g);
        //player
       if (player.isVisible == true){
        g.setColor(Color.red);
        g.fillRect(player.x,player.y,20, 20);
       }
        if(player.moveRight){
            player.x += player.speed;
            if (player.x >= 760)
              player.x = 760;
        }
        String str = new String("Score: " + score);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(str, 20, 20);
        if(player.moveLeft){
            player.x -= player.speed;
              if (player.x <= 0)
                player.x = 0;
        }
        if (lose){ // If the aliens touched the player, or if they touched the bottom, then this will display a game over screen.
          this.setVisible(false);
          JLabel w = new JLabel("The aliens have taken over the world!");
          w.setBounds(800, 1000, 500, 400);
          Main.f.add(Main.p);
        }
          
          
     
        
        
        if (destroyed == ayy.length){ // Checks if the player has destroyed all the aliens
          this.setVisible(false);
          JLabel w = new JLabel("You saved the universe!");
          w.setBounds(500, 400, 100, 30);
          Main.f.add(Main.g);         
        }
        moveAyy();
        moveBullet();
        //ayys isvisible determine
        for(int i = 0; i < ayy.length; i++) {
            if (ayy[i].isVisible) {
                g.fillRect(ayy[i].x, ayy[i].y, 30, 30);
            }
        }

        for(int i = 0; i < Shot.length; i++) {
            if (Shot[i].isVisible) {
                g.fillRect((int)Shot[i].x, (int)Shot[i].y, 10, 10);
            }
        }
    }





    @Override //timer checks this every 5mS
    public void actionPerformed(ActionEvent e){
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode(); //gets the code associated for used key and stores it in code.
        if(code == 37){
            player.moveLeft = true;
        }
        if(code == 39){
            player.moveRight = true;
        }
        if (code == 81)
        {
          System.exit(0);
        }
        
        
    }


    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode(); //gets the code associated for used key and stores it in code.
        if(code == 37){
            player.moveLeft = true;
        }
        if(code == 39){
            player.moveRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 39 || code == 37){
        player.moveRight = false;
        player.moveLeft = false;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (time + attackcooldown < System.currentTimeMillis()) {
            time = System.currentTimeMillis();
            Shot[count].isVisible = true;

            double X = e.getX();
            double Y = e.getY();
            Shot[count].x = player.x;
            Shot[count].y = player.y - 20;
            Shot[count].dx = X - player.x; //x distance between point and player
            Shot[count].dy = player.y - Y; //y distance between point and player
            count = count + 1;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
