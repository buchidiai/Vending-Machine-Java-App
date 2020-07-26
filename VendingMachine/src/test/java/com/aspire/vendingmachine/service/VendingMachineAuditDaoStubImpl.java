/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.service;

import com.aspire.vendingmachine.dao.VendingMachineAuditDao;
import com.aspire.vendingmachine.dao.VendingMachinePersistenceException;
import com.aspire.vendingmachine.dto.Product;
import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {

    @Override
    public void writeAuditEntry(BigDecimal change, Product product) throws VendingMachinePersistenceException {

    }

}
