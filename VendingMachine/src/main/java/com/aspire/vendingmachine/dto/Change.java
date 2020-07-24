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
public class Change {

    enum Denomination {

        QUARTER(new BigDecimal("0.25")),
        DIME(new BigDecimal("0.10")),
        NICKEL(new BigDecimal("0.5")),
        PENNY(new BigDecimal("0.1"));

        private final BigDecimal value;

        Denomination(BigDecimal value) {
            this.value = value;
        }
    }

    private BigDecimal quarters;
    private BigDecimal dimes;
    private BigDecimal nickels;
    private BigDecimal pennies;

}
