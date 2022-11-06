package com.hyperskill.cinemaroom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CinemaRoom {
    private static int TOTAL_ROWS = 9;
    private static int TOTAL_COLUMNS = 9;
    @JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("total_columns")
    private int totalColumns;
    @JsonProperty("available_seats")
    private Collection<Seat> availableSeats;
    @JsonIgnore
    private Collection<Ticket> orderedTickets;

    public CinemaRoom(){
        totalRows = TOTAL_ROWS;
        totalColumns = TOTAL_COLUMNS;
        availableSeats = createSeats();
        this.orderedTickets = new ArrayList<>();
    }

    static Collection<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= TOTAL_ROWS; i++){
            for (int j = 1; j <= TOTAL_COLUMNS; j++){
                if(i <= 4){
                    seats.add(new Seat(i, j, 10));
                }else{
                    seats.add(new Seat(i, j, 8));
                }

            }
        }
        return seats;
    }
}
