package Entity;

import Control.FileEditor;

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
     * @return the HashMap of main course.
     */
    public HashMap<Integer, Food> getMainCourse() {
        return this.mainCourse;
    }

    /**
     * @return the HashMap of drinks.
     */
    public HashMap<Integer, Food> getDrinks() {
        return drinks;
    }

    /**
     * @return the HashMap of desserts.
     */
    public HashMap<Integer, Food> getDesserts() {
        return desserts;
    }

    /**
     * @return the HashMap of promotion sets.
     */
    public HashMap<Integer, PromoSet> getPromoSet() {
        return promoSet;
    }

    /**
     * Adds a food item into this menu.
     * @param food the food item to add.
     */
    public void addToMenu(Food food){
        FoodType type = food.getFoodType();
        if (type==FoodType.DESSERT){
            desserts.put(food.getId(), food);
        }else if(type==FoodType.DRINK){
            drinks.put(food.getId(), food);
        }else if(type==FoodType.MAINCOURSE){
            mainCourse.put(food.getId(), food);
        }else{
            promoSet.put(food.getId(), (PromoSet) food);
        }
    }

    /**
     * Removes a food item from this menu.
     * @param food the food item to remove.
     */
    public void removeFromMenu(Food food){
        FoodType type = food.getFoodType();
        if (type==FoodType.DESSERT){
            desserts.remove(food.getId(), food);
        }else if(type==FoodType.DRINK){
            drinks.remove(food.getId(), food);
        }else if(type==FoodType.MAINCOURSE){
            mainCourse.remove(food.getId(), food);
        }else{
            promoSet.remove(food.getId(), (PromoSet) food);
        }
    }
}

