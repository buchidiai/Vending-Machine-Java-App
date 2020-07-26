/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author louie
 */
public class Change {

    enum Denomination {

        QUARTER(new BigDecimal("0.25")),
        DIME(new BigDecimal("0.10")),
        NICKEL(new BigDecimal("0.05")),
        PENNY(new BigDecimal("0.01"));

        private final BigDecimal value;

        Denomination(BigDecimal value) {
            this.value = value;
        }

        public BigDecimal value() {
            return value;
        }
    }

    private BigDecimal quarters = new BigDecimal("0");
    private BigDecimal dimes = new BigDecimal("0");
    private BigDecimal nickels = new BigDecimal("0");
    private BigDecimal pennies = new BigDecimal("0");

    public Change(BigDecimal amount) {

        while (amount.compareTo(Denomination.QUARTER.value()) > 0) {
            this.quarters = quarters.add(BigDecimal.ONE);
            amount = amount.subtract(Denomination.QUARTER.value());

        }

        while (amount.compareTo(Denomination.DIME.value()) > 0) {
            this.dimes = dimes.add(BigDecimal.ONE);
            amount = amount.subtract(Denomination.DIME.value());

        }

        while (amount.compareTo(Denomination.NICKEL.value()) > 0) {
            this.nickels = nickels.add(BigDecimal.ONE);
            amount = amount.subtract(Denomination.NICKEL.value());

        }

        while (amount.compareTo(Denomination.PENNY.value()) >= 0) {
            this.pennies = pennies.add(BigDecimal.ONE);
            amount = amount.subtract(Denomination.PENNY.value());

        }

    }

    public BigDecimal getQuarters() {
        return quarters;
    }

    public BigDecimal getDimes() {
        return dimes;
    }

    public BigDecimal getNickels() {
        return nickels;
    }

    public BigDecimal getPennies() {
        return pennies;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Change other = (Change) obj;
        if (!Objects.equals(this.quarters, other.quarters)) {
            return false;
        }
        if (!Objects.equals(this.dimes, other.dimes)) {
            return false;
        }
        if (!Objects.equals(this.nickels, other.nickels)) {
            return false;
        }
        if (!Objects.equals(this.pennies, other.pennies)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return quarters + "," + dimes + "," + nickels + "," + pennies;

    }

}
