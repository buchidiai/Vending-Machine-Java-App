/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public interface VendingMachineAuditDao {

    public void writeAuditEntry(BigDecimal change, Product product) throws VendingMachinePersistenceException;

}
