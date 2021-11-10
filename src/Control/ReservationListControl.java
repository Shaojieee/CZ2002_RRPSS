package Control;

import Entity.Reservation;
import Entity.ReservationList;
import Entity.Table;
import Entity.TableList;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReservationListControl {
    /**
     * Creates a new <code>Reservation</code> and adds it to the list.
     * @param name the name of the customer.
     * @param phone the phone number of the customer.
     * @param pax the number of pax with the customer.
     * @param time the reservation time.
     */
    public static void addReservation(String name, int phone, int pax, LocalDateTime time){
        Table table;
        int tableID=-1;
        int maxpax=100;
        int reservationIndex=0;
        int index;
        boolean last = false;
        boolean available_now = true;
        TableList tableList = TableList.getTableList();
        long time_from_now = LocalDateTime.now().until(time, ChronoUnit.MINUTES);
        if(time_from_now<=30){
            available_now = (TableListControl.chooseTable(pax) != null);
        }
        if(!available_now){
            return;
        }

        for(HashMap.Entry<Integer, ArrayList<Reservation>> item : ReservationList.getList().entrySet()){
            table = TableListControl.getTable(item.getKey());
            if(table.getMaxPax()>=pax && maxpax>table.getMaxPax()) {
                ArrayList<Reservation> reservations = item.getValue();
                if(reservations.size()==0){
                    maxpax = table.getMaxPax();
                    tableID = item.getKey();
                    reservationIndex=0;
                    last = false;
                    continue;
                }

                index = reservations.size();
                for(int i=0; i<reservations.size(); i++){
                    if(reservations.get(i).getTime().isAfter(time)){
                        index = i;
                        break;
                    }
                }
                long minutes, minutes_before;
                if(index==reservations.size()){
                    minutes = reservations.get(index-1).getTime().until(time, ChronoUnit.MINUTES);
                    if(minutes>30){
                        tableID = item.getKey();
                        maxpax = table.getMaxPax();
                        reservationIndex = index;
                        last = true;
                    }
                }else if(index==0){
                    minutes = time.until(reservations.get(0).getTime(), ChronoUnit.MINUTES);
                    if (minutes > 30) {
                        tableID = item.getKey();
                        maxpax = table.getMaxPax();
                        reservationIndex = index;
                        last = false;
                    }
                }else{
                    minutes = time.until(reservations.get(index).getTime(), ChronoUnit.MINUTES);
                    minutes_before = reservations.get(index-1).getTime().until(time, ChronoUnit.MINUTES);
                    if(minutes>30 && minutes_before>30){
                        tableID = item.getKey();
                        maxpax = table.getMaxPax();
                        reservationIndex = index;
                        last = false;
                    }
                }
            }
        }
        if(tableID!=-1){
            if(last){
                ReservationList.getList().get(tableID).add(new Reservation(name, phone, pax, tableID, time));
            }else{
                ReservationList.getList().get(tableID).add(reservationIndex, new Reservation(name, phone, pax, tableID, time));
            }
            saveReservationList();
        }

    }

    /**
     * Checks whether there is a table available for the reservation time.
     * @param pax the number of people.
     * @param time the reservation time.
     * @return <code>true</code> if there is an available table, <code>false</code> otherwise.
     */
    public static boolean checkAvailability(int pax, LocalDateTime time){
        updateTableStatus();
        boolean result= false;
        boolean available_now = true;
        TableList tableList = TableList.getTableList();
        long time_from_now = LocalDateTime.now().until(time, ChronoUnit.MINUTES);
        if(time_from_now<=30){
            available_now = (TableListControl.chooseTable(pax) != null);
        }
        if(!available_now){
            return false;
        }

        for(HashMap.Entry<Integer, ArrayList<Reservation>> table : ReservationList.getList().entrySet()){
            if(TableListControl.getTable(table.getKey()).getMaxPax()>=pax) {
                ArrayList<Reservation> reservations = table.getValue();
                if(reservations.size()==0) {
                    return true;
                }

                int index=reservations.size();
                for (int i=0; i<reservations.size(); i++) {
                    if(reservations.get(i).getTime().isAfter(time)){
                        index = i;
                        break;
                    }
                }
                long minutes, minutes_before;
                if(index==reservations.size()){
                    minutes = reservations.get(index-1).getTime().until(time, ChronoUnit.MINUTES);
                    result = (minutes>30);
                }else if(index==0){
                    minutes = time.until(reservations.get(0).getTime(), ChronoUnit.MINUTES);
                    result = (minutes>30);
                }else{
                    minutes = time.until(reservations.get(index).getTime(), ChronoUnit.MINUTES);
                    minutes_before = reservations.get(index-1).getTime().until(time, ChronoUnit.MINUTES);
                    result = (minutes>30 && minutes_before>30);
                }
            }
            if (result){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the customer name and phone number to delete reservation.
     */
    public static void deleteReservation(){
        Scanner sc = new Scanner(System.in);
        System.out.println("=====Deleting Reservation=====");

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        int phone;
        while(true){
            try{
                System.out.print("Enter Customer Contact Number: ");
                phone = sc.nextInt();
                sc.nextLine();
                if(phone<100000000 && phone>79999999){
                    break;
                }else{
                    System.out.println("Invalid phone number!");
                    System.out.println();
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid phone number!");
                System.out.println();
                sc = new Scanner(System.in);
            }
        }

        System.out.print("Enter Reservation Date (dd/mm/yyyy): ");
        LocalDate date;
        String date_str, time_str;
        boolean today;
        while(true){
            try{
                date_str = sc.nextLine();
                String[] date_arr = date_str.split("/", 3);
                date = LocalDate.of(Integer.parseInt(date_arr[2]), Integer.parseInt(date_arr[1]), Integer.parseInt(date_arr[0]));

                if(date.isAfter(LocalDate.now())) {
                    today = false;
                    break;
                }else if(date.isEqual(LocalDate.now())){
                    today = true;
                    break;
                }
                else{
                    System.out.println("Invalid reservation date!");
                    System.out.print("Enter Reservation Date (dd/mm/yyyy): ");
                }
            }catch(DateTimeException e){
                System.out.println("Invalid reservation date!");
                System.out.print("Enter Reservation Date (hh-mm): ");
            }
        }

        System.out.print("Enter Reservation Time (hh-mm): ");
        LocalTime time;
        while(true){
            try{
                time_str = sc.nextLine();
                String[] time_arr = time_str.split("-", 2);
                time = LocalTime.of(Integer.parseInt(time_arr[0]), Integer.parseInt(time_arr[1]));
                if(time.isAfter(LocalTime.of(20,30))){
                    System.out.println("Restaurant closes at 21-00!");
                }else if(time.isBefore(LocalTime.of(9,0))){
                    System.out.println("Restaurant opens at 09-00!");
                } else if(today && time.isAfter(LocalTime.now())) {
                    break;
                }else if(!today){
                    break;
                }else{
                    System.out.println("Invalid reservation time!");
                    System.out.print("Enter Reservation Time (hh-mm): ");
                }
            }catch(DateTimeException e){
                System.out.println("Invalid reservation time!");
                System.out.print("Enter Reservation Time (hh-mm): ");
            }
        }

        LocalDateTime date_time = LocalDateTime.of(date, time);

        for(ArrayList<Reservation> reservations : ReservationList.getList().values()){
            for(Reservation reservation : reservations){
                if(reservation.getCustomer().equals(name,phone) && reservation.getTime().isEqual(date_time)){
                    reservations.remove(reservation);
                    long minutes = LocalDateTime.now().until(reservation.getTime(), ChronoUnit.MINUTES);
                    if(minutes<30){
                        TableList tableList = TableList.getTableList();
                        TableListControl.unreserve(TableListControl.getTable(reservation.getTableID()));
                    }
                    saveReservationList();
                    System.out.println("Reservation for " + name + " has been deleted!");
                    System.out.println();
                    return;
                }
            }
        }
        System.out.println("No reservation under " + name + "!");
        System.out.println();
    }

    /**
     * Updates the status of the tables and deletes any reservations that have exceeded 15 minutes.
     */
    public static void updateTableStatus(){
        TableList tableList = TableList.getTableList();
        ArrayList<Reservation> reservations;
        Table table;
        for (HashMap.Entry<Integer, ArrayList<Reservation>> tables : ReservationList.getList().entrySet()){
            table = TableListControl.getTable(tables.getKey());
            if(tables.getValue().size()!=0){
                reservations = tables.getValue();
                Reservation reservation = reservations.get(0);
                /* Time now - Reservation time */
                LocalDateTime reservationTime = reservation.getTime();
                long time_diff = reservationTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
                /* Customer late by 15 mins */
                if (time_diff>15){
                    TableListControl.unreserve(table);
                    reservations.remove(reservation);

                    /*Upcoming Reservation*/
                }else if(time_diff>-30){

                    /*Allocated table is still occupied*/
                    if(table.isOccupied()){
                        table = TableListControl.chooseTable(reservation.getCustomer().getPax());
                        if(table!=null){
                            TableListControl.reserve(table,reservation);
                            reservation.setTableID(table.getTableId());
                        }
                    }else{
                        TableListControl.reserve(table,reservation);
                    }
                }
            }
        }
    }

    /**
     * Assigns the reserved table to the customer.
     * @param name the name of the customer.
     * @param phone the phone number of the customer.
     * @return the <code>Table</code> object allocated to the customer, <code>null</code> if there are no available tables.
     */
    public Table assignReservation(String name, int phone) {
        TableList tableList = TableList.getTableList();
        for(ArrayList<Reservation> reservations : ReservationList.getList().values()){
            for(Reservation reservation : reservations){
                Table table = TableListControl.getTable(reservation.getTableID());
                if(reservation.getCustomer().equals(name,phone)){
                    if(table.isOccupied()){
                        return null;
                    }else{
                        TableListControl.assign(table, reservation.getCustomer());
                        reservations.remove(reservation);
                        saveReservationList();
                        return table;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks if there is a reservation under the provided name and phone number.
     * @param name the name of the customer.
     * @param phone the phone number of the customer.
     * @return <code>true</code> if there is valid reservation, <code>false</code> otherwise.
     */
    public boolean checkReservation(String name, int phone){
        updateTableStatus();
        for(HashMap.Entry<Integer, ArrayList<Reservation>> item : ReservationList.getList().entrySet()){
            for (Reservation reservation : item.getValue()) {
                if (reservation.getCustomer().equals(name, phone)){
                    long minutes = reservation.getTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
                    if(minutes>-30 && minutes<15){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Adds the table ID to the reservation list
     * @param ID the ID of the table to be added.
     */
    public static void addTable(int ID){
        ReservationList.getList().put(ID, new ArrayList<Reservation>());
        saveReservationList();
    }

    /**
     * Checks whether the reservations under a table can be allocated to other tables.<br>
     * Used when checking if table can be deleted.
     * @param ID the table ID to check.
     * @return <code>true</code> if all the reservations can be allocated to other tables, <code>false</code> otherwise.
     */
    public static boolean checkDelete(int ID){
        updateTableStatus();
        ArrayList<Reservation> table_reservation = ReservationList.getList().get(ID);
        ReservationList.getList().remove(ID);
        TableList tableList = TableList.getTableList();
        Table table = TableListControl.getTable(ID);
        TableList.getTableList().remove(ID);

        for(Reservation reservation : table_reservation){
            if(!checkAvailability(reservation)){
                TableListControl.addTable(table);
                ReservationList.getList().put(ID, table_reservation);
                return false;
            }
        }
        TableListControl.addTable(table);
        ReservationList.getList().put(ID, table_reservation);
        return true;
    }

    /**
     * Checks whether the provided reservation can be allocated to a table.
     * @param cur_reservation the reservation to check.
     * @return <code>true</code> if the reservation can be allocated, <code>false</code> otherwise.
     */
    private static boolean checkAvailability(Reservation cur_reservation){
        int pax = cur_reservation.getCustomer().getPax();
        LocalDateTime time = cur_reservation.getTime();
        TableList tableList = TableList.getTableList();
        boolean result = false;
        boolean available_now = true;
        long time_from_now = LocalDateTime.now().until(time, ChronoUnit.MINUTES);
        if(time_from_now<=30){
            available_now = (TableListControl.chooseTable(pax) != null);
        }
        if(!available_now){
            return false;
        }

        for(HashMap.Entry<Integer, ArrayList<Reservation>> table : ReservationList.getList().entrySet()){
            if(TableListControl.getTable(table.getKey()).getMaxPax()>=pax) {
                ArrayList<Reservation> reservations = table.getValue();
                if(reservations.size()==0) {
                    return true;
                }

                int index=reservations.size();
                for (int i=0; i<reservations.size(); i++) {
                    if(reservations.get(i).getTime().isAfter(time)){
                        index = i;
                        break;
                    }
                }
                long minutes, minutes_before;
                if(index==reservations.size()){
                    minutes = reservations.get(index-1).getTime().until(time, ChronoUnit.MINUTES);
                    result = (minutes>30);
                }else if(index==0){
                    minutes = time.until(reservations.get(0).getTime(), ChronoUnit.MINUTES);
                    result = (minutes>30);
                }else{
                    minutes = time.until(reservations.get(index).getTime(), ChronoUnit.MINUTES);
                    minutes_before = reservations.get(index-1).getTime().until(time, ChronoUnit.MINUTES);
                    result = (minutes>30 && minutes_before>30);
                }
            }
            if (result){
                return true;
            }
        }
        return false;

    }

    /**
     * Deletes the table ID from this list.<br>
     * The existing reservations under the table will be transferred to other tables.
     * @param ID the table ID to delete.
     */
    public static void deleteTable(int ID){
        ArrayList<Reservation> table_reservation = ReservationList.getList().get(ID);
        ReservationList.getList().remove(ID);
        int pax;
        String name;
        int phone;
        LocalDateTime time;
        for(Reservation reservation : table_reservation) {
            name = reservation.getCustomer().getName();
            phone = reservation.getCustomer().getPhone();
            pax = reservation.getCustomer().getPax();
            time = reservation.getTime();
            addReservation(name, phone, pax, time);
        }
        saveReservationList();
    }

    /**
     * Saves the list of reservations.
     */
    public static void saveReservationList(){
        FileEditor.writeReservations(ReservationList.getList());
    }

    /**
     * Prints the list of reservations.
     */
    public static void printReservations(){
        Scanner sc = new Scanner(System.in);
        ReservationList reservationList = ReservationList.getReservationList();
        updateTableStatus();
        ArrayList<Reservation> reservations = sortReservations();
        System.out.println("==========================Reservation List===========================");
        System.out.printf("|| %-12s|| %-5s|| %-15s|| %-8s || %-3s|| %-5s||\n", "Date", "Time" ,"Name", "Contact", "Pax", "Table");
        for(Reservation reservation : reservations){
            printDetails(reservation);
        }
        System.out.println("=====================================================================");
        System.out.println("Press any key to go back ");
        sc.nextLine();
    }

    /**
     * Prints the details of this reservation.
     */
    public static void printDetails(Reservation reservation) {
        LocalDate date = reservation.getTime().toLocalDate();
        LocalTime time = reservation.getTime().toLocalTime();
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String date_str = date_format.format(date);

        DateTimeFormatter time_format = DateTimeFormatter.ofPattern("H:mm");
        String time_str = time_format.format(time);

        System.out.printf("|| %-12s|| %-5s|| %-15s|| %-8s || %-3s|| %-5s||\n", date_str, time_str , reservation.getCustomer().getName(), reservation.getCustomer().getPhone(), reservation.getCustomer().getPax(), reservation.getTableID());
    }

    /**
     * Sorts all the reservation according to their time from earliest to latest.
     * @return the list of sorted reservations.
     */
    private static ArrayList<Reservation> sortReservations(){
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        for(ArrayList<Reservation> cur : ReservationList.getList().values()){
            reservations.addAll(cur);
        }
        reservations.sort((Reservation x, Reservation y) -> x.getTime().isAfter(y.getTime()) ? 1 : -1);
        return reservations;
    }

    /**
     * Gets the customer name, phone number and reservation time to create reservation.
     */
    public static void createReservation(){
        Scanner sc = new Scanner(System.in);

        System.out.println("=====Creating Reservation=====");
        System.out.print("Enter number of pax: ");
        int pax = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Reservation Date (dd/mm/yyyy): ");
        LocalDate date;
        String date_str, time_str;
        boolean today;
        while(true){
            try{
                date_str = sc.nextLine();
                String[] date_arr = date_str.split("/", 3);
                date = LocalDate.of(Integer.parseInt(date_arr[2]), Integer.parseInt(date_arr[1]), Integer.parseInt(date_arr[0]));

                if(date.isAfter(LocalDate.now())) {
                    today = false;
                    break;
                }else if(date.isEqual(LocalDate.now())){
                    today = true;
                    break;
                }
                else{
                    System.out.println("Invalid reservation date!");
                    System.out.print("Enter Reservation Date (dd/mm/yyyy): ");
                }
            }catch(DateTimeException e){
                System.out.println("Invalid reservation date!");
                System.out.print("Enter Reservation Date (hh-mm): ");
            }
        }

        System.out.print("Enter Reservation Time (hh-mm): ");
        LocalTime time;
        while(true){
            try{
                time_str = sc.nextLine();
                String[] time_arr = time_str.split("-", 2);
                time = LocalTime.of(Integer.parseInt(time_arr[0]), Integer.parseInt(time_arr[1]));
                if(time.isAfter(LocalTime.of(20,30))){
                    System.out.println("Restaurant closes at 21-00!");
                }else if(time.isBefore(LocalTime.of(9,0))){
                    System.out.println("Restaurant opens at 09-00!");
                } else if(today && time.isAfter(LocalTime.now())) {
                    break;
                }else if(!today){
                    break;
                }
                System.out.println("Invalid reservation time!");
                System.out.print("Enter Reservation Time (hh-mm): ");
            }catch(DateTimeException e){
                System.out.println("Invalid reservation time!");
                System.out.print("Enter Reservation Time (hh-mm): ");
            }
        }

        LocalDateTime date_time = LocalDateTime.of(date, time);

        if (!ReservationListControl.checkAvailability(pax, date_time)){
            System.out.println("Sorry no available tables at " + time_str + " on " + date_str + "!");
            System.out.println();
            return;
        }

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        int phone;
        while(true){
            try{
                System.out.print("Enter Customer Contact Number: ");
                phone = sc.nextInt();
                sc.nextLine();
                if(phone<100000000 && phone>79999999){
                    break;
                }else{
                    System.out.println("Invalid phone number!");
                    System.out.println();
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid phone number!");
                System.out.println();
                sc = new Scanner(System.in);
            }
        }

        ReservationListControl.addReservation(name, phone, pax, date_time);
        System.out.println("Reservation for " + name + " at " + time_str + " on " + date_str + " has been recorded!");
        System.out.println();
    }

}
