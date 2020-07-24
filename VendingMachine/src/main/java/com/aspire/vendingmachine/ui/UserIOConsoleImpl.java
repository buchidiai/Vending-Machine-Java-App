/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author louie
 */
public class UserIOConsoleImpl implements UserIO {

    final private Scanner in = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        this.print(prompt);
        return in.nextLine();
    }

    @Override
    public int readInt(String prompt) {

        int response = 0;
        boolean valid = true;

        while (valid) {

            try {

                String stringValue = this.readString(prompt);
                response = Integer.parseInt(stringValue);

                valid = false;

            } catch (Exception e) {

                this.print("Value must be a number");

            }

        }

        return response;

    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;

        try {

            do {
                result = readInt(prompt + " (" + min + "-" + max + ")");
            } while (result < min || result > max);

        } catch (Exception e) {

            this.print("Value must be a numberdsd");

        }

        return result;

    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {

        BigDecimal result = new BigDecimal("0");

        try {
            this.print(prompt);
            result = in.nextBigDecimal();

        } catch (Exception e) {

            this.print("Must be a valid amount");

        }

        return result;
    }

}
