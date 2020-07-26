/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.controller;

import com.aspire.vendingmachine.dao.VendingMachinePersistenceException;
import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.dto.Response;
import com.aspire.vendingmachine.service.VendingMachineInsufficentFundsException;
import com.aspire.vendingmachine.service.VendingMachineNoItemInventoryException;
import com.aspire.vendingmachine.service.VendingMachineServiceLayer;
import com.aspire.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        BigDecimal moneyEntered = new BigDecimal("0.00");

        while (keepGoing) {

            //welcome message
            welcomeMessage();
            try {

                //only ask for money and lisy products if there no money in machine
                //ask for user to enter money
                //returns big decimal
                if (moneyEntered.compareTo(BigDecimal.ZERO) <= 0) {
                    //list all rpoducts in inventory
                    listAllProducts(moneyEntered);
                    moneyEntered = getMoneyInserted();
                }

                //check if money is null / ask user  if they want to quit & exit program
                //returns null if they want to quit program
                if (moneyEntered == null) {

                    keepGoing = false;
                    //write changes to file
                    saveInventory();

                    break;
                } else {
                    //list products again after entering money
                    listAllProducts(moneyEntered);
                    //get user's choice of product & verify and sell product
                    moneyEntered = getProductChoice(moneyEntered);
                }

            } catch (VendingMachineInsufficentFundsException | VendingMachineNoItemInventoryException | VendingMachinePersistenceException e) {

                displayError(e.getMessage());

            }

        }
        exitMessage();

    }

    private BigDecimal getProductChoice(BigDecimal moneyEntered) throws VendingMachinePersistenceException, VendingMachineInsufficentFundsException, VendingMachineNoItemInventoryException {

        //get user choice from all products
        //return index of product
        int userSelection = view.getProductSelection(service.getAllProducts());

        boolean hasError = false;
        //error types
        String errortype = "";
        // price of product
        String productPrice = "";

        view.displaySpace();

        try {

            //get product
            Product foundProduct = service.getProduct(userSelection);

            //sell product to user
            //@param Big decimal money entered & int userSelection from index of products
            //returns change and product name sold
            Response response = service.sellProduct(moneyEntered, foundProduct);

            //display change and product sold
            view.displayDispensingItemAndChange(response);

        } catch (VendingMachinePersistenceException | VendingMachineInsufficentFundsException | VendingMachineNoItemInventoryException e) {
            //set boolean because an error ocured
            hasError = true;
            //get error name
            errortype = e.getClass().getSimpleName();
            //get price from error message
            //price was added in throw error exception
            //removed price from message shown to client
            productPrice = e.getMessage().split(",")[e.getMessage().split(",").length - 1];
            //display cleaned up error
            displayError(e.getMessage().split(",")[0]);

        } finally {
            //does clean up catch errors/ get more money or close program
            moneyEntered = cleanUp(hasError, errortype, moneyEntered, productPrice);
        }

        return moneyEntered;

    }

    private BigDecimal getMoneyInserted() throws VendingMachinePersistenceException {
        //get money enetered
        BigDecimal moneyEntered = view.getMoneyInserted();
        //return big decimal of product
        return moneyEntered;
    }

    private BigDecimal addMoreMoney(BigDecimal moneyEntered, String productPrice) {
        //display ammount entered
        view.displayAmountEntered(moneyEntered);
        //ask if user wants to add more money or not
        //returns null if user dont want to add more money
        BigDecimal extraMoney = view.displayWhetherToAddMoreMoney(moneyEntered, productPrice);

        //return extra money
        return extraMoney;
    }

    private void listAllProducts(BigDecimal moneyInVendingMachine) throws VendingMachinePersistenceException {
        //list all products
        view.displayVendingMachineProducts(service.getAllProducts(), moneyInVendingMachine);
    }

    private void saveInventory() throws VendingMachinePersistenceException {
        //write current inventory to file
        service.saveInventory();
    }

    private void welcomeMessage() {
        view.printWelcomeMessage();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void displayError(String error) {
        view.displayErrorMessage(error);
    }

    private BigDecimal cleanUp(boolean hasError, String errortype, BigDecimal moneyEntered, String productPrice) {

        //if error occured
        //check the type and do stuff
        if (hasError) {
            //if error type is insuffiecent funds
            //ask for more money

            if (errortype.equals(VendingMachineInsufficentFundsException.class.getSimpleName())) {

                try {
                    //get extra more so user can get product
                    BigDecimal extraMoney = addMoreMoney(moneyEntered, productPrice);

                    //
                    //
                    //better logic ?
                    //if extra money == 0 set  moneyEntered = BigDecimal.ZERO because money has been refunded
                    //
                    //
                    //
                    //add existing money to extra money added
                    moneyEntered = moneyEntered.add(extraMoney);

                } catch (Exception e) {
                    //will throw exception if the user  doesnt want to add more money (user enters no) or quits
                    //already display refunding money to user
                    //set money in machine back to 0.00

                    if (e.getClass().getSimpleName().equals("NullPointerException")) {

                        moneyEntered = BigDecimal.ZERO;
                    }

                }
                //set entered money plus extra to moneyEntered
                return moneyEntered;

            }

        } else {
            //no error all money used up
            moneyEntered = BigDecimal.ZERO;
        }
        return moneyEntered;
    }

}
