package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.pictureservice.PictureService;
import de.hsrm.mi.swtpro.pflamoehus.product.productapi.ProductRestApi;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagRepository;

@SpringBootTest(webEnvironment =WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProductRestApiTests {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    PictureRepository pictureRepo;
    @Autowired
    TagRepository tagRepo;
    @MockBean
    OrderDetailsService orderDetailsService;
    @MockBean
    OrderDetailsRepository orderDetailsRepo;
    @Autowired
    PictureService pictureService;
    @Autowired
    TagService tagService;
    @Autowired
    ProductRestApi productController;
    @Autowired
    MockMvc mockmvc;

    private final String PATH = "/api/product/";

    @AfterEach
    public void clearRepos() {
        tagRepo.deleteAll();
        pictureRepo.deleteAll();
        productRepo.deleteAll();
    }

    @Test
    public void basecheck() {

        assertThat(productController).isNotNull();
        assertThat(mockmvc).isNotNull();
        assertThat(tagRepo).isNotNull();
        assertThat(pictureRepo).isNotNull();
        assertThat(productService).isNotNull();

        assertThat(productRepo.count()).isGreaterThan(0);
        assertThat(pictureRepo.count()).isGreaterThan(0);

    }

    // get (/products)
    @Test
    @Transactional
    @DisplayName("GET /api/products returns a list containing all products in the database.")
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void api_product_return_list() throws Exception {
        MvcResult result = mockmvc.perform(get(PATH + "/products/")).andExpect(status().isOk()).andReturn();
        // Use ObjectMapper to create object from JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Product> response = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Product>>() {
                });
        assertEquals(response.size(), productRepo.count());
    }

    // get /product/{articlenr}

    @Test
    @Transactional
    @DisplayName("GET /api/product/product/{articlenr} returns a  product.")
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    public void api_product_return_product() throws Exception {

        for (Product product : productRepo.findAll()) {

            MvcResult result = mockmvc.perform(get(PATH + "product/" + product.getArticlenr()))
                    .andExpect(status().isOk()).andReturn();
            // Use ObjectMapper to create object from JSON
            ObjectMapper mapper = new ObjectMapper();
            Product response = mapper.readValue(result.getResponse().getContentAsString(),
                    new TypeReference<Product>() {
                    });
            assertEquals(response.getArticlenr(), product.getArticlenr());
        }
    }

    // delete /product/{articlenr}
    @Test
    @DisplayName("DELETE /api/product/product/{articlenr} deletes a  product.")
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Transactional
    public void api_product_delete_product() throws Exception {

        long allproductNRs = productRepo.count();
        List<Product> allProducts = productRepo.findAll();

        for (Product product : allProducts) {

            mockmvc.perform(delete(PATH + "product/" + product.getArticlenr())).andExpect(status().isOk()).andReturn();
            // Use ObjectMapper to create object from JSON
            allproductNRs -= 1;
            assertEquals(allproductNRs, productRepo.count());

        }
    }

    @Test
    @Transactional
    @DisplayName(" GET api/product/all/roomtypes and all/producttypes return a Map<string,string>")
    public void get_roomtypes_producttypes_returns_map() throws Exception {

        ResultActions response = mockmvc.perform(get(PATH + "all/roomtypes").contentType("application/json"))
                .andExpect(status().isOk());

        for (RoomType type : RoomType.values()) {
            response.andExpect(jsonPath(type.name(),type).value(type.toString()));
        }

        response = mockmvc.perform(get(PATH + "all/producttypes").contentType("application/json")).andExpect(status().isOk());

        for (ProductType product : ProductType.values()) {
            response.andExpect(jsonPath(product.name(),product).value(product.toString()));
        }

    }
}
