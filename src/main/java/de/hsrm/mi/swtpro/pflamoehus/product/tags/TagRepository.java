package de.hsrm.mi.swtpro.pflamoehus.product.tags;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TagRepository for different operations to apply on the database.
 * 
 * @author Svenja Schenk, Marie Scharhag
 * @version 2
 */
 public interface TagRepository extends JpaRepository<Tag,Long> {
    
    /**
     * Find tag with a certain value.
     * 
     * @param value wanted value
     * @return tag
     */
    Optional<Tag> findByValue(String value);

    /**
     * Find tag with given id.
     * 
     * @param id wanted id
     * @return tag
     */
    Optional<Tag> findById(long id);
}
