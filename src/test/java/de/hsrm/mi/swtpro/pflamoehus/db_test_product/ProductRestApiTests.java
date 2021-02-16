package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.InputStream;
import java.util.List;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.OrderDetailsRepository;
import de.hsrm.mi.swtpro.pflamoehus.order.orderdetails.orderdetailsservice.OrderDetailsService;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.pictureservice.PictureService;
import de.hsrm.mi.swtpro.pflamoehus.product.productapi.ProductRestApi;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagRepository;

@SpringBootTest(webEnvironment =WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
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

    private final String PRODUCTNAME = "Olloko";
    private final ProductType PRODUCTTYPE = ProductType.SINK;
    private final RoomType ROOMTYPE = RoomType.BATHROOM;
    private final String DESCRIPTION = "Verruecktes Waschbecken";
    private final String INFORMATION = "Der Wasserhahn laesst Harn herab";
    private final double PRICE = 120.0;
    private final double HIGHT = 20.0;
    private final double WIDTH = 80.0;
    private final double DEPTH = 60.0;
    private final int AVAILABLE = 90;

    @BeforeAll
    public void clear(){
        clearRepos();
    }

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


    
    @Test
    @Transactional
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName(" GET api/product/tags returns a list with all tags in the database")
    public void get_tags_returns_list() throws Exception {
        MvcResult result = mockmvc.perform(get(PATH + "/tags/")).andExpect(status().isOk()).andReturn();
        // Use ObjectMapper to create object from JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Tag> response = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Tag>>() {
                });
        assertEquals(response.size(), tagRepo.count());
    }

    @Test
    @Transactional
    @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName(" GET api/product/picture/{piId} returns byte Array of Picture")
    public void get_picture_returns_bytearray() throws Exception {

        List<Picture> allPics = pictureRepo.findAll();
        for (Picture picture : allPics) {
            MvcResult result = mockmvc.perform(get(PATH + "/picture/" + picture.getId())).andExpect(status().isOk())
                    .andReturn();
            byte[] response = result.getResponse().getContentAsByteArray();

            InputStream in = new ClassPathResource(
                    "/static" + pictureService.findPictureWithID(picture.getId()).getPath()).getInputStream();
            byte[] bytes = IOUtils.toByteArray(in);
            assertArrayEquals(response, bytes);
        }
    }

    @Test
    @Transactional
    @DisplayName(" POST api/product/new ")
    public void post_product() throws Exception {

        Product request = new Product();
        request.setName(PRODUCTNAME);
        request.setProductType(PRODUCTTYPE);
        request.setRoomType(ROOMTYPE);
        request.setPrice(PRICE);
        request.setInformation(INFORMATION);
        request.setDescription(DESCRIPTION);
        request.setHeight(HIGHT);
        request.setWidth(WIDTH);
        request.setDepth(DEPTH);
        request.setAvailable(AVAILABLE);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);

        mockmvc.perform(post(PATH+"/product/new").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson)).andExpect(status().isOk()).andReturn();

        assertEquals(1, productRepo.count());

    }

    // @Test
    // @Transactional
    // @Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    // @DisplayName("POST api/product/{articleNr}/newpicture")
    // public void post_picture() throws Exception{

    //     List<Product> allProducts = productRepo.findAll();

    //     InputStream in = new ClassPathResource(FILE).getInputStream();
    //     MultipartFile multiFile = new MockMultipartFile(FILE, in);
    //     // MultipartFile[] request = null;

    //     for(Product product : allProducts){

    //         mockmvc.perform(post(PATH+"/"+product.getArticlenr()+"/newpicture").contentType(MediaType.MULTIPART_FORM_DATA_VALUE).content(multiFile.getBytes())).andExpect(status().isOk()).andReturn();

    //     }

        



        
    // }




}
