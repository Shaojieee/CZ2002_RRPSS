package Entity;

import Control.FileEditor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains all the information on list of reservations in the restaurant.<br>
 * Only 1 instance can  exist at all times.
 */
public class ReservationList {

    /**
     * The singleton instance of <code>ReservationList</code>.
     */
    private static ReservationList reservationList = null ;

    /**
     * The HashMap containing the reservations.
     * Key is the Table ID. Value is an ArrayList of <code>Reservation</code> objects.
     */
    private static HashMap<Integer, ArrayList<Reservation>> list;

    /**
     * This constructor defines the list of reservations.
     */
    private ReservationList(){
        list = FileEditor.loadReservations();
    }

    /**
     * Initializes the <code>ReservationList</code> object if it has not been initialized.
     * @return the <code>ReservationList</code> object.
     */
    public static ReservationList getReservationList(){
        if(reservationList==null){
            reservationList = new ReservationList();
        }
        return reservationList;
    }

    /**
     * @return the HashMap of reservations.
     */
    public HashMap<Integer,ArrayList<Reservation>> getList(){ return list;}

    /**
     * Removes a reservation from this list of reservations.
     * @param reservation the reservation to remove.
     */
    public void removeFromReservations(Reservation reservation) {
        list.get(reservation.getTableID()).remove(reservation);
    }

    /**
     * Adds a reservation into this list of reservations.
     * @param index the position to insert the reservation at.
     * @param reservation the reservation to insert.
     */
    public void addToReservations(int index, Reservation reservation){
        if(index == list.get(reservation.getTableID()).size()){
            list.get(reservation.getTableID()).add(reservation);
        }else{
            list.get(reservation.getTableID()).add(index, reservation);
        }
    }

    /**
     * Removes a table from this list of reservations.
     * @param tableID the tableID of the table to remove.
     */
    public void removeTable(int tableID){
        list.remove(tableID);
    }

    /**
     * Adds a table into this list of reservations.
     * @param tableID the tableID of the table to add.
     */
    public void addTable(int tableID){
        list.put(tableID, new ArrayList<Reservation>());
    }

    /**
     * Adds a table and all the reservations tagged to the table into this list of reservations
     * @param tableID the tableID of the table to add.
     * @param reservations the list of reservations for the table.
     */
    public void addTable(int tableID, ArrayList<Reservation> reservations){
        list.put(tableID, reservations);
    }

    /**
     * Gets the list of reservations tagged to the specified table ID.
     * @param tableID the table ID.
     * @return the ArrayList of reservations.
     */
    public ArrayList<Reservation> getReservations(int tableID) {
        return list.get(tableID);
    }
}

