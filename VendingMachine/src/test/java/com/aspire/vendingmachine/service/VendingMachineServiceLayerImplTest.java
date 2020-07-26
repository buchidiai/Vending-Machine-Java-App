/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.service;

import com.aspire.vendingmachine.dao.VendingMachineAuditDao;
import com.aspire.vendingmachine.dao.VendingMachineDao;
import com.aspire.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.aspire.vendingmachine.dto.Change;
import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.dto.Response;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author louie
 */
public class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {

        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceLayerImpl(dao, auditDao);

    }

    @BeforeEach
    public void setUp() throws Exception {
//        VendingMachineDao dao = new VendingMachineDaoStubImpl();
//        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
//
//        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }

    @Test
    public void testSellProduct() throws Exception {
        // ARRANGE

        BigDecimal productPrice = new BigDecimal("12.99");
        String productName = "Wine";

        Product testProductToSell = new Product(productName, productPrice, 2);

        BigDecimal moneyInserted = new BigDecimal("15.99");

        BigDecimal moneyLeft = moneyInserted.subtract(productPrice);

        Change change = new Change(moneyLeft);

        Response saleResponse = service.sellProduct(moneyInserted, 1);

        // ACT & ASSERT
        assertEquals(moneyInserted, saleResponse.getMoneyEntered(),
                "Money entered should be same returned.");

        assertEquals(change, saleResponse.getChange(),
                "Money left should be same returned.");

        assertEquals(change, saleResponse.getChange(),
                "change object should be the same.");

        assertEquals(productName, saleResponse.getProductName(),
                "product name should be the same.");
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // ARRANGE
        Product testProduct = new Product("Gum", new BigDecimal("2.00"), 10);

        // ACT & ASSERT
        assertEquals(1, service.getAllProducts().size(),
                "Should only have one product.");
        assertTrue(service.getAllProducts().contains(testProduct),
                "The one product should be Gum.");
    }

//    @Test
//    public void testDecrementQuaitity() throws Exception {
//        // Create our method test inputs
//
//        // -arrange
//        //add product
//        //productname
//        String productName = "mint-gum";
//        // price
//        BigDecimal price = new BigDecimal("2.50");
//        // quantity
//        int quantity = 10;
//        Product productToBeSold = new Product(productName, price, quantity);
//
//        //add products to Dao
//        testDao.addProduct(productToBeSold);
//
//        //price to be paid
//        BigDecimal pricePaid = new BigDecimal("2.50");
//
//        //-act
//        //sell product
////        testDao.sellproduct(productToBeSold, pricePaid);
//
//
//        // Get the product from the DAO
//        Product retrievedProduct = testDao.getProduct(productName);
//
//        //-assert
//        assertTrue(((quantity - 1) == retrievedProduct.getQuantity()),
//                "Checking quantity decremented.");
//
//    }
    @Test
    public void testSellproduct() throws Exception {
        // Create our method test inputs

        // -arrange
        //add product
        //productname
        String productName = "mint-gum";
        // price
        BigDecimal price = new BigDecimal("2.50");
        // quantity
        int quantity = 1;
        Product productToBeSold = new Product(productName, price, quantity);

        //i dont have a add product in service
        //add products to Dao
//        testDao.addProduct(productToBeSold);
        //price to be paid
        BigDecimal pricePaid = new BigDecimal("2.50");

        //-act
        //i dont have a get product in service
        //sell product
//        testDao.sellproduct(productToBeSold, pricePaid);
        // Get the product from the DAO
        Product retrievedProduct = testDao.getProduct(productName);

        //-assert
        //Check the data is equal
        assertEquals(productToBeSold.getProductName(),
                retrievedProduct.getProductName(),
                "Checking product name.");

        assertTrue(((quantity - 1) == retrievedProduct.getQuantity()),
                "Checking quantity decremented.");

        assertEquals(productToBeSold.getPrice(),
                pricePaid,
                "Checking product price.");

        assertEquals(productToBeSold.getQuantity(),
                retrievedProduct.getQuantity(),
                "Checking product quantity.");

    }
}
