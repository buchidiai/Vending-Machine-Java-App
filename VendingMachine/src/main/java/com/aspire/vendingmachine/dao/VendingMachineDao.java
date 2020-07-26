/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.service.VendingMachineNoItemInventoryException;
import java.util.List;

/**
 *
 * @author louie
 */
public interface VendingMachineDao {

    /**
     * Add a Product to the vending machine.
     *
     * @param product product to be added to vending machine
     * @return boolean.
     */
    boolean addProduct(Product product) throws VendingMachinePersistenceException;

    /**
     * Update vending machine inventory from file.
     *
     * @return void.
     */
    void updateinventory() throws VendingMachinePersistenceException;

    /**
     * Returns a List of all products in the vending machine.
     *
     * @return List containing all products in the roster.
     */
    List<Product> getAllProducts() throws VendingMachinePersistenceException;

    /**
     * Returns a Products in the vending machine.
     *
     * @param userSelection to retrieve product by index
     * @return product from vending machine.
     */
    Product getProduct(int userSelection) throws VendingMachinePersistenceException;

    /**
     * Decrement a Product's quantity after a sale.
     *
     * @param product the quantity of the product will decrement by 1
     * @return boolean.
     */
    boolean decrementQuantity(Product product) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException;
;

}
