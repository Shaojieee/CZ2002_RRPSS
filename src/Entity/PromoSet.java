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

    /**
     * @return the HashMap of food items in the promotion set.
     */
    public HashMap<Food, Integer> getItems() {
        return items;
    }

    /**
     * Adds a food item into this promotion set.
     * @param food the food item to add.
     * @param quantity the quantity to add.
     */
    public void addFood(Food food, int quantity){
        for(HashMap.Entry<Food, Integer> item : items.entrySet()){
            if(food.getId()==item.getKey().getId()){
                items.put(item.getKey(), item.getValue()+quantity);
                return;
            }
        }
        items.put(food, quantity);
    }

    /**
     * Removes a food item from this promotion set.
     * @param food the food item to remove.
     * @param quantity the quantity to remove.
     */
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
     * Gets the quantity of the speicifed food item in this promotion set.
     * @param food the food item to find.
     * @return the quantity in this promotion set.
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


    public void printFood(){
        super.printFood();
        System.out.printf("|| %-6s%-3s   %-35s||\n", "ID","Qty", "Name");
        for (HashMap.Entry<Food, Integer> item : this.getItems().entrySet()){
            System.out.printf("|| %-6s%3s   %-35s||\n", item.getKey().getId(), item.getValue(), item.getKey().getName());
        }
        System.out.println("====================================================");
    }

}

