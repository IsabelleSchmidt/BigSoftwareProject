package de.hsrm.mi.swtpro.pflamoehus.productService;

import java.util.List;
import java.util.Optional;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;

public interface ProductService {

    List<Product> allProducts();
    Optional<Product> searchProductwithArticleNr(long articleNr);
    Product editProduct(Product editedProduct);
    void deleteProduct(long id);
    List<Product> findAllProductsWithProductType(ProductType type);

}
