package de.hsrm.mi.swtpro.pflamoehus.product.pictureService;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.PictureRepository;

@Service


public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;

    /**
     * @return List<Picture> returns all pictures found in the database
     */
    @Override
    public List<Picture> findAll() {
        return pictureRepository.findAll();
    }

    /**
     * @param id
     * @return Picture returns the picture with the given id
     */
    @Override
    public Picture findPictureWithID(long id) {

       Optional< Picture> found = pictureRepository.findById(id);
       
        return found.isPresent() ? found.get():null;

    }

    /**
     * @param product
     * @return List<Picture> return alle pictures with the given product
     */
    @Override
    public List<Picture> findPicturesWithProduct(Product product) {
        return pictureRepository.findByProduct(product);
    }

    /**
     * @param path
     * @return List<Picture> Filters the list for images that contain the relative
     *         path
     */
    @Override
    public List<Picture> findAllWithPath(String path) {
        Predicate<Picture> byRelPath = picture -> picture.getPath().contains(path);
        return findAll().stream().filter(byRelPath).collect(Collectors.toList());
    }

}
