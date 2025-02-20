import java.util.*;
import java.io.*;

public class StudentHandler{
          /**
          * This Class is responsible for adding new Students and managing the file related
          * activities such as uplodaing a file and move file from one location to another.
          * Also it shows the students according to the customoized  view 
          **/
          
          /**
          * student_Data holds the student objects based on their ID number that is in
          * an ascending order
          **/
          private static TreeSet<Student> student_Data = new TreeSet<>();
          /**
          * This file object represents the .txt file where the student data is stored
          * in text format
          **/
          static File file = new File("Students_Data.txt");
          /**
          * This csvFile object represents the .csv file where the student data is stored
          * in Comma Seperated Values  format
          **/
          static File csvFile = new File("Student.csv");
          /**
          *  This handler object  is used to access this class methods and prevent from 
          *  un nessesary object creatoins which makes this class as 'SingleTon Class'
          **/
          private static final StudentHandler handler = null;
          /**
          * The MyScanner class's getScanner() method returns the singleton object 
          * for all scanner related activites like reading the data from the user
          **/
          private static Scanner sc = MyScanner.getScanner();
          /**
          * sub_List stores the Subject wise ranking order of the students
          * it Maps the subjects with their specific rank order(Descending Oder)
          **/
          static Map<String,TreeSet<Student>> sub_List = new HashMap<>();
          /**
          * The sole private constructor which makes this class as a singleton class
          * It restricts other classes to create the object of this class, from outside of this class
          **/
          private StudentHandler (){}
          /**
          * getHandler() a static method returns the single instance of this class
          * which remains same for all the users only one object is created for this class at 
          * any given instance
          **/
          public static StudentHandler getHandler(){
                    if(handler == null){
                              return new StudentHandler();
                    }
                    return handler;
          }
          
          /**
          * A public void addStudent() method which takes no args, It gets the inputs from
          * the user to create a new Student object, If the entered student id is already present in 
          * out student_Data then it throws an IllegalArgumentException() with the message of
          * "Student Id Already exisits". If the Id is not there , then it class another method getMarks()
          *  then the student object is created and added to the student_Data
          **/
          public void addStudent(){
                try{    
                    System.out.println("Enter Student's First name:");
                    String fname = sc.next();
                    System.out.println("Enter Student's Last name:");
                    String lname = sc.next();
                    String name = fname+lname;
                    System.out.println("Enter Student ID:");
                    int id = sc.nextInt();
                    System.out.println("Enter Student Age:");
                    int age = sc.nextInt();
                    if(id < 0 || age < 0){
                              throw new IllegalArgumentException("Negative values Found....");
                    }
                              Map<String,Integer> marks_Data = getMarks();
                              if(marks_Data == null){
                                        System.out.println("Can't Add....");
                                        return;
                              }
                              Student s = new Student(name,id,age,marks_Data);
                               if(student_Data.add(s))
                                        System.out.println("Student Added.....");
                               else
                                        throw new IllegalArgumentException("Student ID Already Exists... Please Choose valid ID");
                    }
                    catch(IllegalArgumentException e){
                              System.out.println(e.getMessage());
                    } 
          }
          
          /**
          * public Map<String,Integer> getMarks() method takes no args but return a  Map<String,Integer>
          * which stores the all subject marks of the student,If any of the Subject marks is less than zero or
          * greater than 100 will throw an exception and return a null value.
          **/
          public Map<String,Integer> getMarks(){
          
                    Map<String,Integer> marks = new HashMap<>();
                    try{
                    System.out.println("Enter Tamil Marks:");
                    int tMark = sc.nextInt();
                    marks.put("Tamil",tMark);
                    if(tMark < 0 || tMark > 100){
                              throw new IllegalArgumentException("Invalid Tamil Mark");
                    }
                    System.out.println("Enter English Marks:");
                    int eMark = sc.nextInt();
                    marks.put("English",eMark);
                     if(eMark < 0 || eMark > 100){
                              throw new IllegalArgumentException("Invalid English Mark");
                    }
                     System.out.println("Enter Science Marks:");
                    int sMark = sc.nextInt();
                    marks.put("Science",sMark);
                     if(sMark < 0 || sMark > 100){
                              throw new IllegalArgumentException("Invalid Science Mark");
                    }
                     System.out.println("Enter Maths Marks:");
                    int mMark = sc.nextInt();
                    marks.put("Maths",mMark);
                     if(mMark < 0 || mMark > 100){
                              throw new IllegalArgumentException("Invalid Maths Mark");
                    }
                     System.out.println("Enter Social Science Marks:");
                    int ssMark = sc.nextInt();
                    marks.put("Social Science",ssMark);
                     if(ssMark < 0 || ssMark > 100){
                              throw new IllegalArgumentException("Invalid Social Science Mark");
                    }
                    }
                    catch(IllegalArgumentException e){
                              System.out.println(e.getMessage());
                              return null;
                    }
                    return marks;
                   
          
          }
          
          /**
          * viewAllStudents() method takes no args return nothing it promts the used to choose the options
          * for the view either based on Id or Based on totalmarks or by Alphabetical order of their name
          **/
          public void viewAllStudents(){
                    try{
                              System.out.println("Choose the options\n1.Based On ID\n2.Based On Total Marks\n3.Based On Alphabetical Order");
                              int choice = sc.nextInt();
                              if(choice < 0 || choice > 3){
                                        throw new IllegalArgumentException("Invalid Option");
                              }
                              switch(choice){
                                        case 1:
                                        System.out.println("All Students Detatils");
                                        System.out.println("-------------------------------------");
                                        System.out.println("Id\t|Name\t\t|TotalMarks |");
                                        System.out.println("--------+---------------+------------");
                                        for(Student s : student_Data){
                                                  System.out.println(s.getId()+"\t|"+s.getName()+"\t|"+s.getTotalMarks()+"\t    |");
                                                  System.out.println("--------+---------------+------------");
                                        }
                                        break;
                                        case 2:
                                                  compareTotals();
                                                  break;
                                        case 3:
                                                  compareNames();
                                                  break;          
                              }
                    }catch(IllegalArgumentException e){
                              System.out.println(e.getMessage());
                    }  
          }
          
          /**
          * It is a private no args method internally called by viewAllStudents(), when the user choose to view the
          * student details based on the student's total marks in a descending order. This methos uses the 
          * Student.totalComp comparator object for sorting the student objects 
          **/
          private void compareTotals(){
          
                    TreeSet<Student> s = new TreeSet(Student.totalComp);
                    for(Student s1: student_Data){
                              s.add(s1);
                    }
                    System.out.println("-------------------------------------");
                    System.out.println("Id\t|Name\t\t|TotalMarks |");
                    System.out.println("--------+---------------+------------");
                    for(Student stu : s){
                              System.out.println(stu.getId()+"\t|"+stu.getName()+"\t|"+stu.getTotalMarks()+"\t    |");
                              System.out.println("--------+---------------+------------");
                    }
          }
          
           /**
          * It is a private no args method internally called by viewAllStudents(), when the user choose to view the
          * student details based on the student's names in alphabetical order. This methos uses the 
          * Student.nameComp comparator object for sorting the student objects 
          **/
           private  void compareNames(){
          
                    TreeSet<Student> s = new TreeSet(Student.nameComp);
                    for(Student s1: student_Data){
                              s.add(s1);
                    }
                    System.out.println("-------------------------------------");
                    System.out.println("Id\t|Name\t\t|TotalMarks |");
                    System.out.println("--------+---------------+------------");
                    for(Student stu : s){
                              System.out.println(stu.getId()+"\t|"+stu.getName()+"\t|"+stu.getTotalMarks()+"\t    |");
                              System.out.println("--------+---------------+------------");
                    }
          }
          
          /**
          * This method takes no args and returns nothing , this method asks the user to upload the students data to
          * a text file or to the csv file
          **/
          public void uploadToFile(){
                    
                    System.out.println("Choose the  file format you want:");
                    System.out.println("1.Text file\n2.CSV file");
                    try{
                               int choice = sc.nextInt();
                               if(choice <= 0  || choice >2){
                                        throw new IllegalArgumentException("Invalid Choice");
                               }
                               switch(choice){
                                        case 1:
                                                  saveAsTxt();
                                                  break;
                                        case 2:
                                                  saveAsCsv();
                                                  break;
                               }
                    
                    }catch(InputMismatchException | IllegalArgumentException e){
                              System.out.println(e.getMessage());
                    }
                    
          }
          
          /**
          * It is a private method used by uploadToFile() to upload a file as text file,
          * that is in a .txt format, It uses the PrintWriter object for writing the text data to the file.
          * Initially  the default File for storing the data as text file is decalred  as  "file"
          **/
          private void saveAsTxt(){
                    try(PrintWriter writer = new PrintWriter(new FileWriter(file,true))){
                              for(Student s: student_Data){
                                        writer.println(s.toString());
                              }
                              System.out.println("File Uploaded as Test file......");
                    }catch(IOException e){
                              System.out.println(e.getMessage());
                    }
          }
          
         /**
          * It is a private method used by uploadToFile() to upload a file as csv file,
          * that is in a .csv format, It uses the PrintWriter object for writing the csv formated string of 
          * the student objects to the file.
          * Initially  the default File for storing the data as csv file is decalred  as  "csvFile"
          * the first line of this csv file denotes the colomn names
          **/         
          private void saveAsCsv(){
                    try(PrintWriter writer = new PrintWriter(new FileWriter(csvFile,true))){
                               if(!csvFile.exists()){
                                         csvFile.createNewFile();
                                         writer.println("ID.No,StudentName,TotalMarks");
                               }else {
                                        for(Student s: student_Data){
                                                  String[] words = s.toString().split("\\W+");
                                                  String csvString = words[0]+","+words[1]+","+words[2];
                                                  writer.println(csvString);
                                        }
                               }
                               System.out.println("File Uploaded as CSV file......");
                    }catch(IOException e){
                              System.out.println(e.getMessage());
                    }
          }
          
          /**
          * subView() takes no args and returns nothing and provides the Subject wise students rank ordering
          * by using the Student class's subjects comparator such as tamilSubComp and so on...
          **/
          public void subView(){

                   try{
                              System.out.println("Choose the subject\n1.Tamil\n2.English\n3.Maths\n4.Science\n5.Social Science");
                              int choice = sc.nextInt();
                              if(choice <= 0 || choice > 5){
                                        throw new IllegalArgumentException("Invlaid Choice....");
                              }
                              switch(choice){
                                        case 1:
                                                  subjectComparator("Tamil",Student.tamilSubComp);
                                                  break;
                                        case 2:
                                                  subjectComparator("English",Student.englishSubComp);
                                                  break; 
                                        case 3:
                                                  subjectComparator("Maths",Student.mathSubComp);
                                                  break;
                                        case 4:
                                                  subjectComparator("Science",Student.scienceSubComp);
                                                  break;   
                                        case 5:
                                                  subjectComparator("Social Science",Student.socialSubComp);
                                                  break;                       
                              }
                   }
                   catch(IllegalArgumentException e){
                              System.out.println(e.getMessage());
                   }
          }
          
          /**
          * This subjectComparator takes two parameters
          * @ subName  a String which represents the subject name
          * @ comp        a Comparator object which represents in which subject wise the comparison will be done
          * after comapring the students by the given sub name this method prints their details
          **/
          public void subjectComparator(String subName,Comparator comp){
                    if(! sub_List.containsKey(subName)){
                              sub_List.put(subName,new TreeSet(comp));
                    }
                    for(Student s : student_Data){
                              TreeSet<Student> set = sub_List.get(subName);
                              set.add(s);
                              sub_List.put(subName,set);
                    }
                    System.out.println("Students List Based on "+subName+" Marks");
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Id\t|Name\t\t|TotalMarks\t |"+subName);
                    System.out.println("--------+---------------+----------------+---------------");
                    for(Student s : sub_List.get(subName)){
                               System.out.println(s.getId()+"\t|"+s.getName()+"\t|"+s.getTotalMarks()+"\t         |"+s.getMarksList().get(subName)+"            |");
                               System.out.println("--------+---------------+----------------+---------------");
                    }
                   
          }
          
          /**
          * This copyToFolder(String des,String file) takes two parameters
          * @ des   a String that represents the destination folder path
          * @ file    a String that represents the file present in the destination folder
          * If the destination folder is not exists then this method will creates a new directory by using mkdir()
          * and places the file inside that folder similarly if the destination file is not there then it creates a new file
          * by using createNewFile() method
          **/
          public void copyToFolder(String des,String file){
          
                    try{
                              BufferedReader reader = new BufferedReader(new FileReader(this.file));
                              File desFolder = new File(des);
                              if(!desFolder.exists()){
                                        desFolder.mkdir();
                              }
                              String name = des+"/"+file;
                              File fin = new File(name);
                              if(!fin.exists()){
                                        fin.createNewFile();
                              }
                              PrintWriter writer = new PrintWriter(new FileWriter(fin,true));
                              String line = reader.readLine();
                              while(line != null){
                                        writer.println(line);
                                        line = reader.readLine();
                              }
                              System.out.println("File SuccessFully Moved.....");
                              System.out.println("------------------------------------------------------------------");
                              reader.close();
                              writer.close();
                    }
                    catch(IOException e){
                              System.out.println(e.getMessage());
                    }
          }
          
          
}
