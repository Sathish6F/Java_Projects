import java.util.*;
public class TaskManager{
          static Map<Integer,ArrayDeque<Task>> task_Data=new HashMap<Integer,ArrayDeque<Task>>();
          //To add the taks in the task_Data
          public void addTask(Task t){
                    //First check if the priority the user entered is exists in the task_Data if it is not then create the new ArrayDeque for that priority
                    task_Data.putIfAbsent(t.task_Priority,new ArrayDeque<Task>());
                    for(ArrayDeque<Task> queue : task_Data.values()){
                              for(Task existingTask : queue){
                                        if(existingTask.task_Name.equals(t.task_Name)){
                                                  System.out.println(t.task_Name+" is already existed");
                                                  return;
                                        }
                              }
                    }
                    //Store the task to its corresponding priority's queue
                    task_Data.get(t.task_Priority).add(t);  
                    System.out.println("Task Added:"+t.toString());
                    
          }
          //To view the tasks by it's priority
          public void viewTask(){
                    //Get the tasks from the task_Data by it's priority order then print the tasks objects corresponding to that priority
                    task_Data.forEach( (priority,task) -> {
                                        if(task_Data.containsKey(priority) && !task_Data.get(priority).isEmpty()){
                                                  System.out.println("Priority:"+priority);
                                                  System.out.println("--------------");
                                                  System.out.println(task_Data.get(priority));
                                                  System.out.println("--------------");
                                       }
                              }
                    ); 
                    //To check if the task_Data is empty that is no tasks inside the task_Data
                    boolean flag=false;
                    for(ArrayDeque<Task> queue:task_Data.values()){
                              if(!queue.isEmpty()){
                                        flag=true;
                                        break;
                              }
                    }   
                    if(!flag)
                              System.out.println("No Tasks Found !!!"); 
                             
          }
          //To simulate tasks by it's priority
          public void taskSimulation(){
                    //Using for each loop to get the tasks based on it's priority then for each simulation decreace the task excecution time by one
                   // If the task's excecution time becomes zero then remove that task object from the corresponding queue
                    boolean flag=false;
                              for(ArrayDeque<Task> queue : task_Data.values()){
                                       if(!queue.isEmpty()){
                                                  System.out.println("Executed Task:"+queue.getFirst().task_Name);
                                                  queue.getFirst().task_Time--;
                                                  System.out.println("Remaining time:"+queue.getFirst().task_Time);
                                                  if(queue.getFirst().task_Time==0) queue.removeFirst();
                                                  if(queue.isEmpty()) task_Data.remove(queue);
                                                  flag=true;
                                                  break;
                                        }
                             }
                     if(!flag)
                              System.out.println("All Tasks are executed");
                           
          }
          //To calculate the time for executing the tasks
          public void calculateTime(){
                    int totalTime=0;
                    for(ArrayDeque<Task> queue : task_Data.values()){
                              for(Task t : queue){
                                        totalTime+=t.task_Time;
                              }
                    }
                    System.out.println("The remaining time is:"+totalTime);
          }
          //To mark the task as completed by using the task ID the remove the task object from corresponding queue
          public void markAsCompleted(int taskId){
                    boolean flag=false;
                    l1:for(ArrayDeque<Task> queue : task_Data.values()){
                              for(Task t : queue){
                                        if(t.task_id==taskId){
                                                  System.out.println(t.task_Name+" is Marked as completed");
                                                  flag=true;
                                                  queue.remove(t);
                                                  break l1;
                                        }
                              }
                    }
                    if(!flag) System.out.println("No such Task ID found");
          }
}

