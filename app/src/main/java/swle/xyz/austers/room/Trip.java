package swle.xyz.austers.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
*Created by TSOMH on 2020/5/28$
*Description:
*
*/
@Entity
public class Trip {

    @PrimaryKey(autoGenerate = true)
    int uid;
    private String initiator;
    private String starting;
    private String destination;

    private int seat_left;
    private int year;
    private int month;
    private int day;
    private int hour;



    public Trip(String initiator, String starting, String destination, int seat_left, int year, int month, int day, int hour) {
        this.initiator = initiator;
        this.starting = starting;
        this.destination = destination;
        this.seat_left = seat_left;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }


    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSeat_left() {
        return seat_left;
    }

    public void setSeat_left(int seat_left) {
        this.seat_left = seat_left;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }


}