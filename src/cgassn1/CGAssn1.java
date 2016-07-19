/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgassn1;

/**
 *
 * @author Ashish
 */
import java.awt.*;
import java.awt.event.*;


public class CGAssn1 extends Frame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new CGAssn1();
    }
    
    CGAssn1()
   {  super("dfdf");
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setSize(400, 300);
      add("Center", new ConcentrSq());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      show();
   }
}

    class ConcentrSq extends Canvas {

        int centerX, centerY;
        public ConcentrSq() {
        }
        
        int iX(float x){return Math.round(centerX + x);}
        int iY(float y){return Math.round(centerY - y);}
        
        public void paint(Graphics g){
            Dimension d = getSize();
            int maxX = d.width - 1, maxY = d.height - 1,
            minMaxXY = Math.min(maxX, maxY);
            centerX = maxX/2; centerY = maxY/2;
            float r = 0.5F * minMaxXY;
            float xA, yA, xB, yB, xC, yC, xD, yD, 
            xA1, yA1, xB1, yB1, xC1, yC1, xD1, yD1;
            xA = xD = - r; xB = xC = r;
            yA = yB = - r; yC = yD = r;
            for (int i=0; i<20; i++) 
            {
                g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
                g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
                g.drawLine(iX(xC), iY(yC), iX(xD), iY(yD));
                g.drawLine(iX(xD), iY(yD), iX(xA), iY(yA));
                xA1 = (xA+xB)/2; yA1 = (yA+yB)/2; 
                xB1 = (xB+xC)/2; yB1 = (yB+yC)/2;
                xC1 = (xC+xD)/2; yC1 = (yC+yD)/2; 
                xD1 = (xD+xA)/2; yD1 = (yD+yA)/2;
                xA = xA1; xB = xB1; xC = xC1; xD = xD1;
                yA = yA1; yB = yB1; yC = yC1; yD = yD1;
            } 
            
        }

        
    }