package cinema;

import java.util.UUID;

public class PurchasedSeat {
    private final String token;
    private final Seats ticket;

    public PurchasedSeat(Seats ticket) {
        this.token = UUID.randomUUID().toString();
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public Seats getTicket() {
        return ticket;
    }
}
