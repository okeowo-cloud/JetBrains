package cinema;

import java.util.ArrayList;
import java.util.List;

public class SeatManager {
    int total_rows;
    int total_columns;
    List<Seats> available_seats;

    public SeatManager() {
        this.total_rows = 9;
        this.total_columns = 9;
        this.available_seats = new ArrayList<>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                Seats seats = new Seats(i, j, i <= 4 ? 10 : 8);
                available_seats.add(seats);
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Seats> getAvailable_seats() {
        return available_seats;
    }
}