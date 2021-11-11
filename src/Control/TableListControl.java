package Control;

import Entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

/**
 * This class contains the functions used to control the list of tables.
 */
public class TableListControl {

    /**
     * Number to press to go back in table list page.
     */
    public static final int BACK_OPTION =0;

    /**
     * Edits the list of tables.
     */
    public static void editTable() {
        Scanner sc = new Scanner(System.in);
        int choice=-1;

        while(choice != 0) {
            System.out.println("=======Edit List of Tables=======");
            System.out.println("|1. Add Table                   |");
            System.out.println("|2. Delete Table                |");
            System.out.println("|3. Print Current Table Details |");
            System.out.println("|0. Back                        |");
            System.out.println("=================================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1-> TableListControl.addTable();
                    case 2-> TableListControl.deleteTable();
                    case 3->{
                        TableListControl.printAllTables();
                        System.out.print("Press any key to go back ");
                        sc.nextLine();
                        System.out.println();
                    }
                    case 0->{}
                    default->{
                        System.out.println("Invalid Option!");
                        System.out.println();
                    }

                }
            }
        }
    }

    /**
     * Create a new <code>Table</code> and adds it to the list.
     */
    private static void addTable(){
        Scanner sc = new Scanner(System.in);
        int maxPax;
        while (true) {
            System.out.print("Enter number of pax: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                maxPax = sc.nextInt();
                sc.nextLine();
                if(maxPax%2!=0 || maxPax<2 || maxPax>10){
                    System.out.println("Invalid Pax");
                }else{
                    break;
                }
            }
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
    private static void deleteTable(){
        Scanner sc = new Scanner(System.in);
        int choice;
        printAllTables();
        while(true){
            System.out.print("Enter Table ID to delete: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                choice = sc.nextInt();
                sc.nextLine();
                break;
            }
        }
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
     * Gets the <code>Table</code> object with the requested ID.
     * @param ID the ID of the table.
     * @return the <code>Table</code> object, <code>null</code> if table ID is invalid.
     */
    public static Table getTable(int ID) {
        return TableList.getTableList().getList().getOrDefault(ID, null);
    }

    /**
     * Allocates a table to a customer. <br>
     * If customer has a reservation, the reserved table will the allocated. <br>
     * Else, a table will only be allocated if there is an available table. <br>
     */
    public static void allocateTable(){
        Scanner sc = new Scanner(System.in);
        int choice;

        if(LocalTime.now().isAfter(LocalTime.of(20,40))){
            System.out.println("Sorry kitchen has closed!");
            System.out.println();
            return;
        }
        while(true){
            System.out.println("=Does the customer have a reservation?=");
            System.out.println("|1. Yes                               |");
            System.out.println("|2. No                                |");
            System.out.println("|0. Back                              |");
            System.out.println("=======================================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            switch(choice){
                case 1->{
                    if(!ReservationListControl.checkReservation()){
                        System.out.println("No such reservation!");
                        System.out.println();
                    }
                    return;
                }
                case 2->{
                    int pax;
                    System.out.print("Enter number of Pax: ");
                    while(true) {
                        if(!sc.hasNextInt()){
                            System.out.println("Please enter a number!");
                            System.out.println();
                            sc.nextLine();
                        }else {
                            pax = sc.nextInt();
                            sc.nextLine();
                            if(pax<=0){
                                System.out.println("Invalid Pax!");
                                System.out.print("Enter number of Pax: ");
                            }else{
                                break;
                            }
                        }
                    }
                    updateTables();
                    Table table = TableListControl.chooseTable(pax);
                    if(table!=null){
                        assign(table, new Customer(pax));
                        System.out.println("Table " + table.getTableId()+ " has been allocated!");
                    }else{
                        System.out.println("No available tables!");
                    }
                    System.out.println();
                    return;
                }
                case 0->{
                    return;
                }
                default->{
                    System.out.println("Invalid Option!");
                    System.out.println();
                }
            }
        }
    }


    /**
     * Prints the invoice for the chosen table.<br>
     * Sets the table as available.
     */
    public static void clearTable(){
        Scanner sc = new Scanner(System.in);
        int choice;
        Table table;
        boolean member;

        while(true){
            System.out.println("===================Clear Table====================");
            TableListControl.printOccupied();
            System.out.print("Select Table ID or " + TableListControl.BACK_OPTION + " to go back: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            if (choice == TableListControl.BACK_OPTION){
                return;
            }
            table = TableListControl.getTable(choice);
            if (table!=null && table.isOccupied()){
                break;
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }

        if (table.getCustomer().isMember()){
            member = table.getCustomer().isMember();
        }else{
            while(true){
                System.out.println("==Is customer a member?==");
                System.out.println("|1. Yes                 |");
                System.out.println("|2. No                  |");
                System.out.println("=========================");
                System.out.print("Please enter your choice: ");
                if(!sc.hasNextInt()){
                    System.out.println("Please enter a number!");
                    System.out.println();
                    sc.nextLine();
                    continue;
                }else {
                    choice = sc.nextInt();
                    sc.nextLine();
                }
                if(choice==1){
                    int phone;
                    while(true){
                        try{
                            System.out.print("Enter Customer contact number: ");
                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                            }else {
                                phone = sc.nextInt();
                                sc.nextLine();
                                if (phone < 100000000 && phone > 79999999) {
                                    break;
                                } else {
                                    System.out.println("Invalid phone number!");
                                    System.out.println();
                                }
                            }
                        }
                        catch(InputMismatchException e){
                            System.out.println("Invalid phone number!");
                            System.out.println();
                            sc = new Scanner(System.in);
                        }
                    }
                    member = MemberListControl.checkMember(phone);
                    if(member){
                        System.out.println("Customer is a member!");
                        break;
                    }else{
                        System.out.println("Customer is not a member!");
                    }
                    System.out.println();
                }else if(choice==2){
                    member = false;
                    break;
                } else{
                    System.out.println("Invalid Option!");
                    System.out.println();
                }

            }
        }

        System.out.println("Settling bill for Table " + table.getTableId() + "...");

        TableListControl.generateInvoice(table, member);
        System.out.println("Bill has been settled!");
        TableListControl.available(table);
        System.out.print("Press any key to continue ");
        sc.nextLine();
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
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
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
     * Prints the invoice for a table.
     * @param table the table to print the invoice for.
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
     * Prints the basic details of a table.
     * @param table  the table to print the details for.
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
     * @param table the table to print details for.
     */
    public static void printTableDetails(Table table){
        System.out.println("======================Table Details=======================");
        System.out.printf("|| Table ID: %-43s||\n", table.getTableId() );
        if (table.isOccupied()){
            System.out.println("|| Status: Occupied                                     ||\n");
            if(table.hasOrder()){
                table.getOrder().printOrder();
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
     * Reserves a table for the customer.
     * @param table the table to reserve.
     * @param reservation the details of the reservation.
     */
    public static void reserve(Table table, Reservation reservation) {
        table.setReserved(reservation.getCustomer(), reservation.getCustomer().getPax(), reservation.getTime());
    }

    /**
     * Allocates a table to the customer.
     * @param table the table to assign to the customer.
     * @param customer the customer that has been allocated to this table.
     */
    public static void assign(Table table, Customer customer) {
        table.setOccupied(customer, customer.getPax());
    }

    /**
     * Sets a table as available.
     * @param table the table to set as available.
     */
    public static void available(Table table) {
        table.setAvailable();
    }

    /**
     * Saves the list of tables.
     */
    public static void saveTableList(){
        FileEditor.writeTables(TableList.getTableList().getList());
    }


}
