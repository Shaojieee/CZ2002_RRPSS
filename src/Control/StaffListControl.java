package Control;

import Boundary.StaffListBoundary;
import Entity.Role;
import Entity.Staff;
import Entity.StaffList;

import java.util.HashMap;
import java.util.Scanner;

public class StaffListControl {

    /**
     * Prints the details of all staffs.
     */
    public static void printStaffList(){
        StaffList.getStaffList().printStaffList();
    }

    public static void performActions(int staffID){
        Scanner sc = new Scanner(System.in);
        Role role = StaffList.getStaffList().getRole(staffID);
        int choice;
        boolean exit = false;
        if(role==Role.Manager){
            do{
                StaffListBoundary.printManagerActions();
                if(!sc.hasNextInt()){
                    System.out.println("Please enter a number!");
                    System.out.println();
                    sc.nextLine();
                }else{
                    choice = sc.nextInt();
                    sc.nextLine();
                    exit = ManagerControl.getAction(choice,staffID);
                }
            }while(!exit);

        }else if(role == Role.Staff){

            do{
                StaffListBoundary.printStaffActions();
                if(!sc.hasNextInt()){
                    System.out.println("Please enter a number!");
                    System.out.println();
                    sc.nextLine();
                }else{
                    choice = sc.nextInt();
                    sc.nextLine();
                    exit = StaffControl.getAction(choice, staffID);
                }
            }while(!exit);
        }else{
            System.out.println("Invalid Option!");
            System.out.println();
        }
    }

    public static void addStaff(){
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.print("Enter Staff Name: ");
        String name = sc.nextLine();
        if (checkStaff(name)){
            System.out.println("Staff already exist");
            return;
        }
        String gender;
        while (true){
            System.out.println("======Gender======");
            System.out.println("|1. Male         |");
            System.out.println("|2. Female       |");
            System.out.println("==================");
            System.out.print("Select Gender: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            if (choice == 1) {
                gender = "M";
                break;
            } else if (choice == 2) {
                gender = "F";
                break;
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        int id=1;
        while(checkStaff(id)){
            id++;
        }
        Role role;
        while (true) {
            System.out.println("====Job Titles====");
            System.out.println("|1. Manager      |");
            System.out.println("|2. Staff        |");
            System.out.println("==================");
            System.out.print("Select Job Title: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            if (choice == 1) {
                role = Role.Manager;
                break;
            } else if (choice == 2) {
                role = Role.Staff;
                break;
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        StaffList.getStaffList().add(new Staff(name, id, gender, role));
        System.out.println(name + "(" + role + ", "+ gender + ")" + " has been added!");
        System.out.println();
    }

    public static void removeStaff(int staffID){
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("=============Deleting Staff==============");
            printStaffList();
            System.out.print("Select Staff ID to delete or " + "0 to back: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            if(choice==0){
                return;
            }
            if(staffID==choice){
                System.out.println("Cannot delete own account!");

            } else if (checkStaff(choice)) {
                StaffList.getStaffList().remove(choice);
                System.out.println("Staff has been deleted!");
            } else {
                System.out.println("Invalid Staff ID");
            }
            System.out.println();
        }
    }

    public static Staff getStaff(int staffID){
        HashMap<Integer, Staff> staffList = StaffList.getStaffList().getList();
        return staffList.getOrDefault(staffID, null);
    }

    /**
     * Checks if name is in this list.
     * @param name the name of the staff to be checked.
     * @return <code>true</code> if name is in the list, <code>false</code> otherwise.
     */
    private static boolean checkStaff(String name){
        for (Staff staff : StaffList.getStaffList().getList().values()){
            if (staff.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private static boolean checkStaff(int staffID){
        return StaffList.getStaffList().getList().containsKey(staffID);
    }

    public static void saveStaffList(){
        FileEditor.writeStaff(StaffList.getStaffList().getList());
    }
}
