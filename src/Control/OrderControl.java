package Control;

import Entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class contains the functions used to control an order.
 */
public class OrderControl {

    /**
     * Number to press to go back in order page.
     */
    public static final int BACK_OPTION = 0;


    /**
     * Creates an order for the chosen table.<br>
     * @param staffID the staffID of the staff creating this order.
     */
    public static void createOrder(int staffID){
        Scanner sc = new Scanner(System.in);
        int choice;
        Table table;

        if(LocalTime.now().isAfter(LocalTime.of(20,40))){
            System.out.println("Sorry kitchen has closed!");
            System.out.println();
            return;
        }

        /* Choosing table to take order */
        while(true){
            System.out.println("==================Creating Order==================");
            TableListControl.printOccupied();
            System.out.print("Select Table ID or " + TableListControl.BACK_OPTION + " to go back: ");
            if(!sc.hasNextInt()){
                System.out.println("Please enter a number!");
                System.out.println();
                sc.nextLine();
                continue;
            }else {
                choice = sc.nextInt();
                sc.nextLine();
            }
            if (choice == TableListControl.BACK_OPTION){
                return;
            }
            table = TableListControl.getTable(choice);
            if (table==null){
                System.out.println("Invalid Option!");
                System.out.println();
            } else if (table.isOccupied()){
                break;
            }else{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        Staff staff = StaffListControl.getStaff(staffID);
        Order newOrder = new Order(staff);

        /* Taking order */
        while(true) {
            System.out.println("=======Creating Order=======");
            System.out.println("|1. Add Food               |");
            System.out.println("|2. Remove Food            |");
            System.out.println("|3. View Order             |");
            System.out.println("|4. Send Order             |");
            System.out.println("|0. Back                   |");
            System.out.println("============================");
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
                case 1-> OrderControl.addFood(newOrder);
                case 2-> OrderControl.removeFood(newOrder);
                case 3->{
                    newOrder.printOrder();
                    System.out.print("Press any key to go back ");
                    sc.nextLine();
                    System.out.println();
                }
                case 4-> {
                    if (newOrder.getSize()==0){
                        System.out.println("Order has no item! Unable to submit order!");
                        System.out.println();
                        break;
                    }

                    if (table.hasOrder()){
                        OrderControl.appendOrder(table.getOrder(), newOrder);
                    }else{
                        table.setOrder(newOrder);
                    }

                    System.out.println("Order has been successfully submitted!");
                    System.out.println();
                    return;
                }
                case 0->{
                    System.out.println("Order not submitted!");
                    System.out.println();
                    return;
                }
                default->{
                    System.out.println("Invalid Option!");
                    System.out.println();
                }
            }
        }

    }

    /**
     * Adds food items into an order.
     * @param order the order to add to.
     */
    private static void addFood(Order order) {
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
                    order.addToOrder(food, choice);
                    System.out.println(choice + " " + food.getName() + " has been added!");
                }
                System.out.println();
            }

        }

    }

    /**
     * Removes food item from an order.
     * @param order the order to remove from.
     */
    private static void removeFood(Order order) {
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true) {
            System.out.println("======================Removing Food=======================");
            order.printOrder();

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
                if (order.checkInOrder(food)){
                    while(true) {
                        System.out.print("Enter Quantity to remove: ");
                        if (!sc.hasNextInt()) {
                            System.out.println("Please enter a number!");
                            System.out.println();
                            sc.nextLine();
                        } else {
                            choice = sc.nextInt();
                            sc.nextLine();
                            int qty = order.getFoodQty(food);
                            if (qty<choice || choice<=0) {
                                System.out.println("Invalid Quantity!");
                                System.out.println();
                            }else{
                                break;
                            }
                        }
                    }

                    order.removeFromOrder(food, choice);
                    System.out.println(choice + " " + food.getName() + " has been removed!");
                }else{
                    System.out.println("Invalid Option");
                }
                System.out.println();
            }
        }

    }

    /**
     * Adds an order to another order.
     * @param existingOrder the order to add to.
     * @param newOrder the order to add from.
     */
    private static void appendOrder(Order existingOrder, Order newOrder){
        for (HashMap.Entry<Food, Double[]> new_item : newOrder.getItems().entrySet()){
            existingOrder.addToOrder(new_item.getKey(), (int) Math.round(new_item.getValue()[0]));
        }

    }


    /**
     * Prints the Invoice for an order.
     * @param order the order to print the invoice for.
     * @param tableID the table ID associated to this order.
     * @param member the membership status of the customer associated to this order.
     */
    public static void printInvoice(Order order, int tableID, boolean member) {
        System.out.println("=========================Invoice==========================");
        System.out.printf("|| %-25s|| %-25s||\n", "Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), "Time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        System.out.printf("|| %-25s|| %-25s||\n", "Served by: " + order.getStaff().getName(), "Table ID: " + tableID);

        order.printOrder();
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
     * @param order the order to apply the discount to.
     * @return the discounted amount.
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
