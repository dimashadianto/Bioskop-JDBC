package com.enigma.entitiy;

import java.sql.Date;

public class Film {
    private Integer id;
    private String title;
    private Integer duration;
    private Date showDate;
    private Integer price;
    private  Integer ratingId;

    public Film() {
    }

    public Film(Integer id, String title, Integer duration, String showDate, Integer price, Integer ratingId) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.showDate = Date.valueOf(showDate);
        this.price = price;
        this.ratingId = ratingId;
    }

    public Film(String title, Integer duration, String showDate, Integer price, Integer ratingId) {
        this.title = title;
        this.duration = duration;
        this.showDate = Date.valueOf(showDate);
        this.price = price;
        this.ratingId = ratingId;
    }

    @Override
    public String toString() {
        return "Film {" +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", duration = " + duration +
                ", show_date = " + showDate +
                ", price = " + price +
                ", rating_id = " + ratingId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }
}