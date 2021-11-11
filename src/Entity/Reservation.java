package Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains all the information of a particular reservation. <br>
 * It includes the customer, table ID and the time.
 */
public class Reservation implements Serializable {

    /**
     * The time of this reservation.
     */
    private LocalDateTime time;

    /**
     * The customer who made this reservation.
     */
    private Customer customer;

    /**
     * The table ID of the table allocated to this reservation.
     */
    private int tableID;


    /**
     * This constructor defines a reservation.
     * @param name the name of the customer making this reservation.
     * @param phone the phone number of the customer making this reservation.
     * @param pax the number of people with this customer making this reservation.
     * @param tableID the table ID of the table allocated to this reservation.
     * @param time the time of this reservation
     */
    public Reservation(String name, int phone, int pax, int tableID, LocalDateTime time){
        this.customer = new Customer(name, phone, pax);
        this.time = time;
        this.tableID = tableID;
    }

    /**
     * @return the time of this reservation.
     */
    public LocalDateTime getTime(){
        return this.time;
    }

    /**
     * @return the customer who made this reservation.
     */
    public Customer getCustomer(){
        return this.customer;
    }

    /**
     * @return the table ID of the table allocated to this reservation.
     */
    public int getTableID(){
        return this.tableID;
    }

    /**
     * Sets the table ID of this reservation.
     * @param tableID the new table ID.
     */
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    /**
     * Prints the details of this reservation.
     */
    public void printDetails() {
        LocalDate date = this.getTime().toLocalDate();
        LocalTime time = this.getTime().toLocalTime();
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String date_str = date_format.format(date);

        DateTimeFormatter time_format = DateTimeFormatter.ofPattern("H:mm");
        String time_str = time_format.format(time);

        System.out.printf("|| %-12s|| %-5s|| %-15s|| %-8s || %-3s|| %-5s||\n", date_str, time_str , this.getCustomer().getName(), this.getCustomer().getPhone(), this.getCustomer().getPax(), this.getTableID());
    }

}

