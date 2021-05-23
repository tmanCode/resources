import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;  
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.ImageIcon;

public class Main {
  
  public static JFrame f = new JFrame(); 
  public static JLabel p = new JLabel();
  public static JLabel g = new JLabel();
    
    public static void main(String[] args) throws InterruptedException {
      
        
      JFrame k = new JFrame();
      k.pack();
      k.setVisible(true);
      k.setSize(800, 1000);
      k.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      k.setResizable(false);
      k.setLocationRelativeTo(null);
      
      BufferedImage lose = null;
      BufferedImage win = null;
      BufferedImage menupic = null;
      try{
        lose = ImageIO.read(new File("../resources/lose1.jpg"));
        win = ImageIO.read(new File("../resources/space4.jpg"));
        menupic = ImageIO.read(new File("../resources/spacemenu.png"));
      } catch (IOException e){}
       ImageIcon lo = new ImageIcon(lose.getScaledInstance(k.getWidth(), k.getHeight(), BufferedImage.SCALE_SMOOTH));   
       ImageIcon wi = new ImageIcon(win.getScaledInstance(k.getWidth(), k.getHeight(), BufferedImage.SCALE_SMOOTH));
      ImageIcon me = new ImageIcon(menupic.getScaledInstance(k.getWidth(), k.getHeight(), BufferedImage.SCALE_SMOOTH));
      p.setIcon(lo);
     g.setIcon(wi);
      

      JButton b = new JButton("Click to play");

      
      b.setBounds(300,430,150,70);
      k.setTitle("Space Invaders");
      JLabel a = new JLabel();
      a.setIcon(me);
      k.add(a);
      k.setVisible(true);
      k.add(b);
      
      b.addActionListener(new ActionListener(){  
        
        public void actionPerformed(ActionEvent e){   
          k.setVisible(false);
          GameState s = new GameState();
          f.pack();
          f.add(s);
          f.setVisible(true);
          f.setSize(800, 1000);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setResizable(false);
          f.setLocationRelativeTo(null);
          f.setTitle("Space Invaders");
        }  
      }); 
      
    }


    }


