package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {

    CinemaService cinemaService = new CinemaService();

    @GetMapping("/seats")
    public ResponseEntity<SeatManager> getSeats(){
        return cinemaService.getSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody BookSeat bookSeat) {
        return cinemaService.purchaseSeat(bookSeat);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> token) {
        return cinemaService.returnTicket(token);
    }

    @PostMapping("/stats")
    public ResponseEntity<Map<String, ?>> getStats(@RequestParam Map<String, String> password) {
        return cinemaService.getStatistics(password);
    }
}