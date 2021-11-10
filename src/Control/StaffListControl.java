package Control;

import Boundary.StaffListBoundary;
import Entity.Role;
import Entity.StaffList;

import java.util.Scanner;

public class StaffListControl {

    /**
     * Prints the details of all staffs.
     */
    public static void printStaffList(){
        StaffList.getStaffList().printStaffList();

    }


    public static boolean performActions(int staffID){
        Scanner sc = new Scanner(System.in);
        Role role = StaffList.getStaffList().getStaffRole(staffID);
        int choice;
        if(role==Role.Manager){
            StaffListBoundary.printManagerActions();
            while(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }
            choice = sc.nextInt();
            return ManagerControl.getAction(choice);
        }else if(role == Role.Staff){
            StaffListBoundary.printStaffActions();
            while(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }
            choice = sc.nextInt();
            return StaffControl.getAction(choice);
        }else{
            StaffListBoundary.printInvalid();
            System.out.println();
            return false;
        }
    }


    public static void addStaff(){

    }

    public static void addManager(){

    }

    public static void removeStaff(){

    }



}
