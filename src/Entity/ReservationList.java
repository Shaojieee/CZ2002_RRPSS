package Entity;

import Control.FileEditor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        this.list = FileEditor.loadReservations();
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

    public static HashMap<Integer,ArrayList<Reservation>> getList(){ return list;}

    public static void remove(int tableID, Reservation reservation) {
        list.remove(tableID, reservation);
    }
}

