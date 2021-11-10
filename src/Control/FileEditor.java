package Control;

import Entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class reads, writes and stores data in files.
 */

public class FileEditor {
    /**
     * The file directory to the staff data.
     */
    private static final String staffFile = "data/Staff.ser";

    /**
     * The file directory to the main course data.
     */
    private static final String mainCourseFile = "data/MainCourse.ser";

    /**
     * The file directory to the drinks data.
     */
    private static final String drinksFile = "data/Drinks.ser";

    /**
     * The file directory to the desserts data.
     */
    private static final String dessertsFile = "data/Dessert.ser";

    /**
     * The file directory to the promotion set data.
     */
    private static final String promoSetsFile = "data/PromoSet.ser";

    /**
     * The file directory to the members data.
     */
    private static final String membersFile = "data/Member.ser";

    /**
     * The file directory to the table data.
     */
    private static final String tablesFile = "data/Tables.ser";

    /**
     * The file directory to the sales report data.
     */
    private static final String salesReportFile = "data/SalesReport.ser";

    /**
     * The file directory to the reservations data.
     */
    private static final String reservationsFile = "data/ReservationList.ser";


    /**
     * Writes into the staff file.
     * @param staff the list of staff to save.
     */
    public static void writeStaff(HashMap<Integer, Staff> staff){
        try{
            FileOutputStream fileOut = new FileOutputStream(staffFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(staff);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving Staffs");
        }
    }

    /**
     * Reads from the staff file.
     * @return all the staffs in the file.
     */
    public static HashMap<Integer, Staff> loadStaff(){
        HashMap<Integer, Staff> staffs = new HashMap<Integer, Staff> ();
        try{
            FileInputStream fileIn = new FileInputStream(staffFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            staffs = (HashMap<Integer, Staff> ) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading Staffs");
        }
        return staffs;
    }


    /**
     * Writes into the main course file.
     * @param mainCourse the list of main course to save.
     */
    public static void writeMainCourse(HashMap<Integer, Food> mainCourse){
        try{
            FileOutputStream fileOut = new FileOutputStream(mainCourseFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(mainCourse);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving MainCourse");
        }
    }

    /**
     * Reads from the main course file.
     * @return all the main course in the file.
     */
    public static HashMap<Integer, Food> loadMainCourse(){
        HashMap<Integer, Food> mainCourse = new HashMap<Integer, Food>();
        try{
            FileInputStream fileIn = new FileInputStream(mainCourseFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mainCourse = (HashMap<Integer, Food>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading MainCourse");
        }
        return mainCourse;
    }

    /**
     * Writes into the drinks file.
     * @param drinks the list of drinks to save.
     */
    public static void writeDrink(HashMap<Integer, Food> drinks){
        try{
            FileOutputStream fileOut = new FileOutputStream(drinksFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(drinks);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving Drinks");
        }
    }

    /**
     * Reads from the drinks file.
     * @return all the drinks in the file.
     */
    public static HashMap<Integer, Food> loadDrinks(){
        HashMap<Integer, Food> drinks = new HashMap<Integer, Food>();
        try{
            FileInputStream fileIn = new FileInputStream(drinksFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            drinks = (HashMap<Integer, Food>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading Drinks");
        }
        return drinks;
    }

    /**
     * Writes into the desserts file.
     * @param desserts the desserts to be added.
     */
    public static void writeDesserts(HashMap<Integer, Food> desserts){
        try{
            FileOutputStream fileOut = new FileOutputStream(dessertsFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(desserts);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving Desserts");
        }
    }

    /**
     * Reads from the desserts file.
     * @return all the desserts in the file.
     */
    public static HashMap<Integer, Food> loadDesserts(){
        HashMap<Integer, Food> desserts = new HashMap<Integer, Food>();
        try{
            FileInputStream fileIn = new FileInputStream(dessertsFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            desserts = (HashMap<Integer, Food>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading Desserts");
        }
        return desserts;
    }

    /**
     * Writes into the promotion set file.
     * @param promoSets the list of promotion sets to save.
     */
    public static void writePromoSets(HashMap<Integer, PromoSet> promoSets){
        try{
            FileOutputStream fileOut = new FileOutputStream(promoSetsFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(promoSets);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving PromoSets");
        }
    }

    /**
     * Reads from the promotion set file.
     * @return all the promotion sets in the file.
     */
    public static HashMap<Integer, PromoSet> loadPromoSets(){
        HashMap<Integer, PromoSet> promoSets = new HashMap<Integer, PromoSet>();
        try{
            FileInputStream fileIn = new FileInputStream(promoSetsFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            promoSets = (HashMap<Integer, PromoSet>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading PromoSets");
        }
        return promoSets;
    }

    /**
     * Writes into the members file.
     * @param members the list of members to save.
     */
    public static void writeMembers(ArrayList<Integer> members){
        try{
            FileOutputStream fileOut = new FileOutputStream(membersFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(members);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving Members");
        }
    }

    /**
     * Reads from the members file.
     * @return all the members in the file.
     */
    public static ArrayList<Integer> loadMembers(){
        ArrayList<Integer> members = new ArrayList<Integer>();
        try{
            FileInputStream fileIn = new FileInputStream(membersFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            members = (ArrayList<Integer>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading Members");
        }
        return members;
    }

    /**
     * Writes into the tables file.
     * @param tables the list of tables to save.
     */
    public static void writeTables(HashMap<Integer, Table> tables){
        try{
            FileOutputStream fileOut = new FileOutputStream(tablesFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.reset();
            out.writeObject(tables);
            out.close();
        }catch(Exception e){
            System.out.println("Error in saving Tables");
        }
    }

    /**
     * Reads from the tables file.
     * @return all the tables in the file.
     */
    public static HashMap<Integer, Table> loadTables(){
        HashMap<Integer, Table> tables = new HashMap<Integer, Table>();
        try{
            FileInputStream fileIn = new FileInputStream(tablesFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tables = (HashMap<Integer, Table>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading Tables");
        }
        return tables;
    }


    /**
     * Writes into the sales report file.
     * @param saleReport the sales report to save.
     */
    public static void writeSalesReport(HashMap<String, HashMap<Food, Double[]>> saleReport){
        try{
            FileOutputStream fileOut = new FileOutputStream(salesReportFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(saleReport);
        }catch(Exception e){
            System.out.println("Error in saving Sales Report");
            e.printStackTrace();
        }
    }

    /**
     * Reads from the sales report file.
     * @return all the sales report in the file.
     */
    public static HashMap<String, HashMap<Food, Double[]>> loadSalesReport(){
        HashMap<String, HashMap<Food, Double[]>> salesReport = new HashMap<String, HashMap<Food, Double[]>>();
        try{
            FileInputStream fileIn = new FileInputStream(salesReportFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            salesReport = (HashMap<String, HashMap<Food, Double[]>>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading SalesReport");
            e.printStackTrace();
        }
        return salesReport;
    }

    /**
     * Read from the reservations file.
     * @return all the reservations in the file.
     */
    public static HashMap<Integer, ArrayList<Reservation>> loadReservations() {
        HashMap<Integer, ArrayList<Reservation>> reservationList = new HashMap<Integer, ArrayList<Reservation>>();
        try{
            FileInputStream fileIn = new FileInputStream(reservationsFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            reservationList = (HashMap<Integer, ArrayList<Reservation>>) in.readObject();
            in.close();
        }catch(Exception e){
            System.out.println("Error in loading reservationList");
            e.printStackTrace();
        }
        return reservationList;
    }

    /**
     * Writes into the reservations file.
     * @param reservationList the list of reservations to save.
     */
    public static void writeReservations(HashMap<Integer, ArrayList<Reservation>> reservationList) {
        try{
            FileOutputStream fileOut = new FileOutputStream(reservationsFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(reservationList);
        }catch(Exception e){
            System.out.println("Error in saving Reservations");
            e.printStackTrace();
        }
    }
}
