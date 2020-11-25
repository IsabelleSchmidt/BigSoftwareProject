package de.hsrm.mi.swtpro.pflamoehus.productService;

import java.util.List;
import java.util.Optional;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;

public interface ProductService {

   
     
    /**
     * @return a list of all products 
     */
    List<Product> allProducts();

   
    /**
     * @param id unique key/identifier of the Product
     * @return a product if the id is found, null otherwise
     */
    Optional<Product> searchProductwithArticleNr(long articleNr);


    /**
     * saves the edited product in the database
     * @param editedProduct product object that has been edited 
     * @return the edited, saved product if the saving process was successful, otherwise null
     */
    Product editProduct(Product editedProduct);


    /**
     * Deletes the product with the given id in the database
     */
    void deleteProduct(long id);
    
    /**
     * Counts the available products of the given name and type
     * @param name the product's name
     * @param productType the product's type (e.g chair, table)
     * @return the status of number of available products 
     */

}
