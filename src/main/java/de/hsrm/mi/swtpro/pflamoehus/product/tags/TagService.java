package de.hsrm.mi.swtpro.pflamoehus.product.tags;

import java.util.List;

/**
 * TagService for different operations to apply on the Tags.
 * 
 * @author Marie Scharhag
 * @version 1
 */
public interface TagService {

    /**
     * @return List of all Tags saved in the database
     */
    List<Tag> allTags();

    /**
     * Finds Tag by its article Number
     * 
     * @param id given articel Number
     * @return Optional of Type Tag 
     */
    Tag searchTagWithId(long id);

    /**
     * Finds Tag by its value
     * 
     * @param value given value of Tag
     * @return Optional of Type Tag
     */
    Tag searchTagWithValue(String value);
    
}
