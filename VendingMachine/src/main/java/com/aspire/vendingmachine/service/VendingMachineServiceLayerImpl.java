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
    public Product getProduct(int userSelection) throws VendingMachinePersistenceException, VendingMachineNoItemInventoryException {

        Product productChoice = dao.getProduct(userSelection);

        //product name
        String productName = productChoice.getProductName();

        //check quantity and throw exception
        if (productChoice.getQuantity() == 0) {

            throw new VendingMachineNoItemInventoryException(productName + " is sold out");

        }

        return productChoice;

    }

    @Override
    public Response sellProduct(BigDecimal moneyInserted, Product product) throws VendingMachineInsufficentFundsException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        ;
        //money inserted
        moneyInsertedbyUser = moneyInserted;

        //response
        Response response = null;

        boolean isValid = true;

        //product
        Product productChoice = product;
        //product price
        BigDecimal productPrice = productChoice.getPrice();
        //product name
        String productName = productChoice.getProductName();

        while (isValid) {

            //check if product price is greater than money inserted
            //throw VendingMachineInsufficentFundsException error with price of item
            if (productPrice.compareTo(moneyInserted) > 0) {

                throw new VendingMachineInsufficentFundsException("Insufficent funds, " + productPrice);

            } else if (productPrice.compareTo(moneyInserted) <= 0) {
                //money inserted is less than or equal  product price

                //we can sell product
                //change due
                BigDecimal moneyLeft = moneyInserted.subtract(productPrice);

                //decrement quantity
                boolean productHasBeenSold = decrementProductQuantity(product);
                if (productHasBeenSold) {

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
    public boolean decrementProductQuantity(Product product) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException {

        if (product.getQuantity() == 0) {
            throw new VendingMachineNoItemInventoryException(product.getProductName() + " is sold out");
        }

        return dao.decrementQuantity(product);
    }

    @Override
    public void saveInventory() throws VendingMachinePersistenceException {

        dao.updateinventory();

    }

}
