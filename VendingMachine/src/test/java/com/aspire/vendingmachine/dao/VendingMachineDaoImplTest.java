/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author louie
 */
public class VendingMachineDaoImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        String testFile = "testVendingmachine.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoImpl(testFile);
    }

    @Test
    public void testAddGetproduct() throws Exception {
        // Create our method test inputs
        // -arrange

        //productname
        String productName = "mint-gum";
        // price
        BigDecimal price = new BigDecimal("2.50");

        // quantity
        int quantity = 10;

        Product product = new Product(productName, price, quantity);

        //1 not 0 ~> getProduct subtracts by 1 so we want the first item
        int productIndex = 1;

        //-act
        //add product to Dao
        testDao.addProduct(product);

        // Get the product from the DAO
        Product retrievedProduct = testDao.getProduct(productIndex);

        //-assert
        // Check the data is equal
        assertEquals(product.getProductName(),
                retrievedProduct.getProductName(),
                "Checking product name.");

        assertEquals(product.getPrice(),
                retrievedProduct.getPrice(),
                "Checking product product price.");

        assertEquals(product.getQuantity(),
                retrievedProduct.getQuantity(),
                "Checking product product quantity.");

    }

    @Test
    public void testAddGetAllProducts() throws Exception {
        // Create our first product
        //productname
        String productNameA = "Candy";
        // price
        BigDecimal priceA = new BigDecimal("2.50");

        // quantity
        int quantityA = 10;

        Product productA = new Product(productNameA, priceA, quantityA);

        // Create our second product
        //productname
        String productNameB = "Cookies";
        // price
        BigDecimal priceB = new BigDecimal("1.70");

        // quantity
        int quantityB = 6;

        Product productB = new Product(productNameB, priceB, quantityB);

        // Add both our products to the DAO
        testDao.addProduct(productA);
        testDao.addProduct(productB);

        // Retrieve the list of all products within the DAO
        List<Product> allProducts = testDao.getAllProducts();

        // First check the general contents of the list
        assertNotNull(allProducts, "The list of products must not null");
        assertEquals(2, allProducts.size(), "List of products should have 2 products.");

        // Then the specifics
        assertTrue(testDao.getAllProducts().contains(productA),
                "The list of products should include Candy.");
        assertTrue(testDao.getAllProducts().contains(productB),
                "The list of products should include Cookies.");

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

        //1 not 0 ~> getProduct subtracts by 1 so we want the first item
        int productIndex = 1;

        //add products to Dao
        testDao.addProduct(productToBeSold);

        //-act
        testDao.decrementQuantity(productToBeSold);

        // Get the product from the DAO
        Product retrievedProduct = testDao.getProduct(productIndex);

        //-assert
        assertTrue(((quantity - 1) == retrievedProduct.getQuantity()),
                "Checking quantity decremented.");

    }

}
