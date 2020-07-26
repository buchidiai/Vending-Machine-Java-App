/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.service;

import com.aspire.vendingmachine.dao.VendingMachinePersistenceException;
import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.dto.Response;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author louie
 */
public interface VendingMachineServiceLayer {

    Response sellProduct(BigDecimal moneyInserted, Product product) throws
            VendingMachineInsufficentFundsException,
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException;

    Product getProduct(int userSelection) throws
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException;

    List<Product> getAllProducts() throws VendingMachinePersistenceException;

    boolean decrementProductQuantity(Product product) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException;

    void saveInventory() throws VendingMachinePersistenceException;

}
