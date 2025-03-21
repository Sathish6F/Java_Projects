package model;

public abstract class User {
    private int id;
    private String  name;
    private String address;
    private String mobileNumber;
    private String mail;
    private String passWord;
    protected User(){}
    protected User(int id, String name, String address, String mobileNumber, String mail, String passWord) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.mail = mail;
        this.passWord = passWord;
    }
    protected User(String name, String address, String mobileNumber, String mail, String passWord){
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.mail = mail;
        this.passWord = passWord;
    }
    public User(int id,String name,String address,String mobileNumber,String mail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.mail = mail;
    }

    public User(String name){
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public String getMail() {
        return mail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public abstract String getUserType();
}
