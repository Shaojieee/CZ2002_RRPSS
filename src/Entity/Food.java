package Entity;

/**
 * This class contains all the information of a particular food.<br>
 * It includes the name, price, ID and type of food.
 */
public class Food implements java.io.Serializable{
    /**
     * Name of food.
     */
    private String name;
    /**
     * Price of food.
     */
    private double price;
    /**
     * ID of food.
     */
    private final int id;
    /**
     * Type of food.
     */
    private final FoodType type;

    /**
     * This constructor defines a food item.
     * @param name the name of this food.
     * @param price the price of this food.
     * @param id the ID of this food.
     * @param type the type of this food.
     */
    public Food(String name,double price,int id,FoodType type) {
        this.name=name;
        this.price=price;
        this.id=id;
        this.type = type;
    }

    /**
     * @return the name of this food.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the new name of this food.
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     * @return the price of this food.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price the new price of this food.
     */
    public void setPrice(double price) {
        this.price=price;
    }

    /**
     * @return the ID of this food.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the food type of this food.
     */
    public FoodType getFoodType() {
        return this.type;
    }


    /**
     * Prints the details of this food item.
     */
    public void printFood(){
        System.out.printf("|| %-3s|| %-30s|| %-8s||\n", this.getId(), this.getName(), "$"+String.format("%.2f",this.getPrice()));
    }

}

