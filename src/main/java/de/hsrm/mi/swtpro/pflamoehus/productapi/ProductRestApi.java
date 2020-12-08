package de.hsrm.mi.swtpro.pflamoehus.productapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
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

    @GetMapping("product/{articleNr}")
    public Product getProductWithID(@PathVariable long articleNr) {
        Optional<Product> found = productService.searchProductwithArticleNr(articleNr);
        return found.isEmpty() ? null : found.get();

    }

    @DeleteMapping("product/{articleNr}")
    public void deleteProductWithArticleNr(@PathVariable long articleNr) {
        productService.deleteProduct(articleNr);

    }

    @PostMapping("product/new")
    public Product postNewProduct(@RequestParam String name, @RequestParam double width, @RequestParam double height,
            @RequestParam double depth,
            @RequestParam int available, @RequestParam String picture, @RequestParam String roomType,
            @RequestParam String productType) {

        Product newProduct = new Product();
        return newProduct;

    }

}
