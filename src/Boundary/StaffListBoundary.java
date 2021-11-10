package Boundary;

import Control.ManagerControl;
import Control.StaffControl;

import java.util.Scanner;

public class StaffListBoundary {

    /**
     * Prints the action menu for this manager.
     * @return
     */
    public static void printManagerActions(){
        Scanner sc = new Scanner(System.in);
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
        while(!sc.hasNextInt()){
            System.out.println("Please enter a number!");
            System.out.println();
            sc.nextLine();
        }

    }

    /**
     * Prints the action menu for this staff.
     * @return
     */
    public static void printStaffActions(){
        Scanner sc = new Scanner(System.in);
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
        System.out.println("|10. LogOut                     |");
        System.out.println("=================================");
        System.out.print("Please enter your choice: ");

        while(!sc.hasNextInt()){
            System.out.println("Please enter a number!");
            System.out.println();
            sc.nextLine();
        }



    }


    public static void printInvalid(){
        System.out.println("Invalid Option!");
        System.out.println();
    }

}
