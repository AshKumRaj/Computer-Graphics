
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ashish
 */
public class dummy {
    public static int compareTwo(int a[], int inA, int b[], int inB ){
        int min = 2*a.length+1;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b.length;j++){
                if(a[i]==b[j]){
                    if(inA==inB)
                        if(min>(Math.abs(j-i)))
                            min=Math.abs(j-i);                    
                    else
                        if(min>(Math.abs(j-i)))
                            min=Math.abs(j-i)+a.length;
                        
                }
            }
        }
        if(min==(2*a.length+1))
            return -1;
        return min;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int A[] = new int[n];
        int a1[] = new int[n/2];
        int a2[] = new int[n-n/2];
        for(int i=0; i < n; i++){
            A[i] = in.nextInt();
            if(i<n/2)
                a1[i]=A[i];
            else
                a2[i-n/2]=A[i];
            
            }
        int c1=compareTwo(a1,1,a2,2);
        int c2=compareTwo(a1,1,a1,1);
        int c3=compareTwo(a2,2,a2,2);
        if(c1>0 && c2>0 && c3>0)
            System.out.println(Math.min(c1,Math.min(c2,c3)));
        if(c1>0 && c2>0 && c3<0)
            System.out.println(Math.min(c1,c2));
        if(c1>0 && c2<0 && c3>0)
            System.out.println(Math.min(c1,c3));
        if(c1<0 && c2>0 && c3>0)
            System.out.println(Math.min(c2,c3));
        if(c1<0 && c2<0 && c3>0)
            System.out.println(c3);
        if(c1<0 && c2<0 && c3<0)
            System.out.println(c2);
         if(c1>0 && c2<0 && c3<0)
            System.out.println(c1);
        if(c1<0 && c2<0 && c3<0)
            System.out.println("-1");
        
        
    }
}
