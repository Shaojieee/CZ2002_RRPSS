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


    public HashMap<Integer, Food> getMainCourse() {
        return this.mainCourse;
    }

    public HashMap<Integer, Food> getDrinks() {
        return drinks;
    }

    public HashMap<Integer, Food> getDesserts() {
        return desserts;
    }

    public HashMap<Integer, PromoSet> getPromoSet() {
        return promoSet;
    }
}

