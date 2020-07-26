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
import com.aspire.vendingmachine.dto.Response;
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
    public Response sellProduct(BigDecimal moneyInserted, int userSelection) throws VendingMachineInsufficentFundsException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        ;
        //money inserted
        moneyInsertedbyUser = moneyInserted;

        //all products
        List<Product> products = dao.getAllProducts();

        System.out.println("products size " + products);

        //response
        Response response = null;

        boolean isValid = true;

        //product
        Product productChoice = products.get(userSelection - 1);
        //product price
        BigDecimal productPrice = productChoice.getPrice();
        //product name
        String productName = productChoice.getProductName();

        while (isValid) {

            //check quantity
            if (productChoice.getQuantity() == 0) {

                throw new VendingMachineNoItemInventoryException(productName + " is sold out");

            } else {
                //product is in inventory

                //check if product price is greater than money inserted
                //throw VendingMachineInsufficentFundsException error
                if (productPrice.compareTo(moneyInserted) > 0) {

                    throw new VendingMachineInsufficentFundsException("Insufficent funds, " + productPrice);

                } else if (productPrice.compareTo(moneyInserted) <= 0) {
                    //money inserted is less than or equal  product price

                    //change due
                    BigDecimal moneyLeft = moneyInserted.subtract(productPrice);

                    //decrement quantity
                    productChoice.decrementQuantity();

                    //set moneyInsertedbyUser change due in denomination and full amount and product name
                    response = new Response(moneyInsertedbyUser, new Change(moneyLeft), moneyLeft, productName);

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
