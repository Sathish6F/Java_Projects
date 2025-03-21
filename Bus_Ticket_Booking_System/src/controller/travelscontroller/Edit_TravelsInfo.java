package controller.travelscontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;
import edition.Editor;

public class Edit_TravelsInfo implements Editor{
    private static final DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static Edit_TravelsInfo eT = null;
    private Edit_TravelsInfo(){}

    public static Edit_TravelsInfo getInstance(){
        if(eT==null){
            eT= new Edit_TravelsInfo();
        }
        return eT;
    }

    @Override
    public boolean editUserName(int travelsId, String newName) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_TRAVELS_NAME.getQuery(),new Object[]{newName,travelsId});
        return r!=0;
    }

    @Override
    public boolean editPassword(int travelsId, String newPassWord) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_USER_PASSWORD.getQuery(),new Object[]{newPassWord,travelsId,3});
        return r!=0;
    }

    @Override
    public boolean editAddress(int travelsId, String newAddress) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_TRAVELS_ADDRESS.getQuery(),new Object[]{newAddress,travelsId});
        return r != 0;
    }

    @Override
    public boolean editMobileNumber(int travelsId, String newMobileNumber) {
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_TRAVELS_MOBILE.getQuery(),new Object[]{newMobileNumber,travelsId});
        return r != 0;
    }
}
