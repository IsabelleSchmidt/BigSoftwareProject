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
     * 
     * @param articleNr
     * @return 
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

}
