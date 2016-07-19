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

public class Assign21 extends Frame {
    public static void main(String[] args) {
        // TODO code application logic here
        //This program implements faster version of Breshenam Algorithm. 
        //We complete a single line using xmid and running loop through xmid to x2 and x to xmid.
        new Assign21();
    }
    
    Assign21()
   {  super("Line drawn using faster version of Breshenam Algorithm for point joining (1,1) and (50,150)");
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setSize(400, 300);
      add("Center", new CvAssign21());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      show();
   }
}

class CvAssign21 extends Canvas {
    void putPixel(Graphics g, int x, int y){
        g.drawLine(x, y, x, y);
    }
    public void paint(Graphics g){
        drawLine(g,1,1,50,150);
    }
    void drawLine(Graphics g, int xP, int yP, int xQ, int yQ){
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

    }
}
