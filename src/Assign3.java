/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ashish
 */
/*
I have included
all the animations in Wikipedia and implemented the following additional features 
and requirements:

1) Allow the user to select the order of 1,2,3,4,or 5,
   in which the curve is defined (e.g. quadratic is order 2
   and cubic is order 3). No other order is selectable.
   Default order at 4 (cubic).

2) Provide a scale for the user to set the speed of
   animation (similar to an audio volume bar in MS Windows).
   
3) The number of points (2 endpoints + 0 or more control 
   points) defining the curve depends on the order
   selected in (1) and the points are input through mouse 
   clicks.

4) Provide an integer input box, that allows the user to
   enter an integer (i.e. n in bezier2) to define the 
   granularity of the line segments forming the curve. Default set at 100.

We have provided the delay using 
    TimeUnit.MILLISECONDS.sleep(this.getAnimSp());
in order to produce the animation effect for the Bezier 
curve drawing stages
*/
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Assign3 extends Frame {
    public static void main(String[] args) {
        // TODO code application logic here
        new Assign3();
        
    }
    
    Assign3()
   {  
       super("Click on any two places to draw line and compare the running loops of Algorithms");
      Scanner reader = new Scanner(System.in);  // Reading from System.in
      System.out.println("Enter the order of Bezier curve(1-5): ");
      int s = reader.nextInt();
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setSize(1500, 1500);
      Box box = Box.createVerticalBox();
      add(box);
      CvAssign3 cv = new CvAssign3(s); 
      box.add(cv);
      JSlider js = new JSlider(JSlider.HORIZONTAL,0,100,95);
      js.setMinorTickSpacing(2);
      js.setMajorTickSpacing(10);
      js.setPaintTicks(true);
      js.setPaintLabels(true);   
      js.setLabelTable(js.createStandardLabels(10));
      js.setPreferredSize(new Dimension(200,50));
      box.add(js);
      js.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            cv.setAnimationSpeed(js.getValue());
        }
    }); 
      JPanel jp = new JPanel();
      box.add(jp);
      JTextField jt = new JTextField(10);
      JButton button = new JButton("Change the granularity : n");
      button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent event) {
            // Set word2 with the input string from the textfield
            cv.setN(Integer.parseInt(jt.getText()));
        }
    });
      jp.add(jt);
      jp.add(button);
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      show();
   }    
}

class CvAssign3 extends Canvas {
    Point2D[] p;
   int np = 0, centerX, centerY, size, animSp=5, n=100;

    public int getN() {
        return n;
    }

    public int getAnimSp() {
        return animSp;
    }
   float rWidth = 10.0F, rHeight = 7.5F, eps = rWidth/100F, pixelSize;
   
   CvAssign3(int s)
   {    this.p = new Point2D[s+1];
   size = s+1;
addMouseListener(new MouseAdapter()
      {  public void mousePressed(MouseEvent evt)
         {  float x = fx(evt.getX()), y = fy(evt.getY());
            p[np++] = new Point2D(x, y);
            repaint();
         }
      });
   }

   void initgr()  
   {  Dimension d = getSize();
      int maxX = d.width - 1, maxY = d.height - 1;
      pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
      centerX = maxX/2; centerY = maxY/2;
   }

   int iX(float x){return Math.round(centerX + x/pixelSize);}
   int iY(float y){return Math.round(centerY - y/pixelSize);}
   float fx(int x){return (x - centerX) * pixelSize;}
   float fy(int y){return (centerY - y) * pixelSize;}   
   Point2D middle(Point2D a, Point2D b)
   {  return new Point2D((a.x + b.x)/2, (a.y + b.y)/2);
   }
   
   void bezier(Graphics g, Point2D[] p) throws InterruptedException
   {  
   switch(p.length){
       case 2: bezierLinear(g,p);
           break;
       case 3: bezierQuadratic(g,p);
           break;
           
       case 4: bezierCubic(g,p);           
                break;
       case 5: bezierQuartic(g,p);
           break;
       case 6: bezierquartite(g,p);
           break;    
    default:        break;    
   }
   
   }

   public void paint(Graphics g)
   {  initgr();
      int left = iX(-rWidth/2), right = iX(rWidth/2),
          bottom = iY(-rHeight/2), top = iY(rHeight/2);
      g.drawRect(left, top, right - left, bottom - top);
       for (int i=0; i<np; i++)
      {  // Show tiny rectangle around point:
         g.drawRect(iX(p[i].x)-2, iY(p[i].y)-2, 4, 4);
         if (i > 0) 
            // Draw line p[i-1]p[i]:
            g.drawLine(iX(p[i-1].x), iY(p[i-1].y),
                       iX(p[i].x), iY(p[i].y));
      }
      if (np == size) try {
          
          bezier(g, p);
          repaint();
          
          
   } catch (InterruptedException ex) {
       Logger.getLogger(CvAssign3.class.getName()).log(Level.SEVERE, null, ex);
   }
   }

    private void bezierCubic(Graphics g, Point2D[] p) throws InterruptedException {
        float dt = 1.0F/this.getN();
        Graphics2D g2d = (Graphics2D) g;   
        float   cx3 = -p[0].x+3*(p[1].x-p[2].x)+p[3].x,
           cy3=  -p[0].y+3*(p[1].y-p[2].y)+p[3].y, 
           cx2 = 3*(p[0].x-2*p[1].x+p[2].x),
           cy2 = 3*(p[0].y-2*p[1].y+p[2].y),
           cx1 = 3*(p[1].x-p[0].x),
           cy1 = 3*(p[1].y-p[0].y),
           cx0 = p[0].x, cy0 = p[0].y, x = p[0].x, y=p[0].y,x0,y0;
           
   
           for(int i=1;i<=this.getN();i++){
           float t=i*dt;
           float x1 =  p[0].x+t*(p[1].x-p[0].x),
               y1 = p[0].y+t*(p[1].y-p[0].y),
               x2 = p[1].x+t*(p[2].x-p[1].x),
               y2 = p[1].y+t*(p[2].y-p[1].y),
               x3 = p[2].x+t*(p[3].x-p[2].x),
               y3 = p[2].y+t*(p[3].y-p[2].y);
       float x11 = x1+t*(x2-x1),
               y11=y1+t*(y2-y1),
               x22 = x2+t*(x3-x2),
               y22= y2+t*(y3-y2);
       x0=x;y0=y;
       x=((cx3*t+cx2)*t+cx1)*t+cx0;
       y=((cy3*t+cy2)*t+cy1)*t+cy0;
       
       TimeUnit.MILLISECONDS.sleep(this.getAnimSp());
       g.setColor(Color.LIGHT_GRAY);
       g2d.setStroke(new BasicStroke(2f));       
       for (int h=0; h<np; h++)
      {  // Show tiny rectangle around point:
         
         if (h > 0) 
            // Draw line p[i-1]p[i]:
            g.drawLine(iX(p[h-1].x), iY(p[h-1].y),
                       iX(p[h].x), iY(p[h].y));
      }
       g.setColor(Color.green);
       g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
       g.drawLine(iX(x2), iY(y2), iX(x3), iY(y3));
       g.setColor(Color.blue);
       g.drawLine(iX(x11),iY(y11),iX(x22),iY(y22));
       g.setColor(getBackground());
       TimeUnit.MILLISECONDS.sleep(this.getAnimSp());
       g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
       g.drawLine(iX(x2), iY(y2), iX(x3), iY(y3));
       g.drawLine(iX(x11),iY(y11),iX(x22),iY(y22));
       g.setColor(Color.red);
       g2d.setStroke(new BasicStroke(6f));
       g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
       
      
   }
    }

    private void bezierLinear(Graphics g, Point2D[] p) throws InterruptedException {
        float dt = 1.0F/this.getN();
        Graphics2D g2d = (Graphics2D) g;   
        float cx0 = p[0].x, cy0 = p[0].y,
                cx1 = p[1].x-p[0].x, cy1= p[1].y-p[0].y,x=p[0].x,y=p[0].y,x0,y0;
        for(int i=1;i<=this.getN();i++){
           float t=i*dt;
           x0=x;y0=y;
           x=cx1*t+cx0;
           y=cy1*t+cy0;
           TimeUnit.MILLISECONDS.sleep(this.getAnimSp());
           g.setColor(Color.red);
           g2d.setStroke(new BasicStroke(4f));
           g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
           
        } 
    }

    private void bezierQuadratic(Graphics g, Point2D[] p) throws InterruptedException {
        float dt = 1.0F/this.getN();
        Graphics2D g2d = (Graphics2D) g;   
        Stroke stroke = new BasicStroke(2f);
        float cx0 = p[0].x, cy0=p[0].y,
                cx1=2*p[1].x-2*p[0].x, cy1 = 2*p[1].y-2*p[0].y,
                cx2 = p[0].x-2*p[1].x+p[2].x, cy2 = p[0].y-2*p[1].y+p[2].y, x=p[0].x,y=p[0].y,x0,y0;
        for(int i=1;i<=this.getN();i++){
           float t=i*dt;
           float x1 =  p[0].x+t*(p[1].x-p[0].x),
               y1 = p[0].y+t*(p[1].y-p[0].y),
               x2 = p[1].x+t*(p[2].x-p[1].x),
               y2 = p[1].y+t*(p[2].y-p[1].y);
           x0=x;y0=y;
           x=(cx2*t+cx1)*t+cx0;
           y=(cy2*t+cy1)*t+cy0;
           TimeUnit.MILLISECONDS.sleep(5);
           g.setColor(Color.LIGHT_GRAY);
       g2d.setStroke(new BasicStroke(2f));       
       for (int h=0; h<np; h++)
      {  // Show tiny rectangle around point:
         
         if (h > 0) 
            // Draw line p[i-1]p[i]:
            g.drawLine(iX(p[h-1].x), iY(p[h-1].y),
                       iX(p[h].x), iY(p[h].y));
      }
           g2d.setStroke(stroke);
           g.setColor(Color.green);
           g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
           g.setColor(getBackground());
       TimeUnit.MILLISECONDS.sleep(5);
       g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
       g.setColor(Color.red);
           g2d.setStroke(new BasicStroke(4f));
           g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
           
        } 
    }

    private void bezierQuartic(Graphics g, Point2D[] p) throws InterruptedException {
        float dt = 1.0F/this.getN();
        Graphics2D g2d = (Graphics2D) g;   
        float cx0 = p[0].x, cy0=p[0].y,
                cx1=4*(p[1].x-p[0].x),cy1=4*(p[1].y-p[0].y),
                cx2=6*(p[0].x-2*p[1].x+p[2].x),cy2=6*(p[0].y-2*p[1].y+p[2].y),
                cx3=4*(-p[0].x+3*p[1].x-3*p[2].x+p[3].x),cy3=4*(-p[0].y+3*p[1].y-3*p[2].y+p[3].y),
                cx4=p[0].x-4*p[1].x+6*p[2].x-4*p[3].x+p[4].x,cy4=p[0].y-4*p[1].y+6*p[2].y-4*p[3].y+p[4].y,
                x=p[0].x,y=p[0].y,x0,y0;
        for(int i=1;i<=this.getN();i++){
           float t=i*dt;
           x0=x;y0=y;
           float x1 =  p[0].x+t*(p[1].x-p[0].x),
               y1 = p[0].y+t*(p[1].y-p[0].y),
               x2 = p[1].x+t*(p[2].x-p[1].x),
               y2 = p[1].y+t*(p[2].y-p[1].y),
               x3 = p[2].x+t*(p[3].x-p[2].x),
               y3 = p[2].y+t*(p[3].y-p[2].y),
               x4 = p[3].x+t*(p[4].x-p[3].x),
               y4 = p[3].y+t*(p[4].y-p[3].y);
           float x11 = x1+t*(x2-x1),
               y11=y1+t*(y2-y1),
               x22 = x2+t*(x3-x2),
               y22= y2+t*(y3-y2),
               x33 =  x3+t*(x4-x3),
               y33= y3+t*(y4-y3); 
           float x111 = x11+t*(x22-x11),
               y111=y11+t*(y22-y11),
               x222 = x22+t*(x33-x22),
               y222= y22+t*(y33-y22);
           x=(((cx4*t+cx3)*t+cx2)*t+cx1)*t+cx0;
           y=(((cy4*t+cy3)*t+cy2)*t+cy1)*t+cy0;
           TimeUnit.MILLISECONDS.sleep(this.animSp);
           g.setColor(Color.LIGHT_GRAY);
       g2d.setStroke(new BasicStroke(2f));       
       for (int h=0; h<np; h++)
      {  // Show tiny rectangle around point:
         
         if (h > 0) 
            // Draw line p[i-1]p[i]:
            g.drawLine(iX(p[h-1].x), iY(p[h-1].y),
                       iX(p[h].x), iY(p[h].y));
      }
           g2d.setStroke(new BasicStroke(3f));
       g.setColor(Color.green);
       g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
       g.drawLine(iX(x2), iY(y2), iX(x3), iY(y3));
       g.drawLine(iX(x3), iY(y3), iX(x4), iY(y4));
       g.setColor(Color.blue);
       g.drawLine(iX(x11),iY(y11),iX(x22),iY(y22));
       g.drawLine(iX(x22),iY(y22),iX(x33),iY(y33));
       g.setColor(Color.MAGENTA);
       g.drawLine(iX(x111),iY(y111),iX(x222),iY(y222));
       g.setColor(getBackground());
       TimeUnit.MILLISECONDS.sleep(this.animSp);
       g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
       g.drawLine(iX(x2), iY(y2), iX(x3), iY(y3));
       g.drawLine(iX(x3), iY(y3), iX(x4), iY(y4));
       g.drawLine(iX(x11),iY(y11),iX(x22),iY(y22));
       g.drawLine(iX(x22),iY(y22),iX(x33),iY(y33));
       g.drawLine(iX(x111),iY(y111),iX(x222),iY(y222));
       g.setColor(Color.red);
       g2d.setStroke(new BasicStroke(6f));
       g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
        }
    }

    private void bezierquartite(Graphics g, Point2D[] p) throws InterruptedException {
        float dt = 1.0F/this.getN();
        Graphics2D g2d = (Graphics2D) g;   
        float cx0 = p[0].x, cy0=p[0].y,
                cx1=5*(p[1].x-p[0].x),cy1=5*(p[1].y-p[0].y),
                cx2=10*(p[0].x-2*p[1].x+p[2].x),cy2=10*(p[0].y-2*p[1].y+p[2].y),
                cx3=10*(-p[0].x+3*p[1].x-3*p[2].x+p[3].x),cy3=10*(-p[0].y+3*p[1].y-3*p[2].y+p[3].y),
                cx4=5*(p[0].x-4*p[1].x+6*p[2].x-4*p[3].x+p[4].x),cy4=5*(p[0].y-4*p[1].y+6*p[2].y-4*p[3].y+p[4].y),
                cx5=(-p[0].x+5*p[1].x-10*p[2].x+10*p[3].x-5*p[4].x+p[5].x),
                cy5=(-p[0].y+5*p[1].y-10*p[2].y+10*p[3].y-5*p[4].y+p[5].y),
                x=p[0].x,y=p[0].y,x0,y0;
        for(int i=1;i<=this.getN();i++){
           float t=i*dt;
           x0=x;y0=y;
           float x1 =  p[0].x+t*(p[1].x-p[0].x),
               y1 = p[0].y+t*(p[1].y-p[0].y),
               x2 = p[1].x+t*(p[2].x-p[1].x),
               y2 = p[1].y+t*(p[2].y-p[1].y),
               x3 = p[2].x+t*(p[3].x-p[2].x),
               y3 = p[2].y+t*(p[3].y-p[2].y),
               x4 = p[3].x+t*(p[4].x-p[3].x),
               y4 = p[3].y+t*(p[4].y-p[3].y),
               x5 = p[4].x+t*(p[5].x-p[4].x),
               y5 = p[4].y+t*(p[5].y-p[4].y);    
           float x11 = x1+t*(x2-x1),
               y11=y1+t*(y2-y1),
               x22 = x2+t*(x3-x2),
               y22= y2+t*(y3-y2),
               x33 =  x3+t*(x4-x3),
               y33= y3+t*(y4-y3),
                   x44 =  x4+t*(x5-x4),
               y44= y4+t*(y5-y4); 
           float x111 = x11+t*(x22-x11),
               y111=y11+t*(y22-y11),
               x222 = x22+t*(x33-x22),
               y222= y22+t*(y33-y22),
                   x333 = x33+t*(x44-x33),
               y333= y33+t*(y44-y33);
           float x1111 = x111+t*(x222-x111),
               y1111=y111+t*(y222-y111),
               x2222 = x222+t*(x333-x222),
               y2222= y222+t*(y333-y222);
           x=((((cx5*t+cx4)*t+cx3)*t+cx2)*t+cx1)*t+cx0;
           y=((((cy5*t+cy4)*t+cy3)*t+cy2)*t+cy1)*t+cy0;
           TimeUnit.MILLISECONDS.sleep(this.animSp);
           g.setColor(Color.LIGHT_GRAY);
       g2d.setStroke(new BasicStroke(2f));       
       for (int h=0; h<np; h++)
      {  // Show tiny rectangle around point:
         
         if (h > 0) 
            // Draw line p[i-1]p[i]:
            g.drawLine(iX(p[h-1].x), iY(p[h-1].y),
                       iX(p[h].x), iY(p[h].y));
      }
           g2d.setStroke(new BasicStroke(2f));
       g.setColor(Color.green);
       g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
       g.drawLine(iX(x2), iY(y2), iX(x3), iY(y3));
       g.drawLine(iX(x3), iY(y3), iX(x4), iY(y4));
       g.drawLine(iX(x4), iY(y4), iX(x5), iY(y5));
       g.setColor(Color.blue);
       g.drawLine(iX(x11),iY(y11),iX(x22),iY(y22));
       g.drawLine(iX(x22),iY(y22),iX(x33),iY(y33));
       g.drawLine(iX(x33),iY(y33),iX(x44),iY(y44));
       g.setColor(Color.MAGENTA);
       g.drawLine(iX(x111),iY(y111),iX(x222),iY(y222));
       g.drawLine(iX(x222),iY(y222),iX(x333),iY(y333));
       g.setColor(Color.ORANGE);
       g.drawLine(iX(x1111),iY(y1111),iX(x2222),iY(y2222));
       g.setColor(getBackground());
       TimeUnit.MILLISECONDS.sleep(this.animSp);
       if(  !g.getColor().equals("red")){
           g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
           g.drawLine(iX(x2), iY(y2), iX(x3), iY(y3));
           g.drawLine(iX(x3), iY(y3), iX(x4), iY(y4));
           g.drawLine(iX(x4), iY(y4), iX(x5), iY(y5));
           g.drawLine(iX(x11),iY(y11),iX(x22),iY(y22));
           g.drawLine(iX(x22),iY(y22),iX(x33),iY(y33));
           g.drawLine(iX(x33),iY(y33),iX(x44),iY(y44));
           g.drawLine(iX(x111),iY(y111),iX(x222),iY(y222));
           g.drawLine(iX(x222),iY(y222),iX(x333),iY(y333));
           g.drawLine(iX(x1111),iY(y1111),iX(x2222),iY(y2222));
       } 
       g.setColor(Color.red);
       g2d.setStroke(new BasicStroke(6f));
       g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
        }
    }

    void setAnimationSpeed(int value) {
        this.animSp = 100-value;
    }

    void setN(int text) {
        n = text;
    }

    
}

    

