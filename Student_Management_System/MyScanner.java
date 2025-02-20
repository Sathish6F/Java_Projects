import java.util.Scanner;

public class MyScanner{
          private static final Scanner sc = null;
          private  MyScanner(){}
          
          public static Scanner getScanner(){
                    if(sc == null){
                              return new Scanner(System.in);
                    }
                    return sc;
          }
}
