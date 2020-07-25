/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine;

import com.aspire.vendingmachine.controller.VendingMachineController;
import com.aspire.vendingmachine.dao.VendingMachineAuditDao;
import com.aspire.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.aspire.vendingmachine.dao.VendingMachineDao;
import com.aspire.vendingmachine.dao.VendingMachineDaoImpl;
import com.aspire.vendingmachine.service.VendingMachineServiceLayer;
import com.aspire.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.aspire.vendingmachine.ui.UserIO;
import com.aspire.vendingmachine.ui.UserIOConsoleImpl;
import com.aspire.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author louie
 */
public class App {

    public static void main(String[] args) {

        //Declare a UserIO variable and initialize it with a UserIOConsoleImpl reference
        UserIO myIo = new UserIOConsoleImpl();

        //Declare and instantiate a VendingMachineView object, passing the UserIO created in the previous step into the constructor.
        VendingMachineView myView = new VendingMachineView(myIo);
        //Declare a VendingMachineDao variable and initialize it with a VendingMachineDaoFileImpl reference.
        VendingMachineDao myDao = new VendingMachineDaoImpl();

        // Instantiate the Audit DAO
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoImpl();

        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        //Instantiate a VendingMachineController, passing the VendingMachineDao and VendingMachineView object into the constructor.
        VendingMachineController controller = new VendingMachineController(myService, myView);
        //Call the run method on the controller to kick things off.
        controller.run();
    }
}
