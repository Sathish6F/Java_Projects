import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
public class Student implements Comparable<Student>{
              
          private String name;
          private int id;
          private int age;
          private int totalMarks;
          
          private Map<String,Integer> marks_Data = new HashMap<>();
          
          public static Comparator<Student> totalComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                              if(s1.getTotalMarks() == s2.getTotalMarks()){
                                        return Integer.compare(s1.getId(),s2.getId());
                              }
                              return Integer.compare(s2.getTotalMarks(),s1.getTotalMarks());
                    }
          };
          
           public static Comparator<Student> nameComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                              return s1.getName().compareTo(s2.getName());
                    }
          };
          
          public static Comparator<Student> tamilSubComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                             int m1 = s1.getMarksList().get("Tamil");
                             int m2 = s2.getMarksList().get("Tamil");
                             if(m1 == m2){
                                   return Integer.compare(s1.getId(),s2.getId());
                             }
                             return Integer.compare(m2,m1);
                    }
          };
          
           public static Comparator<Student> englishSubComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                             int m1 = s1.getMarksList().get("English");
                             int m2 = s2.getMarksList().get("English");
                              if(m1 == m2){
                                   return Integer.compare(s1.getId(),s2.getId());
                             }
                             return Integer.compare(m2,m1);
                    }
          };
          public static Comparator<Student> mathSubComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                             int m1 = s1.getMarksList().get("Maths");
                             int m2 = s2.getMarksList().get("Maths");
                              if(m1 == m2){
                                   return Integer.compare(s1.getId(),s2.getId());
                             }
                             return Integer.compare(m2,m1);
                    }
          };
          public static Comparator<Student> scienceSubComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                             int m1 = s1.getMarksList().get("Science");
                             int m2 = s2.getMarksList().get("Science");
                              if(m1 == m2){
                                   return Integer.compare(s1.getId(),s2.getId());
                             }
                             return Integer.compare(m2,m1);
                    }
          };
          public static Comparator<Student> socialSubComp = new Comparator<Student>(){
                    public int compare(Student s1,Student s2){
                             int m1 = s1.getMarksList().get("Social Science");
                             int m2 = s2.getMarksList().get("Social Science");
                              if(m1 == m2){
                                   return Integer.compare(s1.getId(),s2.getId());
                             }
                             return Integer.compare(m2,m1);
                    }
          };
          
          Student(){}
          Student(String name,int id,int age,Map<String,Integer> marks_Data){
                    this.name = name;
                    this.id = id;
                    this.age = age;
                    this.marks_Data= marks_Data;
                    this.totalMarks = calculateTotal();
          }
          
          private int calculateTotal(){
                    int sum = 0;
                    for(Integer i:marks_Data.values()){
                              sum+=i;
                    }
                    return sum;
          }
          public String getName(){
                    return this.name;
          }
          public int getId(){
                    return this.id;
          }
          public int getAge(){
                    return this.age;
          }
          public int getTotalMarks(){
                    return this.totalMarks;
          }
          public Map<String,Integer> getMarksList(){
                    return this.marks_Data;
          }
          public void setMarks(Map<String,Integer> marks){
                    this.marks_Data = marks;
          }
          
          public int compareTo(Student s){
                 return Integer.compare(this.id,s.id);
          }
          
          public String toString(){
                    return this.id+" "+this.name+" "+this.totalMarks;
          }
}
