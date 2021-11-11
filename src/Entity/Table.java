package Entity;

import java.time.LocalDateTime;

/**
 * This class contains the information of a particular table.<br>
 * It includes the table ID, maximum pax, status, customer, order of the table.
 */
public class Table implements java.io.Serializable{
    /**
     * Maximum number of people this table can accommodate.
     */
    private final int maxPax;
    /**
     * The table ID of this table.
     */
    private final int tableId;
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
     * Sets this table as occupied.
     * @param customer the customer assigned to this table.
     * @param pax the number of pax assigned to this table.
     */
    public void setOccupied(Customer customer, int pax) {
        this.occupied = true;
        this.reserved = false;
        this.customer = customer;
        this.pax = pax;
    }

    /**
     * Sets this table as available.
     */
    public void setAvailable(){
        this.occupied = false;
        this.reserved = false;
        this.customer = null;
        this.pax = 0;
        this.order = null;
    }

    /**
     * Sets this table as reserved.
     * @param customer the customer reserving this table.
     * @param pax the number of pax in the reservation.
     * @param time the time of the reservation.
     */
    public void setReserved(Customer customer, int pax, LocalDateTime time) {
        this.reserved = true;
        this.customer = customer;
        this.pax = pax;
        this.reservedTime = time;
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


