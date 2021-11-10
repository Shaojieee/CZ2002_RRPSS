package Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class contains all the information of the menu. <br>
 * It includes the list of main course, drinks, desserts and promotion set. <br>
 * Only 1 instance can exist at all times.
 */
public class Menu {

    /**
     * The number assigned to go back when printing this menu.
     */
    static final int BACK_OPTION = 0;

    /**
     * HashMap of main course. <br>
     * Key is the food ID. Value is the <code>Food</code> object.
     */
    private HashMap<Integer,Food> mainCourse;

    /**
     * HashMap of drinks. <br>
     * Key is the food ID. Value is the <code>Food</code> object.
     */
    private HashMap<Integer,Food> drinks;

    /**
     * HashMap of desserts. <br>
     * Key is the food ID. Value is the <code>Food</code> object.
     */
    private HashMap<Integer,Food> desserts;

    /**
     * HashMap of promotion sets. <br>
     * Key is the food ID. Value is the <code>PromoSet</code> object.
     */
    private HashMap<Integer,PromoSet> promoSet;

    /**
     * The singleton instance of <code>Menu</code>.
     */
    private static Menu menu = null;

    /**
     * This constructor defines a menu.<br>
     * Loads the main course, drinks, desserts and promotion sets.
     */
    private Menu(){
        this.mainCourse = FileEditor.loadMainCourse();
        this.drinks = FileEditor.loadDrinks();
        this.desserts = FileEditor.loadDesserts();
        this.promoSet = FileEditor.loadPromoSets();
    }

    /**
     * Initializes the <code>Menu</code> object if it has not been initialized.
     * @return the <code>Menu</code> object
     */
    public static Menu getMenu(){
        if (menu==null){
            menu = new Menu();
        }
        return menu;
    }

    /**
     * Creates a new promotion set with the provided name
     * @param name the name of the promotion set.
     * @return the <code>PromoSet</code> object.
     */
    public PromoSet newPromoSet(String name){
        return new PromoSet(name, this.newFoodID());
    }

    /**
     * Gets the <code>Food</code> object with the requested ID.
     * @param ID the ID of the food item.
     * @return the <code>Food</code> object.
     */
    public Food getFood(int ID){
        if (this.mainCourse.containsKey(ID)){
            return this.mainCourse.get(ID);
        }else if(this.drinks.containsKey(ID)){
            return this.drinks.get(ID);
        }else {
            return this.desserts.getOrDefault(ID, null);
        }
    }

    /**
     * Gets the <code>Food</code> object with the requested ID and food type.
     * @param ID the ID of the food item.
     * @param type the food type of the food item.
     * @return the <code>Food</code> object.
     */
    public Food getFood(int ID, FoodType type){
        if (type==FoodType.DRINK){
            return this.drinks.getOrDefault(ID, null);
        }else if(type==FoodType.DESSERT){
            return this.desserts.getOrDefault(ID, null);
        }else if(type==FoodType.MAINCOURSE){
            return this.mainCourse.getOrDefault(ID, null);
        }else{
            return this.promoSet.getOrDefault(ID, null);
        }
    }

    /**
     * Gets the <code>Food</code> object with the requested name and food type.
     * @param name the name of the food item.
     * @param type the food type of the food item.
     * @return the <code>Food</code> object.
     */
    public Food getFood(String name, FoodType type){
        if (type==FoodType.DRINK){
            for (Food food : this.drinks.values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }else if(type==FoodType.DESSERT){
            for (Food food : this.desserts.values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }else if(type==FoodType.MAINCOURSE){
            for (Food food : this.mainCourse.values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }else{
            for (Food food : this.promoSet.values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }
        return null;
    }

    /**
     * Gets the <code>Food</code> object with the requested name.
     * @param name the name of the food item.
     * @return the <code>Food</code> object.
     */
    public Food getFood(String name){
        for (Food food : this.drinks.values()) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        for (Food food : this.desserts.values()){
            if (food.getName().equals(name)){
                return food;
            }
        }
        for (Food food : this.mainCourse.values()){
            if (food.getName().equals(name)){
                return food;
            }
        }
        for (Food food : this.promoSet.values()){
            if (food.getName().equals(name)){
                return food;
            }
        }
        return null;
    }

}

