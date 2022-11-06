package com.hyperskill.cinemaroom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean isPurchased;

    public Seat(int row, int column, int price){
        this.row = row;
        this.column = column;
        this.price = price;
    }
}
