package Control;

import Entity.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffControl {
    

    /**
     * Runs the function associated with the choice as displayed by <code>printActions()</code>.
     * @param choice  the choice selected by the staff.
     * @return        <code>true</code> if staff chooses to log out, <code>false</code> otherwise
     */
    public static boolean getAction(int choice){
        switch(choice){
            case 1 -> createOrder();
            case 2 -> clearTable();
            case 3 -> createReservation();
            case 4 -> deleteReservation();
            case 5 -> printReservations();
            case 6 -> checkTableDetails();
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
    public static void createOrder(){
        Scanner sc = new Scanner(System.in);
        int choice;
        Table table;
        TableList tableList = TableList.getTableList();
        Menu menu = Menu.getMenu();

        if(LocalTime.now().isAfter(LocalTime.of(20,40))){
            System.out.println("Sorry kitchen has closed!");
            System.out.println();
            return;
        }

        /* Choosing table to take order */
        while(true){
            System.out.println("==================Creating Order==================");
            tableList.printOccupied();
            System.out.print("Select Table ID or " + TableList.BACK_OPTION + " to go back: ");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == TableList.BACK_OPTION){
                return;
            }
            table = tableList.getTable(choice);
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

        Order newOrder = new Order(this);
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
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1->{
                    while(true){
                        System.out.println("=========Adding Food==========");
                        menu.printMenu(true);
                        System.out.print("Please enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if(choice == Menu.BACK_OPTION){
                            break;
                        }
                        FoodType action = menu.getMenuAction(choice, true);
                        if(action==null){
                            System.out.println("Invalid Option!");
                            System.out.println();
                            continue;
                        }

                        System.out.println("===================Creating Order===================");
                        menu.printCurrentMenu(action);
                        System.out.print("Select Food ID to add or " + Menu.BACK_OPTION + " to go back: ");
                        /* In specific food type menu page */
                        while (true) {
                            choice = sc.nextInt();
                            sc.nextLine();
                            if (choice == Menu.BACK_OPTION){
                                break;
                            }
                            Food food = menu.getFood(choice, action);
                            if (food == null) {
                                System.out.println("Invalid Option!");
                                System.out.println();
                            } else {
                                System.out.print("Enter Quantity: ");
                                choice = sc.nextInt();
                                sc.nextLine();
                                while (choice <= 0) {
                                    System.out.println("Invalid Quantity!");
                                    System.out.println();
                                    System.out.print("Enter Quantity: ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                }
                                newOrder.addFood(food, choice);
                                System.out.println(choice + " " + food.getName() + " has been added!");
                                System.out.println();
                                System.out.println("===================Creating Order===================");
                                menu.printCurrentMenu(action);
                                System.out.print("Select Food ID to add or " + Menu.BACK_OPTION + " to go back: ");
                            }
                        }

                    }
                }
                case 2->{
                    while(true) {
                        System.out.println("======================Removing Food=======================");
                        newOrder.printOrder();

                        if (newOrder.getSize() == 0) {
                            System.out.print("Press any key to go back ");
                            sc.nextLine();
                            System.out.println();
                            break;
                        } else {
                            System.out.print("Select Food ID or " + Order.BACK_OPTION + " to go back: ");
                            int foodId = sc.nextInt();
                            sc.nextLine();
                            if(foodId == Order.BACK_OPTION){
                                break;
                            }
                            Food food = menu.getFood(foodId);
                            if (newOrder.checkInOrder(food)){
                                System.out.print("Enter Quantity to remove: ");
                                choice = sc.nextInt();
                                sc.nextLine();
                                while(!newOrder.checkFoodQty(food, choice)){
                                    System.out.println("Invalid Quantity!");
                                    System.out.println();
                                    System.out.print("Enter Quantity to remove:");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                }
                                newOrder.removeFood(food, choice);
                                System.out.println(choice + " " + food.getName() + " has been removed!");
                            }else{
                                System.out.println("Invalid Option");
                            }
                            System.out.println();
                        }
                    }
                }
                case 3->{
                    newOrder.printOrder();
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
                        table.getOrder().appendOrder(newOrder);
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
        TableList tableList = TableList.getTableList();
        boolean member;

        while(true){
            System.out.println("===================Clear Table====================");
            tableList.printOccupied();
            System.out.print("Select Table ID or " + TableList.BACK_OPTION + " to go back: ");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == TableList.BACK_OPTION){
                return;
            }
            table = tableList.getTable(choice);
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
                choice = sc.nextInt();
                sc.nextLine();
                if(choice==1){
                    int phone;
                    while(true){
                        try{
                            System.out.print("Enter Customer contact number: ");
                            phone = sc.nextInt();
                            sc.nextLine();
                            if(phone<100000000 && phone>79999999){
                                break;
                            } else{
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
                    member = MemberList.getMemberList().checkMember(phone);
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

        table.generateInvoice(member);
        System.out.println("Bill has been settled!");
        table.clearTable();
        System.out.print("Press any key to continue ");
        sc.nextLine();
        System.out.println();
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

        ReservationList reservationList = ReservationList.getReservationList();

        if (!reservationList.checkAvailability(pax, date_time)){
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

        reservationList.addReservation(name, phone, pax, date_time);
        System.out.println("Reservation for " + name + " at " + time_str + " on " + date_str + " has been recorded!");
        System.out.println();
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

        ReservationList reservationList = ReservationList.getReservationList();
        if (reservationList.deleteReservation(name, phone, date_time)){
            System.out.println("Reservation for " + name + " has been deleted!");
        }else{
            System.out.println("No reservation under " + name + "!");
        }

        System.out.println();
    }

    /**
     * Prints the list of reservations.
     */
    public static void printReservations(){
        Scanner sc = new Scanner(System.in);
        ReservationList reservationList = ReservationList.getReservationList();
        reservationList.printReservations();
        System.out.println("Press any key to go back ");
        sc.nextLine();
    }

    /**
     * Prints the list of tables.
     */
    public static void checkTableDetails(){
        TableList tableList = TableList.getTableList();
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            tableList.printAllTables();
            System.out.print("Select Table ID to view reservation or table order or " + TableList.BACK_OPTION + " to go back: ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice==TableList.BACK_OPTION){
                return;
            }
            if(tableList.getTable(choice)!=null){
                tableList.printTableDetails(choice);
                System.out.print("Press any key to go back ");
                sc.nextLine();
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }

        }

    }

    /**
     * Allocates a table to a customer. <br>
     * If customer has a reservation, the reserved table will the allocated. <br>
     * Else, a table will only be allocated if there is an available table. <br>
     */
    public static void allocateTable(){
        Scanner sc = new Scanner(System.in);
        int choice;
        Table table;
        TableList tableList = TableList.getTableList();

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
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1->{
                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();
                    int phone;
                    while(true){
                        try{
                            System.out.print("Enter Customer contact number: ");
                            phone = sc.nextInt();
                            sc.nextLine();
                            if(phone<100000000 && phone>79999999){
                                break;
                            } else{
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
                    ReservationList reservationList = ReservationList.getReservationList();
                    if(reservationList.checkReservation(name, phone)){
                        if((table = reservationList.assignReservation(name,phone))!=null){
                            System.out.println("Table " + table.getTableId() + " has been assigned to " + name + "!");
                        }else{
                            System.out.println("No available tables!");
                        }
                    }else{
                        System.out.println(name + " does not have a reservation!");
                    }
                    System.out.println();
                    return;
                }
                case 2->{
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
                    ReservationList.getReservationList().updateTableStatus();
                    table = tableList.chooseTable(pax);
                    if(table!=null){
                        table.assign(new Customer(pax));
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
     * Add or remove members. <br>
     */
    public static void editMembers(){
        Scanner sc = new Scanner(System.in);
        MemberList memberList = MemberList.getMemberList();
        int choice;
        while(true) {
            System.out.println("=====Edit Member List=====");
            System.out.println("|1. Add Member           |");
            System.out.println("|2. Remove Member        |");
            System.out.println("|0. Back                 |");
            System.out.println("==========================");
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1->{
                    int phone;
                    while(true){
                        try{
                            System.out.print("Enter Customer contact number: ");
                            phone = sc.nextInt();
                            sc.nextLine();
                            if(phone<100000000 && phone>79999999){
                                break;
                            } else{
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


                    if (memberList.checkMember(phone)){
                        System.out.println("Customer already a member!");
                    }else{
                        memberList.addMembers(phone);
                        System.out.println("Customer (" + phone + ") has been added to the Members List!");
                    }
                    System.out.println();
                }
                case 2->{

                    int phone;
                    while(true){
                        try{
                            System.out.print("Enter Customer contact number: ");
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
                        }
                    }
                    if(!memberList.checkMember(phone)){
                        System.out.println("Customer is not a member!");
                    }else{
                        memberList.removeMembers(phone);
                        System.out.println("Customer has been removed from the Members List!");
                    }
                    System.out.println();
                }
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
        TableList tableList = TableList.getTableList();
        if(!tableList.allEmpty()){
            System.out.println("There are still occupied tables!");
            System.out.println();
            return;
        }
        System.out.println("Saving all data...");
        MemberList.getMemberList().saveMembers();
        TableList.getTableList().saveTableList();
        Menu.getMenu().saveMenu();
        StaffList.getStaffList().saveStaffList();
        SalesReport.getSalesReport().saveSalesReport();
        ReservationList.getReservationList().saveReservationList();
        System.out.println("All data saved! Have a good day!");
        System.out.println();
        System.exit(0);
    }
}
