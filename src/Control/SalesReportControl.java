package Control;

import Entity.Food;
import Entity.Order;
import Entity.SalesReport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * This class contains the functions used to control the sales report.
 */
public class SalesReportControl {

    /**
     * Update the sales data with an order.
     * @param order the order to add.
     */
    public static void updateReport(Order order) {
        LocalDate date = LocalDate.now();
        HashMap<String, HashMap<Food, Double[]>> data = SalesReport.getSalesReport().getData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String date_str = formatter.format(date);
        String[]dates= date_str.split(" ");
        String s = String.format("%s %s %s", dates[0], dates[1],dates[2]);//day,month,year

        HashMap<Food,Double[]> items = order.getItems();

        if(data.containsKey(s)){
            HashMap <Food, Double[]> day = data.get(s);
            Food data_item;
            for (Food food : items.keySet()) {
                if((data_item = findFood(s, food))!=null){
                    day.get(data_item)[0]+=items.get(food)[0];
                    day.get(data_item)[1]+=items.get(food)[1];
                } else {
                    day.put(food, items.get(food));
                }
            }
        } else {
            data.put(s, items);
        }
        saveSalesReport();
    }

    /**
     * Gets the period to print the sales report and prints accordingly.
     * Will not print days that have no recorded sales.
     */
    public static void printRevenue() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true){
            System.out.println("==Generate Sales Report==");
            System.out.println("|1. By Month            |");
            System.out.println("|2. By Day              |");
            System.out.println("|0. Back                |");
            System.out.println("=========================");
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

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

                        start_str = formatter.format(start);
                        end_str = formatter.format(end);
                        ArrayList<String> keys = sortCalendar(start_str, end_str);

                        printRevenueByMonth(keys, start_str, end_str);
                        System.out.print("Press any key to back ");
                        sc.nextLine();
                        return;

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

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

                        start_str = formatter.format(start);
                        end_str = formatter.format(end);
                        ArrayList<String> keys = sortCalendar(start_str, end_str);

                        printRevenueByDay(keys, start_str, end_str);
                        System.out.print("Press any key to back ");
                        sc.nextLine();
                        return;

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
     * Prints the sales report by day within the specified period.
     * @param dates the dates in descending order.
     * @param start_str the start date.
     * @param end_str the end date.
     */
    private static void printRevenueByDay(ArrayList<String> dates, String start_str, String end_str){
        HashMap<Food, Double[]> items;
        HashMap<String, HashMap<Food, Double[]>> data = SalesReport.getSalesReport().getData();
        double period_total = 0.00;
        double day_total;
        Food food;
        int qty;
        double price;
        int count = 0;
        System.out.println("=======Sales Report from "+ start_str + " to " + end_str +"========");
        for(String key:dates) {
            count += 1;
            day_total = 0.00;
            items = data.get(key);
            System.out.printf("|| %-54s||\n", "Date: "+ key);
            System.out.printf("|| %-3s   %-30s   %-3s   %-9s||\n", "ID", "Name", "Qty", "Price");
            for(HashMap.Entry<Food, Double[]> item : items.entrySet()){
                food = item.getKey();
                qty = item.getValue()[0].intValue();
                price = item.getValue()[1];
                day_total += price;
                System.out.printf("|| %-3s   %-30s   %-3s   %-9s||\n", food.getId(), food.getName(), qty, "$"+String.format("%.2f", price));
            }
            System.out.printf("|| %45s$%-8s||\n","Day Total: ", String.format("%.2f", day_total));
            System.out.println("===========================================================");
            period_total += day_total;
        }
        if(count==0){
            System.out.println("|                   No records found!                    |");
        }else{
            System.out.printf("|| %45s$%-8s||\n","Total from " + start_str + " to " + end_str + ":  ",String.format("%.2f", period_total));
            System.out.println("===========================================================");
        }
    }

    /**
     * Prints the sales report by month within the specified period.
     * @param dates the dates in descending order.
     * @param start_str the start date.
     * @param end_str the end date.
     */
    private static void printRevenueByMonth(ArrayList<String> dates, String start_str, String end_str) {
        HashMap<Food, Double[]> month = new HashMap<Food, Double[]>();
        HashMap<String, HashMap<Food, Double[]>> data= SalesReport.getSalesReport().getData();
        double period_total = 0.00;
        double month_total = 0.00;
        Food food;
        int qty;
        double price;
        int count = 0;
        String cur_month = dates.get(0).substring(3);
        System.out.println("==========Sales Report from " + start_str.substring(3) + " to " + end_str.substring(3) + "===========");
        for (String key : dates) {
            if(cur_month.equals(key.substring(3))){
                for(HashMap.Entry<Food, Double[]> item : data.get(key).entrySet()){
                    count += 1;
                    Double[] cur = month.getOrDefault(item.getKey(), new Double[]{0.0 ,0.0});
                    cur[0] += item.getValue()[0];
                    cur[1] += item.getValue()[1];
                    month_total += item.getValue()[1];
                    month.put(item.getKey(), cur);
                }
            }else{
                System.out.printf("|| %-54s||\n", "Date: " + cur_month);
                System.out.printf("|| %-3s   %-30s   %-3s   %-9s||\n", "ID", "Name", "Qty", "Price");
                for (HashMap.Entry<Food, Double[]> item : month.entrySet()) {
                    food = item.getKey();
                    qty = item.getValue()[0].intValue();
                    price = item.getValue()[1];
                    month_total += price;
                    System.out.printf("|| %-3s   %-30s   %-3s   %-9s||\n", food.getId(), food.getName(), qty, "$" + String.format("%.2f", price));
                }
                System.out.printf("|| %45s$%-8s||\n", "Month Total: ", String.format("%.2f", month_total));
                System.out.println("===========================================================");
                period_total += month_total;

                month_total = 0.00;
                cur_month = key.substring(3);
                month.clear();
                for(HashMap.Entry<Food, Double[]> item : data.get(key).entrySet()){
                    count += 1;
                    Double[] cur = month.getOrDefault(item.getKey(), new Double[]{0.0 ,0.0});
                    cur[0] += item.getValue()[0];
                    cur[1] += item.getValue()[1];
                    month_total += item.getValue()[1];
                    month.put(item.getKey(), cur);
                }
            }
        }

        System.out.printf("|| %-54s||\n", "Date: " + dates.get(dates.size()-1).substring(3));
        System.out.printf("|| %-3s   %-30s   %-3s   %-9s||\n", "ID", "Name", "Qty", "Price");
        for (HashMap.Entry<Food, Double[]> item : month.entrySet()) {
            food = item.getKey();
            qty = item.getValue()[0].intValue();
            price = item.getValue()[1];
            month_total += price;
            System.out.printf("|| %-3s   %-30s   %-3s   %-9s||\n", food.getId(), food.getName(), qty, "$" + String.format("%.2f", price));
        }
        System.out.printf("|| %45s$%-8s||\n", "Month Total: ", String.format("%.2f", month_total));
        System.out.println("===========================================================");
        period_total += month_total;


        if (count == 0) {
            System.out.println("|                   No records found!                    |");
        } else {
            System.out.printf("|| %45s$%-8s||\n", "Total from " + start_str.substring(3) + " to " + end_str.substring(3) + ": ", String.format("%.2f", period_total));
            System.out.println("===========================================================");
        }
    }

    /**
     * Save all the sales data.
     */
    public static void saveSalesReport(){
        FileEditor.writeSalesReport(SalesReport.getSalesReport().getData());
    }

    /**
     * Gets the <code>Food</code> object instance from a specified date in the sales data.
     * @param date the date to search.
     * @param food the food item to find.
     * @return the <code>Food</code> object instance in the sales data.
     */
    private static Food findFood(String date,Food food){
        int search_id = food.getId();
        HashMap<String, HashMap<Food, Double[]>> data = SalesReport.getSalesReport().getData();
        for(Food data_item : data.get(date).keySet()){
            if(data_item.getId() == search_id){
                return data_item;
            }
        }
        return null;
    }

    /**
     * Sorts the date in the sales data and return the dates in the specified period in descending order.
     * @param start the start date.
     * @param end the end date.
     * @return an array of String with the dates in the specified period in descending order.
     */
    private static ArrayList<String> sortCalendar(String start, String end) {
        String[] calendar = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        HashMap<String, HashMap<Food, Double[]>> data = SalesReport.getSalesReport().getData();
        Object[] keys = data.keySet().toArray();
        for(int i=0;i<keys.length;i++) {
            String[] words = keys[i].toString().split(" ");
            for(int j=0;j<12;j++) {
                if("Sept".equals(words[1])){
                    words[1] = String.valueOf(8+10);
                    break;
                }
                if(calendar[j].equals(words[1])) {
                    words[1]=String.valueOf(j+10);
                    break;
                }
            }
            keys[i]=Integer.valueOf(String.format("%s%s%s",words[2],words[1],words[0]));
        }

        Object[] start_end = {start, end};
        for(int i=0;i<start_end.length;i++) {
            String[] words = start_end[i].toString().split(" ");
            for(int j=0;j<12;j++) {
                if("Sept".equals(words[1])){
                    words[1] = String.valueOf(8+10);
                    break;
                }
                if(calendar[j].equals(words[1])) {
                    words[1]=String.valueOf(j+10);
                    break;
                }
            }
            start_end[i]=Integer.valueOf(String.format("%s%s%s",words[2],words[1],words[0]));
        }


        Arrays.sort(keys, Collections.reverseOrder());
        ArrayList<String> ans = new ArrayList<>();
        for (Object key : keys) {
            if ((int) key < (int) start_end[0] || (int) key > (int) start_end[1]) {
                continue;
            }
            String str = String.valueOf(key);
            int index = 4;
            char[] chars = str.toCharArray();
            String year = new String(chars, 0, index);
            String month = new String(chars, index, 2);
            String date = new String(chars, 6, 2);
            int monthi = Integer.parseInt(month) - 10;
            month = calendar[monthi];
            str = String.format("%s %s %s", date, month, year);
            ans.add(str);
        }

        return ans;
    }
}
