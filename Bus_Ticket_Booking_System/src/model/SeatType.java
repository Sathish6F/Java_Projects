package model;

public enum SeatType {
    SLEEPER("Sl"),
    SEMISLEEPER("Ss");
    private final String type;
    SeatType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
