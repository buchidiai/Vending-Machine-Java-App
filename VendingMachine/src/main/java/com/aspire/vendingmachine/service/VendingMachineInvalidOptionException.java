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
public class VendingMachineInvalidOptionException extends Exception {

    public VendingMachineInvalidOptionException(String message) {
        super(message);
    }

    public VendingMachineInvalidOptionException(String message, Throwable cause) {
        super(message, cause);
    }

}
