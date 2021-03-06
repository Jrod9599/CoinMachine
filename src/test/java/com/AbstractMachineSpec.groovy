package com

import spock.lang.Specification


class AbstractMachineSpec extends Specification {
    def"removing an item from vending machine when there is a product available for purchase and running total is sufficient for purchase"(){
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        and: "a compartment, which consist of row and column name"
        String rowColumn = "A0";
        and: "initial running total"
        double runningTotal = 1.00;
        and: "number of products in a compartment"
        int size = vendingMachine.machine[0][0].size();
        when:"when the customer purchases the item"
        vendingMachine.removeItem(rowColumn, runningTotal);
        then:"Number of products in that compartment decreases by one"
        vendingMachine.machine[0][0].size()== size-1;
    }


    def"removing an item from vending machine when there are no products available for purchase"() {
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        and: "an empty compartment, which consist of row and column name"
        String rowColumn = "C2";
        and: "initial running total"
        double runningTotal = 1.00;
        when: "when the customer purchases the item"
        vendingMachine.removeItem(rowColumn, runningTotal);
        then: "Number of products in that compartment decreases by one"
        thrown(BADENTRY);


    }
    def"removing an item from vending machine when the compartment code is entered incorrectly"() {
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        and: "an empty compartment, which consist of row and column name"
        String rowColumn = "F2";
        and: "initial running total"
        double runningTotal = 1.00;
        when: "when the customer purchases the item"
        vendingMachine.removeItem(rowColumn, runningTotal);
        then: "Number of products in that compartment decreases by one"
        thrown(BADENTRY);

    }
    def "removing an item from vending machine when running total is not enough for purchase"() {
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        and: "a compartment which consist of row and column name"
        String rowColumn = "A0";
        and: "initial running total"
        double runningTotal = 0.05;
        when: "when the customer purchases the item"
        vendingMachine.removeItem(rowColumn, runningTotal);
        then: "Number of products in that compartment decreases by one"
        thrown(INSUFFICIENTFUNDS);
    }

    def "Adding an item to the vending machine"() {
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        and: "a compartment which consist of row and column name"
        String rowColumn = "C2";
        and: "a product"
        Product snack = new Product("chips", "original", 0.5d, 1.0d);
        when: "when the customer purchases the item"
        vendingMachine.addItem(rowColumn, snack);
        then: "Appends the product to the end of that compartment"
        vendingMachine.machine[2][2].getLast() == snack;

    }

    def "Entering the incorrect compartment name when adding an item to the vending machine"() {
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        and: "a compartment which consist of row and column name"
        String rowColumn = "F2";
        and: "a product"
        Product snack = new Product("chips", "original", 0.5d, 1.0d);
        when: "when the customer purchases the item"
        vendingMachine.addItem(rowColumn, snack);
        then: "Appends the product to the end of that compartment"
        thrown(BADENTRY);
    }

    def "Display inventory"() {
        given: "a machine"
        AbstractMachine vendingMachine = new SnackMachine();
        when: "The inventory needs to be displayed"
        vendingMachine.displayInventory();
        then: "Display the inventory"
    }

}
