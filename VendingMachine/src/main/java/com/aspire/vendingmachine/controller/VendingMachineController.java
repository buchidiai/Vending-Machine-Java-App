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
            welcomeMessage();
            try {

                listAllProducts();

                BigDecimal moneyEntered = getMoneyInserted();

                if (moneyEntered == null) {

                    keepGoing = false;
                    saveInventory();

                    break;
                } else {
                    listAllProducts();
                    getProductChoice(moneyEntered);
                }

            } catch (VendingMachineInsufficentFundsException | VendingMachineNoItemInventoryException | VendingMachinePersistenceException e) {
                displayError(e.getMessage());

            }

        }
        exitMessage();

    }

    private void getProductChoice(BigDecimal moneyEntered) throws VendingMachinePersistenceException, VendingMachineInsufficentFundsException, VendingMachineNoItemInventoryException {

        int userSelection = view.getProductSelection(service.getAllProducts());

        view.displaySpace();

        try {

            String[] response = service.sellProduct(moneyEntered, userSelection);

            view.displayDispensingItemAndChange(response);

        } catch (VendingMachinePersistenceException | VendingMachineInsufficentFundsException | VendingMachineNoItemInventoryException e) {

            displayError(e.getMessage());

        }

    }

    private BigDecimal getMoneyInserted() throws VendingMachinePersistenceException {
        //get money enetered
        BigDecimal moneyEntered = view.getMoneyInserted();

        return moneyEntered;
    }

    private void listAllProducts() throws VendingMachinePersistenceException {
        view.displayVendingMachineProducts(service.getAllProducts());
    }

    private void saveInventory() throws VendingMachinePersistenceException {
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
