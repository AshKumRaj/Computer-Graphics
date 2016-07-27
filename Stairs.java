// Beams.java: Generating input files for a spiral staircase. The
//    values of n and alpha (in degrees) as well as the output 
//    file name are to be supplied as program arguments.

import java.io.*;


class Point3D
{  float x, y, z;
   Point3D(double x, double y, double z)
   {  this.x = (float)x; 
      this.y = (float)y; 
      this.z = (float)z;
   }
}
public class Stairs
{  public static void main(String[] args)throws IOException
   {  if (args.length != 3)
      {  System.out.println(
         "Supply n (> 0), alpha (in degrees)\n" +
         "and a filename as program arguments.\n");
         System.exit(1);
      } 
      int n = 0;
      double a = 0, alphaDeg = 0;
      try
      {  n = Integer.valueOf(args[0]).intValue();
         a = 6.0;
         alphaDeg = Double.valueOf(args[1]).doubleValue();
         if (n <= 0 || a < 0.5)throw new NumberFormatException();
      }  
      catch (NumberFormatException e)
      {  System.out.println("n must be an integer > 0");
         System.out.println("a must be a real number >= 0.5");
         System.out.println("alpha must be a real number");
         System.exit(1);
      }
      new Staircase(n, a, alphaDeg * Math.PI / 180, args[2]);
   } 
}

class Staircase
{  FileWriter fw;
   
   Staircase(int n, double a, double alpha, String fileName)
      throws IOException
   {  fw = new FileWriter(fileName);
      Point3D[] P = new Point3D[11];
      double b = 2.0;
      int corners = 20;
      float rInner = 0.0F, rOuter = 1.0F;
      double delta=2*Math.PI/corners;
      P[1] = new Point3D(a+1.0, -b/2, 0);
      P[2] = new Point3D(a+1.0,  b/2, 0);
      P[3] = new Point3D(1.0,  b/2, 0);
      P[4] = new Point3D(1.0, -b/2, 0);
      P[5] = new Point3D(a+1.0, -b/2, 0.2);
      P[6] = new Point3D(a+1.0, b/2, 0.2);
      P[7] = new Point3D(1.0,  b/2, 0.2);
      P[8] = new Point3D(1.0, -b/2, 0.2);
      P[9] = new Point3D(7.0, 0.0, 0.1);
      P[10] = new Point3D(7.0, 0.0, 6.0);
      for (int k=0; k<n; k++)
      {  // Beam k:
         double phi = k * alpha,
            cosPhi = Math.cos(phi), sinPhi = Math.sin(phi);
         int m = 10 * k;
         for (int i=1; i<=10; i++)
         {  double x = P[i].x, y = P[i].y;
            float x1 = (float)(x*cosPhi - y * sinPhi),
                  y1 = (float)(x*sinPhi + y * cosPhi),
                  z1 = (float)(P[i].z + k);
            fw.write((m + i) + " " + x1 + " " + y1 + " " + z1 + 
               "\r\n");
         }
      }
      for (int i=1; i<=corners; i++)
      {  double alpha2 = i * delta,
            cosa = Math.cos(alpha2), sina = Math.sin(alpha2);
         for (int inner=0; inner<2; inner++) 
         {  double r = (inner == 0 ? rOuter : rInner);
            if (r > 0) 
            for (int bottom=0; bottom<2; bottom++) 
            {  int k = (2 * inner + bottom) * corners + i;
               fw.write(String.valueOf(n*10+k+1)); // w = write, i = int, r = real
               fw.write(" " + String.valueOf((float)(r*cosa)));
               fw.write(" " + String.valueOf((float)(r*sina))); // x and y
               fw.write(" " + String.valueOf(1.25*n-1.25*n*bottom)); // bottom: z = 0; top: z = 1
               fw.write("\r\n");
            }
         }
      }
      fw.write("Faces:\r\n");
      for(int k=1;k<=corners;k++){
          int m = n*10+1;
          if(k<corners)
          fw.write(m+k+" ");
          else
              fw.write(m+k+".\r\n");
      }
      for(int k=2*corners;k>corners;k--){
          int m = n*10+1;
          if(k>corners+1)
          fw.write(m+k+" ");
          else
              fw.write(m+k+".\r\n");
      }
      
      // Vertical, rectangular faces:
      for (int i=n*10+2; i<=n*10+1+corners; i++)
      {  int j = i + 1;
         // Outer rectangle:
         if((j+corners)<n*10+2+corners*2){
         wi(j); wi(i); wi(i + corners); wi(j + corners); fw.write(".\r\n");
         }
         else{
             wi(n*10+2);wi(i);wi(i+corners);wi(n*10+2+corners);fw.write(".\r\n");
         }
         
      }
      
      
      for (int k=0; k<n; k++)
      {  // Beam k again:
         int m = 10 * k;
         face(m, 1, 2, 6, 5);
         face(m, 4, 8, 7, 3);
         face(m, 5, 6, 7, 8);
         face(m, 1, 4, 3, 2);
         face(m, 2, 3, 7, 6);
         face(m, 1, 5, 8, 4);
         
      }
      for(int k=0;k<n;k++){
          int m=10*k;
          if(k<n)
            face(m,9,10);
          if(k<n-1)
            face(m,10,20);
      }
   
      fw.close();
   }

   void face(int m, int a, int b, int c, int d)throws IOException
   {  a += m; b += m; c += m; d += m;
      fw.write(a + " " + b + " " + c + " " + d + ".\r\n");
   }
   
   void wi(int x)
      throws IOException
   {  fw.write(" " +String.valueOf(x));
   }

   void wr(double x)
      throws IOException
   {  if (Math.abs(x) < 1e-9) x = 0;
      fw.write(" " + String.valueOf((float)x));
      // float instead of double to reduce the file size
   }
   void face(int m, int a, int b)throws IOException
   {  a += m; b += m; 
      fw.write(a + " " + b + ".\r\n");
   }
   
   
}
