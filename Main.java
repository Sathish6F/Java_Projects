import java.util.Scanner;
class Main{
          public static void main(String[] args){
                    Scanner sc=new Scanner(System.in);
                    boolean flag=true;
                    int choice;
                    while(flag){
                              System.out.println("Welcom to Task Manager");
                              System.out.println("1.Add Task\n2.View Task by priority\n3.Simulate Task\n4.Calculate Time\n5.Mark as completed\n6.Exit");
                              choice=sc.nextInt();
                              sc.nextLine();
                              switch(choice){
                                        case 1:
                                                  //Add Task
                                                  System.out.println("Enter Task Name:");
                                                  String taskName=sc.nextLine();
                                                  System.out.println("Enter Task Priority:");
                                                  int taskPriority=sc.nextInt();
                                                  System.out.println("Enter Task Time:");
                                                  int taskTime=sc.nextInt();
                                                  new TaskManager().addTask(new Task(taskName,taskPriority,taskTime));
                                                  break;
                                        case 2:
                                                  //View Task
                                                  new TaskManager().viewTask();
                                                  break;
                                        case 3:
                                                  //Simulate Task
                                                  new TaskManager().taskSimulation();
                                                  break;
                                        case 4:
                                                  //Calculate Time
                                                  new TaskManager().calculateTime();
                                                  break;
                                        case 5:
                                                  //Mark as complete
                                                  System.out.println("Enter the task ID to mark as completed:");
                                                  int task_id=sc.nextInt();
                                                  new TaskManager().markAsCompleted(task_id);
                                                  break;
                                        case 6:
                                                  flag=false;
                                                  break;  
                                        default:
                                                  System.out.println("choose valid option");
                                                  break;                  
                             }
                             System.out.println("__________________________________________________");
                    }
          }
}
