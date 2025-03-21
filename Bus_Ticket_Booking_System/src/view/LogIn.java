package view;

import Validation.UserValidator;
import controller.SignIn;
import controller.passengercontroller.PassengerSignUp;
import exception.DBUpdationFailedException;
import exception.NoSuchUserException;
import model.Passanger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LogIn extends UserValidator {

    private static Scanner sc=MyScanner.getInstance();
    static {
        SignIn.createTables();
    }

    public void login(){
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%50s%n","Welcome To Bus Ticket Booking System");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\t\t\t\t1.Login\t\t\t\t   2.SignUp");
        try {
            int ch = sc.nextInt();
            while(ch<=0 || ch>2){
                System.out.println("Please Enter the valid Choice");
                ch = sc.nextInt();
            }
            switch (ch) {
                case 1:
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println("Enter Mail id:");
                    String mail = getValidatedEmail();
                    System.out.println("Enter PassWord:");
                    String password = getValidatedPassword();
                    if (!SignIn.getInstance().signIn(mail, password)) {
                        throw new NoSuchUserException("No Users Found");
                    }
                    System.out.println("------------------------------------------------------------------------");
                    break;
                case 2:
                    System.out.println("------------------------------------------------------------------------");
                    signUp();
                    break;
            }
        }catch (NoSuchUserException e){
            LogOut.logOut();
        }catch (InputMismatchException e){
            System.out.println("Enter Valid Inputs");
            login();
        }
    }

    public void signUp(){
        try {
            System.out.println("Enter Name:");
            String name = sc.next();
            System.out.println("Enter Gender:[M-Male,F-Female]");
            String gender = sc.next();
            while (gender.length() > 1 || (gender.charAt(0) != 'M' && gender.charAt(0) != 'F')) {
                System.out.println("Please Enter Valid Gender Choice[M-Male,F-Female]");
                gender = sc.next();
            }
            System.out.println("Enter Age:");
            int age = sc.nextInt();
            while (age <= 17 || age >= 71) {
                System.out.println("Please Entre Valid Age [18-70]");
                age = sc.nextInt();
            }
            System.out.println("Enter City:");
            String city = sc.next();
            System.out.println("Enter Mobile Number:");
            String mobileNumber = getValidatedMobileNumber();
            String mail="",passWord="";
            System.out.println("Enter Mail ID:");
            mail = getValidatedEmail();
            System.out.println("Enter Password:");
            passWord = getValidatedPassword();
            while(SignIn.getInstance().isExistingUser(mail,passWord)){
                System.out.println("User Mail ID/Password already Exist...");
                System.out.println("Enter Mail Id:");
                mail = getValidatedEmail();
                System.out.println("Enter Password");
                passWord = getValidatedPassword();
            }
            //Passanger(String name, String address, String mobileNumber, String mail, String passWord,int age,String gender)
            Passanger passanger = new Passanger(name,city,mobileNumber,mail,passWord,age,gender);
            if(PassengerSignUp.getInstance().addNewPassenger(passanger)){
                System.out.println("Passenger Details Added Successfully");
                login();
            }else{
                throw new DBUpdationFailedException("Sign Up Failed");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter Valid Inputs");
            signUp();
        } catch (DBUpdationFailedException e){
            System.out.println("1.Re try\t2.Exit");
            int choice = sc.nextInt();
            while(choice<=0 || choice >2){
                System.out.println("Please Enter Valid Choice");
                choice = sc.nextInt();
            }
            if(choice == 1){
                signUp();
            }else{
                return;
            }
        }
    }

}
