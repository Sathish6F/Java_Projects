package model;

import java.util.Objects;

public abstract class Seat {
    private int number;
    private String type;
    private boolean is_booked;
    private String bookedGender;

    protected Seat(){}
    protected Seat(int number,String type,String bookedBy){
        this.number = number;
        this.type = type;
        this.bookedGender = bookedBy;
    }
    public abstract String getType();
    public String toString(){
        return this.type+((this.number<=9)?"0":"")+this.number;
    }

    public String getBookedGender(){
        return this.bookedGender;
    }

    public void setBookedGender(String bookedGender) {
        this.bookedGender = bookedGender;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return number == seat.getNumber() && this.type.equals(seat.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }

    public boolean isIs_booked() {
        return is_booked;
    }

    public void setIs_booked(boolean is_booked) {
        this.is_booked = is_booked;
    }
}
