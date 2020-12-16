package de.hsrm.mi.swtpro.pflamoehus.product.picture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findById(long id);

    List<Picture> findByProduct(Product product);
   
  
}
