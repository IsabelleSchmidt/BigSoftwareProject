package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;
import de.hsrm.mi.swtpro.pflamoehus.product.productapi.ProductRestApi;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductRestApiTests {
    

    @Autowired ProductRestApi productController;
    @Autowired ProductService productService;
    @Autowired ProductRepository productRepo;
    @Autowired PictureRepository pictureRepo;
    @Autowired TagRepository tagRepo;
    @Autowired MockMvc mockmvc;

    private final String PATH = "/api/product/";
    
    @Test
    public void basecheck(){
        assertThat(productController).isNotNull();
        assertThat(mockmvc).isNotNull();
        assertThat(tagRepo).isNotNull();
        assertThat(pictureRepo).isNotNull();
        assertThat(productService).isNotNull();

        assertThat(productRepo.count()).isGreaterThan(0);
        assertThat(pictureRepo.count()).isGreaterThan(0);
    }

    //get (/products)
    @Test
    @Transactional
    @DisplayName("GET /api/products returns a list containing all products in the database.")
    public void api_product_return_list() throws Exception{
       MvcResult result =  mockmvc.perform(get(PATH+"/products/")).andExpect(status().isOk()).andReturn();
          //Use ObjectMapper to create object from JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Product> response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
        assertEquals(response.size(),productRepo.count());
    }

    //get /product/{articlenr}

    @Test
    @Transactional
    @DisplayName("GET /api/product/product/{articlenr} returns a  product.")
    public void api_product_return_product() throws Exception{

        for(Product product : productRepo.findAll()){

    
            MvcResult result =  mockmvc.perform(get(PATH+"product/"+product.getArticlenr())).andExpect(status().isOk()).andReturn();
            //Use ObjectMapper to create object from JSON
            ObjectMapper mapper = new ObjectMapper();
            Product response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Product>() {});
            assertEquals(response.getArticlenr(),product.getArticlenr());    
        }
    }
    
    //delete /product/{articlenr}
    @Test
    @DisplayName("DELETE /api/product/product/{articlenr} deletes a  product.")
    @Transactional
    public void api_product_delete_product() throws Exception{
        
        long allproductNRs = productRepo.count();
        List<Product> allProducts = productRepo.findAll();

        for(Product product : allProducts){

            mockmvc.perform(delete(PATH+"product/"+product.getArticlenr())).andExpect(status().isOk()).andReturn();
            //Use ObjectMapper to create object from JSON
           allproductNRs-=1;
           assertEquals(allproductNRs, productRepo.count());
           

        }
    }

   

    @Test
    @Transactional
    @DisplayName(" GET api/product/all/roomtypes and all/producttypes return a Map<string,string>")
    public void get_roomtypes_producttypes_returns_map() throws Exception{
        String response = mockmvc.perform(get(PATH+"all/roomtypes")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        for(RoomType type : RoomType.values()){
            if(type == RoomType.KITCHEN){
                //due to the 'Ü' in 'Küche' the string is weird
                continue;
            }
            assertThat(response).contains(type.toString());
        }
        
        response = mockmvc.perform(get(PATH+"all/producttypes")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        for(ProductType product : ProductType.values()){
            assertThat(response).contains(product.toString());
        }
   
    }
}
