package com.hyperskill.cinemaroom.controller;

import com.hyperskill.cinemaroom.exceptions.CinemaRoomException;
import com.hyperskill.cinemaroom.model.CinemaRoom;
import com.hyperskill.cinemaroom.model.Seat;
import com.hyperskill.cinemaroom.model.Ticket;
import com.hyperskill.cinemaroom.model.Token;
import com.hyperskill.cinemaroom.services.CinemaRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
public class CinemaRoomController {

    private final CinemaRoomService cinemaRoomService;

    public CinemaRoomController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @GetMapping("/seats")
    public CinemaRoom getAvailableSeats(){
        return cinemaRoomService.getCinemaRoom();
    }
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat){
        if(cinemaRoomService.isSeatValid(seat)){
            return new ResponseEntity<>(CinemaRoomException.SEAT_OUT_OF_BOUNDS, HttpStatus.BAD_REQUEST);
        }
        Seat chosenSeat = cinemaRoomService.getChosenSeat(seat);
        if(chosenSeat.isPurchased()){
            return new ResponseEntity<>(CinemaRoomException.SEAT_ALREADY_PURCHASED, HttpStatus.BAD_REQUEST);
        }else{
            Ticket purchasedTicket = cinemaRoomService.purchaseTicket(chosenSeat);
            cinemaRoomService.addPurchasedTicketToList(purchasedTicket);
            cinemaRoomService.setSeatAsPurchased(purchasedTicket.getSeat());
            cinemaRoomService.takeSeat(chosenSeat);
            return new ResponseEntity<>(purchasedTicket, HttpStatus.OK);
        }
    }
    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody Token token){
        Collection <Ticket> orderedTickets = cinemaRoomService.getOrderedTickets();
        for (Ticket returnedTicket : orderedTickets) {
            if (returnedTicket.getToken().equals(token.getToken())){
                cinemaRoomService.removePurchasedTicketFromList(returnedTicket);
                Seat returnedSeat = returnedTicket.getSeat();
                cinemaRoomService.setSeatAsNotPurchased(returnedSeat);
                cinemaRoomService.releaseSeat(returnedSeat);
                return new ResponseEntity<>(Map.of("returned_ticket", returnedSeat), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(CinemaRoomException.WRONG_TOKEN, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/stats")
    public ResponseEntity<Object> getStatistics(@RequestParam(defaultValue = "") @RequestBody String password) {
        if (password.equals("super_secret")){
            return new ResponseEntity<>(cinemaRoomService.getStatistics(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(CinemaRoomException.WRONG_PASSWORD, HttpStatus.UNAUTHORIZED);
    }
}
