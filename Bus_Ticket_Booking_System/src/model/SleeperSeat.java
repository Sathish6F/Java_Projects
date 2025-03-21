package model;

public class SleeperSeat extends Seat{
    public SleeperSeat(int number,String gender){
        super(number,SeatType.SLEEPER.getType(),gender);
    }
    public SleeperSeat(){}
    @Override
    public String getType() {
        return SeatType.SLEEPER.getType();
    }
}
