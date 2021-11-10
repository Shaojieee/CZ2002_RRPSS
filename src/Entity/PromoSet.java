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

    public HashMap<Food, Integer> getItems() {
        return items;
    }

    public void addFood(Food food, int quantity){
        for(HashMap.Entry<Food, Integer> item : items.entrySet()){
            if(food.getId()==item.getKey().getId()){
                items.put(item.getKey(), item.getValue()+quantity);
                return;
            }
        }
        items.put(food, quantity);
    }

    public void removeFood(Food food, int quantity){
        for(HashMap.Entry<Food, Integer> item : items.entrySet()){
            if(food.getId()==item.getKey().getId()){
                if(quantity == item.getValue()){
                    items.remove(item.getKey());
                }else{
                    items.put(item.getKey(), item.getValue()-quantity);
                }
                return;

            }
        }

    }

    /**
     * Checks if the food item is already in this promotion set.
     * @param food the food item to check.
     * @return <code>true</code> if item is in this promotion set, <code>false</code> otherwise.
     */
    public boolean inSet(Food food){
        int search_id = food.getId();
        for(Food item : this.items.keySet()){
            if(item.getId()==search_id){
                return true;
            }
        }
        return false;
    }

    /**
     * @param food the food item to find in this promotion set.
     * @return the number of the specified food item in this promotion set.
     */
    public int getQty(Food food){
        int search_id = food.getId();
        for(Food item : this.items.keySet()){
            if(item.getId()==search_id){
                return this.items.get(item);
            }
        }
        return 0;
    }

}

