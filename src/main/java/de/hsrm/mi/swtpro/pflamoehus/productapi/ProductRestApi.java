package de.hsrm.mi.swtpro.pflamoehus.productapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.productService.ProductService;

@RestController
@RequestMapping("/api")
public class ProductRestApi {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> allProducts(){
        return productService.allProducts();
    }
    
    
}
