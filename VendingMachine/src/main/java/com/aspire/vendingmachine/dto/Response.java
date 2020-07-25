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
public class Response {

    private BigDecimal moneyEntered;
    private Change change;
    private BigDecimal fullChange;
    private String productName;

    public Response(BigDecimal moneyEntered, Change change, BigDecimal fullChange, String productName) {
        this.moneyEntered = moneyEntered;
        this.change = change;
        this.fullChange = fullChange;
        this.productName = productName;
    }

    public BigDecimal getMoneyEntered() {
        return moneyEntered;
    }

    public Change getChange() {
        return change;
    }

    public BigDecimal getFullChange() {
        return fullChange;
    }

    public String getProductName() {
        return productName;
    }

}
