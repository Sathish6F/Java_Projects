import java.util.Scanner;

public class Main{
        
          private static Scanner sc = MyScanner.getScanner();
          private static StudentHandler handler = StudentHandler.getHandler();
          
          
          public static void main(String[] args){
                    boolean flag = true;
                    while(flag){
                               try{
                                        System.out.println("1.Add Student\n2.View All Students\n3.Upload to File\n4.View Based on subMarks\n5.Move To another Folder\n6.Exit");
                                        int choice = sc.nextInt();
                                        if(choice <= 0 || choice >6){
                                                  throw new IllegalArgumentException("Invalid Choice");
                                        } 
                                        switch(choice){
                                                  case 1:
                                                            handler.addStudent();
                                                            break;
                                                  case 2:
                                                            handler.viewAllStudents();
                                                            break;
                                                  case 3:
                                                            handler.uploadToFile();
                                                            break;     
                                                  case 4:
                                                            handler.subView();
                                                            break;   
                                                  case 5:
                                                            System.out.println("Enter the folder Path To move the file:");
                                                            String des = sc.next();
                                                            System.out.println("Enter the File Name:");
                                                            String file = sc.next();
                                                            handler.copyToFolder(des,file);      
                                                            break;               
                                                  case 6:
                                                            flag = false;
                                                            break;               
                                        }
                              }catch(IllegalArgumentException e){
                                        System.out.println(e.getMessage());
                              }  
                    }
          }   
}
