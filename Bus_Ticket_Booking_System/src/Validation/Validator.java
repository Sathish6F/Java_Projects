package Validation;
import java.sql.Date;
public interface Validator {
    public boolean validateEmail(String email);
    public boolean validateMobileNumber(String number);
    public boolean validatePassword(String passWord);

}
