package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CinemaService {
    private final SeatManager seats;
    private int income;
    private final List<PurchasedSeat> purchasedSeatList;
    private final List<ReturnedTicket> returnedTicketList;

    public CinemaService() {
        this.seats = new SeatManager();
        this.purchasedSeatList = new ArrayList<>();
        this.returnedTicketList = new ArrayList<>();
    }

    public ResponseEntity<SeatManager> getSeats(){
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    public ResponseEntity<?> purchaseSeat(BookSeat bookSeat) {
        if (bookSeat.getRow() > seats.getTotal_rows() || bookSeat.getColumn() > seats.getTotal_columns()
                || bookSeat.getRow() <= 0 || bookSeat.getColumn() <= 0) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        for (Seats sits : seats.available_seats) {
            if (sits.getRow() == bookSeat.getRow() && sits.getColumn() == bookSeat.getColumn()) {
                PurchasedSeat purchasedSeat = new PurchasedSeat(sits);
                purchasedSeatList.add(purchasedSeat);
                seats.available_seats.remove(sits);
                income += sits.getPrice();
                return new ResponseEntity<>(purchasedSeat, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> returnTicket(Map<String, String> token) {
        for (ReturnedTicket returnedTicket: returnedTicketList) {
            if(token.containsValue(returnedTicket.getToken())) {
                return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
            }
        }
        for (PurchasedSeat purchasedSeat : purchasedSeatList) {
            if (token.containsValue(purchasedSeat.getToken())) {
                ReturnedTicket returnedTicket = new ReturnedTicket(purchasedSeat.getToken(),purchasedSeat.getTicket());
                returnedTicketList.add(returnedTicket);
                seats.available_seats.add(purchasedSeat.getTicket());
                purchasedSeatList.remove(purchasedSeat);
                income -= returnedTicket.getReturned_ticket().getPrice();

                return new ResponseEntity<>(Map.of("returned_ticket", returnedTicket.getReturned_ticket()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Map<String, ?>> getStatistics(Map<String, String> password) {
        if (!password.containsKey("password") || !password.containsValue("super_secret")) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
        }
        int available_seats = seats.available_seats.size();
        int total_income = income;
        int no_of_purchased_tickets = purchasedSeatList.size();
        return new ResponseEntity<>(Map.of("current_income", total_income,
                "number_of_available_seats", available_seats, "number_of_purchased_tickets", no_of_purchased_tickets),
                HttpStatus.OK);
    }
}