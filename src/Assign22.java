/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ashish
 */

import java.awt.*;
import java.awt.event.*;


public class Assign22 extends Frame {
    public static void main(String[] args) {
        // TODO code application logic here
        //This program implements both the faster and the original version of Breshenam algorithm.
        //It also displays the number of cycles taken to draw both the lines in order to 
        //compare the factor by which the formeer is faster than the other one.
        new Assign22();
    }
    
    Assign22()
   {  super("Click on any two places to draw line and compare the running loops of Algorithms");
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setSize(400, 300);
      add("Center", new CvAssign22());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      show();
   }
}

class CvAssign22 extends Canvas {
    int xi,yi,x0,y0,pixelSize = 1;
    boolean ready = true;
    int centerX,centerY,np=0;
    Point2D[] p = new Point2D[2];
//   float rWidth = 10.0F, rHeight = 7.5F, eps = rWidth/100F, pixelSize;
    
    CvAssign22(){
        {  addMouseListener(new MouseAdapter()
      {  public void mousePressed(MouseEvent evt)
         {  int x = evt.getX(), y = evt.getY();
            if (np == 2) np = 0;
            p[np++] = new Point2D(x, y);
            repaint();
         }
      });
   }
    }
    class Point2D
{  int x, y;
   Point2D(int x, int y){this.x = x; this.y = y;}
}
     
    
    void putPixel(Graphics g, int x, int y){
        g.drawLine(x, y, x, y);
    }
    public void paint(Graphics g){
        if(p[0]!=null && p[1]!=null){
            drawLine(g,p[0].x,p[0].y,p[1].x,p[1].y,"faster");
            System.out.println("Running Breshenam Algorithm now:");
            drawLine(g,p[0].x,p[0].y,p[1].x,p[1].y);
        }
    }
    void drawLine(Graphics g, int xP, int yP, int xQ, int yQ,String f){
        
        int x = xP, y = yP, d = 0, dx = xQ - xP, dy = yQ - yP,
      c, m, xInc = 1, yInc = 1,xmid,x2 = xQ,y2=yQ,cycles=0;
        xmid = (xP+xQ)/2;
        int ymid = (yP+yQ)/2;
   if (dx < 0){xInc = -1; dx = -dx;} 
   if (dy < 0){yInc = -1; dy = -dy;}
   if (dy <= dx)
   {  c = 2 * dx; m = 2 * dy;
      if (xInc < 0) dx++;
      for (;;)
      {  
          putPixel(g, x, y);
      putPixel(g, x2, y2);
      cycles++;
         if (x == xmid) break;
         x += xInc;//increase x
         x2-=xInc;//decrease x2
         d += m;
         if (d >= dx){y += yInc; d -= c;y2-=yInc;}
          
      }
   }
   else
   {  c = 2 * dy; m = 2 * dx;
      if (yInc < 0) dy++;
      for (;;)
      {  putPixel(g, x, y);
      putPixel(g,x2,y2);
      cycles++;
         if (y == ymid) break;
         y += yInc;
         y2-=yInc;
         d += m;
         if (d >= dy){x += xInc; d -= c;x2-=xInc;}
      }
   }
   System.out.println("Total number of cycles for FasterBres: "+cycles);
//   cycles = 0;
    }

    void drawLine(Graphics g, int xP, int yP, int xQ, int yQ) {
        int x = xP, y = yP, d = 0, dx = xQ - xP, dy = yQ - yP,
      c, m, xInc = 1, yInc = 1,cycles=0;
   if (dx < 0){xInc = -1; dx = -dx;} 
   if (dy < 0){yInc = -1; dy = -dy;}
   if (dy <= dx)
   {  c = 2 * dx; m = 2 * dy;
      if (xInc < 0) dx++;
      for (;;)
      {  putPixel(g, x, y);
      cycles++;
         if (x == xQ) break;
         x += xInc;
         d += m;
         if (d >= dx){y += yInc; d -= c;}
      }
   }
   else
   {  c = 2 * dy; m = 2 * dx;
      if (yInc < 0) dy++;
      for (;;)
      {  putPixel(g, x, y);
      cycles++;
         if (y == yQ) break;
         y += yInc;
         d += m;
         if (d >= dy){x += xInc; d -= c;}
      }
   }
   System.out.println("Total number of cycles for Breshenam: "+cycles);
    }
}
