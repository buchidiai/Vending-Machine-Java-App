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
     * @return void.
     */
    void addProduct(Product product) throws VendingMachinePersistenceException;

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
     * @param productName Name of product to to retrieve
     * @return product from vending machine.
     */
    Product getProduct(String productName) throws VendingMachinePersistenceException;

//    /**
//     * Returns a Product from the vending machine.
//     *
//     * @param productName Name of product to retrieve
//     * @return Product selected from the menu options.
//     */
//    Product sellproduct(Product product, BigDecimal moneyProvided) throws VendingMachinePersistenceException;
    /**
     * Decrement a Product's quantity after a sale.
     *
     * @param product the quantity of the product will decrement by 1
     * @return void.
     */
    void decrementQuantity(Product product) throws VendingMachineNoItemInventoryException;
;

}
