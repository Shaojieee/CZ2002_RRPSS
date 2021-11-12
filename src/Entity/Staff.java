package Entity;


/**
 * This class contains the information of a particular staff. <br>
 * It includes the name, ID, gender and the different actions that can be taken.
 */
public class Staff implements java.io.Serializable{
    /**
     * Name of this staff.
     */
    private final String name;

    /**
     * ID of this staff.
     */
    private final int ID;

    /**
     * Gender of this staff.
     */
    private final String gender;

    /**
     * Role of this staff.
     */
    private Role role;

    /**
     * This constructor defines a staff.
     * @param name the name of this staff.
     * @param ID the ID of this staff.
     * @param gender the gender of this staff.
     * @param role the role of this staff.
     */
    public Staff(String name, int ID, String gender, Role role){
        this.name = name;
        this.ID = ID;
        this.gender = gender;
        this.role = role;
    }

    /**
     * @return the name of this staff.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the ID of this staff.
     */
    public int getID() {
        return ID;
    }

    /**
     * @return the gender of this staff.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * @return the role of this staff.
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Prints the details of this staff.
     */
    public void printDetails(){
        String role;
        if(this.role==Role.Manager){
            role = "Manager";
        }else{
            role = "Staff";
        }
        System.out.printf("|| %-7s|| %-3s|| %-20s|| %2s%s%3s ||\n", role, this.ID, this.name, "",this.gender, "");
    }
}

