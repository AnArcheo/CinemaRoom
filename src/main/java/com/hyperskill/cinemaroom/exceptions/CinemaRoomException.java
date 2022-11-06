package com.hyperskill.cinemaroom.exceptions;

public class CinemaRoomException {
    public static final String SEAT_OUT_OF_BOUNDS = "{ \"error\": \"The number of a row or a column is out of bounds!\" }";
    public static final String SEAT_ALREADY_PURCHASED = "{ \"error\": \"The ticket has been already purchased!\" }";
    public static final String WRONG_TOKEN = "{ \"error\": \"Wrong token!\" }";
    public static final String WRONG_PASSWORD = "{ \"error\": \"The password is wrong!\" }";
}
