package de.hsrm.mi.swtpro.pflamoehus.product.productapi;

import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.ProductServiceException;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.PictureResponse;
import de.hsrm.mi.swtpro.pflamoehus.payload.response.ProductResponse;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.pictureservice.PictureService;
import de.hsrm.mi.swtpro.pflamoehus.product.productservice.ProductService;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;
import de.hsrm.mi.swtpro.pflamoehus.product.tags.TagService;

/**
 * ProductRestApi for communication between front- and backend.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian, Marie Scharhag
 * @version 5
 */
@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductRestApi {
  
    @Autowired
    ProductService productService;

    @Autowired
    PictureService pictureService;

    @Autowired
    TagService tagService;


    Logger LOGGER = LoggerFactory.getLogger(ProductRestApi.class);

    /**
     * Return a list of all products in the database.
     * 
     * @return list of products
     */
    @GetMapping("/products")
    public List<Product> allProducts() {
        return productService.allProducts();
    }

    /**
     * Return a List of all tags in the Database
     * 
     * @return list of Tags
     */
    @GetMapping("/tags")
    public List<Tag> allTags(){
        return tagService.allTags();
    }

    /**
     * Return the product with the given id.
     * 
     * @param articleNr given articlenr
     * @return product
     */
    @GetMapping("/product/{articleNr}")
    public Product getProductWithID(@PathVariable long articleNr) {
        Product found = null;
        try {
            found = productService.searchProductwithArticleNr(articleNr);
        } catch (ProductServiceException pse) {
            LOGGER.error(pse.getMessage());
        }

        return found;

    }

    /**
     * Delete a product with the given id.
     * 
     * @param articleNr product that should get deleted
     */
    @DeleteMapping("/product/{articleNr}")
    public boolean deleteProductWithArticleNr(@PathVariable long articleNr) {
        try {
            productService.deleteProduct(articleNr);
        } catch (ProductServiceException pse) {
            LOGGER.error(pse.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Create new product.
     * 
     * @param newProduct the new product that has du get saved
     * @return ProductResponse with Errormessages and Product
     */
    @PostMapping(value = "/product/new")
    public ResponseEntity<ProductResponse> postNewProduct(@Valid @RequestBody Product newProduct,
            BindingResult result) {

        System.out.print("NeuesProduct "+newProduct);
        LOGGER.info("Neues Produkt erhalten!");
        Product product = null;
        ProductResponse response = new ProductResponse(product);


        if (productService.findProductWithName(newProduct.getName()) != null) {
            response.addErrormessage(new Errormessage("name", "Produktname bereits vergeben."));
            return ResponseEntity.ok().body(response);
        }

        if (result.hasErrors()) {

            LOGGER.info("Validationsfehler");

            for (FieldError error : result.getFieldErrors()) {
                response.addErrormessage(new Errormessage(error.getField(), error.getDefaultMessage()));
            }    

            return ResponseEntity.ok().body(response);

        } else {
            try {
                product = productService.editProduct(newProduct);
                response.setProduct(product);

            } catch (ProductServiceException pse) {
                LOGGER.error("Failed to save the product.");
                response.addErrormessage(new Errormessage(null, "SAVING_ERROR"));
                response.addErrormessage(new Errormessage("name", "schon vergeben"));
                return ResponseEntity.badRequest().body(response);
            }
            LOGGER.info(product.toString());
            return ResponseEntity.ok().body(response);
        }

    }

    /**
     * Returns Picture with given Id as byte Array
     * 
     * @param picId the Id of the Picture that should be returned
     * @return Picture as byte Array
     */
    @GetMapping(value = "/picture/{picId}", produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getImage(@PathVariable Long picId){
        byte[] bytes = null;
        String home = System.getProperty("user.home");
        String dir = "upload"; 
        Path startPath = Paths.get(home,dir);
        String picturepath = pictureService.findPictureWithID(picId).getPath();
        try {
        if(picturepath.startsWith(startPath.toString())){
            
            File file = new File(picturepath);
            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
                ImageIO.write(bImage, getType(file), bos);
            bytes = bos.toByteArray();
        
        }else{
            try{
                 InputStream in = new ClassPathResource("/static"+picturepath).getInputStream();
                bytes = IOUtils.toByteArray(in);
            }catch(FileNotFoundException fne){
                LOGGER.error("Bild nicht im static Ordner gefunden. ", fne.getMessage());
            }
           
        }
    } catch (IOException e) {
        LOGGER.error(""+e);
    }
        return bytes;
    }

    /**
     * Returns File Type of an File
     * 
     * @param file from which we want to know the fileType
     * @return File Type of the file
     */
    public String getType(File file) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);

        ImageReader reader = iter.next();
        String formatName = reader.getFormatName();
        return formatName;
    }

    /**
     * Save Pictures for new Product
     * 
     * @param articleNr Article Nummber of Product for Pictures
     * @param pictures Array of Pictures that should be saved
     * @return Picture Response with Errormessages
     */
    @PostMapping(value = "/product/{articleNr}/newpicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PictureResponse> postPicturedata(@PathVariable Long articleNr,
            @RequestPart(name = "picture", required = true) MultipartFile[] pictures){
 
        PictureResponse response = new PictureResponse();

        try {
            
            for(var picture:pictures){
                saveImage(articleNr,picture);
            }
            
        }catch (MaxUploadSizeExceededException max){
            response.addErrormessage(new Errormessage("picture","Bild zu gross zum Upload"));
            return ResponseEntity.badRequest().body(response);
        } 

        return ResponseEntity.ok().body(response);
    }

    /**
     * Save single Image
     * 
     * @param articleNr Article Nummber of Product for Pictures
     * @param pictures Picture that should be saved
     * @return true or false for sucsessful save
     */
    private boolean saveImage(long articleNr, MultipartFile picture) {
        try {
            
            String home = System.getProperty("user.home");
            String dir = "upload";
            String productType = productService.searchProductwithArticleNr(articleNr).getProductType().toString().toLowerCase()+"s";
            String filename = picture.getOriginalFilename();

            InputStream inputStream = picture.getInputStream();
            
            Path upload = Paths.get(home,dir);
            Path path = Paths.get(home,dir,productType);
            Path pathPicture = Paths.get(home,dir,productType,filename);

            if(!Files.exists(upload)){
                new File(upload.toString()).mkdir();
            }
            if(Files.exists(path)){
                if(!Files.exists(pathPicture)){
                    Files.copy(inputStream, pathPicture);
                }
            }else{
                new File(path.toString()).mkdir();
                Files.copy(inputStream, pathPicture);
            }

            Picture newPicture = new Picture();
            newPicture.setPath(pathPicture.toString());
            newPicture.setProduct(getProductWithID(articleNr));
            pictureService.editPicture(newPicture);

       }catch(FileNotFoundException fnoe){
          LOGGER.error("File not Found "+fnoe.getMessage());
          return false;
       }catch(IOException ioe){
          LOGGER.error("IO "+ioe.getMessage());
          return false;
       }
        return true;
    }
    
            
    /**
     * Returns a map of the RoomType names to RoomType String values in JSON format
     *  @see de.hsrm.mi.swtpro.pflamoehus.product.RoomType
     * @return a map in JSON containing all RoomType names and their string values
     */
    @GetMapping("/all/roomtypes") 
    public HashMap<RoomType,String> getAllRoomTypes(){
        HashMap<RoomType,String> allRoomTypes = new HashMap<>();
        for(RoomType type: RoomType.values()){
           allRoomTypes.put(type, type.toString());
        }
        
        return allRoomTypes;
    
    }

     /**
     * Returns a map of the ProductType names to ProductType String values in JSON format
     *  @see de.hsrm.mi.swtpro.pflamoehus.product.ProductType
     * @return a map in JSON containing all ProductType names and their string values
     */
    @GetMapping("/all/producttypes")
    public Map<ProductType,String> getAllProductTypes(){
        
        HashMap<ProductType,String> allProducttypes = new HashMap<>();
        for(ProductType type: ProductType.values()){
            allProducttypes.put(type, type.toString());
        }
        return allProducttypes;
    }

    
  

           

}
