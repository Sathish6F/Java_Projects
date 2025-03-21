package model;

public class Passanger extends User{
    private int age;
    private String gender;
    private String seatNumber;
    private Ticket ticket;

    public Passanger(String name, String address, String mobileNumber, String mail, String passWord,int age,String gender) {
        super(name, address, mobileNumber, mail, passWord);
        this.age = age;
        this.gender = gender;
    }

    public Passanger(int id,String name,String address,String mobileNumber,String mail,int age,String gender){
        super(id,name,address,mobileNumber,mail);
        this.age = age;
        this.gender = gender;
    }

    public Passanger(String name,int age,String gender,String seatNumber){
        super(name);
        this.gender=gender;
        this.age = age;
        this.seatNumber = seatNumber;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String getUserType() {
        return "Passenger";
    }
}
