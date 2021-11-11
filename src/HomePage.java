import Control.*;
import Entity.Role;

import java.util.Scanner;

public class HomePage {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int staffID, role;
        boolean exit;
        while (true) {
            exit=false;
            System.out.println("===Select your role==");
            System.out.println("|1. Manager         |");
            System.out.println("|2. Staff           |");
            System.out.println("=====================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                role = sc.nextInt();
                sc.nextLine();
                while(!exit){
                    if(role==1){
                        StaffListControl.printManagers();

                    }else if(role==2){
                        StaffListControl.printStaffs();
                    }else{
                        System.out.println("Invalid Option!");
                        System.out.println();
                        continue;
                    }
                    System.out.print("Select your Staff ID: ");
                    if(!sc.hasNextInt()){
                        System.out.println("Please enter a number!");
                        System.out.println();
                        sc.nextLine();
                    }else{
                        staffID = sc.nextInt();
                        sc.nextLine();
                        if(StaffListControl.checkRole(staffID, role)){

                            while(!exit){
                                StaffListControl.printAction(staffID);
                                if(!sc.hasNextInt()){
                                    System.out.println("Please enter a number!");
                                    System.out.println();
                                    sc.nextLine();
                                }else{
                                    int action = sc.nextInt();
                                    sc.nextLine();
                                    exit = getAction(action, role, staffID);
                                }
                            }

                        }else{
                            System.out.println("Invalid Option!");
                            System.out.println();
                        }
                    }
                }



            }
        }
    }

    public static boolean getAction(int choice, int role, int staffID) {
        if (role == 1) {
            switch (choice) {
                case 1 -> OrderControl.createOrder(staffID);
                case 2 -> TableListControl.clearTable();
                case 3 -> ReservationListControl.createReservation();
                case 4 -> ReservationListControl.deleteReservation();
                case 5 -> ReservationListControl.printReservations();
                case 6 -> TableListControl.checkTableDetails();
                case 7 -> TableListControl.allocateTable();
                case 8 -> MemberListControl.editMembers();
                case 9 -> MenuControl.editMenu();
                case 10 -> StaffListControl.editStaff(staffID);
                case 11 -> TableListControl.editTable();
                case 12 -> SalesReportControl.printRevenue();
                case 13 -> exitProgram();
                case 14 -> {
                    return true;
                }
                default -> {
                    System.out.println("Invalid Option!");
                    System.out.println();
                }
            }
        } else if(role==2){
            switch (choice) {
                case 1 -> OrderControl.createOrder(staffID);
                case 2 -> TableListControl.clearTable();
                case 3 -> ReservationListControl.createReservation();
                case 4 -> ReservationListControl.deleteReservation();
                case 5 -> ReservationListControl.printReservations();
                case 6 -> TableListControl.checkTableDetails();
                case 7 -> TableListControl.allocateTable();
                case 8 -> MemberListControl.editMembers();
                case 9 -> exitProgram();
                case 10 -> {
                    return true;
                }
                default -> {
                    System.out.println("Invalid Option!");
                    System.out.println();
                }
            }
        }
        return false;
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

