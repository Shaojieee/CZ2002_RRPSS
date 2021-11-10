package Boundary;

import Control.StaffListControl;

import java.util.Scanner;

public class HomePage {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int choice;
        boolean exit=false;
        while (true) {
            StaffListControl.printStaffList();
            System.out.print("Select Staff ID: ");
            choice = sc.nextInt();
            sc.nextLine();
            StaffListControl.performActions(choice);
        }
    }
}

