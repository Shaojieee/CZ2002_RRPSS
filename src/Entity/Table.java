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
     * @return the number of people occupying this table.
     */
    public int getPax() {
        return pax;
    }

    /**
     * Sets the order for this table.
     * @param order the order to be set to this table.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Sets the status of availability for this table.
     * @param occupied the status of availability to be set to this table.
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Sets the reservation status for this table.
     * @param reserved the status of reservation to be set to this table.
     */
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /**
     * Sets the customer for this table.
     * @param customer the customer to be assigned to this table.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets the Pax for this table.
     * @param pax the pax to be set to this table.
     */
    public void setPax(int pax) {
        this.pax = pax;
    }

    /**
     * Sets the reservation timing for this table.
     * @param reservedTime the timing of reservation to be set to this table.
     */
    public void setReservedTime(LocalDateTime reservedTime) {
        this.reservedTime = reservedTime;
    }

    /**
     * @return the reservation timing for the table.
     */
    public LocalDateTime getReservedTime(){ return reservedTime;}
    /**
     * @return <code>true</code> if table has an order, <code>false</code> otherwise.
     */
    public boolean hasOrder(){
        return (order!=null);
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

}


