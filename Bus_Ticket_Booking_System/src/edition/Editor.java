package edition;

public interface Editor {
    boolean editUserName(int userId,String newName);
    boolean editPassword(int userId,String newPassWord);
    boolean editAddress(int userId,String newAddress);
    boolean editMobileNumber(int userId,String newMobileNumber);
}
