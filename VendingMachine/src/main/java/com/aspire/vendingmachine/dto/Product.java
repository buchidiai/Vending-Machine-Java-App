/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public class Product {

    private String productName;
    private BigDecimal price;
    private int quantity;

    public Product(String productName, BigDecimal price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean decrementQuantity() {

        if (this.quantity != 0) {
            this.quantity -= 1;
            return true;
        }

        return false;

    }

    @Override
    public String toString() {
        return "Product{" + "productName=" + productName + ", price=" + price + ", quantity=" + quantity + '}';
    }

}
