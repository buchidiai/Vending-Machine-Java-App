/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.service.VendingMachineNoItemInventoryException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louie
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Product onlyProduct;

    public VendingMachineDaoStubImpl() {
        this.onlyProduct = new Product("Gum", new BigDecimal("2.50"), 10);
    }

    public VendingMachineDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }

    @Override
    public boolean addProduct(Product product) throws VendingMachinePersistenceException {
        return product.getProductName().equals(onlyProduct.getProductName());
    }

    @Override
    public void updateinventory() throws VendingMachinePersistenceException {

    }

    @Override
    public List<Product> getAllProducts() throws VendingMachinePersistenceException {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }

    @Override
    public Product getProduct(Product product) throws VendingMachinePersistenceException {

        if (product.getProductName().equals(onlyProduct.getProductName())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public boolean decrementQuantity(Product product) throws VendingMachineNoItemInventoryException {

        return product.decrementQuantity() == true ? true : false;
    }

}
