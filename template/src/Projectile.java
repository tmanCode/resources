import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Projectile extends Rectangle {
    double dx, dy,x ,y;
    boolean moveUp,isVisible;

    Color color;

      public Projectile(double x, double y, int width, int height, Color color, double dx, double dy){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.dx = dx;
            this.dy = dy;

          moveUp = false;
          isVisible = false;

        }


}