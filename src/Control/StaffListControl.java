package Control;

import Entity.Role;
import Entity.Staff;
import Entity.StaffList;

import java.util.HashMap;
import java.util.Scanner;


/**
 * This class contains the functions used to control the list of staffs.
 */
public class StaffListControl {

    /**
     * Prints the details of all managers.
     */
    public static void printManagers(){
        StaffList.getStaffList().printManager();
    }

    /**
     * Prints the details of all staffs.
     */
    public static void printStaffs(){
        StaffList.getStaffList().printStaff();
    }

    /**
     * Add or remove staffs from the restaurant.<br>
     * The user will not be able to remove himself.
     * @param staffID the staffID of the staff calling this function.
     */
    public static void editStaff(int staffID) {
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
                    case 1 -> addStaff();
                    case 2 -> removeStaff(staffID);
                    case 3 -> {
                        StaffList.getStaffList().printStaffList();
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
     * Adds a staff into the list of staffs.
     */
    private static void addStaff(){
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
        StaffList.getStaffList().addStaff(new Staff(name, id, gender, role));
        System.out.println(name + "(" + role + ", "+ gender + ")" + " has been added!");
        System.out.println();
    }

    /**
     * Removes a staff from the list of staffs.
     * @param staffID the staff ID of the staff to remove.
     */
    private static void removeStaff(int staffID){
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("=============Deleting Staff==============");
            StaffList.getStaffList().printStaffList();
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
                StaffList.getStaffList().removeStaff(choice);
                System.out.println("Staff has been deleted!");
            } else {
                System.out.println("Invalid Staff ID");
            }
            System.out.println();
        }
    }

    /**
     * Returns the <code>Staff</code> object of the specified staff ID.
     * @param staffID the staff ID of the staff.
     * @return the <code>Staff</code> object, <code>null</code> if staff ID is invalid.
     */
    public static Staff getStaff(int staffID){
        HashMap<Integer, Staff> staffList = StaffList.getStaffList().getList();
        return staffList.getOrDefault(staffID, null);
    }

    /**
     * Checks if name is in the list of staff.
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

    /**
     * Checks if the staffI D is in the list of staffs.
     * @param staffID the staff ID to check.
     * @return <code>true</code> if staff ID is in the list, <code>false</code> otherwise.
     */
    private static boolean checkStaff(int staffID){
        return StaffList.getStaffList().getList().containsKey(staffID);
    }


    /**
     * Checks if the staff corresponds to the role.
     * @param staffID the staff ID to check.
     * @param role the role to check.
     * @return <code>true</code> if the staff has input role, <code>false</code> otherwise.
     */
    public static boolean checkRole(int staffID, int role){
        if(role==1){
            return StaffList.getStaffList().getRole(staffID)==Role.Manager;
        }else if (role==2){
            return StaffList.getStaffList().getRole(staffID)==Role.Staff;
        }
        return false;
    }

    /**
     * Saves the list of staff.
     */
    public static void saveStaffList(){
        FileEditor.writeStaff(StaffList.getStaffList().getList());
    }

    /**
     * Prints the action menu of according to the role of the staff.
     * @param staffID the staff ID of the staff.
     */
    public static void printAction(int staffID) {
        Role role;
        if(StaffList.getStaffList().getList().containsKey(staffID)){
            role = StaffList.getStaffList().getRole(staffID);
        }else{
            role = null;
        }

        if(role==Role.Staff){
            System.out.println("=============Welcome=============");
            System.out.println("|1. Take Order                  |");
            System.out.println("|2. Clear Table                 |");
            System.out.println("|3. Create Reservation          |");
            System.out.println("|4. Delete Reservation          |");
            System.out.println("|5. Print Reservation List      |");
            System.out.println("|6. Check Table Details         |");
            System.out.println("|7. Allocate Table              |");
            System.out.println("|8. Edit Member List            |");
            System.out.println("|9. Close Shop                  |");
            System.out.println("|10. Log Out                    |");
            System.out.println("=================================");
            System.out.print("Please enter your choice: ");
        }else if (role == Role.Manager){
            System.out.println("=============Welcome=============");
            System.out.println("|1. Take Order                  |");
            System.out.println("|2. Clear Table                 |");
            System.out.println("|3. Create Reservation          |");
            System.out.println("|4. Delete Reservation          |");
            System.out.println("|5. Print Reservation List      |");
            System.out.println("|6. Check Table Details         |");
            System.out.println("|7. Allocate Table              |");
            System.out.println("|8. Edit Member List            |");
            System.out.println("|9. Edit Menu                   |");
            System.out.println("|10. Edit Staff List            |");
            System.out.println("|11. Edit Table List            |");
            System.out.println("|12. Generate Sales Report      |");
            System.out.println("|13. Close Shop                 |");
            System.out.println("|14. Log Out                    |");
            System.out.println("=================================");
            System.out.print("Please enter your choice: ");
        }else{
            System.out.println("Invalid Option!");
            System.out.println();
        }
    }
}
