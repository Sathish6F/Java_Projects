package controller.travelscontroller;

import controller.databasecontroller.DataBaseOperation;
import controller.databasecontroller.Queries;

public class Edit_PriceInfo {

    private static final DataBaseOperation dbo = DataBaseOperation.getInstance();
    private static Edit_PriceInfo eP = null;
    private Edit_PriceInfo(){}

    public static Edit_PriceInfo getInstance() {
        if(eP == null){
            eP = new Edit_PriceInfo();
        }
        return eP;
    }

    public boolean updatePriceInfo(int busId,int seatId,int newPrice){
        int r = 0;
        r = dbo.executeUpdate(Queries.EDIT_PRICE_INFO.getQuery(),new Object[]{newPrice,busId,seatId});
        return r != 0;
    }

}
