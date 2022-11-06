package com.hyperskill.cinemaroom.services;

import com.hyperskill.cinemaroom.model.CinemaRoom;
import com.hyperskill.cinemaroom.model.Seat;
import com.hyperskill.cinemaroom.model.Ticket;
import com.hyperskill.cinemaroom.model.Token;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@NoArgsConstructor
public class CinemaRoomService {

    private final CinemaRoom cinemaRoom = new CinemaRoom();
    private final  Collection<Seat> availableSeats = cinemaRoom.getAvailableSeats();
    private final Collection<Ticket> orderedTickets = cinemaRoom.getOrderedTickets();

    private int numberOfAvailableSeats = availableSeats.size();
    private int numberOfTicketsSold = 0;
    private int currentIncome = 0;

    public boolean isSeatValid(Seat seat){
        return seat.getRow() > 9 || seat.getColumn() > 9 || seat.getRow() < 1 || seat.getColumn() < 1;
    }

    public Seat getChosenSeat(Seat seat){
        return availableSeats.stream()
                .filter(s -> s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn())
                .findFirst().get();
    }

    public Ticket purchaseTicket(Seat seat){
        Ticket ticket = new Ticket(UUID.randomUUID(), seat);
        return ticket;
    }

    public void addPurchasedTicketToList(Ticket ticket){
        orderedTickets.add(ticket);
    }

    public void removePurchasedTicketFromList(Ticket ticket){
        orderedTickets.remove(ticket);
    }

    public void setSeatAsPurchased(Seat seat){
        seat.setPurchased(true);
    }

    public void setSeatAsNotPurchased(Seat seat){
        seat.setPurchased(false);
    }

    public void takeSeat(Seat seat){
        numberOfAvailableSeats -= 1;
        numberOfTicketsSold += 1;
        currentIncome += seat.getPrice();
    }


    public void releaseSeat(Seat seat){
        numberOfAvailableSeats += 1;
        numberOfTicketsSold -= 1;
        currentIncome -= seat.getPrice();
    }

    public String getStatistics(){
        String response = String.format("{\n" +
                "    \"current_income\": %s,\n" +
                "    \"number_of_available_seats\": %s,\n" +
                "    \"number_of_purchased_tickets\": %s\n" +
                "}",
                getCurrentIncome(),
                getNumberOfAvailableSeats(),
                getNumberOfTicketsSold());
        return response;
    }



}
