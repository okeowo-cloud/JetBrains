package cinema;

public class ReturnedTicket {
    private final String token;
    private final Seats returned_ticket;

    public ReturnedTicket(String token, Seats returned_ticket) {
        this.token = token;
        this.returned_ticket = returned_ticket;
    }

    public String getToken() {
        return token;
    }

    public Seats getReturned_ticket() {
        return returned_ticket;
    }
}