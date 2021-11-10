package Control;

import Entity.Food;
import Entity.FoodType;
import Entity.Menu;
import Entity.PromoSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MenuControl {

    /**
     * The number assigned to go back when printing this menu.
     */
    public static final int BACK_OPTION = 0;

    /**
     * Adds a new promotion set into this menu.
     * @param set the promotion set to add.
     */
    public static void addPromoSet(PromoSet set){
        Menu.getMenu().getPromoSet().put(set.getId(), set);
        saveMenu();
    }

    /**
     * Adds new food item into this menu.<br>
     * Does not include PromoSet.
     * @param name the name of the food item.
     * @param price the price of the food item.
     * @param type the type of food to add.
     */
    public static void addFood(String name, double price, FoodType type){
        Menu menu = Menu.getMenu();
        Food food = new Food(name, price, newFoodID(), type);
        if (type==FoodType.DESSERT){
            menu.getDesserts().put(food.getId(), food);
        }else if(type==FoodType.DRINK){
            menu.getDrinks().put(food.getId(), food);
        }else if(type==FoodType.MAINCOURSE){
            menu.getMainCourse().put(food.getId(), food);
        }
        saveMenu();
    }

    /**
     * Creates a new promotion set with the provided name
     * @param name the name of the promotion set.
     * @return the <code>PromoSet</code> object.
     */
    public static PromoSet newPromoSet(String name){
        return new PromoSet(name, newFoodID());
    }

    /**
     * Removes the food item from this menu.
     * @param food the food item to remove.
     */
    public static void deleteFood(Food food){
        Menu menu = Menu.getMenu();
        int ID = food.getId();
        FoodType type = food.getFoodType();
        if (type==FoodType.DESSERT){
            menu.getDesserts().remove(ID);
        }else if(type==FoodType.DRINK){
            menu.getDrinks().remove(ID);
        }else if(type==FoodType.MAINCOURSE){
            menu.getMainCourse().remove(ID);
        }else{
            menu.getPromoSet().remove(ID);
        }
        saveMenu();
    }

    /**
     * Prints out the type of food in this Menu
     * @param fullMenu if <code>false</code> will not show promotion set, if <code>true</code> will show all 4 food types
     */
    public static void printMenu(boolean fullMenu){
        System.out.println("========Select a Menu=========");
        System.out.println("|1. Main Course              |");
        System.out.println("|2. Drinks                   |");
        System.out.println("|3. Desserts                 |");
        if (fullMenu){
            System.out.println("|4. Promotion Sets           |");
        }
        System.out.println("|"+ BACK_OPTION+". Back                     |");
        System.out.println("==============================");
    }

    /**
     * Returns the food type selected from <code>printMenu()</code>
     * @param choice the selection
     * @param fullMenu if <code>false</code> cannot return promotion set, if <code>true</code> can return promotion set
     * @return the <code>FoodType</code> of the selected choice
     */
    public static FoodType getMenuAction(int choice, boolean fullMenu){
        if (choice==1){
            return FoodType.MAINCOURSE;
        }else if(choice==2){
            return FoodType.DRINK;
        }else if(choice==3){
            return FoodType.DESSERT;
        }else if(choice==4 && fullMenu){
            return FoodType.PROMOTIONSET;
        }else{
            return null;
        }
    }

    /**
     * Prints out the list of main course.
     */
    private static void printMainCourse(){
        HashMap<Integer, Food> mainCourse = Menu.getMenu().getMainCourse();
        System.out.println("====================Main Course=====================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");
        for(HashMap.Entry<Integer, Food> item : mainCourse.entrySet()){
            item.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints out the list of drinks.
     */
    private static void printDrinks(){
        HashMap<Integer, Food> drinks = Menu.getMenu().getDrinks();
        System.out.println("=======================Drinks=======================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");
        for(HashMap.Entry<Integer, Food> item : drinks.entrySet()){
            item.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints out the list of desserts.
     */
    private static void printDesserts(){
        HashMap<Integer, Food> desserts = Menu.getMenu().getDesserts();
        System.out.println("======================Desserts======================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");
        for(HashMap.Entry<Integer, Food> item : desserts.entrySet()){
            item.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints out the list of promotion sets.
     */
    private static void printPromoSet(){
        HashMap<Integer, PromoSet> promoSet = Menu.getMenu().getPromoSet();
        System.out.println("===================Promotion Set====================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");

        for(HashMap.Entry<Integer, PromoSet> set : promoSet.entrySet()){
            set.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints the list of food item according to the provided food type.
     * @param type the type of food to print.
     */
    public static void printCurrentMenu(FoodType type){
        if (type==FoodType.DRINK){
            printDrinks();
        }else if(type==FoodType.MAINCOURSE){
            printMainCourse();
        }else if(type == FoodType.DESSERT){
            printDesserts();
        }else{
            printPromoSet();
        }
    }

    /**
     * Gets the <code>Food</code> object with the requested ID.
     * @param ID the ID of the food item.
     * @return the <code>Food</code> object.
     */
    public static Food getFood(int ID){
        Menu menu = Menu.getMenu();
        if (menu.getMainCourse().containsKey(ID)){
            return menu.getMainCourse().get(ID);
        }else if(menu.getDrinks().containsKey(ID)){
            return menu.getDrinks().get(ID);
        }else if(menu.getDesserts().containsKey(ID)) {
            return menu.getDesserts().get(ID);
        }else{
            return menu.getPromoSet().getOrDefault(ID, null);
        }
    }

    /**
     * Gets the <code>Food</code> object with the requested ID and food type.
     * @param ID the ID of the food item.
     * @param type the food type of the food item.
     * @return the <code>Food</code> object.
     */
    public static Food getFood(int ID, FoodType type){
        Menu menu = Menu.getMenu();
        if (type==FoodType.DRINK){
            return menu.getDrinks().getOrDefault(ID, null);
        }else if(type==FoodType.DESSERT){
            return menu.getDesserts().getOrDefault(ID, null);
        }else if(type==FoodType.MAINCOURSE){
            return menu.getMainCourse().getOrDefault(ID, null);
        }else{
            return menu.getPromoSet().getOrDefault(ID, null);
        }
    }

    /**
     * Gets the <code>Food</code> object with the requested name and food type.
     * @param name the name of the food item.
     * @param type the food type of the food item.
     * @return the <code>Food</code> object.
     */
    public static Food getFood(String name, FoodType type){
        Menu menu = Menu.getMenu();
        if (type==FoodType.DRINK){
            for (Food food : menu.getDrinks().values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }else if(type==FoodType.DESSERT){
            for (Food food : menu.getDesserts().values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }else if(type==FoodType.MAINCOURSE){
            for (Food food : menu.getMainCourse().values()){
                if (food.getName().equals(name)){
                    return food;
                }
            }
        }else{
            for (Food food : menu.getPromoSet().values()){
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
    public static Food getFood(String name){
        Menu menu = Menu.getMenu();
        for (Food food : menu.getDrinks().values()) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        for (Food food : menu.getDesserts().values()){
            if (food.getName().equals(name)){
                return food;
            }
        }
        for (Food food : menu.getMainCourse().values()){
            if (food.getName().equals(name)){
                return food;
            }
        }
        for (Food food : menu.getPromoSet().values()){
            if (food.getName().equals(name)){
                return food;
            }
        }
        return null;
    }

    /**
     * @return an unused food ID in this menu.
     */
    private static int newFoodID(){
        Menu menu = Menu.getMenu();
        int mainCourseID = menu.getMainCourse().keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        int drinksID = menu.getDrinks().keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        int dessertsID = menu.getDesserts().keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        int promoSetID = menu.getPromoSet().keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        return Collections.max(Arrays.asList(mainCourseID, drinksID, dessertsID, promoSetID))+1;
    }

    /**
     * Save the current menu.
     */
    public static void saveMenu(){
        Menu menu = Menu.getMenu();
        FileEditor.writeDesserts(menu.getDesserts());
        FileEditor.writeMainCourse(menu.getMainCourse());
        FileEditor.writeDrink(menu.getDrinks());
        FileEditor.writePromoSets(menu.getPromoSet());
    }
}
