/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.vendingmachine.dao;

import com.aspire.vendingmachine.dto.Product;
import com.aspire.vendingmachine.util.Util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author louie
 */
public class VendingMachineDaoImpl implements VendingMachineDao {

    private final String ROSTER_FILE;
    private static final String DELIMITER = "::";
    public static final int NUMBER_OF_FIELDS = 3;
    private List<Product> products = new ArrayList<>();

    public VendingMachineDaoImpl() {
        ROSTER_FILE = "vendingmachineinventory.txt";
    }

    public VendingMachineDaoImpl(String vendingMachineTextFile) {
        ROSTER_FILE = vendingMachineTextFile;
    }

    @Override
    public List<Product> getAllProducts() throws VendingMachinePersistenceException {
        loadProducts();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product sellproduct(String productName) throws VendingMachinePersistenceException {

        writeProducts();

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private void loadProducts() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentProduct holds the most recent product unmarshalled
        Product currentProduct;
        // Go through ROSTER_FILE line by line, decoding each line into a
        // Product object by calling the unmarshallProduct method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();

            // unmarshall the line into a Product
            currentProduct = unmarshallProduct(currentLine);

            // Put currentProduct into the map using product id as the key
            if (currentProduct != null) {
                products.add(currentProduct);
            }

        }
        // close scanner
        scanner.close();
    }

    private Product unmarshallProduct(String productAsText) {
        // productAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // chips::2.50::10
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in productTokens.
        // Which should look like this:
        // _______________
        // |     |    |   |
        // |chips|2.50|10 |
        // |     |    |   |
        // ---------------
        //  [0]  [1]    [2]

        String[] productTokens = productAsText.split(DELIMITER);

        Product productFromFile;

        if (productAsText.length() == NUMBER_OF_FIELDS) {

            //product name
            String productName = Util.replaceSpecialCharacters(productTokens[0]);

            //produce price
            BigDecimal productPrice = new BigDecimal(productTokens[1]);

            //product quatity
            int quantity = Integer.parseInt(productTokens[2]);

            // Which we can then use to create a new Product object to satisfy
            // the requirements of the Product constructor.
            productFromFile = new Product(productName, productPrice, quantity);

        } else {
            return null;
        }
        // We have now created a product! Return it!
        return productFromFile;
    }

    /**
     * Writes all products in the roster out to a ROSTER_FILE. See loadRoster for file format.
     *
     * @throws ClassRosterPersistenceException if an error occurs writing to the file
     */
    private void writeProducts() throws VendingMachinePersistenceException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to
        // handle any errors that occur.

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save product data.", e);
        }

        // Write out the Product objects to the product file.
        // NOTE TO THE APPRENTICES: We could just grab the product map,
        // get the Collection of Products and iterate over them but we've
        // already created a method that gets a List of Products so
        // we'll reuse it.
        String productAsText;

        for (Product currentProduct : products) {
            // turn a Product into a String
            productAsText = marshallProduct(currentProduct);
            // write the Product object to the file
            out.println(productAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }

        // Clean up
        out.close();
    }

    private String marshallProduct(Product aProduct) {
        // We need to turn a Product object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // chips::2.50::10

        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer.
        // Start with the product id, since that's supposed to be first.
        //productname
        String productAsText = Util.replaceSpecialCharacters(aProduct.getProductName()) + DELIMITER;

        // add the rest of the properties in the correct order:
        // price
        productAsText += aProduct.getPrice() + DELIMITER;

        // quantity
        productAsText += aProduct.getQuantity() + DELIMITER;

        // We have now turned a product to text! Return it!
        return productAsText;
    }

}
