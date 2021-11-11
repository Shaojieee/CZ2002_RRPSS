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

    /**
     * @return the HashMap of staffs.
     */
    public HashMap<Integer, Staff> getList(){
        return this.list;
    }

    /**
     * Prints all the staff in this list of staffs.
     */
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

    /**
     * Prints only the managers in this list of staffs.
     */
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

    /**
     * Prints only the staff in this list of staff
     */
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

    /**
     * Adds a staff into this list of staff.
     * @param staff the staff to add.
     */
    public void addStaff(Staff staff){
        list.put(staff.getID(), staff);
    }

    /**
     * Removes a staff from this list of staffs.
     * @param staffID the staffID of the staff to remove.
     */
    public void removeStaff(int staffID){
        list.remove(staffID);
    }

    /**
     * Gets the role of the specified staff.
     * @param staffID the staffID of the staff.
     * @return the role of the staff.
     */
    public Role getRole(int staffID) {
        if(list.containsKey(staffID)){
            return list.get(staffID).getRole();
        }else{
            return null;
        }
    }
}

