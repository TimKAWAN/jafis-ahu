/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fingerprintrecognition;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import sun.applet.Main;

/**
 *
 * @author taufik
 */
public class CropImage extends JFrame {
   private Image image;
   int startx, starty;
   int stopx, stopy;
   int currentx, currenty;
   Boolean scroolPane;
   
   public void scroolTrue(){
        scroolPane = true;
   }
    
   public CropImage() throws Exception {
      
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {
            System.exit(1);
         }
      });
   
      BorderLayout layout = new BorderLayout();
      getContentPane().setLayout(layout);
      image = new javax.swing.ImageIcon("/Users/taufik/Desktop/finger/finger1.jpeg").getImage();
       
      this.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent me) {
            startx = me.getX();
            starty = me.getY();
            stopx = stopy = 0;
            
             System.out.println(".mousePressed()");
            repaint();
         }
  
         public void mouseReleased(MouseEvent me) {
            stopx = me.getX();
            stopy = me.getY();
            currentx = currenty = 0;
            repaint();
            constructFrame(getCroppedImage(image, startx, starty,
                                           stopx-startx, stopy-starty));
         }
      });
  
      this.addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent me) {
            currentx = me.getX();
            currenty = me.getY();
            repaint();
         }
      });
   }
   
   public void constructFrame(final Image img) {
      JFrame frame = new JFrame() {
         public void paint(Graphics g) {
            g.drawImage(img, 0, 15, null);
         }
      };
      
    
      frame.setSize(img.getWidth(null), img.getHeight(null));
      frame.setVisible(true);
      frame.invalidate();
   }
  
   public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
  
      g2d.drawImage(image, 0, 0, this);
 
     
      if (startx != 0 && currentx != 0) {
             System.out.println("2");
         g2d.setColor(Color.white);
         BasicStroke bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                            5, new float[] {10}, 0);
         g2d.setStroke(bs);
         g2d.drawRect(startx, starty, currentx-startx, currenty-starty);
      }
  
   }
  
   public Image getCroppedImage(Image img, int x, int y, int w, int h) {
      CropImageFilter cif = new CropImageFilter(x, y, w, h);
      FilteredImageSource fis = new FilteredImageSource(img.getSource(), cif);
      Image croppedImage = getToolkit().createImage(fis);
  
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(croppedImage, 0);
      try {
         tracker.waitForID(0);
      } catch (Exception e) {
         e.printStackTrace();
      }
          
      return croppedImage;
   }
  
   public static void main(String []args) throws Exception {
      CropImage main = new CropImage();
     
      main.setSize(663, 663);
     
      

    // main.add(scrollPane);
      main.setVisible(true);
      
     
   }
}
