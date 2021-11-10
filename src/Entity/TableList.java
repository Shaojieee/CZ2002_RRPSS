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

    public static HashMap<Integer,Table> getList(){
        return list;
    }

}

