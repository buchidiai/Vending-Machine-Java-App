/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 *
 * @author louie
 */
public class Util {

    public static String replaceSpecialCharacters(String str) {

        return str.replaceAll("[^a-zA-Z0-9\\s+]", "").trim();

    }

    public static String appendToMoney(BigDecimal money) {

        String currency = money.compareTo(new BigDecimal("1.00")) > 0 ? "$" : "₵";

        return "" + (currency + money);

    }

    public static String capitalizeFirstWord(String str) {

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isNumeric(String strNum) {

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
