package de.hsrm.mi.swtpro.pflamoehus.product;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;

/*
 * ProductRepository for different operations to apply on the database.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 2
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find a product by its id.
     * 
     * @param articlenr wanted id
     * @return a optional of type product found by an id
     */
    Optional<Product> findByArticlenr(long articlenr);

    /**
     * Find a product by its name.
     * 
     * @param name wanted name
     * @return product
     */
    Optional<Product> findByName(String name);

    /**
     * Find products by their producttype.
     * 
     * @param type wanted producttype
     * @return list of products
     */
    List<Product> findByProductType(ProductType type);

    /**
     * Find products by their roomtype.
     * 
     * @param roomtype wanted roomtype
     * @return list of products
     */
    List<Product> findByRoomType(RoomType roomtype);

    /**
     * Find products by their price.
     * 
     * @param price wanted price
     * @return list of products
     */
    List<Product> findByPrice(double price);

    /**
     * Find products by their tags.
     * 
     * @param tags wanted tags
     * @return list of products
     */
    List<Product> findByAllTagsIn(HashSet<Tag> tags);

    /**
     * Find products by their height.
     * 
     * @param height wanted height
     * @return list of products
     */
    List<Product> findByHeight(double height);

    /**
     * Find products by their width.
     * 
     * @param width wanted width
     * @return list of products
     */
    List<Product> findByWidth(double width);

    /**
     * Find products by their depth.
     * 
     * @param depth wanted depth
     * @return list of products
     */
    List<Product> findByDepth(double depth);

    /**
     * Find products by their height, width and depth.
     * 
     * @param height wanted height
     * @param width wanted width
     * @param depth wanted depth
     * @return list of products
     */
    List<Product> findByHeightAndWidthAndDepth(double height, double width, double depth);

    /**
     * Find the name of an product by its articlenr.
     * 
     * @param articlenr wanted articlenr
     * @return name of an product
     */
    String findNameByArticlenr(long articlenr);
}
