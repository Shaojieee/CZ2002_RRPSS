package Entity;

import Control.FileEditor;
import java.util.*;


/**
 * This class contains the information of the sales report. <br>
 * Only 1 instance can exist at all times.
 */
public class SalesReport {

    /**
     * The HashMap containing previous sales data.<br>
     * Key is the date (dd MMM yyyy). Value is a HashMap storing the quantity sold and revenue collected for different food items.
     */
    private HashMap<String,HashMap<Food,Double[]>> data;

    /**
     * The singleton instance of <code>SalesReport</code>.
     */
    private static SalesReport report = null;

    /**
     * This constructor defined the sales report.<br>
     * Loads previous sales data into the HashMap.
     */
    private SalesReport() {
        this.data = FileEditor.loadSalesReport();
    }

    /**
     * Initializes the <code>SalesReport</code> object if it has not been initialized.
     * @return the <code>SalesReport</code> object.
     */
    public static SalesReport getSalesReport(){
        if(report==null){
            report = new SalesReport();
        }
        return report;
    }

    /**
     * @return the HashMap of all the past sales data.
     */
    public HashMap<String, HashMap<Food, Double[]>> getData(){
        return this.data;
    }

}

