package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;

import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductRepoTests {

    final String TESTNAME = "Herbert";
    final ProductType PRODUCTTYPE = ProductType.COUCH;
    final RoomType ROOMTYPE = RoomType.BATHROOM;
    final double PRICE = 10.5;
    final double HEIGHT = 75.0;
    final double WIDHT = 210.5;
    final double DEPTH = 55.0;
    final int AVIABLEPRODUCTS = 3;
    final String DESCRIPTION = "beschreibung";
    final String INFORMATION = "information";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PictureRepository picutreRepository;

    @Autowired
    private OrderDetailsRepository orderRepository;

    @BeforeEach
    void clear_repos() {
        picutreRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void basiccheck() {
        assertThat(ProductRepository.class).isInterface();
        assertThat(productRepository).isNotNull();
    }

    @Test
    @DisplayName("Persist product entity (empty table)")
    void product_persist() {
        final Product unmanaged = new Product();
        unmanaged.setName(TESTNAME);
        unmanaged.setDepth(DEPTH);
        unmanaged.setHeight(HEIGHT);
        unmanaged.setWidth(WIDHT);
        unmanaged.setAvailable(AVIABLEPRODUCTS);
        unmanaged.setProductType(PRODUCTTYPE);
        unmanaged.setRoomType(ROOMTYPE);
        unmanaged.setPrice(PRICE);
        unmanaged.setDescription(DESCRIPTION);
        unmanaged.setInformation(INFORMATION);

        final Product managed = productRepository.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(productRepository.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("Duplicate product names are forbidden")
    void product_name_unique() {
        final Product product1 = new Product();
        product1.setName(TESTNAME);
        product1.setDepth(DEPTH);
        product1.setHeight(HEIGHT);
        product1.setWidth(WIDHT);
        product1.setAvailable(AVIABLEPRODUCTS);
        product1.setProductType(PRODUCTTYPE);
        product1.setRoomType(ROOMTYPE);
        product1.setPrice(PRICE);
        product1.setDescription(DESCRIPTION);
        product1.setInformation(INFORMATION);

        final Product managed1 = productRepository.save(product1);
        assertThat(managed1).isEqualTo(product1);

        final Product product2 = new Product();
        product2.setName(TESTNAME);
        product2.setDepth(DEPTH);
        product2.setHeight(HEIGHT);
        product2.setWidth(WIDHT);
        product2.setAvailable(AVIABLEPRODUCTS);
        product2.setProductType(PRODUCTTYPE);
        product2.setRoomType(ROOMTYPE);
        product2.setPrice(PRICE);
        product2.setDescription(DESCRIPTION);
        product2.setInformation(INFORMATION);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            productRepository.save(product2);
        });

        assertThat(productRepository.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("ProductRepository findByName")
    void product_name_findByName() {
        final int COUNT = 5;

        for (int i = 0; i < COUNT; i++) {
            final Product product1 = new Product();
            product1.setName(TESTNAME + i);
            product1.setDepth(DEPTH + i);
            product1.setHeight(HEIGHT + i);
            product1.setWidth(WIDHT + i);
            product1.setAvailable(AVIABLEPRODUCTS + i);
            product1.setProductType(PRODUCTTYPE);
            product1.setRoomType(ROOMTYPE);
            product1.setPrice(PRICE + i);
            product1.setDescription(DESCRIPTION + i);
            product1.setInformation(INFORMATION + i);
            productRepository.save(product1);
        }

        assertThat(productRepository.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            Product fund = productRepository.findByName(TESTNAME + i).get();
            assertThat(fund.getDepth()).isEqualTo(DEPTH + i);
            assertThat(fund.getPrice()).isEqualTo(PRICE + i);
        }
    }

    @Test
    @DisplayName("ProductRepository findByRoomType")
    void product_roomType_findByRoomtype() {

        final Product product1 = new Product();
        product1.setName(TESTNAME);
        product1.setDepth(DEPTH);
        product1.setHeight(HEIGHT);
        product1.setWidth(WIDHT);
        product1.setAvailable(AVIABLEPRODUCTS);
        product1.setProductType(PRODUCTTYPE);
        product1.setRoomType(ROOMTYPE);
        product1.setPrice(PRICE);
        product1.setDescription(DESCRIPTION);
        product1.setInformation(INFORMATION);

        final Product managed1 = productRepository.save(product1);
        assertThat(managed1).isEqualTo(product1);

        final Product product2 = new Product();
        product2.setName("Otto");
        product2.setDepth(DEPTH);
        product2.setHeight(HEIGHT);
        product2.setWidth(WIDHT);
        product2.setAvailable(AVIABLEPRODUCTS);
        product2.setProductType(PRODUCTTYPE);
        product2.setRoomType(ROOMTYPE);
        product2.setPrice(PRICE);
        product2.setDescription(DESCRIPTION);
        product2.setInformation(INFORMATION);

        final Product managed2 = productRepository.save(product2);
        assertThat(managed2).isEqualTo(product2);

        List<Product> fund = productRepository.findByRoomType(ROOMTYPE);
        assertThat(fund.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("ProductRepository findBy...")
    void product_findBy() {

        final Product product1 = new Product();
        product1.setName(TESTNAME);
        product1.setDepth(DEPTH);
        product1.setHeight(HEIGHT);
        product1.setWidth(WIDHT);
        product1.setAvailable(AVIABLEPRODUCTS);
        product1.setProductType(PRODUCTTYPE);
        product1.setRoomType(ROOMTYPE);
        product1.setPrice(PRICE);
        product1.setDescription(DESCRIPTION);
        product1.setInformation(INFORMATION);

        final Product managed1 = productRepository.save(product1);
        assertThat(managed1).isEqualTo(product1);

        final Product product2 = new Product();
        product2.setName("Otto");
        product2.setDepth(DEPTH);
        product2.setHeight(HEIGHT);
        product2.setWidth(WIDHT);
        product2.setAvailable(AVIABLEPRODUCTS);
        product2.setProductType(ProductType.PLANT);
        product2.setRoomType(ROOMTYPE);
        product2.setPrice(123.4);
        product2.setDescription(DESCRIPTION);
        product2.setInformation(INFORMATION);

        final Product managed2 = productRepository.save(product2);
        assertThat(managed2).isEqualTo(product2);

        List<Product> fund = productRepository.findByProductType(ProductType.COUCH);
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
