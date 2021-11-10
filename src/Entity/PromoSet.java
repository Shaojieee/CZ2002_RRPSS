package Entity;

import java.util.HashMap;

/**
 * This class contains all the information of a particular promotion set.<br>
 * It includes the name, ID, food items and price.
 */
public class PromoSet extends Food implements java.io.Serializable{

    /**
     * Food items in this promotion set.
     * Key is the food item. Value is the quantity of item in this promotion set.
     */
    private HashMap<Food, Integer> items;

    /**
     * This constructor defines a promotion set.
     * @param name the name of this promotion set.
     * @param id the ID of this promotion set.
     */
    public PromoSet(String name, int id){
        super(name, 0.0, id, FoodType.PROMOTIONSET);
        this.items = new HashMap<Food, Integer>();
    }

    /**
     * @return the number of distinct food items in this promotion set.
     */
    public int getSize(){
        return this.items.size();
    }

}

