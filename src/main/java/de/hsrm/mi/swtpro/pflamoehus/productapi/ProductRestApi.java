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

@RestController
@RequestMapping("/api")
public class ProductRestApi {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> allProducts() {
        return productService.allProducts();
    }

    @GetMapping("/product/{articleNr}")
    public Product getProductWithID(@PathVariable long articleNr) {
        Optional<Product> found = productService.searchProductwithArticleNr(articleNr);
        return found.isEmpty() ? null : found.get();

    }

    @DeleteMapping("/product/{articleNr}")
    public void deleteProductWithArticleNr(@PathVariable long articleNr) {
        productService.deleteProduct(articleNr);

    }

    @PostMapping("/product/new")
    public Product postNewProduct(@RequestBody Product newProduct) {
        //TODO bratenserviceexception fangen, wenn wir eine haben
        return productService.editProduct(newProduct);

    }

    @GetMapping("/{articleNr}/pictures")
    public Set<Picture> getAllPicturesOfAProduct(@PathVariable long articleNr ){
         Set<Picture> allPictures = null; 
        try{
         allPictures = productService.searchProductwithArticleNr(articleNr).get().getAllPictures();
        }catch(ProductApiException pae){
            //TODO: Abgefangene exception per fehlercode mitgeben? 
        }
        return allPictures;
    }




}
