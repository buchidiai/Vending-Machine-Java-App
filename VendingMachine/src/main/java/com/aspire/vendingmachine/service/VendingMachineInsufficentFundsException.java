/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.service;

/**
 *
 * @author louie
 */
public class VendingMachineInsufficentFundsException extends Exception {

    public VendingMachineInsufficentFundsException(String message) {
        super(message);
    }

    public VendingMachineInsufficentFundsException(String message, Throwable cause) {
        super(message, cause);
    }

}
