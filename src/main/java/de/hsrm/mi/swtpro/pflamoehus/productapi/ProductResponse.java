package de.hsrm.mi.swtpro.pflamoehus.productapi;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

public class ProductResponse {
    private List<Errormessage> allErrors;
    private Product product;



public ProductResponse(Product product){
    this.product = product;
    allErrors = new ArrayList<>();
}

public ProductResponse(Product product, List<Errormessage> allErrors){
    this(product);
    this.allErrors = allErrors;
}

public void addErrormessage(Errormessage error){
    allErrors.add(error);
}

public List<Errormessage> getAllErrors() {
    return allErrors;
}

public void setAllErrors(List<Errormessage> allErrors) {
    this.allErrors = allErrors;
}

public Product getProduct() {
    return product;
}

public void setProduct(Product product) {
    this.product = product;
}

}