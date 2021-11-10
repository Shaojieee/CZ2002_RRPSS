package Control;

import Entity.TableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ManagerControl extends StaffControl{



    /**
     * Runs the function associated with the choice as displayed by <code>printActions()</code>.
     * @param choice  the choice selected by this manager.
     * @return        <code>true</code> if manager chooses to log out, <code>false</code> otherwise.
     */
    public static boolean getAction(int choice){
        switch(choice){
            case 1-> createOrder();
            case 2-> clearTable();
            case 3-> createReservation();
            case 4-> deleteReservation();
            case 5-> printReservations();
            case 6-> checkTableDetails();
            case 7-> allocateTable();
            case 8-> editMembers();
            case 9-> editMenu();
            case 10-> editStaff();
            case 11-> editTable();
            case 12-> generateSalesReport();
            case 13-> exitProgram();
            case 14->{
                return true;
            }
            default->{
                System.out.println("Invalid Option!");
                System.out.println();
            }
        }
        return false;
    }

    /**
     * Get the start and end date and generates the sales report for the period.
     */
    private static void generateSalesReport() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true){
            System.out.println("==Generate Sales Report==");
            System.out.println("|1. By Month            |");
            System.out.println("|2. By Day              |");
            System.out.println("|0. Back                |");
            System.out.println("=========================");
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1 ->{
                    try{
                        System.out.print("Enter the start month (mm/yyyy): ");
                        String start_str = sc.nextLine();
                        System.out.print("Enter the end month(inclusive) (mm/yyyy): ");
                        String end_str = sc.nextLine();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate start = LocalDate.parse("01/"+start_str, format);
                        LocalDate end = LocalDate.parse("01/"+end_str, format);
                        int last_day = end.lengthOfMonth();
                        end = LocalDate.parse(last_day+"/"+end_str, format);
                        SalesReport report = SalesReport.getSalesReport();
                        report.printRevenue(start, end, false);
                        System.out.print("Press any key to back ");
                        sc.nextLine();
                    }catch(DateTimeParseException e){
                        System.out.println("Invalid Format!");
                        System.out.println();
                    }
                }
                case 2->{
                    try{
                        System.out.print("Enter the start date (dd/mm/yyyy): ");
                        String start_str = sc.nextLine();
                        System.out.print("Enter the end date(inclusive) (dd/mm/yyyy): ");
                        String end_str = sc.nextLine();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate start = LocalDate.parse(start_str, format);
                        LocalDate end = LocalDate.parse(end_str, format);
                        SalesReport report = SalesReport.getSalesReport();
                        report.printRevenue(start, end, true);
                        System.out.print("Press any key to back ");
                        sc.nextLine();
                    }catch(DateTimeParseException e){
                        System.out.println("Invalid Format!");
                        System.out.println();
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

    /**
     * Add or remove tables from the restaurant.
     */
    private static void editTable() {
        Scanner sc = new Scanner(System.in);
        TableList tableList = TableList.getTableList();
        int choice=-1;

        while(choice != 0) {
            System.out.println("=======Edit List of Tables=======");
            System.out.println("|1. Add Table                   |");
            System.out.println("|2. Delete Table                |");
            System.out.println("|3. Print Current Table Details |");
            System.out.println("|0. Back                        |");
            System.out.println("=================================");
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1:
                    System.out.print("Enter number of pax: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    while(choice%2!=0 || choice<2 || choice>10){
                        System.out.println("Invalid Pax");
                        System.out.print("Enter number of pax: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                    }
                    tableList.addTable(choice);
                    System.out.println("Table (Pax: " + choice + ") has been added!");
                    System.out.println();
                    break;

                case 2:
                    tableList.printAllTables();
                    System.out.print("Enter Table ID to delete: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    Table table = tableList.getTable(choice);
                    if(table==null){
                        System.out.println("Invalid Option!");
                        System.out.println();
                    }else if(table.isOccupied()){
                        System.out.println("Table is currently occupied!");
                    }else{
                        ReservationList reservationList = ReservationList.getReservationList();
                        if(reservationList.checkDelete(table.getTableId())){
                            tableList.deleteTable(table.getTableId());
                            reservationList.deleteTable(table.getTableId());
                        }else{
                            System.out.println("Unable to delete table as there will be unallocated reservations!");
                        }
                    }
                    System.out.println();
                    break;

                case 3:
                    tableList.printAllTables();
                    System.out.print("Press any key to go back ");
                    sc.nextLine();
                    System.out.println();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid Option!");
                    System.out.println();
                    break;
            }
        }
    }


    /**
     * Add or remove staffs from the restaurant.
     * This manager will not be able to remove himself.
     */
    private static void editStaff() {
        Scanner sc = new Scanner(System.in);
        StaffList staffList = StaffList.getStaffList();
        int choice=-1;

        while(choice != 0) {
            System.out.println("=======Edit List of Staffs========");
            System.out.println("|1. Add Staff                    |");
            System.out.println("|2. Delete Staff                 |");
            System.out.println("|3. Print Current Staff Details  |");
            System.out.println("|0. Back                         |");
            System.out.println("==================================");
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1:
                    System.out.print("Enter Staff Name: ");
                    String name = sc.nextLine();
                    if (staffList.checkStaff(name)){
                        System.out.println("Staff already exist");
                        break;
                    }
                    String gender;
                    while (true){
                        System.out.println("======Gender======");
                        System.out.println("|1. Male         |");
                        System.out.println("|2. Female       |");
                        System.out.println("==================");
                        System.out.print("Select Gender: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice == 1) {
                            gender = "M";
                            break;
                        } else if (choice == 2) {
                            gender = "F";
                            break;
                        }else{
                            System.out.println("Invalid Option!");
                            System.out.println();
                        }
                    }

                    while (true) {
                        System.out.println("====Job Titles====");
                        System.out.println("|1. Manager      |");
                        System.out.println("|2. Staff        |");
                        System.out.println("==================");
                        System.out.print("Select Job Title: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice == 1) {
                            staffList.addManager(name, gender);
                            break;
                        } else if (choice == 2) {
                            staffList.addStaff(name, gender);
                            break;
                        }else{
                            System.out.println("Invalid Option!");
                            System.out.println();
                        }
                    }
                    System.out.println(name + "(" + gender + ")" + " has been added!");
                    System.out.println();
                    break;
                case 2:
                    while(true) {
                        System.out.println("=============Deleting Staff==============");
                        staffList.printStaffList();
                        System.out.print("Select Staff ID to delete or " +
                                "0 to back: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if(choice==0){
                            choice = 1;
                            break;
                        }
                        if( this.getID()==choice){
                            System.out.println("Cannot delete own account!");

                        } else if (staffList.checkStaff(choice)) {
                            staffList.deleteStaff(choice);
                            System.out.println("Staff has been deleted!");
                        } else {
                            System.out.println("Invalid Staff ID");
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    staffList.printStaffList();
                    System.out.print("Press any key to go back ");
                    sc.nextLine();
                    System.out.println();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Option!");
                    System.out.println();
                    break;
            }
        }
    }

    /**
     * Add, remove or edit the menu.
     */
    private static void editMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        Menu menu = Menu.getMenu();
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
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1 -> this.addFood(FoodType.MAINCOURSE);
                case 2 -> this.addFood(FoodType.DRINK);
                case 3 -> this.addFood(FoodType.DESSERT);
                case 4 -> {
                    System.out.print("Please enter name of set: ");
                    String name = sc.nextLine();
                    if(menu.getFood(name,FoodType.PROMOTIONSET)!=null){
                        System.out.println("Promotion Set already exist!");
                        System.out.println();
                        break;
                    }
                    PromoSet newSet = menu.newPromoSet(name);
                    editPromoSet(newSet);
                }
                case 5 -> {
                    while(true) {
                        System.out.println("==========Edit Food===========");
                        menu.printMenu(true);
                        System.out.print("Please enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if(choice == Menu.BACK_OPTION){
                            break;
                        }
                        FoodType action = menu.getMenuAction(choice, true);

                        if (action==null) {
                            System.out.println("Invalid Option!");
                            System.out.println();
                            continue;
                        }
                        while (true) {
                            System.out.println("=====================Edit Food======================");
                            menu.printCurrentMenu(action);
                            System.out.print("Select Food ID to edit or "+ Menu.BACK_OPTION + " to back: ");
                            choice = sc.nextInt();
                            sc.nextLine();
                            if(choice == Menu.BACK_OPTION){
                                break;
                            }
                            Food food = menu.getFood(choice, action);
                            if(food==null){
                                System.out.println("Invalid Option!");
                                System.out.println();
                                continue;
                            }
                            if (action != FoodType.PROMOTIONSET) {
                                /* In specific food type menu page */
                                while(choice!=0){
                                    System.out.println("===============Edit Food===============");
                                    System.out.printf("| %-29s%7s|\n", food.getName(), "$"+String.format("%.2f", food.getPrice()));
                                    System.out.println("|1. Change Name                       |");
                                    System.out.println("|2. Change Price                      |");
                                    System.out.println("|0. Back                              |");
                                    System.out.println("=======================================");
                                    System.out.print("Please enter your choice: ");
                                    choice = sc.nextInt();
                                    sc.nextLine();

                                    switch(choice){
                                        case 1->{
                                            String old = food.getName();
                                            System.out.print("Enter new name: ");
                                            String name = sc.nextLine();
                                            if (menu.getFood(name)!=null){
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
                case 6 -> {
                    while(true){
                        System.out.println("========Deleting Food=========");
                        menu.printMenu(true);
                        System.out.print("Please enter your choice: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        if(choice==Menu.BACK_OPTION){
                            break;
                        }
                        FoodType action = menu.getMenuAction(choice, true);
                        if (action==null){
                            System.out.println("Invalid Option");
                            System.out.println();
                            continue;
                        }
                        while (true) {
                            System.out.println("===================Deleting Food====================");
                            menu.printCurrentMenu(action);
                            System.out.print("Select Food ID to delete or " + Menu.BACK_OPTION + " to go back: ");
                            /* In specific food type menu page */
                            choice = sc.nextInt();
                            sc.nextLine();
                            if (choice == Menu.BACK_OPTION) {
                                break;
                            }
                            Food food = menu.getFood(choice, action);
                            if (food == null) {
                                System.out.println("Invalid Option!");
                            } else {
                                menu.deleteFood(food);
                                System.out.println(food.getName() + " has been removed!");
                            }
                            System.out.println();
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

    /**
     * Get the details of the food item and add to the menu.
     * @param type the type of food being added.
     */
    private void addFood(FoodType type){
        Scanner sc = new Scanner(System.in);
        Menu menu = Menu.getMenu();
        System.out.print("Enter name of " + type+ ": ");
        String name = sc.nextLine();
        if (menu.getFood(name, type)!=null){
            System.out.println(type + " already exist");
            System.out.println();
            return;
        }
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        while(price<=0.0){
            System.out.println("Invalid Price!");
            System.out.print("Enter Price: ");
            price = sc.nextDouble();
            sc.nextLine();
        }
        menu.addFood(name, Math.round(price*100.0)/100.0, type);
        System.out.println(name + " has been add to " + type + "!");
        System.out.println();
    }

    /**
     * Edit promotion set.
     * @param newSet the promotion set being edited.
     */
    private void editPromoSet(PromoSet newSet){
        int choice;
        Scanner sc = new Scanner(System.in);
        Menu menu = Menu.getMenu();
        while(true){
            System.out.println("====Edit Promotion Set====");
            System.out.println("|1. Add Food             |" );
            System.out.println("|2. Remove Food          |" );
            System.out.println("|3. Set Price            |" );
            System.out.println("|4. View Set             |" );
            System.out.println("|5. Save Set             |" );
            System.out.println("|0. Back                 |");
            System.out.println("==========================");
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1->{
                    while (true) {
                        System.out.println("=====Add to Promotion Set=====");
                        menu.printMenu(false);
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice == Menu.BACK_OPTION) {
                            break;
                        }
                        FoodType action = menu.getMenuAction(choice, false);
                        if (action == null) {
                            System.out.println("Invalid Option!");
                            System.out.println();
                            continue;
                        }
                        while(true){
                            System.out.println("================Add to Promotion Set================");
                            menu.printCurrentMenu(action);
                            System.out.print("Select Food ID to add or " + Menu.BACK_OPTION + " to back: ");
                            choice = sc.nextInt();
                            sc.nextLine();
                            if(choice==Menu.BACK_OPTION){
                                break;
                            }
                            Food food = menu.getFood(choice, action);
                            if (food == null) {
                                System.out.println("Invalid Food ID!");
                            } else {
                                System.out.print("Enter Quantity: ");
                                int quantity = sc.nextInt();
                                sc.nextLine();
                                while (quantity <= 0) {
                                    System.out.println("Invalid Quantity!");
                                    System.out.print("Enter Quantity: ");
                                    quantity = sc.nextInt();
                                    sc.nextLine();
                                }
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
                case 2->{
                    while (true) {
                        if (newSet.getSize() == 0) {
                            System.out.println("There are currently no items in this Promotion Set!");
                            System.out.println();
                            break;
                        }
                        System.out.println("===================Deleting Food====================");
                        newSet.printFoodID();
                        System.out.print("Select Food ID to delete or " + Menu.BACK_OPTION + " to back: ");


                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice == Menu.BACK_OPTION) {
                            break;
                        } else {
                            Food food = menu.getFood(choice);
                            if (newSet.inSet(food)) {
                                System.out.print("Enter Quantity: ");
                                int quantity = sc.nextInt();
                                sc.nextLine();
                                if (newSet.getQty(food) >= quantity && quantity>=0) {
                                    newSet.removeFood(food, quantity);
                                } else {
                                    System.out.println("Invalid Quantity!");
                                    System.out.println();
                                }
                                System.out.println(quantity + " " + food.getName() + " has been removed from Promotion Set!");
                            } else {
                                System.out.println(food.getName() + " is not in Promotion Set!");
                            }
                            System.out.println();
                        }
                    }
                }
                case 3->{
                    System.out.print("Enter new Price: ");
                    double price = sc.nextDouble();
                    sc.nextLine();
                    while(price<=0.0){
                        System.out.println("Invalid Price!");
                        System.out.print("Enter new Price: ");
                        price = sc.nextDouble();
                        sc.nextLine();
                    }
                    newSet.setPrice(Math.round(price*100.0)/100.0);
                    System.out.println("Price has been set at $" +  String.format("%.2f",newSet.getPrice()) + "!");
                    System.out.println();
                }
                case 4->{
                    System.out.println("====================================================");
                    newSet.printFood();
                    System.out.print("Press any key to back ");
                    sc.nextLine();
                    System.out.println();
                }
                case 5->{
                    boolean test = true;
                    if(newSet.getSize()==0){
                        test=false;
                        System.out.println("Promotion Set is empty! Please add food!");
                    }
                    if(newSet.getPrice()==0.00){
                        test=false;
                        System.out.println("Promotion Set price is set at $0.00! Please set price!");
                    }
                    if(test){
                        menu.addPromoSet(newSet);
                        System.out.println(newSet.getName() + " has been saved!");
                        System.out.println();
                        return;
                    }else{
                        System.out.println();
                    }

                }
                case 0 ->{
                    System.out.println(newSet.getName() + " not added to menu!");
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
}
