package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.dao.DataIntegrityViolationException;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductRepoTests {
    
    @LocalServerPort
    private int port;


    final String TESTNAME = "Herbert";
    final String PRODUCTTYPE = "Sofa/Couch";
    final String ROOMTYPE = "Wohnzimmer";
    final double PRICE = 10.5;
    final double HEIGHT = 75.0;
    final double WIDHT = 210.5;
    final double DEPTH = 55.0;
    final int AVIABLEPRODUCTS = 3;
    final String PICTURE = "src/main/resources/static/sofas/sofa1.jpg";

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void basiccheck(){
        assertThat(ProductRepository.class).isInterface();
        assertThat(productRepository).isNotNull();
    }

    @Test
    @DisplayName("Persist product entity (empty table)")    
    public void product_persist(){
        final Product unmanaged = new Product();
        unmanaged.setName(TESTNAME);
        unmanaged.setDepth(DEPTH);
        unmanaged.setHeight(HEIGHT);
        unmanaged.setWidth(WIDHT);
        unmanaged.setNrAvailableItems(AVIABLEPRODUCTS);
        unmanaged.setProductType(PRODUCTTYPE);
        unmanaged.setRoomType(ROOMTYPE);
        unmanaged.setPrice(PRICE);
        unmanaged.setPicture(PICTURE);

        productRepository.deleteAll();
        final Product managed = productRepository.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(productRepository.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("Duplicate product names are forbidden")
    public void product_name_unique(){
        final Product product1 = new Product();
        product1.setName(TESTNAME);
        product1.setDepth(DEPTH);
        product1.setHeight(HEIGHT);
        product1.setWidth(WIDHT);
        product1.setNrAvailableItems(AVIABLEPRODUCTS);
        product1.setProductType(PRODUCTTYPE);
        product1.setRoomType(ROOMTYPE);
        product1.setPrice(PRICE);
        product1.setPicture(PICTURE);

        productRepository.deleteAll();

        final Product managed1 = productRepository.save(product1);
        assertThat(managed1).isEqualTo(product1);   

        final Product product2 = new Product();
        product2.setName(TESTNAME);
        product2.setDepth(DEPTH);
        product2.setHeight(HEIGHT);
        product2.setWidth(WIDHT);
        product2.setNrAvailableItems(AVIABLEPRODUCTS);
        product2.setProductType(PRODUCTTYPE);
        product2.setRoomType(ROOMTYPE);
        product2.setPrice(PRICE);
        product2.setPicture(PICTURE);

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
            Product managed2 = productRepository.save(product2);
            assertThat(managed2).isEqualTo(product2);
        });

        assertThat(productRepository.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("ProductRepository findByName")
    public void product_name_findByName(){
        final int COUNT = 5;

        productRepository.deleteAll();

        for (int i = 0; i < COUNT; i++){
            final Product product1 = new Product();
            product1.setName(TESTNAME+i);
            product1.setDepth(DEPTH+i);
            product1.setHeight(HEIGHT+i);
            product1.setWidth(WIDHT+i);
            product1.setNrAvailableItems(AVIABLEPRODUCTS+i);
            product1.setProductType(PRODUCTTYPE);
            product1.setRoomType(ROOMTYPE);
            product1.setPrice(PRICE+i);
            product1.setPicture(PICTURE);
            productRepository.save(product1);
        }

        assertThat(productRepository.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++){
            Product fund = productRepository.findByName(TESTNAME+i);
            assertThat(fund.getDepth()).isEqualTo(DEPTH+i);
            assertThat(fund.getPrice()).isEqualTo(PRICE+i);
        }
    }

    @Test
    @DisplayName("ProductRepository findByRoomType")
    public void product_roomType_findByRoomtype(){

        productRepository.deleteAll();

        final Product product1 = new Product();
        product1.setName(TESTNAME);
        product1.setDepth(DEPTH);
        product1.setHeight(HEIGHT);
        product1.setWidth(WIDHT);
        product1.setNrAvailableItems(AVIABLEPRODUCTS);
        product1.setProductType(PRODUCTTYPE);
        product1.setRoomType(ROOMTYPE);
        product1.setPrice(PRICE);
        product1.setPicture(PICTURE);

        final Product managed1 = productRepository.save(product1);
        assertThat(managed1).isEqualTo(product1);

        final Product product2 = new Product();
        product2.setName("Otto");
        product2.setDepth(DEPTH);
        product2.setHeight(HEIGHT);
        product2.setWidth(WIDHT);
        product2.setNrAvailableItems(AVIABLEPRODUCTS);
        product2.setProductType(PRODUCTTYPE);
        product2.setRoomType(ROOMTYPE);
        product2.setPrice(PRICE);
        product2.setPicture(PICTURE);

        final Product managed2 = productRepository.save(product2);
        assertThat(managed2).isEqualTo(product2);

        List<Product> fund = productRepository.findByRoomType("Wohnzimmer");
        assertThat(fund.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("ProductRepository findBy...")
    public void product_findBy(){

        productRepository.deleteAll();

        final Product product1 = new Product();
        product1.setName(TESTNAME);
        product1.setDepth(DEPTH);
        product1.setHeight(HEIGHT);
        product1.setWidth(WIDHT);
        product1.setNrAvailableItems(AVIABLEPRODUCTS);
        product1.setProductType(PRODUCTTYPE);
        product1.setRoomType(ROOMTYPE);
        product1.setPrice(PRICE);
        product1.setPicture(PICTURE);

        final Product managed1 = productRepository.save(product1);
        assertThat(managed1).isEqualTo(product1);

        final Product product2 = new Product();
        product2.setName("Otto");
        product2.setDepth(DEPTH);
        product2.setHeight(HEIGHT);
        product2.setWidth(WIDHT);
        product2.setNrAvailableItems(AVIABLEPRODUCTS);
        product2.setProductType("Pflanze");
        product2.setRoomType(ROOMTYPE);
        product2.setPrice(123.4);
        product2.setPicture(PICTURE);

        final Product managed2 = productRepository.save(product2);
        assertThat(managed2).isEqualTo(product2);

        List<Product> fund = productRepository.findByProductType("Sofa/Couch");
        assertThat(fund.size()).isEqualTo(1);

        List<Product> fund2 = productRepository.findByHeight(HEIGHT);
        assertThat(fund2.size()).isEqualTo(2);

        List<Product> fund3 = productRepository.findByWidth(WIDHT);
        assertThat(fund3.size()).isEqualTo(2);

        List<Product> fund4 = productRepository.findByDepth(DEPTH);
        assertThat(fund4.size()).isEqualTo(2);

        List<Product> fund5 = productRepository.findByPrice(PRICE);
        assertThat(fund5.size()).isEqualTo(1);

        List<Product> fund6 = productRepository.findByHeightAndWidthAndDepth(HEIGHT, WIDHT, DEPTH);
        assertThat(fund6.size()).isEqualTo(2);

        

    }

   

 


   

    
}
