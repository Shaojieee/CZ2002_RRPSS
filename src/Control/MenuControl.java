package Control;

import Entity.Food;
import Entity.FoodType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MenuControl {

    /**
     * Adds a new promotion set into this menu.
     * @param set the promotion set to add.
     */
    public void addPromoSet(PromoSet set){
        this.promoSet.put(set.getId(), set);
        this.saveMenu();
    }

    /**
     * Adds new food item into this menu.<br>
     * Does not include PromoSet.
     * @param name the name of the food item.
     * @param price the price of the food item.
     * @param type the type of food to add.
     */
    public void addFood(String name, double price, FoodType type){
        Food food = new Food(name, price, this.newFoodID(), type);
        if (type==FoodType.DESSERT){
            this.desserts.put(food.getId(), food);
        }else if(type==FoodType.DRINK){
            this.drinks.put(food.getId(), food);
        }else if(type==FoodType.MAINCOURSE){
            this.mainCourse.put(food.getId(), food);
        }
        this.saveMenu();
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
     * Removes the food item from this menu.
     * @param food the food item to remove.
     */
    public void deleteFood(Food food){
        int ID = food.getId();
        FoodType type = food.getFoodType();
        if (type==FoodType.DESSERT){
            this.desserts.remove(ID);
        }else if(type==FoodType.DRINK){
            this.drinks.remove(ID);
        }else if(type==FoodType.MAINCOURSE){
            this.mainCourse.remove(ID);
        }else{
            this.promoSet.remove(ID);
        }
        this.saveMenu();
    }

    /**
     * Prints out the type of food in this Menu
     * @param fullMenu if <code>false</code> will not show promotion set, if <code>true</code> will show all 4 food types
     */
    public void printMenu(boolean fullMenu){
        System.out.println("========Select a Menu=========");
        System.out.println("|1. Main Course              |");
        System.out.println("|2. Drinks                   |");
        System.out.println("|3. Desserts                 |");
        if (fullMenu){
            System.out.println("|4. Promotion Sets           |");
        }
        System.out.println("|"+BACK_OPTION+". Back                     |");
        System.out.println("==============================");
    }

    /**
     * Returns the food type selected from <code>printMenu()</code>
     * @param choice the selection
     * @param fullMenu if <code>false</code> cannot return promotion set, if <code>true</code> can return promotion set
     * @return the <code>FoodType</code> of the selected choice
     */
    public FoodType getMenuAction(int choice, boolean fullMenu){
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
    private void printMainCourse(){
        System.out.println("====================Main Course=====================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");
        for(HashMap.Entry<Integer, Food> item : this.mainCourse.entrySet()){
            item.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints out the list of drinks.
     */
    private void printDrinks(){
        System.out.println("=======================Drinks=======================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");
        for(HashMap.Entry<Integer, Food> item : this.drinks.entrySet()){
            item.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints out the list of desserts.
     */
    private void printDesserts(){
        System.out.println("======================Desserts======================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");
        for(HashMap.Entry<Integer, Food> item : this.desserts.entrySet()){
            item.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints out the list of promotion sets.
     */
    private void printPromoSet(){
        System.out.println("===================Promotion Set====================");
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", "ID", "Name", "Price");

        for(HashMap.Entry<Integer, PromoSet> set : this.promoSet.entrySet()){
            set.getValue().printFood();
        }
        System.out.println("====================================================");
    }

    /**
     * Prints the list of food item according to the provided food type.
     * @param type the type of food to print.
     */
    public void printCurrentMenu(FoodType type){
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

    /**
     * @return an unused food ID in this menu.
     */
    private int newFoodID(){
        int mainCourseID = this.mainCourse.keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        int drinksID = this.drinks.keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        int dessertsID = this.desserts.keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        int promoSetID = this.promoSet.keySet().stream().max((entry1, entry2) -> entry1 > entry2? 1 : -1).orElse(0);
        return Collections.max(Arrays.asList(mainCourseID, drinksID, dessertsID, promoSetID))+1;
    }

    /**
     * Save the current menu.
     */
    public void saveMenu(){
        FileEditor.writeDesserts(this.desserts);
        FileEditor.writeMainCourse(this.mainCourse);
        FileEditor.writeDrink(this.drinks);
        FileEditor.writePromoSets(this.promoSet);
    }
}
