package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Product findById (Integer id);
    Product findByName (String name);
    List<Product> findByProductType (String type);
    List<Product> findByRoomType (String roomtype);
    
}
