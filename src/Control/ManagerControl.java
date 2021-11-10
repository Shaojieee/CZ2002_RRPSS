package Control;

import Entity.*;

import java.util.Scanner;

public class ManagerControl extends StaffControl{



    /**
     * Runs the function associated with the choice as displayed by <code>printActions()</code>.
     * @param choice  the choice selected by this manager.
     * @return        <code>true</code> if manager chooses to log out, <code>false</code> otherwise.
     */
    public static boolean getAction(int choice, int staffID){
        switch(choice){
            case 1-> createOrder(staffID);
            case 2-> clearTable();
            case 3-> ReservationListControl.createReservation();
            case 4-> ReservationListControl.deleteReservation();
            case 5-> ReservationListControl.printReservations();
            case 6-> TableListControl.checkTableDetails();
            case 7-> allocateTable();
            case 8-> editMembers();
            case 9-> editMenu();
            case 10-> editStaff(staffID);
            case 11-> editTable();
            case 12-> SalesReportControl.printRevenue();
            case 13-> exitProgram();
            case 14->{
                return true;
            }
            default->{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        return false;
    }


    /**
     * Add or remove tables from the restaurant.
     */
    private static void editTable() {
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
                    case 1:
                        TableListControl.addTable();
                    case 2:
                        TableListControl.deleteTable();

                    case 3:
                        TableListControl.printAllTables();
                        System.out.print("Press any key to go back ");
                        sc.nextLine();
                        System.out.println();
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Invalid Option!");
                        System.out.println();
                        break;
                }
            }
        }
    }


    /**
     * Add or remove staffs from the restaurant.
     * This manager will not be able to remove himself.
     */
    private static void editStaff(int staffID) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while(true) {
            System.out.println("=======Edit List of Staffs========");
            System.out.println("|1. Add Staff                    |");
            System.out.println("|2. Delete Staff                 |");
            System.out.println("|3. Print Current Staff Details  |");
            System.out.println("|0. Back                         |");
            System.out.println("==================================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> StaffListControl.addStaff();
                    case 2 -> StaffListControl.removeStaff(staffID);
                    case 3 -> {
                        StaffListControl.printStaffList();
                        System.out.print("Press any key to go back ");
                        sc.nextLine();
                        System.out.println();
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Invalid Option!");
                        System.out.println();
                    }
                }
            }
        }
    }

    /**
     * Add, remove or edit the menu.
     */
    private static void editMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("========Edit Menu=========");
            System.out.println("|1. Add Main Course      |");
            System.out.println("|2. Add Drinks           |");
            System.out.println("|3. Add Desserts         |");
            System.out.println("|4. Add Promotion Set    |");
            System.out.println("|5. Edit Menu Items      |");
            System.out.println("|6. Delete Menu Items    |");
            System.out.println("|0. Back                 |");
            System.out.println("==========================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> MenuControl.addFood(FoodType.MAINCOURSE);
                    case 2 -> MenuControl.addFood(FoodType.DRINK);
                    case 3 -> MenuControl.addFood(FoodType.DESSERT);
                    case 4 -> MenuControl.addFood(FoodType.PROMOTIONSET);
                    case 5 -> {
                        while (true) {
                            System.out.println("==========Edit Food===========");
                            MenuControl.printMenu(true);
                            System.out.print("Please enter your choice: ");
                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                            }else {
                                choice = sc.nextInt();
                                sc.nextLine();
                                if (choice == MenuControl.BACK_OPTION) {
                                    break;
                                }
                                FoodType action = MenuControl.getMenuAction(choice, true);
                                if (action == null) {
                                    System.out.println("Invalid Option!");
                                    System.out.println();
                                } else {
                                    MenuControl.editMenu(action);
                                }
                            }
                        }
                    }
                    case 6 -> {
                        while (true) {
                            System.out.println("========Deleting Food=========");
                            MenuControl.printMenu(true);
                            System.out.print("Please enter your choice: ");
                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                            }else {
                                choice = sc.nextInt();
                                sc.nextLine();
                                if (choice == MenuControl.BACK_OPTION) {
                                    break;
                                }
                                FoodType action = MenuControl.getMenuAction(choice, true);
                                if (action == null) {
                                    System.out.println("Invalid Option");
                                    System.out.println();

                                } else {
                                    MenuControl.deleteFood(action);
                                }
                            }
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Invalid Option!");
                        System.out.println();
                    }
                }
            }
        }
    }

}
