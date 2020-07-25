/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.ui;

import com.aspire.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author louie
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void printWelcomeMessage() {
        displayVendingMachineWelcome();

    }

    public void displayVendingMachineWelcome() {
        io.print("*******************************************************************\n"
                + "*******************************************************************");
        System.out.println("******** Welcome To The Best Vending Machine In The World *********");
        System.out.println("***************** We have Everything!! Literally ******************");
        io.print("*******************************************************************\n"
                + "*******************************************************************");

    }

    public void displayVendingMachineProducts(List<Product> products) {

        io.print("____________________________________________");
        System.out.printf("|%5s|%13s|%10s|%8s \n", "Choice", "Name", "Price", "Qty");
        io.print("--------------------------------------------");
        Product p;

        for (int i = 0; i < products.size(); i++) {
            p = products.get(i);
            displayProducts(i, p.getProductName(), p.getPrice(), p.getQuantity());
        }

    }

    private void displayProducts(int index, String productName, BigDecimal price, int quantity) {
        String currency = price.compareTo(new BigDecimal("1.00")) > 0 ? "$" + price : "₵" + price;
        String soldOut = quantity == 0 ? "sold out" : String.valueOf(quantity);

        System.out.printf("|%-6d|%13s|%10s|%10s \n", index + 1, productName, currency, soldOut);
    }

    public void displayDispensingItemAndChange(String[] productName) {

        io.print("*******************************************");
        io.print("      *******************************      ");
        io.print("      *******************************      ");
        io.print("            Dispensing " + productName[0] + "       ");
        io.print("      *******************************      ");
        io.print("      *******************************      ");
        io.print("*******************************************");
        displayChange(productName[1]);
        io.print("*******************************************");
        io.print("*******************************************");

        displaySpace();
        displaySpace();

        io.readString("Press Enter to continue");
    }

    public void displayChange(String changeAmount) {

        String[] result = changeAmount.split(",");

        boolean changeDue = true;

        for (String s : result) {

            if (Integer.parseInt(s) > 0) {
                changeDue = false;
            }

        }

        if (changeDue == false) {
            io.print("              " + result[0] + " x " + "Quarters" + "       ");
            io.print("              " + result[1] + " x " + "Dimes" + "       ");
            io.print("              " + result[2] + " x " + "Nickels" + "       ");
            io.print("              " + result[3] + " x " + "Pennies" + "       ");
        } else {
            io.print("     No Change due, Thank you come Again     ");

        }

    }

    public int getProductSelection(List<Product> products) {
        displaySpace();
        return io.readInt("Please choose from one of our products:", 1, products.size());

    }

    public BigDecimal getMoneyInserted() {

        displaySpace();

        BigDecimal moneyEntered = io.readBigDecimal("Please enter some money to get a Product. (1.45)");
        if (moneyEntered == null) {

            return moneyEntered;
        } else {
            String currency = moneyEntered.compareTo(new BigDecimal("1.00")) > 0 ? "$" : "₵";

            io.print("You entered " + currency + moneyEntered);

        }
        displaySpace();
        return moneyEntered;

    }

    public void displaySpace() {
        io.print("");
    }

    public void displayExitBanner() {
        displaySpace();
        displayBanner();
        io.print("Thank you for shopping with us!!");
        displayBanner();
    }

    public void displayBanner() {
        io.print("*******************************************\n"
                + "*******************************************");
    }

    public void displayDispensingMessage() {
        displaySpace();
        io.print("=== DISPENSING ===");
        displaySpace();
    }

    public void displayErrorMessage(String errorMsg) {
        displaySpace();
        io.print("=== ERROR ===");
        io.print(errorMsg);
        displaySpace();
    }

}
