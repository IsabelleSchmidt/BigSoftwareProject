package de.hsrm.mi.swtpro.pflamoehus.productapi;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.ProductApiException;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.productService.ProductService;

/*
 * ProductRestApi for communication between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
 */
@RestController
@RequestMapping("/api")
public class ProductRestApi {

    @Autowired
    ProductService productService;

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
        Optional<Product> found = productService.searchProductwithArticleNr(articleNr);
        return found.isEmpty() ? null : found.get();

    }

    /**
     * Delete a product with the given id.
     * 
     * @param articleNr product that should get deleted
     */
    @DeleteMapping("/product/{articleNr}")
    public void deleteProductWithArticleNr(@PathVariable long articleNr) {
        productService.deleteProduct(articleNr);

    }

    /**
     * Create new product.
     * 
     * @param newProduct the new product that has du get saved
     * @return new product
     */
    @PostMapping("/product/new")
    public Product postNewProduct(@RequestBody Product newProduct) {
        // TODO: bratenserviceexception fangen, wenn wir eine haben
        return productService.editProduct(newProduct);

    }

    /**
     * Get all pictures of an product.
     * 
     * @param articleNr articlenr of the wanted product
     * @return all pictures
     */
    @GetMapping("/{articleNr}/pictures")
    public Set<Picture> getAllPicturesOfAProduct(@PathVariable long articleNr) {
        Set<Picture> allPictures = null;
        try {
            if (productService.searchProductwithArticleNr(articleNr).isPresent()) {
                allPictures = productService.searchProductwithArticleNr(articleNr).get().getAllPictures();
            }
        } catch (ProductApiException pae) {
            // TODO: Abgefangene exception per fehlercode mitgeben?
        }
        return allPictures;
    }

}
