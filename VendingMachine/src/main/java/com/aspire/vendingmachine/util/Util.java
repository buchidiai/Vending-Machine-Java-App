/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.util;

/**
 *
 * @author louie
 */
public class Util {

    public static String replaceSpecialCharacters(String str) {

        return str.replace("[^a-zA-Z0-9\\s+]", "");

    }

    public static String capitalizeFirstWord(String str) {

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
