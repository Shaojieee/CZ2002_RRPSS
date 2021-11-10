package Entity;

import Control.MemberListControl;

/**
 * This class contains all the information of a particular customer.<br>
 * It includes name, phone number, the number of people in the group and the membership status.
 */
public class Customer implements java.io.Serializable{
    /**
     * Name of this customer.
     */
    private String name;
    /**
     * Phone number of this customer.
     */
    private int phone;
    /**
     * Number of people with this customer.
     */
    private int pax;
    /**
     * Membership status of this customer.
     */
    private boolean member;

    /**
     * This constructor defines a customer making a reservation.
     * @param name the name of this customer.
     * @param phone the phone number of this customer.
     * @param pax the number of people with this customer.
     */
    public Customer(String name, int phone, int pax) {
        this.name = name;
        this.phone = phone;
        this.member = MemberListControl.checkMember(phone);
        this.pax = pax;
    }

    /**
     * This constructor defines a walk-in customer.
     * @param pax the number of people with this customer.
     */
    public Customer(int pax){
        this.pax = pax;
    }

    /**
     * @return the name of this customer.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of pax with this customer.
     */
    public int getPax() {
        return pax;
    }

    /**
     * @return <code>true</code> if this customer is a member, <code>false</code> otherwise.
     */
    public boolean isMember() {
        return member;
    }

    /**
     * Checks if the name and phone number are the same as this customer.
     * @param name the name to be checked.
     * @param phone the phone number to be checked.
     * @return <code>true</code> if the name and phone number are the same, <code>false</code> otherwise.
     */
    public boolean equals(String name, int phone){
        return (this.name.equals(name) && this.phone==phone);
    }

    /**
     * @return the phone number of this customer
     */
    public int getPhone() {
        return this.phone;
    }
}
