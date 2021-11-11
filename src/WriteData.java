import Control.FileEditor;
import Entity.*;

import java.util.ArrayList;
import java.util.HashMap;

public class WriteData {


    public static void main(String[] args){


        HashMap<Integer, Staff> staffList = new HashMap<Integer, Staff>();

        staffList.put(1,new Staff("Darryl", 1, "M", Role.Staff));
        staffList.put(2,new Staff("Imax", 2, "M", Role.Staff));
        staffList.put(3,new Staff("Kai Teng", 3, "M", Role.Manager));
        staffList.put(4,new Staff("Zi Heng", 4, "M", Role.Staff));

        FileEditor.writeStaff(staffList);

        ArrayList<Integer> memberList = new ArrayList<Integer>();

        memberList.add(91234567);
        memberList.add(81234567);
        memberList.add(94569870);
        memberList.add(93245678);

        FileEditor.writeMembers(memberList);

        HashMap<Integer, Food> mainCourse = new HashMap<Integer, Food>();

        Food chickenAlfredo= new Food("Chicken Alfredo", 13.95, 1, FoodType.MAINCOURSE);

        mainCourse.put(1,chickenAlfredo);
        mainCourse.put(2,new Food("Chicken Picatta", 13.95,2,FoodType.MAINCOURSE));
        Food turkeyClub = new Food("Turkey Club", 11.95, 3, FoodType.MAINCOURSE);
        mainCourse.put(3,turkeyClub);
        Food lobsterPie= new Food("Lobster Pie",19.95,4,FoodType.MAINCOURSE);
        mainCourse.put(4,lobsterPie);
        Food seafoodAlfredo= new Food("Seafood Alfredo",15.95,5,FoodType.MAINCOURSE);
        mainCourse.put(5,seafoodAlfredo);
        Food primeRib = new Food("Prime Rib",20.95,6,FoodType.MAINCOURSE);
        mainCourse.put(6,primeRib);
        Food shrimpScampi = new Food("Shrimp Scampi",18.95,7,FoodType.MAINCOURSE);
        mainCourse.put(7,shrimpScampi);
        mainCourse.put(8,new Food("Turkey Dinner",13.95,8,FoodType.MAINCOURSE));
        mainCourse.put(9,new Food("Stuffed Chicken",14.95,9,FoodType.MAINCOURSE));

        FileEditor.writeMainCourse(mainCourse);


        HashMap<Integer, Food> drinks = new HashMap<Integer, Food>();

        Food cokeFloat= new Food("Coke Float", 4.90, 10, FoodType.DRINK);
        drinks.put(10,cokeFloat);
        drinks.put(11,new Food("Sprite Float", 4.90, 11, FoodType.DRINK));
        drinks.put(12,new Food("Mineral Water", 4, 12, FoodType.DRINK));
        drinks.put(13,new Food("Sparkling Water", 4.90, 13, FoodType.DRINK));
        drinks.put(14,new Food("Hot Chocolate", 5.90, 14, FoodType.DRINK));
        drinks.put(15,new Food("Caffe Latte", 5.90, 15, FoodType.DRINK));
        drinks.put(16,new Food("Cappuccino", 5.90, 16, FoodType.DRINK));
        drinks.put(17,new Food("Tiger Jug", 36.90, 17, FoodType.DRINK));
        drinks.put(18,new Food("Tiger Mug", 11.90, 18, FoodType.DRINK));
        Food tigerPint= new Food("Tiger Pint", 15.90, 19, FoodType.DRINK);
        drinks.put(19,tigerPint);
        drinks.put(20,new Food("Heineken", 11.90, 20, FoodType.DRINK));
        drinks.put(21,new Food("Erdinger", 16.90, 21, FoodType.DRINK));
        Food appleJuice=new Food("Apple Juice", 6.90, 22, FoodType.DRINK);
        drinks.put(22,appleJuice);
        drinks.put(23,new Food("Cranberry Juice", 6.90, 23, FoodType.DRINK));
        drinks.put(24,new Food("Lime Juice", 6.90, 24, FoodType.DRINK));
        FileEditor.writeDrink(drinks);


        HashMap<Integer, Food> desserts = new HashMap<Integer, Food>();

        Food gelatoMisto= new Food("Gelato Misto", 12.90 , 25, FoodType.DESSERT);
        desserts.put(25,gelatoMisto);
        Food tiramisuCake= new Food("Tiramisu Cake", 13.90 , 26, FoodType.DESSERT);
        desserts.put(26,tiramisuCake);
        desserts.put(27,new Food("Panna Cotta", 12.90 , 27, FoodType.DESSERT));
        Food lavaCake= new Food("Lava Cake", 15.90 , 28, FoodType.DESSERT);
        desserts.put(28,lavaCake);
        desserts.put(29,new Food("Dark Chocolate Mousse Cake", 12.90 , 29, FoodType.DESSERT));
        desserts.put(30,new Food("New York Cheese Cake", 12.90 , 30, FoodType.DESSERT));
        FileEditor.writeDesserts(desserts);

        HashMap<Integer, Table> tableList = new HashMap<Integer, Table>();

        Table table1 = new Table(2,1);
        Table table2 = new Table(4,2);
        Table table3 = new Table(6,3);
        Table table4 = new Table(8,4);
        Table table5 = new Table(10,5);


        tableList.put(1, table1);
        tableList.put(2, table2);
        tableList.put(3, table3);
        tableList.put(4, table4);
        tableList.put(5, table5);

        FileEditor.writeTables(tableList);

        HashMap<Integer, ArrayList<Reservation>> reservationList = new HashMap<Integer, ArrayList<Reservation>>();

        reservationList.put(1, new ArrayList<Reservation>());
        reservationList.put(2, new ArrayList<Reservation>());
        reservationList.put(3, new ArrayList<Reservation>());
        reservationList.put(4, new ArrayList<Reservation>());
        reservationList.put(5, new ArrayList<Reservation>());

        FileEditor.writeReservations(reservationList);


        HashMap<Integer, PromoSet> promoSet = new HashMap<Integer, PromoSet>();
        PromoSet set = new PromoSet("TGIF",31);
        set.setPrice(42.90);
        set.addFood(primeRib,1);
        set.addFood(tigerPint,1);
        set.addFood(tiramisuCake,1);
        promoSet.put(31,set);

        set = new PromoSet("Monday Blues",32);
        set.setPrice(35.0);
        set.addFood(appleJuice,1);
        set.addFood(turkeyClub,1);
        set.addFood(lavaCake,1);
        promoSet.put(32,set);

        set = new PromoSet("Seafood Platter",33);
        set.setPrice(99.0);
        set.addFood(seafoodAlfredo,2);
        set.addFood(shrimpScampi,2);
        set.addFood(lobsterPie,2);
        promoSet.put(33,set);

        set = new PromoSet("Kids Combo Set",34);
        set.setPrice(28.50);
        set.addFood(cokeFloat,1);
        set.addFood(gelatoMisto,1);
        set.addFood(chickenAlfredo,1);
        promoSet.put(34,set);

        FileEditor.writePromoSets(promoSet);


        HashMap<String, HashMap<Food, Double[]>> salesReport = new HashMap<String, HashMap<Food,Double[]>>();
        HashMap<Food, Double[]> day = new HashMap<Food , Double[]>();


        day.put(chickenAlfredo, new Double[] {25.0, Math.round(25.0* chickenAlfredo.getPrice()*100.0)/100.0});
        day.put(lavaCake, new Double[] {15.0, Math.round(15.0* lavaCake.getPrice()*100.0)/100.0});
        salesReport.put("10 Oct 2021", day);

        day = new HashMap<>();
        day.put(gelatoMisto, new Double[] {20.0, Math.round(20.0* gelatoMisto.getPrice()*100.0)/100.0});
        day.put(tigerPint, new Double[] {15.0, Math.round(15.0* tigerPint.getPrice()*100.0)/100.0});
        day.put(set, new Double[] {5.0, Math.round(5.0*set.getPrice()*100.0)/100.0});
        salesReport.put("20 Oct 2021", day);

        day = new HashMap<>();
        day.put(lobsterPie, new Double[] {10.0, Math.round(10.0* lobsterPie.getPrice()*100.0)/100.0});
        day.put(primeRib, new Double[] {15.0, Math.round(15.0* primeRib.getPrice()*100.0)/100.0});
        day.put(tiramisuCake, new Double[] {20.0, Math.round(20.0*tiramisuCake.getPrice()*100.0)/100.0});
        salesReport.put("30 Sep 2021", day);

        day = new HashMap<>();
        day.put(cokeFloat, new Double[] {10.0, Math.round(10.0* cokeFloat.getPrice()*100.0)/100.0});
        day.put(turkeyClub, new Double[] {12.0, Math.round(12.0* turkeyClub.getPrice()*100.0)/100.0});
        day.put(shrimpScampi, new Double[] {18.0, Math.round(18.0*shrimpScampi.getPrice()*100.0)/100.0});
        salesReport.put("01 Nov 2021", day);

        FileEditor.writeSalesReport(salesReport);





    }
}
