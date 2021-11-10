package Control;

import Entity.MemberList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberListControl {


    public static void addMembers(){
        Scanner sc = new Scanner(System.in);
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
