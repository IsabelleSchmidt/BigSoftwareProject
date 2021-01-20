package de.hsrm.mi.swtpro.pflamoehus.product.productapi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;


/*
 * ProductRestApi for communication between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductRestApi {

    @Autowired
    ProductService productService;

    Logger productRestApiLogger = LoggerFactory.getLogger(ProductRestApi.class);

    /**
     * Return a list of all products in the database.
     * 
     * @return list of products
     */
    @GetMapping("/products")
    public List<Product> allProducts() {
        return productService.allProducts();
    }

    /**
     * Return the product with the given id.
     * 
     * @param articleNr given articlenr
     * @return product
     */
    @GetMapping("/product/{articleNr}")
    public Product getProductWithID(@PathVariable long articleNr) {
        Product product = null;
        try{
             product = productService.searchProductwithArticleNr(articleNr);
        }catch(ProductServiceException pse){
            return product;
        }
       
        return product;

    }

    /**
     * Delete a product with the given id.
     * 
     * @param articleNr product that should get deleted
     * @return true or false
     */
    @DeleteMapping(value="/product/{articleNr}")
    public boolean deleteProductWithArticleNr(@PathVariable long articleNr) {
        try{
            productService.deleteProduct(articleNr);
        }catch(ProductServiceException pse){
            return false;
        }
        
        return true;
    }


    /**
     * Get all pictures of an product.
     * 
     * @param articleNr articlenr of the wanted product
     * @return all pictures
     */
    @GetMapping("/{articleNr}/pictures")
    public Set<Picture> getAllPicturesOfAProduct(@PathVariable long articleNr) {
        Product found;
       
        try {
             found = productService.searchProductwithArticleNr(articleNr);

        } catch (ProductServiceException pae) {
            productRestApiLogger.error("Failed to get the pictures.");
            return new HashSet<>();
        }
        return found.getAllPictures();
    }

    
  


}
