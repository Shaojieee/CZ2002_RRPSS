package Boundary;

import Control.*;
import Entity.Role;

import java.util.Scanner;

public class HomePage {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int staffID;
        boolean exit=false;
        while (true) {
            StaffListControl.printStaffList();
            System.out.print("Select Staff ID: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                staffID = sc.nextInt();
                sc.nextLine();

                while(!exit){
                    Role role = StaffListControl.getAction(staffID);
                    if(!sc.hasNextInt()){
                        System.out.println("Please enter a number!");
                        System.out.println();
                        sc.nextLine();
                    }else {
                        int action = sc.nextInt();
                        sc.nextLine();
                        exit = getAction(action, role, staffID);
                    }
                }
            }

        }
    }

    public static boolean getAction(int choice, Role role, int staffID) {
        if (role == Role.Manager) {
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
        } else {
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

