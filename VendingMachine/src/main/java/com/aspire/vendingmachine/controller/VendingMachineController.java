/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.controller;

import com.aspire.vendingmachine.dao.VendingMachinePersistenceException;
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

        while (keepGoing) {
            //welcome message
            welcomeMessage();
            try {
                //list all rpoducts in inventory
                listAllProducts();

                //ask for user to enter money
                //returns big decimal
                BigDecimal moneyEntered = getMoneyInserted();

                //check if money is null / ask user  if they want to quit & exit program
                //returns null if they want to quit program
                if (moneyEntered == null) {

                    keepGoing = false;
                    //write changes to file
                    saveInventory();

                    break;
                } else {
                    //list products again after entering money
                    listAllProducts();
                    //get user's choice of product & verify and sell product
                    getProductChoice(moneyEntered);
                }

            } catch (VendingMachineInsufficentFundsException | VendingMachineNoItemInventoryException | VendingMachinePersistenceException e) {
                displayError(e.getMessage());

            }

        }
        exitMessage();

    }

    private void getProductChoice(BigDecimal moneyEntered) throws VendingMachinePersistenceException, VendingMachineInsufficentFundsException, VendingMachineNoItemInventoryException {

        //get user choice from all products
        //return index of product
        int userSelection = view.getProductSelection(service.getAllProducts());

        view.displaySpace();

        try {
            //sell product to user
            //@param Big decimal money entered & int userSelection from index of products
            //returns change and product name sold
            String[] response = service.sellProduct(moneyEntered, userSelection);

            //display change and product sold
            view.displayDispensingItemAndChange(response);

        } catch (VendingMachinePersistenceException | VendingMachineInsufficentFundsException | VendingMachineNoItemInventoryException e) {

            displayError(e.getMessage());

        }

    }

    private BigDecimal getMoneyInserted() throws VendingMachinePersistenceException {
        //get money enetered
        BigDecimal moneyEntered = view.getMoneyInserted();
        //return big decimal of product
        return moneyEntered;
    }

    private void listAllProducts() throws VendingMachinePersistenceException {
        //list all products
        view.displayVendingMachineProducts(service.getAllProducts());
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

}
