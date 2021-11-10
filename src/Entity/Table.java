package Entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains the information of a particular table.<br>
 * It includes the table ID, maximum pax, status, customer, order of the table.
 */
public class Table implements java.io.Serializable{
    /**
     * Maximum number of people this table can accommodate.
     */
    private int maxPax;
    /**
     * The table ID of this table.
     */
    private int tableId;
    /**
     * The current number of people at the table.
     */
    private int pax;

    /**
     * <code>true</code> if table is reserved, <code>false</code> otherwise.
     */
    private boolean reserved;
    /**
     * <code>true</code> if table is occupied, <code>false</code> otherwise.
     */
    private boolean occupied;
    /**
     * The customer that has reversed or occupied this table.<br>
     * <code>null</code> if table is not reserved or occupied.
     */
    private Customer customer;
    /**
     * The current order that the customer at this table has placed.<br>
     * <code>null</code> if table is not occupied or if customer has not placed order.
     */
    private Order order;
    /**
     * The time the customer has reserved this table for.<br>
     * <code>null</code> if table is not reserved.
     */
    private LocalDateTime reservedTime;

    /**
     * This constructor defines a table.
     * @param maxPax the maximum number of people this table can accommodate.
     * @param tableId the table ID for this table.
     */
    public Table(int maxPax, int tableId){
        this.maxPax = maxPax;
        this.tableId = tableId;
        this.pax = 0;
        this.reserved = false;
        this.occupied = false;
        this.customer = null;
        this.order = null;
        this.reservedTime = null;
    }

    /**
     * @return the maximum number of people this table can accommodate.
     */
    public int getMaxPax() {
        return maxPax;
    }

    /**
     * @return the table ID of this table.
     */
    public int getTableId() {
        return tableId;
    }

    /**
     * @return the <code>Customer</code> object of this table.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return the <code>Order</code> object of this table.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the order for this table.
     * @param order the order to be set to this table.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return <code>true</code> if table is reserved, <code>false</code> otherwise.
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * @return <code>true</code> if table is occupied, <code>false</code> otherwise.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Prints the basic details this table.
     */
    public void printBasicDetails() {
        if (this.occupied){
            System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", this.tableId, "Occupied", this.pax, this.maxPax);
        }else if(this.reserved){
            System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", this.tableId, "Reserved at " + this.reservedTime.toLocalTime(), this.pax, this.maxPax);
        }else{
            System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", this.tableId, "Available", "-", this.maxPax);
        }
    }

}


