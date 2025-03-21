package model;

import java.util.List;

public class Travels extends User {
    private String owner_name;
    private List<Bus> buses;
    private int totalBuses;

    public Travels(int id, String name, String address, String mobileNumber, String mail, String passWord, String owner_name) {
        super(id, name, address, mobileNumber, mail, passWord);
        this.owner_name = owner_name;
    }

    public Travels(int id, String name, String address, String mobileNumber, String mail,String owner_name,int totalBuses){
        super(id,name,address,mobileNumber,mail);
        this.owner_name = owner_name;
        this.totalBuses = totalBuses;
    }
    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    @Override
    public String getUserType() {
        return "Travels";
    }
}
