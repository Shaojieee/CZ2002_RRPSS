package Entity;

import Control.FileEditor;

import java.util.HashMap;
import java.util.Set;

/**
 * This class contains list of tables in the restaurant.<br>
 * Only 1 instance can exist at all times.
 */
public class TableList {

    /**
     * The number assigned to go back when printing this list of tables
     */
    public static final int BACK_OPTION = 0;

    /**
     * The HashMap containing the tables.
     * Key is the Table ID. Value is the <code>Table</code> object.
     */
    private HashMap<Integer, Table> list;

    /**
     * The singleton instance of <code>TableList</code>.
     */
    private static TableList tableList = null;

    /**
     * This constructor defines the list of tables.
     * Loads the tables' data into the HashMap.
     */
    private TableList(){
        this.list = FileEditor.loadTables();
    }

    /**
     * Initializes the <code>TableList</code> object if it has not been initialized.
     * @return the <code>TableList</code> object.
     */
    public static TableList getTableList() {
        if (tableList==null){
            tableList = new TableList();
        }
        return tableList;
    }

    /**
     * Gets the <code>Table</code> object with teh requested ID.
     * @param ID the ID of the table.
     * @return the <code>Table</code> object.
     */
    public Table getTable(int ID) {
        return this.list.getOrDefault(ID, null);
    }

    /**
     * Prints the list of tables.
     */
    public void printAllTables() {
        System.out.println("======================Tables======================");
        System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", "Table ID", "Status", "Pax", "Size");
        for (Table table: list.values()){
            table.printBasicDetails();
        }
        if(list.size()==0){
            System.out.println("||    There are no tables in the restaurant     ||");
        }
        System.out.println("==================================================");
    }

    /**
     * Prints the list of occupied tables.
     */
    public void printOccupied() {
        int count = 0;
        System.out.println("=================Occupied Tables==================");
        System.out.printf("|| %-8s|| %-20s|| %-4s|| %-4s||\n", "Table ID", "Status", "Pax", "Size");
        for (Table table : list.values()){
            if (table.isOccupied()){
                count++;
                table.printBasicDetails();
            }
        }
        if (count==0){
            System.out.println("||     No tables are occupied at the moment     ||");
        }
        System.out.println("==================================================");
    }

}

