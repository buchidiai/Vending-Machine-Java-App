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
        this.onlyProduct = new Product("Tacos", new BigDecimal("2.50"), 10);
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
        System.out.println("Here 1");
        productList.add(onlyProduct);
        System.out.println("added " + onlyProduct);
        return productList;
    }

    @Override
    public boolean decrementQuantity(Product product) throws VendingMachineNoItemInventoryException {

        //onlyproduct not used ?
        return product.decrementQuantity() ? true : false;
    }

    @Override
    public Product getProduct(int userSelection) throws VendingMachinePersistenceException {
        Product foundProduct = null;

        //right way ?
        List<Product> productList = new ArrayList<>();

        productList.add(onlyProduct);

        foundProduct = productList.get(userSelection);

        return foundProduct;
    }
}
