package Control;

import Entity.MemberList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberListControl {


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
                case 1-> addMembers();
                case 2-> removeMembers();
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

    public static void addMembers(){
        Scanner sc = new Scanner(System.in);
        int phone;
        while(true){
            try{
                System.out.print("Enter Customer contact number: ");
                if(!sc.hasNextInt()){
                    System.out.println("Invalid phone number!");
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

        if (checkMember(phone)){
            System.out.println("Customer already a member!");
        }else{
            MemberList.getMemberList().add(phone);
            System.out.println("Customer (" + phone + ") has been added to the Members List!");
        }
        System.out.println();
    }

    public static void removeMembers(){
        Scanner sc = new Scanner(System.in);
        int phone;
        while(true){
            try{
                System.out.print("Enter Customer contact number: ");
                if(!sc.hasNextInt()){
                    System.out.println("Invalid phone number!");
                    System.out.println();
                    sc.nextLine();
                }else{
                    phone = sc.nextInt();
                    sc.nextLine();
                    if(phone<100000000 && phone>79999999){
                        break;
                    }else{
                        System.out.println("Invalid phone number!");
                        System.out.println();
                    }
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid phone number!");
                System.out.println();
            }
        }
        if(!checkMember(phone)){
            System.out.println("Customer is not a member!");
        }else{
            MemberList.getMemberList().remove(phone);
            System.out.println("Customer has been removed from the Members List!");
        }
        System.out.println();
    }

    public static boolean checkMember(int phone){
        return MemberList.getMemberList().getList().contains(phone);
    }

    public static void saveMembers(){
        FileEditor.writeMembers(MemberList.getMemberList().getList());
    }

}
