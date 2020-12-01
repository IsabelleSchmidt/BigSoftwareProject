package de.hsrm.mi.swtpro.pflamoehus.product;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.tags.Tag;
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Product findByArticlenr (long articlenr);
    Product findByName (String name);
    List<Product> findByProductType (String type);
    List<Product> findByRoomType (String roomtype);
    List<Product> findByPrice (double price);
    List<Product> findByAllTagsIn(HashSet<Tag> tags);
    List<Product> findByHeight(double height);
    List<Product> findByWidth(double width);
    List<Product> findByDepth(double depth);
    List<Product> findByHeightAndWidthAndDepth(double height, double width, double depth);
    String findNameByArticlenr(long articlenr);
}
