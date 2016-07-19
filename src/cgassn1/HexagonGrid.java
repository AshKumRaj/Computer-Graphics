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

public class HexagonGrid extends Frame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new HexagonGrid();
    }
    
    HexagonGrid()
   {  super("dfdf");
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setSize(400, 300);
      add("Center", new HexagonGrid1());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      show();
   }
}

class HexagonGrid1 extends Canvas{
    double centerX, centerY;
        public HexagonGrid1() {
        }
        
        int iX(float x){return (int) Math.round(centerX + x);}
        int iY(float y){return (int) Math.round(centerY - y);}
        
        public void paint(Graphics g){
            Dimension d = getSize();
            int maxX = d.width - 1, maxY = d.height - 1, r = maxX/30;
            centerX = maxX/2; centerY = maxY/2;
            double y = Math.sqrt(3d)/2 * r;
            for(int i=-5;i<=5;i++){//for x
                for(int j=-5;j<5;j++){//for y
                    g.drawLine(iX(0), iY(0), iX(r/2), iY((float) y));
                    g.drawLine(iX(0), iY(0), iX(r/2), iY((float) -y));
                    g.drawLine(iX(r/2), iY((float) y), iX(3*r/2), iY((float) y));
                    g.drawLine(iX(3*r/2), iY((float) y), iX(2*r), iY(0));
                    g.drawLine(iX(2*r), iY(0), iX(3*r/2), iY((float) -y));
                    g.drawLine(iX(3*r/2), iY((float) -y), iX(r/2), iY((float) -y));
                    g.drawLine(iX(2*r), iY(0), iX(3*r), iY(0));
                    centerY = maxY/2+2*j*y;
                }
                centerX = maxX/2+3*r*i; 
            }           
        }
}