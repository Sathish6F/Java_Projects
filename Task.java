import java.util.*;
class Task{
          static int id=1;
          String task_Name;
          int task_Priority;
          int task_Time;
          int task_id;
          Task(String task_Name,int task_Priority,int task_Time){
                    this.task_Name=task_Name;
                    this.task_Priority=task_Priority;
                    this.task_Time=task_Time;
                    this.task_id=id++;
          }
        
          public String toString(){
                    return "[ ID:"+this.task_id+", Name:"+this.task_Name+", Priority:"+this.task_Priority+", Time:"+this.task_Time+"Min ]";
          }
}

