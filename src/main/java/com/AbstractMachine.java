package com;

import com.google.gson.Gson;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
@Primary
public abstract class AbstractMachine {

    //Instance variables
    static final String COMPANY = "Backpfeifengesicht";
    static private Map<COINS, Integer> holdings = new HashMap<COINS, Integer>();
    static private String myLocation = "HOME";
    static protected LinkedList<Product>[][] machine;
    private CoinBuffer coinBuffer;
    static private String machineId;

    public enum COINS {
        NICKEL(.05), DIME(.10), QUARTER(.25);
        double value;

        COINS(double v) {
            value = v;
        }

        double getValue() {
            return value;
        }
    }

    AbstractMachine() {     //removed setting location constuctor
        holdings.put(COINS.NICKEL, 5);
        holdings.put(COINS.DIME, 0);
        holdings.put(COINS.QUARTER, 6);

        this.createRows();
        this.hardCodedMachineFiller();
        coinBuffer = new CoinBuffer(this);
    }

    public void startCoinBuffer(){
        coinBuffer.initiateInterface();

    }

    //Getters
    public String getMyLocation() {
        return myLocation;
    }

    static public String getMoneyAmount() {
        double total = 0;
        for (Map.Entry<COINS, Integer> m : holdings.entrySet()) {
            total += m.getKey().getValue() * m.getValue();
        }
        return Double.toString(total);
    }

    // used to return each coin
    static public String getCoinAmount() {

/*
        holdings.put(COINS.DIME, new Integer(4));
        holdings.put(COINS.NICKEL, new Integer(5));
        holdings.put(COINS.QUARTER, new Integer(6));
*/

        Gson gson = new Gson();
        return gson.toJson(holdings);
    }

    //Methods
    public void addItem(String RowColumn, Product product) throws BADENTRY {
        char[] entry = RowColumn.toCharArray();
        int row;
        int col;
        switch (entry[0]) {
            case 'A':
                row = 0;
                break;
            case 'B':
                row = 1;
                break;
            case 'C':
                row = 2;
                break;
            case 'D':
                row = 3;
                break;
            case 'E':
                row = 4;
                break;
            default:
                throw new BADENTRY();
        }
        col = Character.getNumericValue(entry[1]);
        machine[row][col].add(product);
    }

    public double removeItem(String RowColumn, double runningTotal) throws BADENTRY, INSUFFICIENTFUNDS {
        char[] entry = RowColumn.toUpperCase().toCharArray();
        int row;
        int col;
        switch (entry[0]) {
            case 'A':
                row = 0;
                break;
            case 'B':
                row = 1;
                break;
            case 'C':
                row = 2;
                break;
            case 'D':
                row = 3;
                break;
            case 'E':
                row = 4;
                break;
            default:
                throw new BADENTRY();
        }
        col = Character.getNumericValue(entry[1]);

        if (machine[row][col].isEmpty()) {
            throw new BADENTRY();
        }

        double price = 0;

        try {

            price = ((machine[row][col].peek()).retailPrice);

            if (price > runningTotal) {
                throw new INSUFFICIENTFUNDS();
            }

            System.out.println("Enjoy Your "+ machine[row][col].pop().name);

        } catch (ArrayIndexOutOfBoundsException ar) {
            throw new BADENTRY();

        }
        return runningTotal - price;
    }

    public void addQuarters(int quantity) {
        int currentQuantity = holdings.get(COINS.QUARTER);
        int newQuantity = currentQuantity + quantity;
        holdings.replace(COINS.QUARTER, newQuantity);
    }

    public void addDimes(int quantity) {
        int currentQuantity = holdings.get(COINS.DIME);
        int newQuantity = currentQuantity + quantity;
        holdings.replace(COINS.DIME, newQuantity);
    }

    public void addNickels(int quantity) {
        int currentQuantity = holdings.get(COINS.NICKEL);
        int newQuantity = currentQuantity + quantity;
        holdings.replace(COINS.NICKEL, newQuantity);
    }

/*    public void displayMoneyAmount() {
        double total = 0;
        for (Map.Entry<COINS, Integer> m : holdings.entrySet()) {
            System.out.println(m.getKey() + ":\t\t" + m.getValue());
            total += m.getKey().getValue() * m.getValue();
        }
        System.out.println("Total $:\t" + total);
    }*/

    static public void changeLocation(String location) {
        myLocation = location;
    }

    static public String getLocation(){return myLocation;}

    //Abstract methods
    public void displayInventory() {
        System.out.println("Compartment\tName\tPrice");
        System.out.println("---------------------------------------");
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (machine[row][col].size() > 0) {
                    char rowLetter;
                    switch (row) {
                        case 0:
                            rowLetter = 'A';
                            break;
                        case 1:
                            rowLetter = 'B';
                            break;
                        case 2:
                            rowLetter = 'C';
                            break;
                        case 3:
                            rowLetter = 'D';
                            break;
                        case 4:
                            rowLetter = 'E';
                            break;
                        default:
                            rowLetter = ' ';
                    }
                    Product p = machine[row][col].peek();
                    System.out.println(rowLetter + Integer.toString(col) + "\t\t\t" + p.name + "\t\t\t" + p.retailPrice);
                }
            }
        }
    }

    abstract void hardCodedMachineFiller();

    abstract void createRows();

    /*public void getLogFile() {

            String log;
            String OS = System.getProperty("os.name").toLowerCase();
            if(OS.contains("windows")){
                log="logs\\";
            }else
            {
                log = "logs/";

            }
            String logPathName = log + myMachineID + ".txt";

            URL url = getClass().getResource(logPathName);
            File file = new File(url.getPath());

            try (BufferedReader reader = Files.newBufferedReader(file.toPath())){

                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            } catch (NullPointerException n) {
                System.out.println("You must be using Windows you filthy animal?");
            }

        }

    }

    public void createLogFile(){
        String log;
        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.contains("windows")){
            log="logs\\";
        }else
        {
            log = "logs/";

        }

        URL logFolder = getClass().getResource(log);
        String machineLogPath = logFolder.getPath() + ".txt";

        File file = new File(machineLogPath);

        try {
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                writer.write("Created \t\t\t " + LocalDateTime.now());
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    //Probably useless
    //returns a String of accepted coins
    public String getAcceptedCoins() {
        StringBuilder acceptable = new StringBuilder();
        for (COINS c : COINS.values()) {
            acceptable.append(c).append(" ");

        }
        return acceptable.toString();
    }

}
