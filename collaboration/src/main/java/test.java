import java.io.*;
import java.util.Scanner;

public class test {
   public static void main (String args[]) throws IOException{
//       char c;
//       c = (char) System.in.read();
//       String result[] = new String[100];
//       int i = 0;
//       while(c != '?'){
//           if(c == 'A'){
//               result[i] = "90--100";
//           }
//           else if(c == 'B'){
//               result[i] = "80--89";
//           }
//           else if(c == 'C'){
//               result[i] = "70--79";
//           }
//           else if(c == 'D'){
//               result[i] = "60--69";
//           }
//           else if(c == 'E'){
//               result[i] = "0-59";
//           }
//           else{
//               result[i] = "Invalid input";
//           }
//
//           System.out.println(result[i]);
//           i++;
//           c = (char) System.in.read();
//       }

       int repeat,ri;
       int a,b,c,d;
       double x,x1,x2;
       Scanner in = new Scanner(System.in);
       repeat = in.nextInt();
       for(ri=1;ri<=repeat;ri++){
           a = in.nextInt();
           b = in.nextInt();
           c = in.nextInt();

           if(b*b - 4*a*c < 0){

               System.out.println("方程无实数解！");
           }
           else if(b == 0 && c == 0 && a == 0){
               System.out.println("参数都为零，方程无意义！");
           }
           else if(b == 0 && c != 0 && a == 0){
               System.out.println("a和b为零，c不为零，方程不成立！");
           }
           else if(a == 0){
               x = (double)(-1)*c/b;
               System.out.println("x="+Math.round(x*100)/100.0);
           }
           else{
               int temp = b*b-4*a*c;
               double r1 = (-1)*b+Math.sqrt(temp);
               double r2 = (-1)*b-Math.sqrt(temp);
               x1 = r1/(2*a);
               x2 = r2/(2*a);

               if(x1 == x2){
                   System.out.println("x="+Math.round(x1*100)/100.0);
               }
               else{
                   System.out.println("x1="+Math.round(x1*100)/100.0);
                   System.out.println("x2="+Math.round(x2*100)/100.0);
               }
           }
       }
   }
}
