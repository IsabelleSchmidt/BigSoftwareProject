package de.hsrm.mi.swtpro.pflamoehus.product.picture.pictureservice;

import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;

/**
 * PictureService for different operations to apply on the pictures.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public interface PictureService {
    
    /**
     * To edit and save a given (new) picture.
     * 
     * @param editedPicture given picture
     * @return picture
     */
    Picture editPicture(Picture editedPicture);

    /**
     * @return all pictures found in the database
     */
    public List<Picture> findAll();

    /**
     * @param id wanted id
     * @return the picture with the given id
     */
    public Picture findPictureWithID(long id);

    /**
     * @param product wanted product
     * 
     * @return all pictures with the given product
     */
    public List<Picture> findPicturesWithProduct(Product product);

    /**
     * Filters the list for images that contain the relative path
     * 
     * @param path wanted path
     * @return list of pictures
     */
    public List<Picture> findAllWithPath(String path);

    /**
     * Deletes a given picture from the database
     * @param picture picture to be deleted
     */
    public void deletePicture(Picture picture);

}
