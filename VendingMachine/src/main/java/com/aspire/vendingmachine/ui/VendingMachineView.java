/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.ui;

import com.aspire.vendingmachine.dto.Change;
import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.dto.Response;
import com.aspire.vendingmachine.util.Util;
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

    public int getProductSelection(List<Product> products) {

        displaySpace();
        return io.readInt("Please choose from one of our products:", 1, products.size());

    }

    public void displaySpace() {
        io.print("");
    }

    public void displayDispensingItemAndChange(Response response) {

        //display product name
        displayDispensingItem(response.getProductName());
        //display changed returned
        displayDispensingChange(response.getMoneyEntered(), response.getChange(), response.getFullChange());

    }

    public BigDecimal getMoneyInserted() {

        displaySpace();
        //get money entered
        BigDecimal moneyEntered = io.readBigDecimal("Please enter some money to get a Product:  e.g - [1.45]");
        if (moneyEntered == null) {
            return moneyEntered = null;
        } else {

            displayAmountEntered(moneyEntered);

        }
        displaySpace();
        return moneyEntered;

    }

    public void printWelcomeMessage() {
        displayVendingMachineWelcome();
    }

    public void displayVendingMachineWelcome() {
        io.print("*******************************************************************\n"
                + "*******************************************************************");
        io.print("******** Welcome To The Best Vending Machine In The World *********");
        io.print("***************** We have Everything!! Literally ******************");
        io.print("*******************************************************************\n"
                + "*******************************************************************");

    }

    public void displayAmountEntered(BigDecimal moneyEntered) {

        io.print("You entered " + Util.appendToMoney(moneyEntered));
    }

    public BigDecimal displayWhetherToAddMoreMoney(BigDecimal moneyEntered, String productPrice) {
        //intitlize extra Money
        BigDecimal extraMoney = null;
        //validate input
        boolean isvalid = true;

        //convert  String product price from error throw for insufficent funds  to BigDecimal
        BigDecimal productPrice_Converted = new BigDecimal(productPrice.trim());

        //calculaye money needed
        BigDecimal moneyNeeded = (productPrice_Converted.subtract(moneyEntered));

        //display money needed
        io.print("You need " + Util.appendToMoney(moneyNeeded));

        while (isvalid) {

            //ask to add money or not
            String response = io.readString("Would you like to add more money?: (y/n)");

            //check is response is a number or empty string
            if (Util.isNumeric(response) || response.trim().isEmpty()) {

                io.print("Please no numbers or special characters, enter (y/n)");
                //send them back up to try again
                continue;

            } else {
                //check if response is yes or no
                if (response.toLowerCase().charAt(0) == 'y' || response.toLowerCase().charAt(0) == 'n') {

                    //if yes
                    if (response.toLowerCase().charAt(0) == 'y') {
                        //answer is yes
                        //set extra money to money inserted
                        extraMoney = getMoneyInserted(moneyNeeded);

                        //extra money will be null if user wanted to exit while entering money
                        if (extraMoney == null) {
                            //display their refund
                            displayRefundingMoney(moneyEntered);
                        }

                        //exit while loop
                        isvalid = false;
                    } else {
                        //user doesnt want to add money
                        //display their refund
                        displayRefundingMoney(moneyEntered);
                        //exit while loop
                        isvalid = false;
                        //set extra money to null becuase they have been refunded
                        extraMoney = null;
                    }

                } else {
                    //entered something other than yes or no
                    io.print("Please enter (y/n)");
                    continue;
                }

            }

            isvalid = false;

        }
        return extraMoney;
    }

    public void displayVendingMachineProducts(List<Product> products, BigDecimal moneyInMachine) {
        //display header for fields
        io.print("____________________________________________");
        System.out.printf("|%5s|%13s|%10s|%8s \n", "Choice", "Name", "Price", "Qty");
        io.print("--------------------------------------------");
        //initiize product
        Product p;

        if (products.size() == 0) {
            io.print("There are no items in this vending machine");

        } else {
            for (int i = 0; i < products.size(); i++) {
                //set product in array
                p = products.get(i);
                //display projecy
                displayProducts(i, p.getProductName(), p.getPrice(), p.getQuantity());
            }
        }

        displaySpace();
        //display amount of money in machine
        io.print("There is " + Util.appendToMoney(moneyInMachine) + " in this machine");

    }

    private void displayProducts(int index, String productName, BigDecimal price, int quantity) {
        //display products formatted
        System.out.printf("|%-6d|%13s|%10s|%10s \n", index + 1, productName, Util.appendToMoney(price), quantity == 0 ? "sold out" : String.valueOf(quantity));
    }

    private void displayDispensingItem(String productName) {

        io.print("*******************************************");
        io.print("      *******************************      ");
        io.print("      *******************************      ");
        io.print("            Dispensing " + productName + "       ");
        io.print("      *******************************      ");
        io.print("      *******************************      ");
    }

    private void displayDispensingChange(BigDecimal moneyEntered, Change change, BigDecimal fullChange) {

        io.print("            Your entered " + Util.appendToMoney(moneyEntered) + "       ");
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("          Your Change is " + Util.appendToMoney(fullChange) + "       ");

        displayChange(change);
        io.print("*******************************************");
        io.print("*******************************************");

        displaySpace();
        displaySpace();

        io.readString("Press Enter to continue");
    }

    private void displayDispensingChange(BigDecimal moneyEntered, Change change) {

        io.print("            You enterted $" + moneyEntered + "       ");
        io.print("*******************************************");
        io.print("*******************************************");
        displayChange(change);
        io.print("*******************************************");
        io.print("*******************************************");

        displaySpace();
        displaySpace();

        io.readString("Press Enter to continue");
    }

    private void displayChange(Change changeAmount) {

        //set change denomination
        BigDecimal quarter = changeAmount.getQuarters();
        BigDecimal nickel = changeAmount.getNickels();
        BigDecimal dime = changeAmount.getDimes();
        BigDecimal penny = changeAmount.getPennies();

        io.print("              " + quarter + " x " + "Quarters" + "        ");
        io.print("              " + dime + " x " + "Dimes" + "       ");
        io.print("              " + nickel + " x " + "Nickels" + "       ");
        io.print("              " + penny + " x " + "Pennies" + "       ");
        io.print("          Thank you come Again!         ");
    }

    private BigDecimal getMoneyInserted(BigDecimal moneyNeeded) {

        displaySpace();

        BigDecimal moneyEntered = io.readBigDecimal("Please enter " + Util.appendToMoney(moneyNeeded) + " to get your product");

        //if money entered == null ~> this means the user want to exit program
        if (moneyEntered == null) {
            return moneyEntered = null;

        } else {

            displayAmountEntered(moneyEntered);

        }
        displaySpace();
        return moneyEntered;

    }

    private void displayRefundingMoney(BigDecimal moneyEntered) {
        io.print("*******************************************");
        io.print("***** Ok, your money will be refunded *****");
        //dsiplay money entered and change
        displayDispensingChange(moneyEntered, new Change(moneyEntered));

    }

    public void displayExitBanner() {
        displaySpace();
        displayBanner();
        io.print("Thank you for shopping with us!!");
        displayBanner();
    }

    private void displayBanner() {
        io.print("*******************************************\n"
                + "*******************************************");
    }

    public void displayErrorMessage(String errorMsg) {

        io.print("*******************************************");
        io.print("***************** ERROR *****************");
        io.print("      *******************************      ");
        io.print("*********** " + errorMsg + " ***********");
        io.print("      *******************************      ");
        displaySpace();
        io.readString("Press Enter to continue");
    }

}
