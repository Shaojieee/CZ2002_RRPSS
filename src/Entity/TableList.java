package Entity;

import Control.FileEditor;

import java.util.HashMap;

/**
 * This class contains list of tables in the restaurant.<br>
 * Only 1 instance can exist at all times.
 */
public class TableList {


    /**
     * The HashMap containing the tables.
     * Key is the Table ID. Value is the <code>Table</code> object.
     */
    private static HashMap<Integer, Table> list;

    /**
     * The singleton instance of <code>TableList</code>.
     */
    private static TableList tableList = null;

    /**
     * This constructor defines the list of tables.
     * Loads the tables' data into the HashMap.
     */
    private TableList(){
        list = FileEditor.loadTables();
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
     * @return the HashMap of tables.
     */
    public HashMap<Integer,Table> getList(){
        return list;
    }

    /**
     * Removes a table from this list of tables.
     * @param tableID the table ID of the table to remove.
     */
    public void removeTable(int tableID){
        list.remove(tableID);
    }

    /**
     * Adds a table into this list of tables.
     * @param table the table to add.
     */
    public void addTable(Table table) {
        list.put(table.getTableId(), table);
    }

    /**
     * @return number of tables.
     */
    public int size(){
        return list.size();
    }
}

