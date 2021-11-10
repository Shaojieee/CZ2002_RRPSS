package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * This class contains all the information of a particular order. <br>
 * It includes the items in the order, the total price and the staff that took this order.
 */
public class Order{

    /**
     * Food items in this order.
     * Key is the food item. Value is a list containing the quantity and total price of the food item.
     */
    private HashMap<Food,Double[]> items;

    /**
     * Total price of this order.
     */
    private double totalPrice;

    /**
     * The staff that took this order.
     */
    private Staff staff;


    /**
     * This constructor defines an order.
     * @param staff the staff that took this order.
     */
    public Order(Staff staff) {
        this.items = new HashMap<Food, Double[]>();
        this.totalPrice=0;
        this.staff=staff;
    }

    /**
     * @return the HashMap of food items in this order
     */
    public HashMap<Food,Double[]> getItems() {
        return this.items;
    }

    /**
     * Sets the price of this order.
     * @param price the new price of this order.
     */
    public void setPrice(double price){this.totalPrice = price;}

    /**
     * @return the staff that took this order.
     */
    public Staff getStaff() {
        return this.staff;
    }

    /**
     * @return the total number of food items in this order.
     */
    public int getSize(){
        return this.items.values().stream().mapToInt(x->x[0].intValue()).sum();
    }

    /**
     * Prints the food items in this order.
     */
    public void printOrder(){
        System.out.println("==========================Order===========================");
        if (this.items.size()==0){
            System.out.println("|          This order does not have any items!           |");
            System.out.println("==========================================================");
            System.out.println();
            return;
        }
        System.out.printf("|| %-3s|| %-30s|| %-3s|| %-8s||\n", "ID", "Name" , "Qty", "Price");
        Food food;
        int qty;
        double price;
        for (HashMap.Entry<Food, Double[]> item : this.items.entrySet()){
            food = item.getKey();
            qty = item.getValue()[0].intValue();
            price = item.getValue()[1];
            System.out.printf("|| %-3s|| %-30s|| %-3s|| %-8s||\n", food.getId(), food.getName(), qty, "$"+String.format("%.2f",price));
        }
        System.out.println("==========================================================");

    }

    public double getTotalPrice() {
        return this.totalPrice;
    }
}
