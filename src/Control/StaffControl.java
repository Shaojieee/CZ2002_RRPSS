package Control;

import Entity.*;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffControl {


    /**
     * Runs the function associated with the choice as displayed by <code>printActions()</code>.
     * @param choice  the choice selected by the staff.
     * @return        <code>true</code> if staff chooses to log out, <code>false</code> otherwise
     */
    public static boolean getAction(int choice, int staffID){
        switch(choice){
            case 1 -> createOrder(staffID);
            case 2 -> clearTable();
            case 3 -> ReservationListControl.createReservation();
            case 4 -> ReservationListControl.deleteReservation();
            case 5 -> ReservationListControl.printReservations();
            case 6 -> TableListControl.checkTableDetails();
            case 7 -> allocateTable();
            case 8 -> editMembers();
            case 9 -> exitProgram();
            case 10 -> {
                return true;
            }
            default -> {
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        return false;
    }

    /**
     * Creates an order for the chosen table.<br>
     * Add or remove food from the order.
     */
    public static void createOrder(int staffID){
        Scanner sc = new Scanner(System.in);
        int choice;
        Table table;

        if(LocalTime.now().isAfter(LocalTime.of(20,40))){
            System.out.println("Sorry kitchen has closed!");
            System.out.println();
            return;
        }

        /* Choosing table to take order */
        while(true){
            System.out.println("==================Creating Order==================");
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
            if (table==null){
                System.out.println("Invalid Option!");
                System.out.println();
            } else if (table.isOccupied()){
                break;
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        Staff staff = StaffListControl.getStaff(staffID);
        Order newOrder = new Order(staff);

        /* Taking order */
        while(true) {
            System.out.println("=======Creating Order=======");
            System.out.println("|1. Add Food               |");
            System.out.println("|2. Remove Food            |");
            System.out.println("|3. View Order             |");
            System.out.println("|4. Send Order             |");
            System.out.println("|0. Back                   |");
            System.out.println("============================");
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
                case 1-> OrderControl.addFood(newOrder);
                case 2-> OrderControl.removeFood(newOrder);
                case 3->{
                    OrderControl.printOrder(newOrder);
                    System.out.print("Press any key to go back ");
                    sc.nextLine();
                    System.out.println();
                }
                case 4-> {
                    if (newOrder.getSize()==0){
                        System.out.println("Order has no item! Unable to submit order!");
                        System.out.println();
                        break;
                    }

                    if (table.hasOrder()){
                        OrderControl.appendOrder(table.getOrder(), newOrder);
                    }else{
                        table.setOrder(newOrder);
                    }

                    System.out.println("Order has been successfully submitted!");
                    System.out.println();
                    return;
                }
                case 0->{
                    System.out.println("Order not submitted!");
                    System.out.println();
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
        TableListControl.clearTable(table);
        System.out.print("Press any key to continue ");
        sc.nextLine();
        System.out.println();
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
                    TableListControl.allocateTable();
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
     * Add or remove members. <br>
     */
    public static void editMembers(){
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("=====Edit Member List=====");
            System.out.println("|1. Add Member           |");
            System.out.println("|2. Remove Member        |");
            System.out.println("|0. Back                 |");
            System.out.println("==========================");
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
                case 1-> MemberListControl.addMembers();
                case 2-> MemberListControl.removeMembers();
                case 0->{
                    return;
                }
                default ->{
                    System.out.println("Invalid Option!");
                    System.out.println();
                }
            }
        }
    }

    /**
     * Saves all the data in the program and terminate it
     */
    public static void exitProgram() {
        if(!TableListControl.allEmpty()){
            System.out.println("There are still occupied tables!");
            System.out.println();
            return;
        }
        System.out.println("Saving all data...");
        MemberListControl.saveMembers();
        TableListControl.saveTableList();
        MenuControl.saveMenu();
        StaffListControl.saveStaffList();
        SalesReportControl.saveSalesReport();
        ReservationListControl.saveReservationList();
        System.out.println("All data saved! Have a good day!");
        System.out.println();
        System.exit(0);
    }
}
