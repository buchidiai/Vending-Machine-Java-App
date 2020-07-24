/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import java.util.List;

/**
 *
 * @author louie
 */
public interface VendingMachineDao {

    /**
     * Returns a List of all products in the vending machine.
     *
     * @return List containing all products in the roster.
     */
    List<Product> getAllProducts() throws VendingMachinePersistenceException;

    /**
     * Returns a Product from the vending machine.
     *
     * @param productName ID of the student to retrieve
     * @return Product selected from the menu options.
     */
    Product sellproduct(String productName) throws VendingMachinePersistenceException;

}
