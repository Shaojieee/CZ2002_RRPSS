package Control;

import Entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class OrderControl {

    /**
     * The number assigned to go back when printing this order
     */
    public static final int BACK_OPTION = 0;


    /**
     * Adds food items into this order.
     */
    public static void addFood(Order order) {
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true){
            System.out.println("=========Adding Food==========");
            MenuControl.printMenu(true);
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
            if(choice == MenuControl.BACK_OPTION){
                break;
            }
            FoodType action = MenuControl.getMenuAction(choice, true);
            if(action==null){
                System.out.println("Invalid Option!");
                System.out.println();
                continue;
            }

            while (true) {
                System.out.println("===================Creating Order===================");
                MenuControl.printCurrentMenu(action);
                System.out.print("Select Food ID to add or " + MenuControl.BACK_OPTION + " to go back: ");
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
                if (choice == MenuControl.BACK_OPTION){
                    break;
                }
                Food food = MenuControl.getFood(choice, action);
                if (food == null) {
                    System.out.println("Invalid Option!");
                    System.out.println();
                } else {
                    while(true) {
                        System.out.print("Enter Quantity: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Please enter a number!");
                            System.out.println();
                            sc.nextLine();
                        } else {
                            choice = sc.nextInt();
                            sc.nextLine();
                            if (choice <= 0) {
                                System.out.println("Invalid Quantity!");
                                System.out.println();
                            }else{
                                break;
                            }
                        }
                    }

                    HashMap<Food, Double[]> items = order.getItems();
                    Double[] arr = {(double) choice, food.getPrice()*(double)choice};
                    order.setPrice(order.getTotalPrice()+arr[1]);
                    if(checkInOrder(order, food)){
                        Double[] cur = items.get(food);
                        arr[0] += cur[0];
                        arr[1] += cur[1];
                    }

                    items.put(food, arr);
                    System.out.println(choice + " " + food.getName() + " has been added!");
                    System.out.println();
                    System.out.println("===================Creating Order===================");
                    MenuControl.printCurrentMenu(action);
                    System.out.print("Select Food ID to add or " + MenuControl.BACK_OPTION + " to go back: ");
                }
            }

        }

    }

    /**
     * Removes food item from this order.
     */
    public static void removeFood(Order order) {
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("======================Removing Food=======================");
            printOrder(order);

            if (order.getSize() == 0) {
                System.out.print("Press any key to go back ");
                sc.nextLine();
                System.out.println();
                break;
            } else {
                int foodId;
                System.out.print("Select Food ID or " + BACK_OPTION + " to go back: ");
                if(!sc.hasNextInt()){
                    System.out.println("Please enter a number!");
                    System.out.println();
                    sc.nextLine();
                    continue;
                }else {
                    foodId = sc.nextInt();
                    sc.nextLine();
                }
                if(foodId == BACK_OPTION){
                    break;
                }
                Food food = MenuControl.getFood(foodId);
                if (checkInOrder(order, food)){
                    while(true) {
                        System.out.print("Enter Quantity to remove: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Please enter a number!");
                            System.out.println();
                            sc.nextLine();
                        } else {
                            choice = sc.nextInt();
                            sc.nextLine();
                            if (!checkFoodQty(order, food, choice)) {
                                System.out.println("Invalid Quantity!");
                                System.out.println();
                            }else{
                                break;
                            }
                        }
                    }

                    HashMap<Food, Double[]> items = order.getItems();
                    Double[] arr = {(double) choice, food.getPrice()*(double)choice};
                    Double[] cur = items.get(food);
                    cur[0] -= arr[0];
                    cur[1] -= arr[1];
                    if(cur[0]==0){
                        items.remove(food);
                    }else{
                        items.put(food, cur);
                    }
                    order.setPrice(order.getTotalPrice()-arr[1]);

                    System.out.println(choice + " " + food.getName() + " has been removed!");
                }else{
                    System.out.println("Invalid Option");
                }
                System.out.println();
            }
        }

    }

    /**
     * Checks if the food item is in this order.
     * @param order
     * @param food the food item to check.
     * @return <code>true</code> if the food item is in this order, <code>false</code> otherwise.
     */
    private static boolean checkInOrder(Order order, Food food){
        return order.getItems().containsKey(food);
    }

    /**
     * Checks if the quantity in this order is higher than or equal to the specified quantity for the specified food item.
     * @param food the food item to check.
     * @param quantity the quantity to be compared with.
     * @return <code>true</code> if the quantity in this order is higher than or equal to the specified quantity, <code>false</code> otherwise.
     */
    private static boolean checkFoodQty(Order order, Food food, int quantity){
        return order.getItems().get(food)[0].intValue()>=quantity;
    }

    /**
     * Adds an order to this order.
     * @param newOrder the order to add.
     */
    public static void appendOrder(Order existingOrder, Order newOrder){
        Food food;
        Double[] arr;
        double existingPrice = existingOrder.getTotalPrice();
        HashMap<Food, Double[]> existingItems = existingOrder.getItems();
        for (HashMap.Entry<Food, Double[]> new_item : newOrder.getItems().entrySet()){
            food = new_item.getKey();
            arr = new_item.getValue();
            existingPrice += arr[1];
            if(existingItems.containsKey(food)){
                Double[] cur = existingItems.get(food);
                arr[0] += cur[0];
                arr[1] += cur[1];
            }
            existingItems.put(food, arr);
        }
        existingOrder.setPrice(existingPrice);
    }

    /**
     * Prints the food items in this order.
     */
    public static void printOrder(Order order){
        HashMap<Food, Double[]> items = order.getItems();
        System.out.println("==========================Order===========================");
        if (items.size()==0){
            System.out.println("|          This order does not have any items!           |");
            System.out.println("==========================================================");
            System.out.println();
            return;
        }
        System.out.printf("|| %-3s|| %-30s|| %-3s|| %-8s||\n", "ID", "Name" , "Qty", "Price");
        Food food;
        int qty;
        double price;
        for (HashMap.Entry<Food, Double[]> item : items.entrySet()){
            food = item.getKey();
            qty = item.getValue()[0].intValue();
            price = item.getValue()[1];
            System.out.printf("|| %-3s|| %-30s|| %-3s|| %-8s||\n", food.getId(), food.getName(), qty, "$"+String.format("%.2f",price));
        }
        System.out.println("==========================================================");

    }

    /**
     * Prints the Invoice for this order.
     * @param tableID the table ID associated to this order.
     * @param member the membership status of the customer associated to this order.
     */
    public static void printInvoice(Order order, int tableID, boolean member) {
        System.out.println("=========================Invoice==========================");
        System.out.printf("|| %-25s|| %-25s||\n", "Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), "Time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        System.out.printf("|| %-25s|| %-25s||\n", "Served by: " + order.getStaff().getName(), "Table ID: " + tableID);

        printOrder(order);
        double subTotal = Math.round(order.getTotalPrice()*100.0)/100.0;
        double discount, gst, total;
        System.out.printf("%47s $%7s||\n", "Sub-Total:", String.format("%.2f",subTotal));

        if(member){
            discount = memberDiscount(order);
            gst = Math.round((order.getTotalPrice())*0.07*100.0)/100.0;
            total = Math.round((order.getTotalPrice()+gst)*100.0)/100.0;
            System.out.printf("%47s-$%7s||\n", "Member Discount:", String.format("%.2f", discount));
        }else{
            gst = Math.round(subTotal*0.07*100.0)/100.0;
            total = Math.round((subTotal+gst)*100.0)/100.0;
        }

        System.out.printf("%47s $%7s||\n", "GST:", String.format("%.2f",gst));
        System.out.printf("%47s $%7s||\n", "Total:", String.format("%.2f",total));
        System.out.println("==========================================================");
        System.out.println("=================Thank you for visiting!==================");

        SalesReportControl.updateReport(order);
    }


    /**
     * Update this order with a members discount of 5%.
     * @return the discounted amount.
     * @param order
     */
    private static double memberDiscount(Order order){
        HashMap<Food, Double[]> items = order.getItems();
        double totalPrice = 0.00;
        double discounted_item;
        double total_discount = 0.00;
        for(Double [] item : items.values()){
            discounted_item = Math.round(item[1]*0.95*100.0)/100.0;
            total_discount += (item[1] - discounted_item);
            item[1] = discounted_item;
            totalPrice += discounted_item;
        }
        order.setPrice(totalPrice);
        return total_discount;
    }

}
