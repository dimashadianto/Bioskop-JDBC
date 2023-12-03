package com.enigma.entitiy;

public class Seat {
    private Integer id;
    private String seatNumber;
    private Integer theaterId;

    public Seat() {
    }

    public Seat(Integer id, String seatNumber, Integer theaterId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.theaterId = theaterId;
    }

    public Seat(String seatNumber, Integer theaterId) {
        this.seatNumber = seatNumber;
        this.theaterId = theaterId;
    }

    @Override
    public String toString() {
        return "Seat {" +
                "id = " + id +
                ", seat_number = '" + seatNumber + '\'' +
                ", theater_id = " + theaterId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

}