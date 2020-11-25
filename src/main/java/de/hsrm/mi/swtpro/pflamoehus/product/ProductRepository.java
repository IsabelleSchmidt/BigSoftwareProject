package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Product findById (Integer id);
    Product findByName (String name);

    List<Product> findByProductType (String type);
    List<Product> findByRoomType (String roomtype);
    List<Product> findByPrice (Integer price);
    List<Product> findByTagsIn(ArrayList<String> tags);
    List<Product> findByProductSizeIn(ArrayList<Double> productSize);
    //For availability, all products should be counted by name and type
    int countByNameAndProductType(String name, String type); 

}
