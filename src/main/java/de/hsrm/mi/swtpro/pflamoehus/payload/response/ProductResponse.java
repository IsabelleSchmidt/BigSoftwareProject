package de.hsrm.mi.swtpro.pflamoehus.payload.response;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.productapi.Errormessage;
/** 
 * Product Response Entity for coummunication between backend and frontend
 * 
 * @author Svenja Schenk, Marie Scharhag
 * @version 1
 */
public class ProductResponse {

    private List<Errormessage> allErrors;
    private Product product;


/**
 * Constructor for Product Response
 * 
 * @param product Product for the ProductResponse
 */
public ProductResponse(Product product){
    this.product = product;
    allErrors = new ArrayList<>();
}
/**
 * Constructor for Product Response
 * 
 * @param product Product for the ProductResponse
 * @param allErrors Arrays that occured while saving Product
 */
public ProductResponse(Product product, List<Errormessage> allErrors){
    this(product);
    this.allErrors = allErrors;
}

/**
 * Adds new Error Message to ProductResponse
 * 
 * @param error Error that should add to the Product Response
 */
public void addErrormessage(Errormessage error){
    allErrors.add(error);
}

/**
 * 
 * @return returns List of all Errormessages
 */
public List<Errormessage> getAllErrors() {
    return allErrors;
}

/**
 * Adds a List of Errormessages
 * 
 * @param allErrors List of Errormessages that should be added
 */
public void setAllErrors(List<Errormessage> allErrors) {
    this.allErrors = allErrors;
}

/**
 * 
 * @return Product from Product Response
 */
public Product getProduct() {
    return product;
}

/**
 * Sets Product to Product Response
 * @param product that should be set
 */
public void setProduct(Product product) {
    this.product = product;
}

}