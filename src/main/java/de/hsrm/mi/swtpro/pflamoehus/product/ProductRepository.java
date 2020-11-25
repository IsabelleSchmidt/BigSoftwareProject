package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.tags.Tag;



public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Product findByArticlenr (Integer articlenr);
    Product findByName (String name);
    List<Product> findByProductType (String type);
    List<Product> findByRoomType (String roomtype);
    List<Product> findByPrice (Integer price);
     List<Product> findByAllTagsIn(ArrayList<Tag> tags);
    List<Product> findByHeight(double height);
    List<Product> findByWidth(double width);
    List<Product> findByDepth(double depth);
    List<Product> findByHeightAndWidthAndDepth(double height, double width, double depth);
   
}
