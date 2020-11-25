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


    @Override
    public List<Product> allProducts() {

        return productRepo.findAll();
    }

    
    @Override
    public Optional<Product> searchProductwithId(long id) {
        Optional<Product> opt = productRepo.findById(id);
        return opt.isEmpty() ?  null :  opt;
    }

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

    @Override
    public void deleteProduct(long id) {
        Optional<Product> opt = productRepo.findById(id);
        if(opt.isEmpty()){
            productServiceLogger.info("Product was not deleted, id not found");
        }else{
            productRepo.delete(opt.get());
        }

    }

    @Override
    public String countAvailableProducts(String name, String productType) {
        int nrAvailable = productRepo.countByNameAndProductType(name, productType);
        if (nrAvailable == 0){
            return "EMPTY";
        }
        return (nrAvailable >0 && nrAvailable < 20) ?  "MEDIUM": "FULL";
    }


    
}
