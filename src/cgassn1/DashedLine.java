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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DashedLine extends Frame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new DashedLine();
    }
    
    DashedLine()
   {  super("dfdf");
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setSize(400, 300);
//      add("Center", new ConcentrSq());
      add("Center", new DashedLine1());
//      add("Center", new HexagonGrid());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      show();
   }
}

 class DashedLine1 extends Canvas {
    int centerX, centerY;
        public DashedLine1() {
        }
        
        int iX(float x){return Math.round(centerX + x);}
        int iY(float y){return Math.round(centerY - y);}
        
        public void paint(Graphics g){
            Dimension d = getSize();
            int maxX = d.width - 1, maxY = d.height - 1;
            centerX = maxX/2; centerY = maxY/2;
            int minMaxXY = Math.min(maxX, maxY);
            float r = 0.5F * minMaxXY;
            
            float xA = -r, xB = r, xC = r, xD = -r, yA = -r/2, yB = -r/2, yC = r/2, yD = r/2;
            g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
            g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
            g.drawLine(iX(xC), iY(yC), iX(xD), iY(yD));
            g.drawLine(iX(xD), iY(yD), iX(xA), iY(yA));
            r = r/2;
            float xA1 = -r, xB1 = r, xC1 = r, xD1 = -r, yA1 = -r/2, yB1 = -r/2, yC1 = r/2, yD1 = r/2;
            dashedLine(g,iX(xA1), iY(yA1), iX(xB1), iY(yB1));
            dashedLine(g,iX(xC1), iY(yC1), iX(xB1), iY(yB1));
            dashedLine(g,iX(xD1), iY(yD1), iX(xC1), iY(yC1));
            dashedLine(g,iX(xD1), iY(yD1), iX(xA1), iY(yA1));
            //draw slanted lines
            dashedLine(g,iX(xA1), iY(yA1), iX(xA), iY(yA));
            dashedLine(g,iX(xB1), iY(yB1), iX(xB), iY(yB));
            dashedLine(g,iX(xC), iY(yC), iX(xC1), iY(yC1));
            dashedLine(g,iX(xD), iY(yD), iX(xD1), iY(yD1));
                       
            
        }

    private void dashedLine(Graphics g, float xA, float yA, float xB, float yB) {
        float u1 = xB-xA;
        float u2 = yB-yA;
        int dashLength = 20;
        double L = Math.sqrt(u1*u1 + u2*u2);
        int n = (int) Math.floor((L/dashLength + 1 )/2);
        float h1 = u1/(2*n-1);
        float h2 = u2/(2*n-1);
        for(int i = 0;i<n;i++){
            int x1 = (int) (xA+(h1*2*i));
            g.drawLine(x1, (int)(yA+(2*i*h2)), (int)(xA+(2*i+1)*h1), (int)(yA+(2*i+1)*h2));
        }
        
        
    }
}