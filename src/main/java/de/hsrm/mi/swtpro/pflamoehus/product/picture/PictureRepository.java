package de.hsrm.mi.swtpro.pflamoehus.product.picture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;

/**
 * PictureRepository for different operations to apply on the database.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public interface PictureRepository extends JpaRepository<Picture, Long> {

    /**
     * Finding a picture by its id.
     * 
     * @param id given id
     * @return optional of type picture
     */
    Optional<Picture> findById(long id);

    /**
     * Finding a picture by its product.
     * 
     * @param product given product
     * @return list of picutres
     */
    List<Picture> findByProduct(Product product);

}
