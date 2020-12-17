package de.hsrm.mi.swtpro.pflamoehus.tags;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * TagRepository for different operations to apply on the database.
 * 
 * @author Svenja Schenk
 * @version 1
 */
 public interface TagRepository extends JpaRepository<Tag,Long> {
    
    /**
     * Find tag with a certain value.
     * 
     * @param value wanted value
     * @return tag
     */
    Tag findByValue(String value);

    /**
     * Find tag with given id.
     * 
     * @param id wanted id
     * @return tag
     */
    Tag findById(long id);
}
