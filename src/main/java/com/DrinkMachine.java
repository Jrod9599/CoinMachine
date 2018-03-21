package com;

import java.util.LinkedList;

public class DrinkMachine extends AbstractMachine {

    protected LinkedList[][] machine;

    DrinkMachine(String location) {
        myLocation =location;
        createRows();
        coinBuffer = new CoinBuffer(this);
        coinBuffer.initiateInterface();
    }

    void createRows(){
        machine = new LinkedList[5][];
        int columns;
        columns = 6;
        for (int i = 0; i < machine.length; i++) {
            if (i < 3) {
                machine[i] = new LinkedList[columns];
            }
            if (i >= 3) {
                columns = 5;
                machine[i] = new LinkedList[columns];
            }
            for (int j = 0; j < columns; j++) {
                machine[i][j] = new LinkedList<Product>();
            }
        }
    }
    public void displayInventory(){
        for (LinkedList[] row : machine) {
            System.out.println();
            for (LinkedList col : row) {
                if (col.size() > 0) {
                    Product p = (Product) col.peek();
                    System.out.print(p.name + " $" + p.retailPrice + " ");
                } else System.out.print("\t\t");
            }
        }
        System.out.println();
    }
}
