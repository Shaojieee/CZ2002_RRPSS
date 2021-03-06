package Control;

import Entity.Food;
import Entity.FoodType;
import Entity.Menu;
import Entity.PromoSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


/**
 * This class contains the functions used to control the menu.
 */
public class MenuControl {

    /**
     * Number to press to go back in menu page.
     */
    public static final int BACK_OPTION = 0;

    /**
     * Edits the current menu.
     */
    public static void editMenu(){
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("========Edit Menu=========");
            System.out.println("|1. Add Main Course      |");
            System.out.println("|2. Add Drinks           |");
            System.out.println("|3. Add Desserts         |");
            System.out.println("|4. Add Promotion Set    |");
            System.out.println("|5. Edit Menu Items      |");
            System.out.println("|6. Delete Menu Items    |");
            System.out.println("|0. Back                 |");
            System.out.println("==========================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> MenuControl.addFood(FoodType.MAINCOURSE);
                    case 2 -> MenuControl.addFood(FoodType.DRINK);
                    case 3 -> MenuControl.addFood(FoodType.DESSERT);
                    case 4 -> MenuControl.addFood(FoodType.PROMOTIONSET);
                    case 5 -> {
                        while (true) {
                            System.out.println("==========Edit Food===========");
                            MenuControl.printMenu(true);
                            System.out.print("Please enter your choice: ");
                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                            }else {
                                choice = sc.nextInt();
                                sc.nextLine();
                                if (choice == MenuControl.BACK_OPTION) {
                                    break;
                                }
                                FoodType type = MenuControl.getMenuAction(choice, true);
                                if (type == null) {
                                    System.out.println("Invalid Option!");
                                    System.out.println();
                                } else {
                                    while (true) {
                                        System.out.println("=====================Edit Food======================");
                                        printCurrentMenu(type);
                                        System.out.print("Select Food ID to edit or "+ BACK_OPTION + " to back: ");
                                        if(!sc.hasNextInt()){
                                            System.out.println("Please enter a number!");
                                            System.out.println();
                                            sc.nextLine();
                                            continue;
                                        }else {
                                            choice = sc.nextInt();
                                            sc.nextLine();
                                        }
                                        if(choice == BACK_OPTION){
                                            break;
                                        }
                                        Food food = getFood(choice, type);
                                        if(food==null){
                                            System.out.println("Invalid Option!");
                                            System.out.println();
                                            continue;
                                        }
                                        if (type != FoodType.PROMOTIONSET) {
                                            /* In specific food type menu page */
                                            while(choice!=0){
                                                System.out.println("===============Edit Food===============");
                                                System.out.printf("| %-29s%7s|\n", food.getName(), "$"+String.format("%.2f", food.getPrice()));
                                                System.out.println("|1. Change Name                       |");
                                                System.out.println("|2. Change Price                      |");
                                                System.out.println("|0. Back                              |");
                                                System.out.println("=======================================");
                                                System.out.print("Please enter your choice: ");
                                                if(!sc.hasNextInt()){
                                                    System.out.println("Please enter a number!");
                                                    System.out.println();
                                                    sc.nextLine();
                                                    continue;
                                                }else {
                                                    choice = sc.nextInt();
                                                    sc.nextLine();
                                                }
                                                switch(choice){
                                                    case 1->{
                                                        String old = food.getName();
                                                        System.out.print("Enter new name: ");
                                                        String name = sc.nextLine();
                                                        name = name.trim();
                                                        if (getFood(name)!=null){
                                                            System.out.println("Duplicate name!");
                                                            System.out.println();
                                                            break;
                                                        }
                                                        food.setName(name);
                                                        System.out.println(old + " has been changed to "+ name + "!");
                                                        System.out.println();
                                                    }
                                                    case 2->{
                                                        double old = food.getPrice();
                                                        System.out.print("Enter new price: ");
                                                        double price = sc.nextDouble();
                                                        sc.nextLine();
                                                        if(price<=0.0){
                                                            System.out.println("Invalid Price!");
                                                            System.out.println();
                                                            break;
                                                        }
                                                        food.setPrice(Math.round(price*100.0)/100.0);
                                                        System.out.println("Price change from $" + old + " to $" + price + "!");
                                                        System.out.println();
                                                    }
                                                    case 0->{
                                                    }
                                                    default ->{
                                                        System.out.println("Invalid Option!");
                                                        System.out.println();
                                                    }
                                                }
                                            }
                                        }else{
                                            editPromoSet((PromoSet) food);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 6 -> {
                        while (true) {
                            System.out.println("========Deleting Food=========");
                            MenuControl.printMenu(true);
                            System.out.print("Please enter your choice: ");
                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                            }else {
                                choice = sc.nextInt();
                                sc.nextLine();
                                if (choice == MenuControl.BACK_OPTION) {
                                    break;
                                }
                                FoodType action = MenuControl.getMenuAction(choice, true);
                                if (action == null) {
                                    System.out.println("Invalid Option");
                                    System.out.println();

                                } else {
                                    MenuControl.deleteFood(action);
                                }
                            }
                        }
                    }
                    case 0 -> {
                        return;
                    }
                    default -> {
                        System.out.println("Invalid Option!");
                        System.out.println();
                    }
                }
            }
        }
    }

    /**
     * Edits promotion set.
     * @param newSet the promotion set to edit.
     * @return the edited <code>PromSet</code> object.
     */
    private static PromoSet editPromoSet(PromoSet newSet){
        int choice;
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("====Edit Promotion Set====");
            System.out.println("|1. Add Food             |" );
            System.out.println("|2. Remove Food          |" );
            System.out.println("|3. Set Price            |" );
            System.out.println("|4. Change Name          |" );
            System.out.println("|5. View Set             |" );
            System.out.println("|0. Back                 |" );
            System.out.println("==========================");
            System.out.print("Please enter your choice: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
            }else{
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {
                        while (true) {
                            System.out.println("=====Add to Promotion Set=====");
                            printMenu(false);
                            System.out.print("Please enter your choice: ");
                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                            }else {
                                choice = sc.nextInt();
                                sc.nextLine();
                                if (choice == BACK_OPTION) {
                                    break;
                                }
                                FoodType action = getMenuAction(choice, false);
                                if (action == null) {
                                    System.out.println("Invalid Option!");
                                    System.out.println();
                                    continue;
                                }
                                while (true) {
                                    System.out.println("================Add to Promotion Set================");
                                    printCurrentMenu(action);
                                    System.out.print("Select Food ID to add or " + BACK_OPTION + " to back: ");
                                    if(!sc.hasNextInt()){
                                        System.out.println("Please enter a number!");
                                        System.out.println();
                                        sc.nextLine();
                                    }else{
                                        choice = sc.nextInt();
                                        sc.nextLine();
                                        if (choice == BACK_OPTION) {
                                            break;
                                        }
                                        Food food = getFood(choice, action);
                                        if (food == null) {
                                            System.out.println("Invalid Food ID!");
                                        } else {
                                            int quantity=-1;
                                            do{
                                                System.out.print("Enter Quantity: ");
                                                if(!sc.hasNextInt()){
                                                    System.out.println("Please enter a number!");
                                                    System.out.println();
                                                    sc.nextLine();
                                                }else{
                                                    quantity = sc.nextInt();
                                                    sc.nextLine();
                                                    if (quantity <= 0) {
                                                        System.out.println("Invalid Quantity!");
                                                    }
                                                }
                                            }while(quantity<=0);

                                            newSet.addFood(food, quantity);
                                            System.out.println(quantity + " " + food.getName() + " has been added!");
                                            System.out.println();
                                            System.out.println("====================================================");
                                            newSet.printFood();
                                        }
                                        System.out.println();
                                    }
                                }
                            }
                        }
                    }
                    case 2 -> {
                        while (true) {
                            if (newSet.getSize() == 0) {
                                System.out.println("There are currently no items in this Promotion Set!");
                                System.out.println();
                                break;
                            }
                            System.out.println("===================Deleting Food====================");
                            newSet.printFood();
                            System.out.print("Select Food ID to delete or " + BACK_OPTION + " to back: ");

                            if(!sc.hasNextInt()){
                                System.out.println("Please enter a number!");
                                System.out.println();
                                sc.nextLine();
                                continue;
                            }else {
                                choice = sc.nextInt();
                                sc.nextLine();
                            }

                            if (choice == BACK_OPTION) {
                                break;
                            } else {
                                Food food = getFood(choice);
                                if (newSet.inSet(food)) {
                                    System.out.print("Enter Quantity: ");
                                    if(!sc.hasNextInt()){
                                        System.out.println("Please enter a number!");
                                        System.out.println();
                                        sc.nextLine();
                                    }else {
                                        int quantity = sc.nextInt();
                                        sc.nextLine();
                                        if (newSet.getQty(food) >= quantity && quantity >= 0) {
                                            newSet.removeFood(food, quantity);
                                            System.out.println(quantity + " " + food.getName() + " has been removed from Promotion Set!");
                                        } else {
                                            System.out.println("Invalid Quantity!");
                                        }
                                        System.out.println();
                                    }
                                } else {
                                    System.out.println(food.getName() + " is not in Promotion Set!");
                                }
                                System.out.println();
                            }
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter new Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();
                        while (price <= 0.0) {
                            System.out.println("Invalid Price!");
                            System.out.print("Enter new Price: ");
                            price = sc.nextDouble();
                            sc.nextLine();
                        }
                        newSet.setPrice(Math.round(price * 100.0) / 100.0);
                        System.out.println("Price has been set at $" + String.format("%.2f", newSet.getPrice()) + "!");
                        System.out.println();
                    }
                    case 4 -> {
                        String old = newSet.getName();
                        System.out.print("Enter new name: ");
                        String name = sc.nextLine();
                        name = name.trim();
                        if (getFood(name)!=null){
                            System.out.println("Duplicate name!");
                            System.out.println();
                            break;
                        }
                        newSet.setName(name);
                        System.out.println(old + " has been changed to "+ name + "!");
                        System.out.println();
                    }
                    case 5 -> {
                        System.out.println("====================================================");
                        newSet.printFood();
                        System.out.print("Press any key to back ");
                        sc.nextLine();
                        System.out.println();
                    }
                    case 0 -> {
                        boolean test = true;
                        if (newSet.getSize() == 0) {
                            test = false;
                            System.out.println("Promotion Set is empty! Please add food!");
                        }
                        if (newSet.getPrice() == 0.00) {
                            test = false;
                            System.out.println("Promotion Set price is set at $0.00! Please set price!");
                        }
                        if (test) {
                            return newSet;
                        } else {
                            System.out.println();
                        }
                    }
                    default -> {
                        System.out.println("Invalid Option!");
                        System.out.println();
                    }
                }
            }
        }
    }

    /**
     * Adds new food item into the menu.<br>
     * @param type the type of food to add.
     */
    private static void addFood(FoodType type){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of " + type+ ": ");
        String name = sc.nextLine();
        name = name.trim();
        if (MenuControl.getFood(name, type)!=null){
            System.out.println(type + " already exist");
            System.out.println();
            return;
        }
        Food food;
        if(type==FoodType.PROMOTIONSET){
            food = editPromoSet(new PromoSet(name, newFoodID()));
        }else{
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            sc.nextLine();
            while(price<=0.0){
                System.out.println("Invalid Price!");
                System.out.print("Enter Price: ");
                price = sc.nextDouble();
                sc.nextLine();
            }
            food = new Food(name, Math.round(price*100.0)/100.0, newFoodID(), type);

        }
        Menu.getMenu().addToMenu(food);
        saveMenu();

        System.out.println(name + " has been add to " + type + "!");
        System.out.println();

    }

    /**
     * Removes the food item from the menu.
     * @param type the food type to remove.
     */
    private static void deleteFood(FoodType type){
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("===================Deleting Food====================");
            printCurrentMenu(type);
            System.out.print("Select Food ID to delete or " + BACK_OPTION + " to go back: ");
            /* In specific food type menu page */
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            if (choice == BACK_OPTION) {
                break;
            }
            Food food = getFood(choice, type);
            if (food == null) {
                System.out.println("Invalid Option!");
            } else {
                Menu.getMenu().removeFromMenu(food);
                saveMenu();
                System.out.println(food.getName() + " has been removed!");
            }
            System.out.println();
        }
    }

    /**
     * Prints out the type of food in the menu
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
    private static void printMainCourseMenu(){
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
    private static void printDrinksMenu(){
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
    private static void printDessertsMenu(){
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
    private static void printPromoSetMenu(){
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
            printDrinksMenu();
        }else if(type==FoodType.MAINCOURSE){
            printMainCourseMenu();
        }else if(type == FoodType.DESSERT){
            printDessertsMenu();
        }else{
            printPromoSetMenu();
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
     * @return an unused food ID in the menu.
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
