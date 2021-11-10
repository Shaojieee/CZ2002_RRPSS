package Control;

import Entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

public class TableListControl {


    public static final int BACK_OPTION =0 ;

    /**
     * Create a new <code>Table</code> and adds it to the list.
     */
    public static void addTable(){
        Scanner sc = new Scanner(System.in);
        int maxPax;
        System.out.print("Enter number of pax: ");
        maxPax = sc.nextInt();
        sc.nextLine();
        while(maxPax%2!=0 || maxPax<2 || maxPax>10){
            System.out.println("Invalid Pax");
            System.out.print("Enter number of pax: ");
            maxPax = sc.nextInt();
            sc.nextLine();
        }
        int id = 1;
        Set<Integer> tableID = TableList.getTableList().getList().keySet();
        while(tableID.contains(id)){
            id++;
        }
        TableList.getTableList().addTable(new Table(maxPax, id));

        ReservationList.getReservationList().addTable(id);

        saveTableList();

        System.out.println("Table (Pax: " + maxPax + ") has been added!");
        System.out.println();
    }

    /**
     * Remove table from the list according to the ID.
     */
    public static void deleteTable(){
        Scanner sc = new Scanner(System.in);
        int choice;
        printAllTables();
        System.out.print("Enter Table ID to delete: ");
        choice = sc.nextInt();
        sc.nextLine();
        Table table = getTable(choice);
        if(table==null){
            System.out.println("Invalid Option!");
            System.out.println();
        }else if(table.isOccupied()){
            System.out.println("Table is currently occupied!");
        }else{
            if(ReservationListControl.checkDelete(table.getTableId())){
                TableList.getTableList().removeTable(table.getTableId());
                saveTableList();
                ReservationListControl.deleteTable(table.getTableId());
            }else{
                System.out.println("Unable to delete table as there will be unallocated reservations!");
            }
        }
        System.out.println();

    }

    /**
     * Gets the <code>Table</code> object with teh requested ID.
     * @param ID the ID of the table.
     * @return the <code>Table</code> object.
     */
    public static Table getTable(int ID) {
        return TableList.getTableList().getList().getOrDefault(ID, null);
    }

    public static void allocateTable(){
        Scanner sc = new Scanner(System.in);
        int pax;
        System.out.print("Enter number of Pax: ");
        do {
            pax = sc.nextInt();
            sc.nextLine();
            if(pax<=0){
                System.out.println("Invalid Pax!");
                System.out.print("Enter number of Pax: ");
            }
        }while(pax<=0);
        updateTables();
        Table table = TableListControl.chooseTable(pax);
        if(table!=null){
            assign(table, new Customer(pax));
            System.out.println("Table " + table.getTableId()+ " has been allocated!");
        }else{
            System.out.println("No available tables!");
        }
        System.out.println();
    }

    /**
     * Allocates an available table that can accommodate the provided number of pax.
     * @param pax the number of pax to allocate.
     * @return the <code>Table</code> object allocated to the customer, <code>null</code> if there are no suitable tables.
     */
    public static Table chooseTable(int pax){
        Table allocated = null;
        for(Table table : TableList.getTableList().getList().values()){
            if(table.isOccupied()||table.isReserved()){
                continue;
            }
            if(table.getMaxPax()>=pax){
                if (allocated==null){
                    allocated = table;
                }else if(allocated.getMaxPax() > table.getMaxPax()){
                    allocated = table;
                }
            }
        }
        return allocated;
    }

    /**
     * Updates the status of the reservation according to the current time.
     */
    private static void updateTables(){
        ReservationListControl.updateTableStatus();
    }

    /**
     * Checks whether all the tables are available.
     * @return <code>true</code> if all tables are available, <code>false</code> otherwise.
     */
    public static boolean allEmpty() {
        for(Table table : TableList.getTableList().getList().values()){
            if(table.isOccupied()){
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the list of tables.
     */
    public static void checkTableDetails(){
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            printAllTables();
            System.out.print("Select Table ID to view reservation or table order or " + BACK_OPTION + " to go back: ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice==BACK_OPTION){
                return;
            }
            if(getTable(choice)!=null){
                printTableDetails(getTable(choice));
                System.out.print("Press any key to go back ");
                sc.nextLine();
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
    }

    /**
     * Prints the list of occupied tables.
     */
    public static void printOccupied() {
        updateTables();
        int count = 0;
        System.out.println("=================Occupied Tables==================");
        System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", "Table ID", "Status", "Pax", "Size");
        for (Table table : TableList.getTableList().getList().values()){
            if (table.isOccupied()){
                count++;
                printBasicDetails(table);
            }
        }
        if (count==0){
            System.out.println("||     No tables are occupied at the moment     ||");
        }
        System.out.println("==================================================");
    }

    /**
     * Prints the list of tables.
     */
    public static void printAllTables() {
        updateTables();
        System.out.println("======================Tables======================");
        System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", "Table ID", "Status", "Pax", "Size");
        for (Table table: TableList.getTableList().getList().values()){
            printBasicDetails(table);
        }
        if(TableList.getTableList().size()==0){
            System.out.println("||    There are no tables in the restaurant     ||");
        }
        System.out.println("==================================================");
    }

    /**
     * Prints the invoice for this table.
     * @param member <code>true</code> if customer is a member, <code>false</code> otherwise.
     */
    public static void generateInvoice(Table table,boolean member) {
        if (table.getOrder()==null){
            System.out.println("No order taken");
        }else{
            OrderControl.printInvoice(table.getOrder(),table.getTableId(), member);
        }

    }

    /**
     * Prints the basic details this table.
     */
    public static void printBasicDetails(Table table) {
        if (table.isOccupied()){
            System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", table.getTableId(), "Occupied", table.getPax(), table.getMaxPax());
        }else if(table.isReserved()){
            System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", table.getTableId(), "Reserved at " + table.getReservedTime().toLocalTime(),table.getPax(), table.getMaxPax());
        }else{
            System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", table.getTableId(), "Available", "-", table.getMaxPax());
        }

    }

    /**
     * Prints more in-depth detail of the specified table.
     * @param table Contains the detail of the table to be printed.
     */
    public static void printTableDetails(Table table){
        System.out.println("======================Table Details=======================");
        System.out.printf("|| Table ID: %-43s||\n", table.getTableId() );
        if (table.isOccupied()){
            System.out.println("|| Status: Occupied                                     ||\n");
            if(table.hasOrder()){
                OrderControl.printOrder(table.getOrder());
            }else{
                System.out.println("==========================Order===========================");
                System.out.println("|          This order does not have any items!           |");
                System.out.println("==========================================================");
            }

        }else if(table.isReserved()){
            LocalDate date = table.getReservedTime().toLocalDate();
            LocalTime time = table.getReservedTime().toLocalTime();
            DateTimeFormatter date_format = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String date_str = date_format.format(date);

            DateTimeFormatter time_format = DateTimeFormatter.ofPattern("H:mm");
            String time_str = time_format.format(time);
            System.out.println("||Status: Reserved                                      ||\n");
            System.out.printf("|| Date: %-11s Time: %-5s%-24s||\n", date_str, time_str, "");
            System.out.printf("|| Name: %-15s Contact: %-10s Pax: %-2s%4s||\n",table.getCustomer().getName(), table.getCustomer().getPhone(), table.getPax(), "");
        }else{
            System.out.printf("|| Status: %-20s Maximum Pax: %-4s%31s||\n", "Available", table.getMaxPax(), "");

        }
        System.out.println("==========================================================");
    }

    /**
     * Unreserved this table.
     */
    public static void unreserve(Table table){
        table.setReserved(false);
        table.setOccupied(false);
        table.setCustomer(null);
        table.setPax(0);
        table.setReservedTime(null);
    }

    /**
     * Reserves this table for the customer.
     * @param reservation the details of the reservation.
     */
    public static void reserve(Table table, Reservation reservation) {
        table.setReserved(true);
        table.setCustomer(reservation.getCustomer());
        table.setPax(reservation.getCustomer().getPax());
        table.setReservedTime(reservation.getTime());
    }

    /**
     * Allocates this table to the customer.
     * @param customer the customer that has been allocated to this table.
     */
    public static void assign(Table table, Customer customer) {
        table.setOccupied(true);
        table.setReserved(false);
        table.setCustomer(customer);
        table.setPax(customer.getPax());
    }

    /**
     * Sets this table as available.
     */
    public static void clearTable(Table table) {
        table.setOccupied(false);
        table.setReserved(false);
        table.setCustomer(null);
        table.setOrder(null);
        table.setPax(0);
    }

    /**
     * Saves the list of tables.
     */
    public static void saveTableList(){
        FileEditor.writeTables(TableList.getTableList().getList());
    }


}
