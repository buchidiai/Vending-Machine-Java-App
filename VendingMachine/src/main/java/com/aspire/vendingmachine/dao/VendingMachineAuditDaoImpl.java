/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author louie
 */
public class VendingMachineAuditDaoImpl implements VendingMachineAuditDao {

    public static final String AUDIT_FILE = "auditactivity.txt";

    @Override
    public void writeAuditEntry(BigDecimal change, Product product) throws VendingMachinePersistenceException {
        PrintWriter out;

        try {

            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
        }

        LocalDateTime ld = LocalDateTime.now();

        String time = ld.getDayOfWeek() + "-" + ld.getMonth() + " " + ld.getDayOfMonth() + "," + ld.getYear();
        out.println(time + "::" + product.getProductName() + "::" + product.getPrice() + "::" + product.getQuantity() + "::" + change);
        out.flush();
    }

}
