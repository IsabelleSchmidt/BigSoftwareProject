package de.hsrm.mi.swtpro.pflamoehus.productService;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;

public class ProductServiceImpl implements ProductService {

    @Autowired ProductRepository productRepo;
    Logger productServiceLogger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /** 
     * @return a list of all products
     */
    @Override
    public List<Product> allProducts() {

        return productRepo.findAll();
    }

    
    
    /**
     * @param id unique key/identifier of the Product
     * @return a product if the id is found, null otherwise
     */
    @Override
    public Optional<Product> searchProductwithId(long id) {
        Optional<Product> opt = productRepo.findById(id);
        return opt.isEmpty() ?  null :  opt;
    }

    
    /**
     * saves the edited product in the database
     * @param editedProduct product object that has been edited 
     * @return the edited, saved product if the saving process was successful, otherwise null
     */
    @Override
    public Product editProduct(Product editedProduct) {
        try{
            productRepo.save(editedProduct);
        }catch(OptimisticLockException oLE){
            oLE.printStackTrace();
            //Hier koennen auch eigene Exceptions geworfen werden
        }
        return editedProduct;
    }

    
    /**
     * Deletes the product with the given id in the database
     */
    @Override
    public void deleteProduct(long id) {
        Optional<Product> opt = productRepo.findById(id);
        if(opt.isEmpty()){
            productServiceLogger.info("Product was not deleted, id not found");
        }else{
            productRepo.delete(opt.get());
        }

    }

    
    /**
     * Counts the available products of the given name and type
     * @param name the product's name
     * @param productType the product's type (e.g chair, table)
     * @return the status of number of available products 
     */
    @Override
    public String countAvailableProducts(String name, String productType) {
        int nrAvailable = productRepo.countByNameAndProductType(name, productType);
        if (nrAvailable == 0){
            return "EMPTY";
        }
        return (nrAvailable >0 && nrAvailable < 20) ?  "MEDIUM": "FULL";
    }



    
}
