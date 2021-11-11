package Entity;

import Control.FileEditor;

import java.util.HashMap;

/**
 * This class contains the list of staff of the restaurant. <br>
 * Only 1 instance can exist at all times.
 */
public class StaffList {

    /**
     * The singleton instance of <code>StaffList</code>.
     */
    private static StaffList staffList = null;

    /**
     * The HashMap containing the staffs.
     * Key is the staff ID. Value is the <code>Staff</code> Object.
     */
    private HashMap<Integer, Staff> list;

    /**
     * This constructor defines the list of staffs.
     * Loads the staffs' data into the list.
     */
    private StaffList(){
        this.list = FileEditor.loadStaff();
    }

    /**
     * Initializes the <code>StaffList</code> object if it has not been initialized.
     * @return the <code>StaffList</code> object.
     */
    public static StaffList getStaffList(){
        if (staffList==null){
            staffList = new StaffList();
        }
        return staffList;
    }

    public HashMap<Integer, Staff> getList(){
        return this.list;
    }

    public void printStaffList(){
        HashMap<Integer, Staff> staffList = StaffList.getStaffList().getList();
        System.out.println("====================Staff List=====================");
        System.out.printf("|| %-7s|| %-3s|| %-20s|| Gender ||\n", "Role", "ID", "Name");
        for(Staff staff: staffList.values()){
            staff.printDetails();
        }

        if(staffList.size()==0){
            System.out.println("||     There are no staffs in the restaurant     ||");
        }
        System.out.println("===================================================");
    }

    public void printManager(){
        HashMap<Integer, Staff> staffList = StaffList.getStaffList().getList();
        System.out.println("===================Manager List====================");
        System.out.printf("|| %-7s|| %-3s|| %-20s|| Gender ||\n", "Role", "ID", "Name");
        for(Staff staff: staffList.values()){
            if(staff.getRole()==Role.Manager){
                staff.printDetails();
            }
        }
        if(staffList.size()==0){
            System.out.println("||    There are no managers in the restaurant    ||");
        }
        System.out.println("===================================================");
    }

    public void printStaff(){
        HashMap<Integer, Staff> staffList = StaffList.getStaffList().getList();
        System.out.println("====================Staff List=====================");
        System.out.printf("|| %-7s|| %-3s|| %-20s|| Gender ||\n", "Role", "ID", "Name");
        for(Staff staff: staffList.values()){
            if(staff.getRole()==Role.Staff){
                staff.printDetails();
            }
        }
        if(staffList.size()==0){
            System.out.println("||     There are no staff in the restaurant     ||");
        }
        System.out.println("===================================================");
    }

    public void addStaff(Staff staff){
        list.put(staff.getID(), staff);
    }

    public void removeStaff(int staffID){
        list.remove(staffID);
    }

    public Role getRole(int staffID) {
        if(list.containsKey(staffID)){
            return list.get(staffID).getRole();
        }else{
            return null;
        }
    }
}

