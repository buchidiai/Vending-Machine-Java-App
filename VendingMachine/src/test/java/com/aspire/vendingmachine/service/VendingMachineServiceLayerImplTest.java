/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.service;

import com.aspire.vendingmachine.dao.VendingMachineAuditDao;
import com.aspire.vendingmachine.dao.VendingMachineDao;
import com.aspire.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.aspire.vendingmachine.dao.VendingMachinePersistenceException;
import com.aspire.vendingmachine.dto.Change;
import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.dto.Response;
import java.math.BigDecimal;
import static org.assertj.core.api.Java6Assertions.assertThat;
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
    }

    @Test
    public void testSellProduct() throws Exception {
        // ARRANGE
        Response saleResponse = null;

        BigDecimal productPrice = new BigDecimal("12.99");
        String productName = "Wine";

        Product testProductToSell = new Product(productName, productPrice, 2);

        BigDecimal moneyInserted = new BigDecimal("15.99");

        BigDecimal moneyLeft = moneyInserted.subtract(productPrice);

        Change change = new Change(moneyLeft);

        // ACT & ASSERT
        try {
            saleResponse = service.sellProduct(moneyInserted, testProductToSell);

        } catch (VendingMachineInsufficentFundsException | VendingMachinePersistenceException | VendingMachineNoItemInventoryException e) {

            System.out.println(" Error: " + e.getMessage());
        }

        assertEquals(moneyInserted, saleResponse.getMoneyEntered(),
                "Money entered should be same returned.");

        assertTrue(change.equals(saleResponse.getChange()),
                "change should be correct.");

        assertEquals(moneyLeft, saleResponse.getFullChange(),
                "money left should be the same an full change.");

        assertEquals(productName, saleResponse.getProductName(),
                "product name should be the same.");
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // ARRANGE
        Product testProduct = new Product("Tacos", new BigDecimal("2.50"), 10);

        System.out.println("testProduct created " + testProduct);

        // ACT & ASSERT
        assertEquals(1, service.getAllProducts().size(),
                "Should only have one product.");

        assertThat(service.getAllProducts().get(0)).isEqualToComparingFieldByField(testProduct).as("should have testProduct which is Tacos");
    }

    @Test
    public void testDecrementQuaitity() throws Exception {
        // Create our method test inputs

        // -arrange
        //add product
        //productname
        String productName = "mint-gum";
        // price
        BigDecimal price = new BigDecimal("2.50");
        // quantity
        int quantity = 10;

        Product productToBeSold = new Product(productName, price, quantity);

        //add products to Dao
        boolean decremented = service.decrementProductQuantity(productToBeSold);

        assertTrue(decremented,
                "Checking quantity was decremented.");

    }

}
