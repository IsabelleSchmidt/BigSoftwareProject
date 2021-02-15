package de.hsrm.mi.swtpro.pflamoehus.product.productservice;

import java.util.List;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
/**
 * ProductService for different operations to apply on the products.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 1
 */
public interface ProductService {

    /**
     * @return list of all products saved in the database
     */
    List<Product> allProducts();

    /**
     * Finds the product by its given articlenr.
     * 
     * @param articleNr wanteed articlenr
     * @return optional of type product
     */
    Product searchProductwithArticleNr(long articleNr);

    /**
     * To edit and save a given (new) product.
     * 
     * @param editedProduct given product
     * @return product
     */
    Product editProduct(Product editedProduct);

    /**
     * Deleting a product by its given id.
     * 
     * @param id productid that has to be deleted
     */
    void deleteProduct(long id);

    /**
     * Find all products with a certain type of product.
     * 
     * @param type wanted producttype
     * @return list of products
     */
    List<Product> findAllProductsWithProductType(ProductType type);

    /**
     * @param name productname
     * @return product with given name
     */
    Product findProductWithName(String name);

}
