package model;

public class SemiSleeperSeat extends Seat{
    public SemiSleeperSeat(int number,String gender){
        super(number,SeatType.SEMISLEEPER.getType(),gender);
    }
    public SemiSleeperSeat(){}
    @Override
    public String getType() {
        return SeatType.SEMISLEEPER.getType();
    }
}
