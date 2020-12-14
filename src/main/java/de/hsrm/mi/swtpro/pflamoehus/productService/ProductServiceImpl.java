package de.hsrm.mi.swtpro.pflamoehus.productService;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.ProductApiException;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepo;
    Logger productServiceLogger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * @return a list of all products
     */
    @Override
    public List<Product> allProducts() {

        return productRepo.findAll();
    }

    /**
     * @param articleNr unique key/identifier of the Product
     * @return a product if the id is found, null otherwise
     */
    @Override
    public Optional<Product> searchProductwithArticleNr(long articleNr) {
        Optional<Product> product = productRepo.findById(articleNr);
        if (product.isEmpty()) {
            throw new ProductApiException("Product is not in the database.");
        }
        return product;
    }

    /**
     * saves the edited product in the database
     * 
     * @param editedProduct product object that has been edited
     * @return the edited, saved product if the saving process was successful,
     *         otherwise null
     */
    @Override
    public Product editProduct(Product editedProduct) {
        try {
            editedProduct = productRepo.save(editedProduct);
        } catch (OptimisticLockException oLE) {
            productServiceLogger.error("Products can only be edited by one person at a time.");
            // TODO: productserviceexception werfen
        }
        return editedProduct;
    }

    /**
     * Deletes the product with the given id in the database
     */
    @Override
    public void deleteProduct(long id) {
        Optional<Product> opt = productRepo.findById(id);
        if (!opt.isPresent()) {
            productServiceLogger.info("Product was not deleted, articleNr not found");
            throw new ProductApiException("Product could not be deleted. Product wasn't found in the database.");
        } else {
            productRepo.delete(opt.get());
        }

    }

    @Override
    public List<Product> findAllProductsWithProductType(ProductType type) {
        // TODO Auto-generated method stub
        return productRepo.findByProductType(type.toString());
    }

 





    
}
