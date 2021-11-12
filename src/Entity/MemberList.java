package Entity;

import Control.FileEditor;

import java.util.ArrayList;

/**
 * This class contains the list of members of the restaurant.<br>
 * The members are identified by their phone number.<br>
 * Only 1 instance can exist at all times.
 */
public class MemberList {

    /**
     * The singleton instance of <code>MemberList</code>.
     */
    private static MemberList memberList = null;

    /**
     * The list containing all members' phone number.
     */
    private ArrayList<Integer> list;

    /**
     * This constructor defines the list of members.<br>
     * Loads the members' data into the list.
     */
    private MemberList(){
        this.list = FileEditor.loadMembers();
    }

    /**
     * Initializes the <code>MemberList</code> object if it has not been initialized.
     * @return the <code>MemberList</code> object.
     */
    public static MemberList getMemberList(){
        if (memberList == null){
            memberList = new MemberList();
        }
        return memberList;
    }

    /**
     * @return the ArrayList of members.
     */
    public ArrayList<Integer> getList(){
        return this.list;
    }

    /**
     * Adds a member into this list of members
     * @param phone the phone number to add.
     */
    public void add(int phone){
        list.add(phone);
    }

    /**
     * Removes a member from this list of members.
     * @param phone the phone number to remove.
     */
    public void remove(int phone){
        list.remove((Object) phone);
    }

}
