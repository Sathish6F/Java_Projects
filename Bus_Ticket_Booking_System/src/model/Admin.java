package model;

public class Admin extends User{

    public Admin(int id, String name, String address, String mobileNumber, String mail, String passWord) {
        super(id, name, address, mobileNumber, mail, passWord);
    }
    public Admin(){
        super();
    }
    @Override
    public String getUserType() {
        return "Admin";
    }
}
