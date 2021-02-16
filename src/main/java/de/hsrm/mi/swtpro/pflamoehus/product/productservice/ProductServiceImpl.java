package de.hsrm.mi.swtpro.pflamoehus.product.productservice;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetails;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.pictureservice.PictureService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;


/**
 * ProductServiceImpl for implementing the interface 'ProductService'.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired ProductRepository productRepo;
    @Autowired PictureService pictureService;
    @Autowired OrderDetailsService orderDetailsService;
    Logger productServiceLogger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * 
     * @return list of all products saved in the database
     */
    @Override
    public List<Product> allProducts() {
        return productRepo.findAll();
    }

    /**
     * Finds the product by its given articlenr.
     * 
     * @param articleNr wanted articlenr
     * @return optional of type product
     */
    @Override
    public Product searchProductwithArticleNr(long articleNr) throws ProductServiceException {
        Optional<Product> product = productRepo.findById(articleNr);
        if (product.isEmpty()) {
            throw new ProductServiceException("Product is not in the database.");
        }
        return product.get();
    }

    /**
     * To edit and save a given (new) product.
     * 
     * @param editedProduct given product that has to be saved
     * @return product
     */
    @Override
    public Product editProduct(Product editedProduct) throws ProductServiceException{
        try {
            editedProduct = productRepo.save(editedProduct);
        } catch (OptimisticLockException oLE) {
            productServiceLogger.error("Products can only be edited by one person at a time.");
            throw new ProductServiceException();
        }
        return editedProduct;
    }

    /**
     * Deleting a product by its given id.
     * 
     * @param id product-id that has to be deleted
     */
    @Override
    @Transactional
    public void deleteProduct(long id) {
        Product product = searchProductwithArticleNr(id);
        Set<Picture> allPictures = product.getAllPictures();
        Set<OrderDetails> allDetails = product.getAllOrderDetails();
        Set<Tag> allTags = product.getAllTags();

            for(Tag tag: allTags){
                tag.getAllProductsWithTag().remove(product);
            }
            for(OrderDetails detail: allDetails){
                orderDetailsService.deleteOrderDetail(detail.getOrderDetailsID());
            }

            for(Picture picture : allPictures){
               picture.setProduct(null); 
            }
            
            productRepo.delete(product);
    }

    

    /**
     * Find all products with a certain type of product.
     * 
     * @param type wanted producttype
     * @return list of products
     */
    @Override
    public List<Product> findAllProductsWithProductType(ProductType type) {
        return productRepo.findByProductType(type);
    }

    @Override
    public Product findProductWithName(String name) {
        Optional<Product> found = productRepo.findByName(name);
        
        return found.isPresent() ? found.get() : null;
    }

}
