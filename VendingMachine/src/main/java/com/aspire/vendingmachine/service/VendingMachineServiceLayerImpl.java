/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.service;

import com.aspire.vendingmachine.dao.VendingMachineAuditDao;
import com.aspire.vendingmachine.dao.VendingMachineDao;
import com.aspire.vendingmachine.dao.VendingMachinePersistenceException;
import com.aspire.vendingmachine.dto.Change;
import com.aspire.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author louie
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private BigDecimal moneyInsertedbyUser;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public String[] sellProduct(BigDecimal moneyInserted, int userSelection) throws VendingMachineInsufficentFundsException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        ;
        //money inserted
        moneyInsertedbyUser = moneyInserted;

        //all products
        List<Product> products = dao.getAllProducts();

        //Response
        String[] response = new String[2];

        //
        boolean isValid = true;

        //product
        Product productChoice = products.get(userSelection - 1);
        //product price
        BigDecimal productPrice = productChoice.getPrice();

        while (isValid) {

            //check quantity
            if (productChoice.getQuantity() == 0) {

                throw new VendingMachineNoItemInventoryException("Sorry " + productChoice.getProductName() + " is currently sold out.");

            } else {
                //product is in inventory

                //check if product price is greater than money inserted
                //throw VendingMachineInsufficentFundsException error
                if (productPrice.compareTo(moneyInserted) > 0) {

                    throw new VendingMachineInsufficentFundsException("Insufficent funds.");

                } else if (productPrice.compareTo(moneyInserted) <= 0) {
                    //money inserted is less than or equal  product price

                    //change due
                    BigDecimal moneyLeft = moneyInserted.subtract(productPrice);

                    Change change = new Change(moneyLeft);

                    //decrement quantity
                    productChoice.decrementQuantity();

                    //set change due and product name
                    response[0] = productChoice.getProductName();
                    response[1] = change.toString();

                    auditDao.writeAuditEntry(moneyLeft, productChoice);

                }

            }
            isValid = false;
        }

        return response;

    }

    @Override
    public List<Product> getAllProducts() throws VendingMachinePersistenceException {

        return dao.getAllProducts();
    }

    @Override
    public void saveInventory() throws VendingMachinePersistenceException {

        dao.updateinventory();

    }
}
