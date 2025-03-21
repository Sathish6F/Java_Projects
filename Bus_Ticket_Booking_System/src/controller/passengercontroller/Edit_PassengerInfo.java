package controller.passengercontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import edition.Editor;

public class Edit_PassengerInfo implements Editor {

    private static final DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static Edit_PassengerInfo eP = null;
    private Edit_PassengerInfo(){}

    public static Edit_PassengerInfo getInstance(){
        if(eP == null){
            eP = new Edit_PassengerInfo();
        }
        return eP;
    }

    @Override
    public boolean editUserName(int passengerId,String newName) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_USER_NAME.getQuery(),new Object[]{newName,passengerId});
        return r!=0;
    }

    @Override
    public boolean editPassword(int passengerId,String newPassword) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_USER_PASSWORD.getQuery(),new Object[]{newPassword,passengerId,2});
        return r!=0;
    }

    @Override
    public boolean editAddress(int passengerId,String newAddress) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_PASSENGER_ADDRESS.getQuery(),new Object[]{newAddress,passengerId});
        return r!=0;
    }

    @Override
    public boolean editMobileNumber(int passengerId,String newMobileNumber) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_PASSENGER_MOBILE.getQuery(),new Object[]{newMobileNumber,passengerId});
        return r!=0;
    }
}
