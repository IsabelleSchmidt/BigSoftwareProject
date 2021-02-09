package de.hsrm.mi.swtpro.pflamoehus.product.picture.pictureservice;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.PictureServiceException;
import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;

/**
 * PictureServiceImpl for implementing the interface 'PictureService'.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;
    Logger pictureServiceLogger = LoggerFactory.getLogger(PictureServiceImpl.class);


    /**
     * Returns all pictures found in the database.
     * 
     * @return list of pictures
     */
    @Override
    public List<Picture> findAll() {
        return pictureRepository.findAll();
    }

    /**
     * Returns the picture with the given id.
     * 
     * @param id given id
     * @return picture 
     */
    @Override
    public Picture findPictureWithID(long id) {

       Optional< Picture> found = pictureRepository.findById(id);
       
        return found.isPresent() ? found.get():null;

    }

    /**
     * Returns alle pictures with the given product.
     * 
     * @param product given product
     * @return list of pictures
     */
    @Override
    public List<Picture> findPicturesWithProduct(Product product) {
        return pictureRepository.findByProduct(product);
    }

    /**
     * Filters the list for images that contain the relative path.
     * 
     * @param path given path
     * @return list of pictures
     */
    @Override
    public List<Picture> findAllWithPath(String path) {
        Predicate<Picture> byRelPath = picture -> picture.getPath().contains(path);
        return findAll().stream().filter(byRelPath).collect(Collectors.toList());
    }

    /**
     * To edit and save a given (new) picture.
     * 
     * @param editedPicture given picture that has to be saved
     * @return picture
     */
    @Override
    public Picture editPicture(Picture editedPicture) {
        try {
            editedPicture = pictureRepository.save(editedPicture);
        } catch (OptimisticLockException oLE) {
            pictureServiceLogger.error("Pictures can only be edited by one person at a time.");
            throw new PictureServiceException();
        }
        return editedPicture;
    }

    @Override
    public void deletePicture(Picture picture) {
        pictureRepository.delete(picture);

    }

}
